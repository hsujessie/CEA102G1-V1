package com.ticket_type.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.ticket_type.model.*;
import com.identity.model.*;
import com.movie_version.model.*;

public class TicTypServlet extends HttpServlet{

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
				/***************************1.�����ШD�Ѽ�****************************************/
				Integer tictyp_no = new Integer(req.getParameter("tictyp_no"));
				
				/***************************2.�}�l�d�߸��****************************************/
				TicTypService ticket_typeservice = new TicTypService();
				TicTypVO ticket_typeVO = ticket_typeservice.getOneTicket_type(tictyp_no);
								
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("ticket_typeVO", ticket_typeVO); // ��Ʈw���X��empVO����,�s�Jreq
				String url = "/back-end/ticket_type/update_ticket_type_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // ���\���update_emp_input.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƨ��X�ɥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}		
		if ("update".equals(action)) { // �Ӧ�update_emp_input.jsp���ШD
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); 
			req.setAttribute("requestURL", requestURL); 
			
		
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				Integer tictyp_no = new Integer(req.getParameter("tictyp_no").trim());
				Integer movver_no = new Integer(req.getParameter("movver_no").trim());
				Integer ide_no = new Integer(req.getParameter("ide_no").trim());				

				Integer tictyp_price = new Integer(req.getParameter("tictyp_price").trim());


				TicTypVO ticket_typeVO = new TicTypVO();
				ticket_typeVO.setTictyp_no(tictyp_no);
				ticket_typeVO.setMovver_no(movver_no);
				ticket_typeVO.setIde_no(ide_no);
				ticket_typeVO.setTictyp_price(tictyp_price);

				
				/***************************2.�}�l�ק���*****************************************/
				TicTypService ticket_typeService = new TicTypService();
				ticket_typeVO = ticket_typeService.updateTicket_type(tictyp_no, movver_no, ide_no, tictyp_price);
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				IdeService identitySvc = new IdeService();
				if(requestURL.equals("/back-end/identity/listTicket_typesByIde_no.jsp") || requestURL.equals("/back-end/movie_version/listAllMovie_version.jsp"))
					req.setAttribute("listTicket_typesByIde_no",identitySvc.getTicket_typesByIde_no(ide_no)); 
				MovVerService movie_versionSvc = new MovVerService();
				if(requestURL.equals("/back-end/movie_version/listTicket_type_ByMovver_no.jsp") || requestURL.equals("/back-end/movie_version/listAllMovie_version.jsp"))
					req.setAttribute("listTicket_type_ByMovver_no",movie_versionSvc.getTicket_typeByMovver_no(movver_no)); 

				
				String url = requestURL+"?tictyp_no="+tictyp_no; 
				RequestDispatcher successView = req.getRequestDispatcher(url);   
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/ticket_type/update_ticket_type_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { 
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/
				Integer movver_no = new Integer(req.getParameter("movver_no").trim());
				Integer ide_no = new Integer(req.getParameter("ide_no").trim());
				Integer tictyp_price = null;
				try {
					tictyp_price = new Integer(req.getParameter("tictyp_price").trim());
				} catch (NumberFormatException e) {
					tictyp_price = 0;
					errorMsgs.add("����ж�Ʀr.");
				}

				TicTypVO ticket_typeVO = new TicTypVO();
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
				
				/***************************2.�}�l�s�W���***************************************/
				TicTypService Ticket_typeService = new TicTypService();
				ticket_typeVO = Ticket_typeService.addTicket_type(movver_no, ide_no, tictyp_price);
				
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				String url = "/back-end/ticket_type/listAllTicket_type.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);				
				
				/***************************��L�i�઺���~�B�z**********************************/
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
			
			String requestURL = req.getParameter("requestURL"); // �e�X�R�����ӷ��������|: �i�ର�i/emp/listAllEmp.jsp�j ��  �i/dept/listEmps_ByDeptno.jsp�j �� �i /dept/listAllDept.jsp�j
			String whichPage = req.getParameter("whichPage");   // �e�X�R�����ӷ��������ĴX��(�u�Ω�:istAllEmp.jsp)
			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				Integer tictyp_no = new Integer(req.getParameter("tictyp_no"));
				
				/***************************2.�}�l�R�����***************************************/
				TicTypService Ticket_typeService = new TicTypService();
				Ticket_typeService.deleteTicket_type(tictyp_no);
				
				/***************************3.�R������,�ǳ����(Send the Success view)***********/
				
				String url = requestURL+"?whichPage="+whichPage;               // �e�X�R�����ӷ��������ĴX��(�u�Ω�:istAllEmp.jsp)
				RequestDispatcher successView = req.getRequestDispatcher(url); // �R�����\��,���^�e�X�R�����ӷ�����
				successView.forward(req, res);
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�R����ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}		
		
		
		
	
	}
}
