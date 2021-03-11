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
		System.out.println("action:"+action);
		

		// 來自前台 movies_subpage.jsp的請求
		if ("insert".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs",errorMsgs);
			
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				Integer movNo = null;
				movNo = new Integer(req.getParameter("movNo").trim());
				
				Integer memNo = null;
				memNo = new Integer(req.getParameter("memNo").trim());
				String comContent = req.getParameter("comContent").trim(); 
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
