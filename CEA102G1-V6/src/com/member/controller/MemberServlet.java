package com.member.controller;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.board.model.BoardVO;
import com.member.model.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

public class MemberServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
/***************************開始流程***************************/		
					if ("getAll".equals(action)) {
/***************************開始查詢資料 ***************************/
									MemberJDBCDAO dao = new MemberJDBCDAO();
									List<MemberVO> list = dao.getAll();

/****************查詢完成,準備轉交(Send the Success view)***************/
//									HttpSession session = req.getSession();
									req.setAttribute("list", list);    // 資料庫取出的list物件,存入session
									String url = "/back_end/Member/listAllMember.jsp";
									RequestDispatcher successView = req.getRequestDispatcher(url);
									successView.forward(req, res);
									return;
								}
					
/***************************開始流程********************************/										
					if ("getOne_For_Update".equals(action)) { 
			
						List<String> errorMsgs = new LinkedList<String>();
						req.setAttribute("errorMsgs", errorMsgs);
						
						try {
/***************************1.接收請求參數***************************/
							Integer memNo = new Integer(req.getParameter("memNo"));

/***************************2.開始查詢資料***************************/
							MemberServic memberServic = new MemberServic();
							MemberVO memberVO = memberServic.getOneMember(memNo);
//							BoardTypeService boardTypeService = new BoardTypeService();
//							List<BoardTypeVO> boardTypeVOList = boardTypeService.getAll();
										
/***************************3.查詢完成,準備轉交(Send the Success view)****/

							req.setAttribute("MemberVO", memberVO);         
//							req.setAttribute("boardTypeVOList", boardTypeVOList);

							String url = "/back_end/Member/uptate_member_input.jsp";
							RequestDispatcher successView = req.getRequestDispatcher(url);
							successView.forward(req, res);

/***************************其他可能的錯誤處理*************************/
						} 
						catch (Exception e) {
							errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
							RequestDispatcher failureView = req
									.getRequestDispatcher("/back_end/Member/listAllMember.jsp");
							failureView.forward(req, res);
						}
					}

///***************************完成一個流程*****************************/		
					
					if ("update".equals(action)) { 
					
						List<String> errorMsgs = new LinkedList<String>();
					
						req.setAttribute("errorMsgs", errorMsgs);
					
						try {
/***************************1.接收請求參數 - 輸入格式的錯誤處理************/
							Integer memNo = new Integer(req.getParameter("memNo").trim());
							
							String memName = req.getParameter("memName").trim();
							if (memName == null || memName.trim().length() == 0) {
								errorMsgs.add("姓名請勿空白");
							}	
							
							String memAccount = req.getParameter("memAccount").trim();
							if (memAccount == null || memAccount.trim().length() == 0) {
								errorMsgs.add("帳號請勿空白");
							}	
							String memPassword = req.getParameter("memPassword").trim();
							if (memPassword == null || memPassword.trim().length() == 0 ) {
								errorMsgs.add("密碼請勿空白");
							}	
							String memMail = req.getParameter("memMail").trim();
							if (memMail == null || memMail.trim().length() == 0) {
								errorMsgs.add("mail請勿空白");
							}	
							
							MemberVO memberVO = new MemberVO();
							memberVO.setMemName(memName);
							memberVO.setMemAccount(memAccount);
							memberVO.setMemPassword(memPassword);
							memberVO.setMemMail(memMail);
							memberVO.setMemAccount(memAccount);
							memberVO.setMemAccount(memAccount);
							memberVO.setMemAccount(memAccount);
							
							
							if (!errorMsgs.isEmpty()) {
								req.setAttribute("MemberVO", memberVO); // 含有輸入格式錯誤的empVO物件,也存入req
								RequestDispatcher failureView = req
										.getRequestDispatcher("/back_end/Member/uptate_member_input.jsp/");
								failureView.forward(req, res);
								return; //程式中斷
							}
///***************************2.開始修改資料******************************/
							

							MemberServic memberSvc = new MemberServic();
							memberSvc.updateMember(memNo,memName,memAccount,memPassword,memMail);
							memberVO = memberSvc.getOneMember(memNo);
							
///***************************3.修改完成,準備轉交(Send the Success view)*******/
							req.setAttribute("MemberVO", memberVO);
							String url = "/back_end/Member/listOneMember.jsp";
							RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
							successView.forward(req, res);

///***************************其他可能的錯誤處理****************************/
						} catch (Exception e) {
							errorMsgs.add("修改資料失敗:"+e.getMessage());
							RequestDispatcher failureView = req
									.getRequestDispatcher("/back_end/Member/update_member_input.jsp");
							failureView.forward(req, res);
						}
					}
