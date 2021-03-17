package com.identity.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.identity.model.IdeService;
import com.ticket_type.model.*;

public class IdeServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
	
		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");
		
		if("listTicket_typesByIde_no_A".equals(action)||"listTicket_typesByIde_no_B".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				Integer ide_no = new Integer (req.getParameter("ide_no"));
				
				IdeService identitySvc = new IdeService();
				Set<TicTypVO> set = identitySvc.getTicket_typesByIde_no(ide_no);
				
				req.setAttribute("listTicket_typesByIde_no", set);
				
				String url = null;
				if("listTicket_typesByIde_no_A".equals(action))
					url = "/back-end/identity/listTicket_typesByIde_no.jsp";
				else if ("listTicket_typesByIde_no_B".equals(action))
					url = "/back-end/identity/listAllIdentity.jsp";
				
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
					
			} catch (Exception e) {
				throw new ServletException(e);
			}
			
		}
		
		
		
		
	}
	
	
	
}
