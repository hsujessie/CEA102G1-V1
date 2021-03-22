package com.comment.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.comment.model.ComService;
import com.comment.model.ComVO;
import com.movie.model.MovService;
import com.movie.model.MovVO;

@MultipartConfig()
public class ComServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		doPost(req,res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println("comment action: "+action);
		

		// 來自前台 movies_subpage.jsp的請求
		if ("insert".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs",errorMsgs);
			
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String movNoStr = req.getParameter("movNo").trim();
				Integer movNo = null;
				if(movNoStr == null || movNoStr.length() == 0) {
					errorMsgs.put("movNo","movNoStr is null.");
				}else {
					movNo = new Integer(movNoStr);
				}

				String memNoStr = req.getParameter("memNo").trim();
				Integer memNo = null;
				if(memNoStr == null || memNoStr.length() == 0) {
					errorMsgs.put("memNo","memNoStr is null.");
				}else {
					memNo = new Integer(memNoStr);
				}
				
				
				String comContent = req.getParameter("comContent").trim(); 
				if(comContent == null || memNoStr.length() == 0) {
					errorMsgs.put("comContent","comContent is null.");
				}
				java.sql.Timestamp comTime = new Timestamp(System.currentTimeMillis());

				System.out.println("movNo= " + movNo);
				System.out.println("memNo= " + memNo);
				System.out.println("comContent= " + comContent);
				System.out.println("comTime= " + comTime);
				
				Integer comStatus = 0;
				ComVO comVO = new ComVO();
				comVO.setMovNo(movNo);
				comVO.setMemNo(memNo);
				comVO.setComTime(comTime);
				comVO.setComContent(comContent);
				comVO.setComStatus(comStatus);

				if (!errorMsgs.isEmpty()) {
					System.out.println(errorMsgs);
					return;
				}
				/***************************2.開始新增資料***************************************/				
				ComService comSvc = new ComService();
				comSvc.addCom(movNo, memNo, comTime, comContent, comStatus);
					
				/***************************3.新增完成,準備轉交(Send the Success view)***********/	
				req.setAttribute("comVO", comVO);
				
				MovService movSvc = new MovService();
				MovVO movVO = movSvc.getOneMov(movNo);
				req.setAttribute("movVO", movVO);
				
				String url = "/front-end/movies/movies_subpage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);	
				
				/***************************其他可能的錯誤處理**********************************/
			}catch (Exception e) {
				errorMsgs.put("Exception",e.getMessage());
				String url = "/front-end/movies/movies_subpage.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		} 
		
		
	}
}
