package com.admin.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import com.admin.model.AdmService;
import com.admin.model.AdmVO;

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
			/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				
			//員工姓名
			String admName = req.getParameter("admName");
			if (admName == null || admName.trim().isEmpty()) {
				errorMsgs.add("姓名不可為空白");
			}
			
			
			//帳號
			String admAccount = req.getParameter("admAccount");
			if (admAccount == null || admAccount.trim().isEmpty()) {
				errorMsgs.add("帳號不可為空白");
			}
			
			//密碼
			String admPassword = req.getParameter("admPassword");
			if (admPassword == null || admPassword.trim().isEmpty()) {
				errorMsgs.add("密碼不可為空白");
			}
			//信箱
			String admMail = req.getParameter("admMail");
			if (admMail == null || admMail.trim().isEmpty()) {
				errorMsgs.add("信箱不可為空白");
			}
			
			//照片
			Part part = req.getPart("admImg");
			byte[] admImg = null;
			if (part == null || part.getSize() == 0) {
				errorMsgs.add("必須上傳照片");
			} else {
				InputStream is = part.getInputStream();
				admImg = new byte[is.available()];
				is.read(admImg);
			}
			
			
			AdmVO admVO = new AdmVO();
			admVO.setAdmAccount(admAccount);
			admVO.setAdmImg(admImg);
			admVO.setAdmMail(admMail);
			admVO.setAdmPassword(admPassword);
			
			//有錯誤時，轉交回新增頁面
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("admVO", admVO);
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admin/addAdmin.jsp");
				failureView.forward(req, res);
				return;
			}
			
			/***************************2.開始新增資料***************************************/
			AdmService admSvc = new AdmService();
			admVO = admSvc.addAdm(admName, admImg, admAccount, admPassword, admMail);
			
			/***************************3.新增完成,準備轉交(Send the Success view)***********/
			String url = "/back-end/admin/listAllAdmin.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admin/addAdmin.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
