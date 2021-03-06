package com.food.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.admin.model.AdmService;
import com.admin.model.AdmVO;
import com.food.model.FooService;
import com.food.model.FooVO;
import com.food_cate.model.FooCatService;

@MultipartConfig()
public class FooServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("insert".equals(action)) {
			
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
			/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				
			//餐點名稱
			String fooName = req.getParameter("fooName");
			if (fooName == null || fooName.trim().isEmpty()) {
				errorMsgs.put("fooName","餐點名稱不可為空白");
			}
			
			//餐點類別編號
			Integer fooCatNo = new Integer(req.getParameter("fooCatNo"));
			
			//餐點圖片
			Part part = req.getPart("fooImg");
			byte[] fooImg = null;
			if (part == null || part.getSize() == 0) {
				errorMsgs.put("fooImg","必須上傳餐點圖片");
			} else {
				InputStream is = part.getInputStream();
				fooImg = new byte[is.available()];
				is.read(fooImg);
			}
			
			//餐點價格
			Integer fooPrice = null;
			try {
				 fooPrice = new Integer(req.getParameter("fooPrice").trim());
				 if (fooPrice < 0) {
					 fooPrice = 0;
					 errorMsgs.put("fooPrice","餐點價格請填大於0的整數.");
				 }
			} catch (NumberFormatException e) {
				fooPrice = 0;
				errorMsgs.put("fooPrice","餐點價格請填整數.");
			}
			
			FooVO fooVO = new FooVO();
			fooVO.setFooName(fooName);
			fooVO.setFooCatNo(fooCatNo);
			fooVO.setFooPrice(fooPrice);
			
