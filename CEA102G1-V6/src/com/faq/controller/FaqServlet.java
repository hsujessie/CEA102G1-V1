package com.faq.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.faq.model.*;
import com.faq_type.model.FaqtypService;
public class FaqServlet extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String str = req.getParameter("faq_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("�п�J�s��");
				}

				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/faq/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				Integer faq_no = null;
				try {
					faq_no = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("�s���榡�����T");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/faq/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				FaqService faqSvc = new FaqService();
				FaqVO faqVO = faqSvc.getOneFaq(faq_no);
				if (faqVO == null) {
					errorMsgs.add("�d�L���");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/faq/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
				
				req.setAttribute("faqVO", faqVO);
				String url = "/back-end/faq/listOneFaq.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); 
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.add("�L�k���o���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/faq/select_page.jsp");
				failureView.forward(req, res);
			}
		} 
		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); 
			req.setAttribute("requestURL", requestURL);
			
			String whichPage = req.getParameter("whichPage");
			req.setAttribute("whichPage", whichPage);   
			
			try {
				/***************************1.�����ШD�Ѽ�****************************************/
				Integer faq_no = new Integer(req.getParameter("faq_no"));
				
				/***************************2.�}�l�d�߸��****************************************/
				FaqService faqSvc = new FaqService();
				FaqVO faqVO = faqSvc.getOneFaq(faq_no);
								
				/***************************3.�d�ߧ���,�ǳ����(Send the Success view)************/
				req.setAttribute("faqVO", faqVO);         
				String url = "/back-end/faq/update_faq_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add("�L�k���o�n�ק諸���:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/faq/listAllFaq.jsp");
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
				Integer faq_no = new Integer(req.getParameter("faq_no").trim());
				Integer faqtyp_no =new Integer(req.getParameter("faqtyp_no").trim());
				String faq_question = req.getParameter("faq_question");
				if(faq_question == null || faq_question.trim().length() == 0) {
					errorMsgs.add("���D�G�@�ФŪť�");
				}
				String faq_answer = req.getParameter("faq_answer");
				if(faq_answer == null || faq_answer.trim().length() == 0) {
					errorMsgs.add("�^���G�@�ФŪť�");
				}
				
				FaqVO faqVO= new FaqVO();
				faqVO.setFaq_no(faq_no);
				faqVO.setFaqtyp_no(faqtyp_no);
				faqVO.setFaq_question(faq_question);
				faqVO.setFaq_answer(faq_answer);
				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("faqVO", faqVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/faq/update_faq_input.jsp");
					failureView.forward(req, res);
					return; //�{�����_
				}
				
				/***************************2.�}�l�ק���*****************************************/
				FaqService faqSvc = new FaqService();
				faqVO = faqSvc.updateFaq(faq_no, faqtyp_no, faq_question, faq_answer);
				
				/***************************3.�ק粒��,�ǳ����(Send the Success view)*************/
				FaqtypService faq_typeSvc = new FaqtypService();
				if(requestURL.equals("/back-end/faq_type/listFaq_ByFaq_type.jsp") || requestURL.equals("/back-end/faq_type/listAllFaq_type.jsp"))
					req.setAttribute("listFaq_ByFaq_type",faq_typeSvc.getFaqsByFaqtyp_no(faqtyp_no)); // ��Ʈw���X��list����,�s�Jrequest
					
				String url = requestURL+"?whichPage="+whichPage+"&faq_no="+faq_no;
				RequestDispatcher successView = req.getRequestDispatcher(url); // �ק令�\��,���listOneEmp.jsp
				successView.forward(req, res);

				/***************************��L�i�઺���~�B�z*************************************/
			} catch (Exception e) {
				errorMsgs.add("�ק��ƥ���:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/faq/update_faq_input.jsp");
				failureView.forward(req, res);
			}			
		}
		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***********************1.�����ШD�Ѽ� - ��J�榡�����~�B�z*************************/

				Integer faqtyp_no =new Integer(req.getParameter("faqtyp_no").trim());
				String faq_question = req.getParameter("faq_question");
				if(faq_question == null || faq_question.trim().length() == 0) {
					errorMsgs.add("���D�G�@�ФŪť�");
				}
				String faq_answer = req.getParameter("faq_answer");
				if(faq_answer == null || faq_answer.trim().length() == 0) {
					errorMsgs.add("�^���G�@�ФŪť�");
				}
				

				FaqVO faqVO= new FaqVO();
				faqVO.setFaqtyp_no(faqtyp_no);
				faqVO.setFaq_question(faq_question);
				faqVO.setFaq_answer(faq_answer);


				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("faqVO", faqVO); // �t����J�榡���~��empVO����,�]�s�Jreq
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/faq/addFaq.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.�}�l�s�W���***************************************/
				FaqService faqsvc = new FaqService();
				faqVO = faqsvc.addFaq(faqtyp_no, faq_question, faq_answer);
				/***************************3.�s�W����,�ǳ����(Send the Success view)***********/
				String url = "/back-end/faq/listAllFaq.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // �s�W���\�����listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************��L�i�઺���~�B�z**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/faq/addFaq.jsp");
				failureView.forward(req, res);
			}			
		}
		if ("delete".equals(action)) { // �Ӧ�listAllEmp.jsp ��  /dept/listEmps_ByDeptno.jsp���ШD

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // �e�X�R�����ӷ��������|: �i�ର�i/emp/listAllEmp.jsp�j ��  �i/dept/listEmps_ByDeptno.jsp�j �� �i /dept/listAllDept.jsp�j �� �i /emp/listEmps_ByCompositeQuery.jsp�j

			try {
				/***************************1.�����ШD�Ѽ�***************************************/
				Integer faq_no = new Integer(req.getParameter("faq_no"));
				
				/***************************2.�}�l�R�����***************************************/
				FaqService faqsvc = new FaqService();
				FaqVO faqVO = faqsvc.getOneFaq(faq_no);
				faqsvc.deleteFaq(faq_no);
				
				/***************************3.�R������,�ǳ����(Send the Success view)***********/
				FaqtypService faq_typeSvc = new FaqtypService();
				if(requestURL.equals("/back-end/faq_type/listFaq_ByFaq_type.jsp") || requestURL.equals("/back-end/faq_type/listAllFaq_type.jsp"))
					req.setAttribute("listFaq_ByFaq_type",faq_typeSvc.getFaqsByFaqtyp_no(faqVO.getFaqtyp_no())); // ��Ʈw���X��list����,�s�Jrequest
				
				
				String url = requestURL;
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
