package com.theater.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.movie_version.model.MovVerService;
import com.theater.model.*;

public class TheServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req,res);
	}
	
	
	public void doPost(HttpServletRequest req , HttpServletResponse res) throws ServletException, IOException{
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				Integer the_no = new Integer(req.getParameter("the_no"));
				
				TheService theSvc = new TheService();
				TheVO theaterVO = theSvc.getOneTheater(the_no);
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/theater/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				req.setAttribute("theaterVO", theaterVO); 
				String url = "/back-end/theater/listOneTheater.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/theater/select_page.jsp");
				failureView.forward(req, res);
			}
		} 
		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); 
			req.setAttribute("requestURL", requestURL);
			
 
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				Integer the_no = new Integer(req.getParameter("the_no"));
				
				/***************************2.�}�l�d�߸��****************************************/
				TheService theSvc = new TheService();
				TheVO theaterVO = theSvc.getOneTheater(the_no);
								
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("theaterVO", theaterVO);         
				String url = "/back-end/theater/update_theater_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/theater/listAllTheater.jsp");
				failureView.forward(req, res);
			}			
		}
		if ("update".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); 
			req.setAttribute("requestURL", requestURL);
			
			String whichPage = req.getParameter("whichPage");
			req.setAttribute("whichPage", whichPage);   
		
			try {
				/***************************1.�����ШD�Ѽ� - ��J�榡�����~�B�z**********************/
				Integer the_no = new Integer(req.getParameter("the_no").trim());
				String the_seat = req.getParameter("the_seat");
				Integer movver_no = new Integer(req.getParameter("movver_no").trim());								
				
				String the_seatno = theaterSeat.seatNoTransform.seatNoTran(the_seat);
				
				
				TheVO theaterVO = new TheVO();
				theaterVO.setThe_seat(the_seat);
				theaterVO.setThe_seatno(the_seatno);
				theaterVO.setMovver_no(movver_no);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("TheaterVO", theaterVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/theater/update_theater_input.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				
				/***************************2.�}�l�ק���*****************************************/
				TheService theSvc = new TheService();
				theaterVO = theSvc.updateTheater(the_no, movver_no, the_seat, the_seatno);
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				MovVerService movie_versionSvc = new MovVerService();
				if(requestURL.equals("/back-end/movie_version/listTheaters_ByMovie_version.jsp") || requestURL.equals("/back-end/movie_version/listAllMovie_version.jsp"))
					req.setAttribute("listTheaters_Bymovie_version",movie_versionSvc.getTheatersByMovver_no(movver_no)); 
					
				String url = requestURL+"?the_no="+the_no;
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				e.printStackTrace();
				errorMsgs.add("�ק��ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/theater/update_theater_input.jsp");
				failureView.forward(req, res);
			}			
		}
		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/

				Integer movver_no = new Integer(req.getParameter("movver_no").trim());
				
				String the_seat = req.getParameter("the_seat").trim();
				String the_seatno = theaterSeat.seatNoTransform.seatNoTran(the_seat);
				

				TheVO theaterVO = new TheVO();
				theaterVO.setMovver_no(movver_no);
				theaterVO.setThe_seat(the_seat);
				theaterVO.setThe_seatno(the_seatno);


				if (!errorMsgs.isEmpty()) {
					req.setAttribute("theaterVO", theaterVO); 
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/theater/addTheater.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.�}�l�s�W���***************************************/
				TheService theSvc = new TheService();
				theaterVO = theSvc.addTheater(movver_no, the_seat, the_seatno);
				Integer the_no = theaterVO.getThe_no();
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				String url = "/back-end/theater/listAllTheater.jsp?the_no=";
				RequestDispatcher successView = req.getRequestDispatcher(url+the_no);
				successView.forward(req, res);				
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/theater/addTheater.jsp");
				failureView.forward(req, res);
			}			
		}
	}
}
