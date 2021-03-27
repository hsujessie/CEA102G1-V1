package com.admin.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import org.json.JSONArray;
import org.json.JSONObject;

import com.admin.model.AdmService;
import com.admin.model.AdmVO;
import com.admin_auth.model.AdmAutService;
import com.admin_auth.model.AdmAutVO;
import com.func.model.FunService;
import com.func.model.FunVO;

@MultipartConfig
public class AdmServlet extends HttpServlet {
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
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				
				// 員工姓名
				String admName = req.getParameter("admName");
				String admNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (admName == null || admName.trim().isEmpty()) {
					errorMsgs.put("admName", "姓名不可為空白");
				} else if(!admName.trim().matches(admNameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("admName","姓名只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }

				// 帳號
				String admAccount = req.getParameter("admAccount");
				String admAccountReg = "^[(a-zA-Z0-9)]{3,10}$";
				AdmService admSvc = new AdmService();
				if (admAccount == null || admAccount.trim().isEmpty()) {
					errorMsgs.put("admAccount", "帳號不可為空白");
				} else if(!admAccount.trim().matches(admAccountReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("admAccount","帳號只能是英文字母、數字和, 且長度必需在3到10之間");
	            } else if (admSvc.checkRepeat(admAccount)) {
	            	errorMsgs.put("admAccount","帳號重覆，請重新輸入");
	            }

				// 信箱
				String admMail = req.getParameter("admMail");
				if (admMail == null || admMail.trim().isEmpty()) {
					errorMsgs.put("admMail", "信箱不可為空白");
				}

				// 照片
				Part part = req.getPart("admImg");
				byte[] admImg = null;
				if (part == null || part.getSize() == 0) {
					errorMsgs.put("admImg", "必須上傳照片");
				} else {
					InputStream is = part.getInputStream();
					admImg = new byte[is.available()];
					is.read(admImg);
				}

				// 權限
				String[] funNoArray = req.getParameterValues("funNo");

				AdmVO admVO = new AdmVO();
				admVO.setAdmName(admName);
				admVO.setAdmAccount(admAccount);
				admVO.setAdmImg(admImg);
				admVO.setAdmMail(admMail);
				
				if(funNoArray == null) {
					errorMsgs.put("funNo", "至少勾選一項權限");
				}

				// 有錯誤時，轉交回新增頁面
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("addAdmVO", admVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admin/addAdmin.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				
				admVO = admSvc.addAdm(admName, admImg, admAccount, admMail, funNoArray);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String addSuccess = "【  " + admName + " 】" + "新增成功";
				req.setAttribute("addSuccess", addSuccess);	
				
				String url = "/back-end/admin/listAllAdmin.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.put("error",e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admin/addAdmin.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				
				//員工編號
				Integer admNo = new Integer(req.getParameter("admNo"));

				// 員工姓名
				String admName = req.getParameter("admName");
				String admNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (admName == null || admName.trim().isEmpty()) {
					errorMsgs.put("admName", "姓名不可為空白");
				} else if(!admName.trim().matches(admNameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("admName","姓名只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }

				// 帳號
				String admAccount = req.getParameter("admAccount");
				String admAccountReg = "^[(a-zA-Z0-9)]{3,10}$";
				AdmService admSvc = new AdmService();
				if (admAccount == null || admAccount.trim().isEmpty()) {
					errorMsgs.put("admAccount", "帳號不可為空白");
				} else if(!admAccount.trim().matches(admAccountReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.put("admAccount","帳號只能是英文字母、數字和, 且長度必需在3到10之間");
	            }

				
				// 信箱
				String admMail = req.getParameter("admMail");
				if (admMail == null || admMail.trim().isEmpty()) {
					errorMsgs.put("admMail", "信箱不可為空白");
				}

				// 照片
				Part part = req.getPart("admImg");
				byte[] admImg = null;
				

				// 狀態
				Integer admStatus = new Integer(req.getParameter("admStatus"));

				// 權限
				String[] funNoArray = req.getParameterValues("funNo");
				if(funNoArray == null) {
					errorMsgs.put("funNo", "至少勾選一項權限");
				}

				AdmVO admVO = new AdmVO();
				admVO.setAdmNo(admNo);
				admVO.setAdmName(admName);
				admVO.setAdmAccount(admAccount);
				admVO.setAdmImg(admImg);
				admVO.setAdmMail(admMail);
				admVO.setAdmStatus(admStatus);

				// 有錯誤時，轉交回新增頁面
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("oneAdmVO", admVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admin/updateAdmin.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				
				if (part.getSize() != 0) {
					InputStream is = part.getInputStream();
					admImg = new byte[is.available()];
					is.read(admImg);
					admVO = admSvc.updateAdm(admNo, admName, admImg, admAccount, admMail, admStatus, funNoArray);
				} else {
					admVO = admSvc.updateAdmNoImg(admNo, admName, admAccount, admMail, admStatus, funNoArray);
				}
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String updateSuccess = "【  " + admName + " 】" + "修改成功";
				req.setAttribute("updateSuccess", updateSuccess);
				
				if(requestURL.equals("/back-end/admin/listAdmin_ByCompositeQuery.jsp")){
					HttpSession session = req.getSession();
					Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
					List<AdmVO> list  = admSvc.getAll(map);
					req.setAttribute("listAdmins_ByCompositeQuery",list); //  複合查詢, 資料庫取出的list物件,存入request
				}
				
				String url = requestURL;
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.put("error",e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admin/updateAdmin.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_for_update".equals(action)) {

			Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				Integer admNo = new Integer(req.getParameter("admNo"));

				/*************************** 2.開始查詢資料 ***************************************/
				AdmService admSvc = new AdmService();
				AdmVO admVO = admSvc.getOneAdm(admNo);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ***********/
				req.setAttribute("oneAdmVO", admVO);
				String url = "/back-end/admin/updateAdmin.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.put("error",e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admin/listAllAdmin.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("login".equals(action)) {
			
			LinkedList<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				
				String admAccount = req.getParameter("admAccount");
				String admPassword = req.getParameter("admPassword");
				
				/*************************** 2.開始查詢資料 ***************************************/
				AdmService admSvc = new AdmService();
				AdmVO admVO = admSvc.allowAdmin(admAccount, admPassword);
				
				if (admVO == null) {
					errorMsgs.add("登入失敗,請重新確認帳號及密碼");
				} else if (admVO.getAdmStatus() == 1) {
					errorMsgs.add("登入失敗,請重新確認帳號及密碼");
				}
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/login.jsp");
					failureView.forward(req, res);
					return;
				}
				
				AdmAutService admAutSvc = new AdmAutService();
				List<String> funList = admAutSvc.getAdmFun(admVO.getAdmNo());
				
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ***********/
				HttpSession session = req.getSession();
				session.setAttribute("admVO", admVO);
				session.setAttribute("funList", funList);
				
				String url = (String) session.getAttribute("back_end_location");
				if (url == null) {
					url = req.getContextPath() + "/back-end/index.jsp";
				}
				res.sendRedirect(url);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/login.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("logout".equals(action)) {
			HttpSession session = req.getSession();
			session.removeAttribute("admVO");
			session.removeAttribute("funList");
			
			res.sendRedirect(req.getContextPath() + "/back-end/index.jsp");
		}
		
		if ("listAdmins_ByCompositeQuery".equals(action)) {
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
				AdmService admSvc = new AdmService();
				List<AdmVO> list = admSvc.getAll(map);
				
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("listAdmins_ByCompositeQuery", list);
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/admin/listAdmin_ByCompositeQuery.jsp");
				successView.forward(req, res);
				
				
			} catch (Exception e) {
				errorMsgs.put("error",e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admin/listAllAdmin.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("get_fun_byAdmNo".equals(action)) {
			res.setContentType("text/plain;charset=UTF-8");
			PrintWriter out = res.getWriter();
			try {
				
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************************/ 
				Integer admNo = new Integer(req.getParameter("admNo"));
				/*************************** 2.開始查詢資料 ***************************************/
				AdmService admSvc = new AdmService();
				AdmVO admVO = admSvc.getOneAdm(admNo);
				List<AdmAutVO> list = admSvc.getAuthsByAdmNo(admNo);
				
				FunService funSvc = new FunService();
				JSONArray jsonArray = funSvc.getFunName(list);
				
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				JSONObject obj = new JSONObject();
				
				obj.put("admNo", admNo);
				obj.put("admName", admVO.getAdmName());
				obj.put("funList", jsonArray);
				
				out.print(obj.toString());
			} catch (Exception e) {
				out.print(e.getMessage());
			}
		}
			
	}

}
