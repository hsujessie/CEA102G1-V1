package com.comment_report.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.comment.model.ComService;
import com.comment.model.ComVO;
import com.comment_report.model.ComRepService;
import com.comment_report.model.ComRepVO;
import com.movie.model.MovService;
import com.movie.model.MovVO;

public class ComRepServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		doPost(req,res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		// 來自前台 movies_subpage.jsp的請求
		if ("insert".equals(action)) {			
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/					
				Integer comNo = null;
				comNo = new Integer(req.getParameter("comNo").trim());
				
				Integer memNo = null;
				memNo = new Integer(req.getParameter("memNo").trim());				
				
				String comRepReason = null;
				String[] comRepReasonArr = req.getParameterValues("comRepReason"); 
				for(int i = 0; i < comRepReasonArr.length; i++) {
					comRepReason = comRepReasonArr[i];
				}
				
				java.sql.Timestamp comRepTime = new Timestamp(System.currentTimeMillis());

				Integer comRepStatus = 0; //0:未處理 

				Integer movNo = new Integer(req.getParameter("movNo").trim());
				
				/***************************2.開始新增資料***************************************/				
				ComRepService comRepSvc = new ComRepService();
				comRepSvc.addComRep(comNo, memNo, comRepReason, comRepTime, comRepStatus);
				
				ComService comSvc = new ComService();
				ComVO comVO = comSvc.getOneCom(comNo);

				MovService movSvc = new MovService();
				MovVO movVO = movSvc.getOneMov(movNo);
					
				/***************************3.新增完成,準備轉交(Send the Success view)***********/	
				req.setAttribute("comVO", comVO);
				req.setAttribute("movVO", movVO);
				
				String url = "/front-end/movies/movies_subpage.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);	
				
				/***************************其他可能的錯誤處理**********************************/
			
			}catch (Exception e) {
				System.out.println("insert資料失敗: " + e.getMessage());
				String url = "/front-end/movies/movies_subpage.jsp";
				RequestDispatcher failureView = req.getRequestDispatcher(url);
				failureView.forward(req, res);
			}
		} 

		// 來自listAllComReport.jsp的請求
		if("getOne_For_Update".equals(action)) { 
			String requestURL = req.getParameter("requestURL");		
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/			
				Integer comRepNo = new Integer(req.getParameter("comRepNo").trim());		
				Integer comNo = new Integer(req.getParameter("comNo").trim());
				
				/***************************2.開始新增資料***************************************/				
				ComRepService comRepSvc = new ComRepService();
				ComRepVO comRepVO = comRepSvc.getOneComRep(comRepNo);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/	
	            req.setAttribute("comRepVO", comRepVO); 

				ComService comSvc = new ComService();
				ComVO comVO = comSvc.getOneCom(comNo);
	            req.setAttribute("comVO", comVO);
	            
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/comment_report/update_comreport_input.jsp");				
				successView.forward(req, res);	
				
				/***************************其他可能的錯誤處理**********************************/
			
			}catch (Exception e) {
				System.out.println("getOne_For_Updated失敗: " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		
		// 來自update_cpmreport_input.jsp的請求
		if ("update".equals(action)) {
			String requestURL = req.getParameter("requestURL");
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer comRepStatus = new Integer(req.getParameter("comRepStatus").trim());
				Integer comRepNo = new Integer(req.getParameter("comRepNo").trim());
				Integer comNo = new Integer(req.getParameter("comNo").trim());
				
				if("1".equals(req.getParameter("comRepStatus").trim())) { //檢舉成功	
					Integer comStatus = 1; //短評狀態不顯示		
					ComService comSvc = new ComService();				
					comSvc.updateCom(comStatus, comNo);
				}
				
				/***************************2.開始修改資料*****************************************/		
				ComRepService comRepSvc = new ComRepService();
				comRepSvc.updateComRep(comRepStatus, comRepNo);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/	
				String updateSuccess = "【 檢舉狀態 】" + "已審核";
				req.setAttribute("updateSuccess", updateSuccess);
				
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/comment_report/listAllComReport.jsp");
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			}catch (Exception e) {
				System.out.println("修改資料失敗: " + e.getMessage());
				
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
			
		}
		// 來自listAllComReport.jsp的請求
		if ("searchComRepStatus".equals(action)) {
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer comRepStatus = new Integer(req.getParameter("comRepStatus").trim());  

	    	    /***************************2.開始查詢資料*****************************************/ 		
				ComRepService comRepSvc = new ComRepService();
				List<ComRepVO> list = comRepSvc.findComRepByComReStatus(comRepStatus);
				req.setAttribute("listComReps_ByQuery",list);
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/		            
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/comment_report/listAllComReports_ByQuery.jsp");
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			}catch (Exception e) {
				System.out.println("Exception: " + e.getMessage());
				
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/comment_report/listAllComReport.jsp");
				failureView.forward(req, res);
			}				
		}
				
								
		
	}
}
