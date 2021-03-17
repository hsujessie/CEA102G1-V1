package com.movie_version.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.movie_version.model.*;
import com.theater.model.*;
import com.ticket_type.model.TicTypVO;

public class MovVerServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
	
		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");
		
		if("listTheaters_Bymovie_version_A".equals(action)||"listTheaters_Bymovie_version_B".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				Integer movver_no = new Integer (req.getParameter("movver_no"));
				
				MovVerService movie_versionSvc = new MovVerService();
				Set<TheVO> set = movie_versionSvc.getTheatersByMovver_no(movver_no);
				
				req.setAttribute("listTheaters_Bymovie_version", set);
				
				String url = null;
				if("listTheaters_Bymovie_version_A".equals(action))
					url = "/back-end/movie_version/listTheaters_ByMovie_version.jsp";
				else if ("listTheaters_Bymovie_version_B".equals(action))
					url = "/back-end/movie_version/listAllMovie_version.jsp";
				
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
					
			} catch (Exception e) {
				throw new ServletException(e);
			}
			
		}
		if("listTicket_type_ByMovver_no_A".equals(action)||"listTicket_type_ByMovver_no_B".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				Integer movver_no = new Integer (req.getParameter("movver_no"));
				
				MovVerService movie_versionSvc = new MovVerService();
				Set<TicTypVO> set = movie_versionSvc.getTicket_typeByMovver_no(movver_no);
				
				req.setAttribute("listTicket_type_ByMovver_no", set);
				
				String url = null;
				if("listTicket_type_ByMovver_no_A".equals(action))
					url = "/back-end/movie_version/listTicket_type_ByMovver_no.jsp";
				else if ("listTicket_type_ByMovver_no_B".equals(action))
					url = "/back-end/movie_version/listAllMovie_version.jsp";
				
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
					
			} catch (Exception e) {
				throw new ServletException(e);
			}
			
		}
		
		
		
		
	}
	
	
	
	
	
	
	
}
