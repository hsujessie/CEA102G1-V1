package com.satisfaction.controller;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.movie.model.MovService;
import com.movie.model.MovVO;
import com.satisfaction.model.SatService;


@MultipartConfig()
public class SatServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		doPost(req,res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println("action:"+action);// 來自前台 movies_subpage.jsp的請求
		
		
		if ("insert".equals(action)) {		
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				Integer satRating = null;
				String[] satRatingArr = null;
				satRatingArr = req.getParameterValues("satRating");
				satRating = satRatingArr.length;
				
				Integer movNo = null;
				Integer memNo = null;
				movNo = new Integer(req.getParameter("movNo").trim());
				memNo = new Integer(req.getParameter("memNo").trim());
				
				System.out.println("satRating= " + satRating);
				System.out.println("movNo= " + movNo);
				System.out.println("memNo= " + memNo);
				
				/***************************2.開始新增資料***************************************/				
				SatService satSvc = new SatService();
				satSvc.addSat(movNo, memNo, satRating);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/	
				MovService movSvc = new MovService();
				MovVO movVO = movSvc.getOneMov(movNo);
				req.setAttribute("movVO", movVO);
				
				String url = "/front-end/movies/movies_subpage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);	
				
				/***************************其他可能的錯誤處理**********************************/
			}catch (Exception e) {
				System.out.println("Exception: " + e.getMessage());
				String url = "/front-end/movies/movies_subpage.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		} 
		
		
	}
}
