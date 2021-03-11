package com.faq.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.faq.model.*;
import com.faq_type.model.Faq_typeService;
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
					errorMsgs.add("請輸入編號");
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
					errorMsgs.add("編號格式不正確");
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
					errorMsgs.add("查無資料");
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
				errorMsgs.add("無法取得資料:" + e.getMessage());
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
				/***************************1.接收請求參數****************************************/
				Integer faq_no = new Integer(req.getParameter("faq_no"));
				
				/***************************2.開始查詢資料****************************************/
				FaqService faqSvc = new FaqService();
				FaqVO faqVO = faqSvc.getOneFaq(faq_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("faqVO", faqVO);         
				String url = "/back-end/faq/update_faq_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
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
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer faq_no = new Integer(req.getParameter("faq_no").trim());
				Integer faqtyp_no =new Integer(req.getParameter("faqtyp_no").trim());
				String faq_question = req.getParameter("faq_question");
				if(faq_question == null || faq_question.trim().length() == 0) {
					errorMsgs.add("問題：　請勿空白");
				}
				String faq_answer = req.getParameter("faq_answer");
				if(faq_answer == null || faq_answer.trim().length() == 0) {
					errorMsgs.add("回答：　請勿空白");
				}
				
				FaqVO faqVO= new FaqVO();
				faqVO.setFaq_no(faq_no);
				faqVO.setFaqtyp_no(faqtyp_no);
				faqVO.setFaq_question(faq_question);
				faqVO.setFaq_answer(faq_answer);
				
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("faqVO", faqVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/faq/update_faq_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				FaqService faqSvc = new FaqService();
				faqVO = faqSvc.updateFaq(faq_no, faqtyp_no, faq_question, faq_answer);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				Faq_typeService faq_typeSvc = new Faq_typeService();
				if(requestURL.equals("/back-end/faq_type/listFaq_ByFaq_type.jsp") || requestURL.equals("/back-end/faq_type/listAllFaq_type.jsp"))
					req.setAttribute("listFaq_ByFaq_type",faq_typeSvc.getFaqsByFaqtyp_no(faqtyp_no)); // 資料庫取出的list物件,存入request
					
				String url = requestURL+"?whichPage="+whichPage+"&faq_no="+faq_no;
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
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
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/

				Integer faqtyp_no =new Integer(req.getParameter("faqtyp_no").trim());
				String faq_question = req.getParameter("faq_question");
				if(faq_question == null || faq_question.trim().length() == 0) {
					errorMsgs.add("問題：　請勿空白");
				}
				String faq_answer = req.getParameter("faq_answer");
				if(faq_answer == null || faq_answer.trim().length() == 0) {
					errorMsgs.add("回答：　請勿空白");
				}
				

				FaqVO faqVO= new FaqVO();
				faqVO.setFaqtyp_no(faqtyp_no);
				faqVO.setFaq_question(faq_question);
				faqVO.setFaq_answer(faq_answer);


				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("faqVO", faqVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/faq/addFaq.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始新增資料***************************************/
				FaqService faqsvc = new FaqService();
				faqVO = faqsvc.addFaq(faqtyp_no, faq_question, faq_answer);
				/***************************3.新增完成,準備轉交(Send the Success view)***********/
				String url = "/back-end/faq/listAllFaq.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/faq/addFaq.jsp");
				failureView.forward(req, res);
			}			
		}
		if ("delete".equals(action)) { // 來自listAllEmp.jsp 或  /dept/listEmps_ByDeptno.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL"); // 送出刪除的來源網頁路徑: 可能為【/emp/listAllEmp.jsp】 或  【/dept/listEmps_ByDeptno.jsp】 或 【 /dept/listAllDept.jsp】 或 【 /emp/listEmps_ByCompositeQuery.jsp】

			try {
				/***************************1.接收請求參數***************************************/
				Integer faq_no = new Integer(req.getParameter("faq_no"));
				
				/***************************2.開始刪除資料***************************************/
				FaqService faqsvc = new FaqService();
				FaqVO faqVO = faqsvc.getOneFaq(faq_no);
				faqsvc.deleteFaq(faq_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/
				Faq_typeService faq_typeSvc = new Faq_typeService();
				if(requestURL.equals("/back-end/faq_type/listFaq_ByFaq_type.jsp") || requestURL.equals("/back-end/faq_type/listAllFaq_type.jsp"))
					req.setAttribute("listFaq_ByFaq_type",faq_typeSvc.getFaqsByFaqtyp_no(faqVO.getFaqtyp_no())); // 資料庫取出的list物件,存入request
				
				
				String url = requestURL;
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