///***************************完成一個流程*****************************/		
					if ("delete".equals(action)) { 

						List<String> errorMsgs = new LinkedList<String>();
						req.setAttribute("errorMsgs", errorMsgs);

						try {
///***************************1.接收請求參數**************************/
							Integer memNo = new Integer(req.getParameter("memNo"));
							
///***************************2.開始刪除資料**************************/
							MemberJDBCDAO memberJDBCDAO = new MemberJDBCDAO();
							memberJDBCDAO.delete(memNo);
							
							
							List<MemberVO> list = memberJDBCDAO.getAll();
							req.setAttribute("list", list);
							
///***************************3.刪除完成,準備轉交(Send the Success view)****/								
							String url = "/back_end/Member/listAllMember.jsp";
							RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
							successView.forward(req, res);
//							
///***************************其他可能的錯誤處理************************/
						} catch (Exception e) {
							errorMsgs.add("刪除資料失敗:"+e.getMessage());
							RequestDispatcher failureView = req
									.getRequestDispatcher("/back_end/Member/listAllMember.jsp");
							failureView.forward(req, res);
						}
					}
/***************************完成一個流程****************************/			
		
					if ("insert".equals(action)) { // 來自addEmp.jsp的請求  
						
						List<String> errorMsgs = new LinkedList<String>();
						req.setAttribute("errorMsgs", errorMsgs);

						try {
//						
///***********************1.接收請求參數 - 輸入格式的錯誤處理***************/
//						
//
							String memName = req.getParameter("memName").trim();
							String memNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
							if (memName == "null" || memName.trim().isEmpty()) {
								errorMsgs.add("會員姓名請勿空白");
							} 
								
//							 else (!memName.trim().matches(memNameReg)) { // 以下練習正則(規)表示式(regular-expression)
//							    errorMsgs.add("會員姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
//							}
//								
							String memAccount = req.getParameter("memAccount").trim();	
							String memAccountReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
							if (memName == null || memName.trim().isEmpty()) {
								errorMsgs.add("會員帳號請勿空白");
							}
////							 else (!memName.trim().matches(memNameReg)) { 
////							    errorMsgs.add("會員姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
////							}	
							String memPassword = req.getParameter("memPassword").trim();	
//							String memPasswordReg = "^[(a-zA-Z0-9_)]{2,12}$";
							if (memName == null || memName.trim().isEmpty()) {
								errorMsgs.add("會員密碼請勿空白");
							}	
							String memMail = req.getParameter("memMail").trim();	
							String memMailReg = "^[(a-zA-Z0-9@)]{2,10}$";
							if (memName == null || memName.trim().isEmpty()) {
								errorMsgs.add("會員mail請勿空白");
							}	
//							
							MemberVO memberVO = new MemberVO();
							memberVO.setMemName(memName);
							memberVO.setMemAccount(memAccount);
							memberVO.setMemPassword(memPassword);
							memberVO.setMemMail(memMail);
							memberVO.setMemAccount(memAccount);
							memberVO.setMemAccount(memAccount);
							memberVO.setMemAccount(memAccount);
//							
//							
							if (!errorMsgs.isEmpty()) {					
								req.setAttribute("MemberVO", memberVO); 
								RequestDispatcher failureView = req
										.getRequestDispatcher("/back_end/Member/addMember.jsp");
								failureView.forward(req, res);
								return;
							}
//							
///***************************2.開始新增資料****************************/
							MemberServic memberSvc = new MemberServic();							//使用BoardService的addBoard方法將值傳到前端
							memberVO = memberSvc.addMember(memName, memAccount,memPassword,memMail);
							req.setAttribute("MemberVO", memberVO);
							
//							List<MemberVO> list = memberSvc.getAll();
//							req.setAttribute("list", list);
//							
///***************************3.新增完成,準備轉交(Send the Success view)****/
							String url = "/back_end/Member/listAllMember.jsp";
							RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
							successView.forward(req, res);				
//							
///***************************其他可能的錯誤處理************************/
						}catch (Exception e) {
							errorMsgs.add(e.getMessage());
							RequestDispatcher failureView = req
									.getRequestDispatcher("/back_end/Member/addMember.jsp");
							failureView.forward(req, res);
						}
					}