			//有錯誤時，轉交回新增頁面
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("fooVO", fooVO);
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/foo/addFoo.jsp");
				failureView.forward(req, res);
				return;
			}
			
			/***************************2.開始新增資料***************************************/
			FooService fooSvc = new FooService();
			fooSvc.addFoo(fooName, fooCatNo, fooImg, fooPrice);
			
			/***************************3.新增完成,準備轉交(Send the Success view)***********/
			String addSuccess = "【  " + fooName + " 】" + "新增成功";
			req.setAttribute("addSuccess", addSuccess);	
			
			String url = "/back-end/foo/listAllFoo.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.put("error",e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/foo/addFoo.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_For_Update".equals(action)) {
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/back-end/foo/listAllFoo.jsp】 或  【/back-end/fooCat/listFoosByFooCatNo.jsp】 或 【/back-end /fooCat/listAllFooCat.jsp】
			
			try {
				/***************************1.接收請求參數****************************************/
				Integer fooNo = new Integer(req.getParameter("fooNo"));
				
				/***************************2.開始查詢資料****************************************/
				FooService fooSvc = new FooService();
				FooVO fooVO = fooSvc.getOneFoo(fooNo);
				
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("fooVO", fooVO);
				String url = "/back-end/foo/updateFoo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/***************************其他可能的錯誤處理************************************/
			} catch (Exception e) {
				errorMsgs.put("error","修改資料取出時失敗:"+e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		
		if ("update".equals(action)) {
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/back-end/foo/listAllFoo.jsp】 或  【/back-end/fooCat/listFoosByFooCatNo.jsp】 或 【/back-end /fooCat/listAllFooCat.jsp】
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
					
				//餐點名稱
				String fooName = req.getParameter("fooName");
				if (fooName == null || fooName.trim().isEmpty()) {
					errorMsgs.put("fooName","餐點名稱不可為空白");
				}
				
				//餐點類別編號
				Integer fooCatNo = new Integer(req.getParameter("fooCatNo"));
				
				//餐點圖片
				Part part = req.getPart("fooImg");
				byte[] fooImg = null;
				
				
				//餐點價格
				Integer fooPrice = null;
				try {
					 fooPrice = new Integer(req.getParameter("fooPrice").trim());
					 if(fooPrice < 0) {
						 fooPrice = 0;
						 errorMsgs.put("fooPrice","餐點價格請填大於0的整數.");
					 }
				} catch (NumberFormatException e) {
					fooPrice = 0;
					errorMsgs.put("fooPrice","餐點價格請填整數.");
				}
				
				Integer fooStatus = new Integer(req.getParameter("fooStatus"));
				Integer fooNo = new Integer(req.getParameter("fooNo"));
				
				FooVO fooVO = new FooVO();
				fooVO.setFooName(fooName);
				fooVO.setFooCatNo(fooCatNo);
				fooVO.setFooPrice(fooPrice);
				fooVO.setFooNo(fooNo);
				fooVO.setFooStatus(fooStatus);
				
				//有錯誤時，轉交回修改頁面
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("fooVO", fooVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/foo/updateFoo.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始修改資料***************************************/
				FooService fooSvc = new FooService();
				
				if (part.getSize() != 0) {
					InputStream is = part.getInputStream();
					fooImg = new byte[is.available()];
					is.read(fooImg);
					fooVO = fooSvc.updateFoo(fooNo, fooName, fooCatNo, fooImg, fooPrice, fooStatus);
				} else {
					fooVO = fooSvc.updateFooNoImg(fooNo, fooName, fooCatNo, fooPrice, fooStatus);
				}
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				FooCatService fooCatSvc = new FooCatService();
				if(requestURL.equals("/back-end/fooCat/listFoosByFooCatNo.jsp") || requestURL.equals("/back-end/fooCat/listAllFooCat.jsp")) {
					req.setAttribute("listFoos_ByFooCatNo",fooCatSvc.getFoosByFooCatNo(fooCatNo)); // 資料庫取出的Set物件,存入request
				} else if (requestURL.equals("/back-end/foo/listFoosByFooStatus.jsp")){
					req.setAttribute("listFoos_ByFooStatus",fooSvc.getFoosByFooStatus(fooStatus));
				}
				
				String updateSuccess = "【  " + fooName + " 】" + "修改成功";
				req.setAttribute("updateSuccess", updateSuccess);
				
				if(requestURL.equals("/back-end/foo/listFood_ByCompositeQuery.jsp")){
					HttpSession session = req.getSession();
					Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
					List<FooVO> list  = fooSvc.getAll(map);
					req.setAttribute("listFoods_ByCompositeQuery",list); //  複合查詢, 資料庫取出的list物件,存入request
				}
				
				
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllFoo.jsp
				successView.forward(req, res);
				} catch (Exception e) {
					errorMsgs.put("error",e.getMessage());
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/foo/updateFoo.jsp");
					failureView.forward(req, res);
				}
		}
		
		if ("change_Status".equals(action)) {
			LinkedList<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/back-end/foo/listAllFoo.jsp】 或  【/back-end/fooCat/listFoosByFooCatNo.jsp】 或 【/back-end /fooCat/listAllFooCat.jsp】
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				
				Integer fooNo = new Integer(req.getParameter("fooNo"));
				
				/***************************2.開始新增資料***************************************/
				FooService fooSvc = new FooService();
				FooVO fooVO = fooSvc.getOneFoo(fooNo);
				fooSvc.changeFooStatus(fooNo, fooVO.getFooStatus());
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				
				if(requestURL.equals("/back-end/foo/listFood_ByCompositeQuery.jsp")){
					HttpSession session = req.getSession();
					Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
					List<FooVO> list  = fooSvc.getAll(map);
					req.setAttribute("listFoods_ByCompositeQuery",list); //  複合查詢, 資料庫取出的list物件,存入request
				}
				
				
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllFoo.jsp
				successView.forward(req, res);
				} catch (Exception e) {
					errorMsgs.add(e.getMessage());
					RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
				}
		}
		
		if ("listFoods_ByCompositeQuery".equals(action)) {
			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************************/ 
				HttpSession session = req.getSession();
				Map<String, String[]> map = (Map<String,String[]>)session.getAttribute("map");
				if(req.getParameter("whichPage") == null) {
					HashMap<String, String[]> map1 = new HashMap<String, String[]>(req.getParameterMap());
					
					session.setAttribute("map",map1);
					map = map1;
				}
				
				/*************************** 2.開始查詢資料 ***************************************/
				FooService fooSvc = new FooService();
				List<FooVO> list = fooSvc.getAll(map);
				
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("listFoods_ByCompositeQuery", list);
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/foo/listFood_ByCompositeQuery.jsp");
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.put("error",e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/foo/listAllFoo.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
