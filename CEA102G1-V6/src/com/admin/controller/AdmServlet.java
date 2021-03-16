package com.admin.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

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
import com.admin_auth.model.AdmAutVO;

@MultipartConfig
public class AdmServlet extends HttpServlet {
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
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				// 員工姓名
				String admName = req.getParameter("admName");
				if (admName == null || admName.trim().isEmpty()) {
					errorMsgs.add("姓名不可為空白");
				}

				// 帳號
				String admAccount = req.getParameter("admAccount");
				if (admAccount == null || admAccount.trim().isEmpty()) {
					errorMsgs.add("帳號不可為空白");
				}

				// 信箱
				String admMail = req.getParameter("admMail");
				if (admMail == null || admMail.trim().isEmpty()) {
					errorMsgs.add("信箱不可為空白");
				}

				// 照片
				Part part = req.getPart("admImg");
				byte[] admImg = null;
				if (part == null || part.getSize() == 0) {
					errorMsgs.add("必須上傳照片");
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
					errorMsgs.add("至少選擇一項權限");
				}

				// 有錯誤時，轉交回新增頁面
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("addAdmVO", admVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admin/addAdmin.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				AdmService admSvc = new AdmService();
				admVO = admSvc.addAdm(admName, admImg, admAccount, admMail, funNoArray);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String addSuccess = "【  " + admName + " 】" + "新增成功";
				req.setAttribute("addSuccess", addSuccess);	
				
				String url = "/back-end/admin/listAllAdmin.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admin/addAdmin.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {

			LinkedList<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				
				//員工編號
				Integer admNo = new Integer(req.getParameter("admNo"));

				// 員工姓名
				String admName = req.getParameter("admName");
				if (admName == null || admName.trim().isEmpty()) {
					errorMsgs.add("姓名不可為空白");
				}

				// 帳號
				String admAccount = req.getParameter("admAccount");
				if (admAccount == null || admAccount.trim().isEmpty()) {
					errorMsgs.add("帳號不可為空白");
				}

				// 密碼
				String admPassword = req.getParameter("admPassword");
				if (admPassword == null || admPassword.trim().isEmpty()) {
					errorMsgs.add("密碼不可為空白");
				}
				// 信箱
				String admMail = req.getParameter("admMail");
				if (admMail == null || admMail.trim().isEmpty()) {
					errorMsgs.add("信箱不可為空白");
				}

				// 照片
				Part part = req.getPart("admImg");
				byte[] admImg = null;
				

				// 狀態
				Integer admStatus = new Integer(req.getParameter("admStatus"));

				// 權限
				String[] funNoArray = req.getParameterValues("funNo");

				AdmVO admVO = new AdmVO();
				admVO.setAdmNo(admNo);
				admVO.setAdmName(admName);
				admVO.setAdmAccount(admAccount);
				admVO.setAdmImg(admImg);
				admVO.setAdmMail(admMail);
				admVO.setAdmPassword(admPassword);
				admVO.setAdmStatus(admStatus);

				// 有錯誤時，轉交回新增頁面
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("admVO", admVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admin/updateAdmin.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				AdmService admSvc = new AdmService();
				
				if (part.getSize() != 0) {
					InputStream is = part.getInputStream();
					admImg = new byte[is.available()];
					is.read(admImg);
					admVO = admSvc.updateAdm(admNo, admName, admImg, admAccount, admPassword, admMail, admStatus, funNoArray);
				} else {
					admVO = admSvc.updateAdmNoImg(admNo, admName, admAccount, admPassword, admMail, admStatus, funNoArray);
				}
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String updateSuccess = "【  " + admName + " 】" + "修改成功";
				req.setAttribute("updateSuccess", updateSuccess);
				
				String url = "/back-end/admin/listAllAdmin.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admin/updateAdmin.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_for_update".equals(action)) {

			LinkedList<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/

				Integer admNo = new Integer(req.getParameter("admNo"));

				/*************************** 2.開始查詢資料 ***************************************/
				AdmService admSvc = new AdmService();
				AdmVO admVO = admSvc.getOneAdm(admNo);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ***********/
				req.setAttribute("admVO", admVO);
				String url = "/back-end/admin/updateAdmin.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admin/updateAdmin.jsp");
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
				}
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/login.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/*************************** 3.查詢完成,準備轉交(Send the Success view) ***********/
				HttpSession session = req.getSession();
				session.setAttribute("admVO", admVO);
				
				String url = (String) session.getAttribute("location");
				if (url == null) {
					url = req.getContextPath() + "/back-end/index.jsp";
				}
				res.sendRedirect(url);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admin/updateAdmin.jsp");
				failureView.forward(req, res);
			}
		}

	}

}