/***************************完成一個流程***************************/		
					if ("get_Login".equals(action)) { // 來自login.登入的請求
						
						List<String> errorMsgs = new LinkedList<String>();
						req.setAttribute("errorMsgs", errorMsgs);
					try {
	/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
							String str = req.getParameter("memAccount");
							
	//System.out.println(str);
							if (str == null || (str.trim()).isEmpty()) {
								errorMsgs.add("請輸入帳號");
							}
							
							if (!errorMsgs.isEmpty()) {
								RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Member_Login/login.jsp");
								failureView.forward(req, res);
								return;
							}
				
							String memAccount = null;
							try {
									memAccount = new String(str);
								} catch (Exception e) {
									errorMsgs.add("帳號不正確");
								}
								if (!errorMsgs.isEmpty()) {
									RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Member_Login/login.jsp");
									failureView.forward(req, res);
									return;// 程式中斷
								}
					
								String pas = req.getParameter("memPassword");
								if (pas == null || (pas.trim()).isEmpty()) {
									errorMsgs.add("請輸入密碼");
								}
								
								if (!errorMsgs.isEmpty()) {
									RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Member_Login/login.jsp");
									failureView.forward(req, res);
									return;// 程式中斷
								}
					
								String memPassword = null;
								try {
									memPassword = new String(pas);
								} catch (Exception e) {
									errorMsgs.add("密碼不正確");
								}
								
								if (!errorMsgs.isEmpty()) {
									RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Member_Login/login.jsp");
									failureView.forward(req, res);
									return;
								}
								
					
	/*************************** 2.開始查詢資料 *****************************************/
								MemberServic memSvc = new MemberServic();
								MemberVO memberVO = memSvc.getOneAccount(memAccount,memPassword);
								if (memberVO == null) {
									errorMsgs.add("帳號密碼錯誤,請重新登錄");
								}
								
								if (!errorMsgs.isEmpty()) {
									RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Member_Login/login.jsp");
									failureView.forward(req, res);
									return;// 程式中斷
								}
					
								/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
								req.setAttribute("MemberVO", memberVO); // 資料庫取出的memVO物件,存入req
								HttpSession session = req.getSession();
								session.setAttribute("MemberVO", memberVO); // 資料庫取出的memVO物件,存入session
								String url = "/front-end/Member_Login/login_success.jsp";
								RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 login_success.jsp
								successView.forward(req, res);
				
							/*************************** 其他可能的錯誤處理 *************************************/
							} catch (Exception e) {
								errorMsgs.add("無法取得資料:" + e.getMessage());
								RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Member_Login/login.jsp");
								failureView.forward(req, res);
							}
						}
					
					if("ajaxGetMemberName".equals(action)) {
						res.setCharacterEncoding("UTF-8");
						res.setContentType("text; charset=utf-8");
						PrintWriter out = res.getWriter();
						Integer memNo = new Integer(req.getParameter("memNo").trim());
						MemberServic memSvc = new MemberServic();
						String memName = memSvc.getOneMember(memNo).getMemName();
						out.print(memName);
						return;
					}
		
		
		
		
		
		
		
		
		
		
		
		
		
		
//==================================結束==============================		
	}
}
