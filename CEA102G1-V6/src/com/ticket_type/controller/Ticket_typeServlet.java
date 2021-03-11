package com.ticket_type.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.ticket_type.model.*;
import com.identity.model.*;
import com.movie_version.model.*;

public class Ticket_typeServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); 
			req.setAttribute("requestURL", requestURL);
			
			String whichPage = req.getParameter("whichPage");
			req.setAttribute("whichPage", whichPage);   
			
			try {
				/***************************1.接收請求參數****************************************/
				Integer tictyp_no = new Integer(req.getParameter("tictyp_no"));
				
				/***************************2.開始查詢資料****************************************/
				Ticket_typeService ticket_typeservice = new Ticket_typeService();
				Ticket_typeVO ticket_typeVO = ticket_typeservice.getOneTicket_type(tictyp_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("ticket_typeVO", ticket_typeVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/ticket_type/update_ticket_type_input.jsp";
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
		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); 
			req.setAttribute("requestURL", requestURL); 
			
			String whichPage = req.getParameter("whichPage"); 
			req.setAttribute("whichPage", whichPage);   
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer tictyp_no = new Integer(req.getParameter("tictyp_no").trim());
				Integer movver_no = new Integer(req.getParameter("movver_no").trim());
				Integer ide_no = new Integer(req.getParameter("ide_no").trim());				

				Integer tictyp_price = null;
				try {
					tictyp_price = new Integer(req.getParameter("tictyp_price").trim());
				} catch (NumberFormatException e) {
					tictyp_price = 0;
					errorMsgs.add("獎金請填數字.");
				}


				Ticket_typeVO ticket_typeVO = new Ticket_typeVO();
				ticket_typeVO.setTictyp_no(tictyp_no);
				ticket_typeVO.setMovver_no(movver_no);
				ticket_typeVO.setIde_no(ide_no);
				ticket_typeVO.setTictyp_price(tictyp_price);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("ticket_typeVO", ticket_typeVO); 
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/ticket_type/update_ticket_type_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				Ticket_typeService ticket_typeService = new Ticket_typeService();
				ticket_typeVO = ticket_typeService.updateTicket_type(tictyp_no, movver_no, ide_no, tictyp_price);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				IdentityService identitySvc = new IdentityService();
				if(requestURL.equals("/back-end/identity/listTicket_typesByIde_no.jsp") || requestURL.equals("/back-end/movie_version/listAllMovie_version.jsp"))
					req.setAttribute("listTicket_typesByIde_no",identitySvc.getTicket_typesByIde_no(ide_no)); 
				Movie_versionService movie_versionSvc = new Movie_versionService();
				if(requestURL.equals("/back-end/movie_version/listTicket_type_ByMovver_no.jsp") || requestURL.equals("/back-end/movie_version/listAllMovie_version.jsp"))
					req.setAttribute("listTicket_type_ByMovver_no",movie_versionSvc.getTicket_typeByMovver_no(movver_no)); 

				
				String url = requestURL+"?whichPage="+whichPage+"&tictyp_no="+tictyp_no; 
				RequestDispatcher successView = req.getRequestDispatcher(url);   
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/ticket_type/update_ticket_type_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { 
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				Integer movver_no = new Integer(req.getParameter("movver_no").trim());
				Integer ide_no = new Integer(req.getParameter("ide_no").trim());
				Integer tictyp_price = null;
				try {
					tictyp_price = new Integer(req.getParameter("tictyp_price").trim());
				} catch (NumberFormatException e) {
					tictyp_price = 0;
					errorMsgs.add("售價請填數字.");
				}

				Ticket_typeVO ticket_typeVO = new Ticket_typeVO();
				ticket_typeVO.setMovver_no(movver_no);
				ticket_typeVO.setIde_no(ide_no);
				ticket_typeVO.setTictyp_price(tictyp_price);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("ticket_typeVO", ticket_typeVO); 
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/ticket_type/addticket_type.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				Ticket_typeService Ticket_typeService = new Ticket_typeService();
				ticket_typeVO = Ticket_typeService.addTicket_type(movver_no, ide_no, tictyp_price);
				
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/ticket_type/listAllTicket_type.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/ticket_type/addTicket_type.jsp");
				failureView.forward(req, res);
			}
		}
		
       
		if ("delete".equals(action)) { 

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // 送出刪除的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或  【/dept/listEmps_ByDeptno.jsp】 或 【 /dept/listAllDept.jsp】
			String whichPage = req.getParameter("whichPage");   // 送出刪除的來源網頁的第幾頁(只用於:istAllEmp.jsp)
			try {
				/***************************1.接收請求參數***************************************/
				Integer tictyp_no = new Integer(req.getParameter("tictyp_no"));
				
				/***************************2.開始刪除資料***************************************/
				Ticket_typeService Ticket_typeService = new Ticket_typeService();
				Ticket_typeService.deleteTicket_type(tictyp_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/
				
				String url = requestURL+"?whichPage="+whichPage;               // 送出刪除的來源網頁的第幾頁(只用於:istAllEmp.jsp)
				RequestDispatcher successView = req.getRequestDispatcher(url); // 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}		
		
		
		
	
	}
}
