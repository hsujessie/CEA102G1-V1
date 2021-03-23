package com.faq_type.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.faq.model.FaqVO;
import com.faq_type.model.FaqtypService;

public class FaqtypServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
	
		req.setCharacterEncoding("UTF-8");

		String action = req.getParameter("action");
		
		if("listFaq_ByFaq_type_A".equals(action)||"listFaq_ByFaq_type_B".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				Integer faqtyp_no = new Integer (req.getParameter("faqtyp_no"));
				
				FaqtypService faq_typeSvc = new FaqtypService();
				Set<FaqVO> set = faq_typeSvc.getFaqsByFaqtyp_no(faqtyp_no);
				
				req.setAttribute("listFaq_ByFaq_type", set);
				
				String url = null;
				if("listFaq_ByFaq_type_A".equals(action))
					url = "/back-end/faq_type/listFaq_ByFaq_type.jsp";
				else if ("listFaq_ByFaq_type_B".equals(action))
					url = "/back-end/faq_type/listAllFaq_type.jsp";
				
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
					
			} catch (Exception e) {
				throw new ServletException(e);
			}
			
		}
			
		
		
		
		
	}
}
