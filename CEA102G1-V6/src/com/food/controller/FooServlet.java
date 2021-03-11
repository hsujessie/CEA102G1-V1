package com.food.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

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
			
			LinkedList<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
			/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				
			//餐點名稱
			String fooName = req.getParameter("fooName");
			if (fooName == null || fooName.trim().isEmpty()) {
				errorMsgs.add("餐點名稱不可為空白");
			}
			
			//餐點類別編號
			Integer fooCatNo = new Integer(req.getParameter("fooCatNo"));
			
			//餐點簡介
			String fooIntro = req.getParameter("fooIntro");
			if (fooIntro == null || fooIntro.trim().isEmpty()) {
				errorMsgs.add("餐點簡介不可為空白");
			}
			
			//餐點圖片
			Part part = req.getPart("fooImg");
			byte[] fooImg = null;
			if (part == null || part.getSize() == 0) {
				errorMsgs.add("必須上傳餐點圖片");
			} else {
				InputStream is = part.getInputStream();
				fooImg = new byte[is.available()];
				is.read(fooImg);
			}
			
			//餐點價格
			Integer fooPrice = null;
			try {
				 fooPrice = new Integer(req.getParameter("fooPrice").trim());
			} catch (NumberFormatException e) {
				fooPrice = 0;
				errorMsgs.add("餐點價格請填數字.");
			}
			
			FooVO fooVO = new FooVO();
			fooVO.setFooName(fooName);
			fooVO.setFooCatNo(fooCatNo);
			fooVO.setFooIntro(fooIntro);
			fooVO.setFooImg(fooImg);
			fooVO.setFooPrice(fooPrice);
			
			//有錯誤時，轉交回新增頁面
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("fooVO", fooVO); // 含有輸入格式錯誤的empVO物件,也存入req
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/foo/addFoo.jsp");
				failureView.forward(req, res);
				return;
			}
			
			/***************************2.開始新增資料***************************************/
			FooService fooSvc = new FooService();
			fooSvc.addFoo(fooName, fooCatNo, fooIntro, fooImg, fooPrice);
			
			/***************************3.新增完成,準備轉交(Send the Success view)***********/
			String url = "/back-end/foo/listAllFoo.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
			successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/foo/addFoo.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("getOne_For_Display".equals(action)) {
			LinkedList<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String str = req.getParameter("fooNo");
				
				if (str == null || str.trim().isEmpty()) {
					errorMsgs.add("餐點編號不可為空白");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/foo/fooSelectPage.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				Integer fooNo = null;
				try {
					fooNo = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("餐點編號格式不正確");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/foo/fooSelectPage.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始新增資料***************************************/
				FooService fooSvc = new FooService();
				FooVO fooVO = fooSvc.getOneFoo(fooNo);
				
				if (fooVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				req.setAttribute("fooVO", fooVO);
				String url = "/back-end/foo/listOneFoo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);
				} catch (Exception e) {
					errorMsgs.add(e.getMessage());
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/foo/addFoo.jsp");
					failureView.forward(req, res);
				}
			
		}
		
		if ("getOne_For_Update".equals(action)) {
			LinkedList<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/back-end/foo/listAllFoo.jsp】 或  【/back-end/fooCat/listFoosByFooCatNo.jsp】 或 【/back-end /fooCat/listAllFooCat.jsp】
			
			try {
				/***************************1.接收請求參數****************************************/
				Integer fooNo = new Integer(req.getParameter("fooNo"));
				
				/***************************2.開始查詢資料****************************************/
				FooService fooSvc = new FooService();
				FooVO fooVO = fooSvc.getOneFoo(fooNo);
				
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("fooVO", fooVO); // 資料庫取出的fooVO物件,存入req
				String url = "/back-end/foo/updateFoo.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交update_emp_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料取出時失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		
		if ("update".equals(action)) {
			LinkedList<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // 送出修改的來源網頁路徑: 可能為【/back-end/foo/listAllFoo.jsp】 或  【/back-end/fooCat/listFoosByFooCatNo.jsp】 或 【/back-end /fooCat/listAllFooCat.jsp】
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
					
				//餐點名稱
				String fooName = req.getParameter("fooName");
				if (fooName == null || fooName.trim().isEmpty()) {
					errorMsgs.add("餐點名稱不可為空白");
				}
				
				//餐點類別編號
				Integer fooCatNo = new Integer(req.getParameter("fooCatNo"));
				
				//餐點簡介
				String fooIntro = req.getParameter("fooIntro");
				if (fooIntro == null || fooIntro.trim().isEmpty()) {
					errorMsgs.add("餐點簡介不可為空白");
				}
				
				//餐點圖片
				Part part = req.getPart("fooImg");
				byte[] fooImg = null;
				
				
				//餐點價格
				Integer fooPrice = null;
				try {
					 fooPrice = new Integer(req.getParameter("fooPrice").trim());
				} catch (NumberFormatException e) {
					fooPrice = 0;
					errorMsgs.add("餐點價格請填數字.");
				}
				
				Integer fooStatus = new Integer(req.getParameter("fooStatus"));
				Integer fooNo = new Integer(req.getParameter("fooNo"));
				
				FooVO fooVO = new FooVO();
				fooVO.setFooName(fooName);
				fooVO.setFooCatNo(fooCatNo);
				fooVO.setFooIntro(fooIntro);
				fooVO.setFooImg(fooImg);
				fooVO.setFooPrice(fooPrice);
				fooVO.setFooNo(fooNo);
				fooVO.setFooStatus(fooStatus);
				
				//有錯誤時，轉交回修改頁面
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("fooVO", fooVO); // 含有輸入格式錯誤的fooVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/foo/updateFoo.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				FooService fooSvc = new FooService();
				
				if (part.getSize() != 0) {
					InputStream is = part.getInputStream();
					fooImg = new byte[is.available()];
					is.read(fooImg);
					fooVO = fooSvc.updateFoo(fooNo, fooName, fooCatNo, fooIntro, fooImg, fooPrice, fooStatus);
				} else {
					fooVO = fooSvc.updateFooNoImg(fooNo, fooName, fooCatNo, fooIntro, fooPrice, fooStatus);
				}
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				FooCatService fooCatSvc = new FooCatService();
				if(requestURL.equals("/back-end/fooCat/listFoosByFooCatNo.jsp") || requestURL.equals("/back-end/fooCat/listAllFooCat.jsp")) {
					req.setAttribute("listFoos_ByFooCatNo",fooCatSvc.getFoosByFooCatNo(fooCatNo)); // 資料庫取出的Set物件,存入request
				} else if (requestURL.equals("/back-end/foo/listFoosByFooStatus.jsp")){
					req.setAttribute("listFoos_ByFooStatus",fooSvc.getFoosByFooStatus(fooStatus));
				}
				
				
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllFoo.jsp
				successView.forward(req, res);
				} catch (Exception e) {
					errorMsgs.add(e.getMessage());
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/foo/updateFoo.jsp");
					failureView.forward(req, res);
				}
		}
		
		if ("listFoos_ByFooStatus".equals(action)) {
			LinkedList<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				Integer fooStatus = new Integer(req.getParameter("fooStatus"));
				
				/***************************2.開始新增資料***************************************/
				FooService fooSvc = new FooService();
				Set<FooVO> set = fooSvc.getFoosByFooStatus(fooStatus);
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				req.setAttribute("listFoos_ByFooStatus", set);
				
				String url = "/back-end/foo/listFoosByFooStatus.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllFoo.jsp
				successView.forward(req, res);
				
				} catch (Exception e) {
					errorMsgs.add(e.getMessage());
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
				FooCatService fooCatSvc = new FooCatService();
				if(requestURL.equals("/back-end/fooCat/listFoosByFooCatNo.jsp") || requestURL.equals("/back-end/fooCat/listAllFooCat.jsp")) {
					req.setAttribute("listFoos_ByFooCatNo",fooCatSvc.getFoosByFooCatNo(fooVO.getFooCatNo())); // 資料庫取出的Set物件,存入request
				} else if (requestURL.equals("/back-end/foo/listFoosByFooStatus.jsp")){
					req.setAttribute("listFoos_ByFooStatus",fooSvc.getFoosByFooStatus((fooVO.getFooStatus() == 0)? 1 : 0));
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
		
		
	}

}
