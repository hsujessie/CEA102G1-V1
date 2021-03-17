package com.member.controller;
import java.io.*;
import java.util.*;


import javax.servlet.*;
import javax.servlet.http.*;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.ServletOutputStream;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.servlet.http.*;
import com.member.model.*;


@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)


public class MemberServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
/***************************開始***********************************/		
					if ("getAll".equals(action)) {
///***************************開始查詢資料 ***************************/
														MemberJDBCDAO dao = new MemberJDBCDAO();
														List<MemberVO> list = dao.getAll();

///****************查詢完成,準備轉交(Send the Success view)***************/
//														HttpSession session = req.getSession();
														req.setAttribute("list", list);    // 資料庫取出的list物件,存入session
														String url = "/back-end/Member/listAllMember.jsp";
														RequestDispatcher successView = req.getRequestDispatcher(url);
														successView.forward(req, res);
														return;
														}
				
///***************************開始流程********************************/										
					if ("getOne_For_Update".equals(action)) { 

														List<String> errorMsgs = new LinkedList<String>();
														req.setAttribute("errorMsgs", errorMsgs);
														
														try {
///***************************1.接收請求參數***************************/
														Integer memNo = new Integer(req.getParameter("memNo"));

///***************************2.開始查詢資料***************************/
														MemberService memberService = new MemberService();
														MemberVO memberVO = memberService.getOneMember(memNo);

							//							BoardTypeService boardTypeService = new BoardTypeService();
							//							List<BoardTypeVO> boardTypeVOList = boardTypeService.getAll();
//										
///***************************3.查詢完成,準備轉交(Send the Success view)****/
//
														req.setAttribute("MemberVO", memberVO);         
							//							req.setAttribute("boardTypeVOList", boardTypeVOList);
							
														String url = "/back-end/Member/uptate_member_input.jsp";
														RequestDispatcher successView = req.getRequestDispatcher(url);
														successView.forward(req, res);
//
///***************************其他可能的錯誤處理*************************/
														} 
														catch (Exception e) {
															errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
															RequestDispatcher failureView = req
																	.getRequestDispatcher("/back-end/Member/listAllMember.jsp");
															failureView.forward(req, res);
														}
														}
//
///***************************完成一個流程*****************************/		
//					
					if ("update".equals(action)) { 
				
													List<String> errorMsgs = new LinkedList<String>();
													req.setAttribute("errorMsgs", errorMsgs);
												
													try {
///***************************1.接收請求參數 - 輸入格式的錯誤處理************/
													Integer memNo = new Integer(req.getParameter("memNo").trim());
													String memName = req.getParameter("memName").trim();
													String memAccount = req.getParameter("memAccount").trim();
													String memPassword = req.getParameter("memPassword").trim();
													String memMail = req.getParameter("memMail").trim();
													Integer memWallet = null;
															try {
																	memWallet = new Integer(req.getParameter("memWallet").trim());
																} catch (NumberFormatException e) {
																	errorMsgs.add("請填數,沒有請填0");
															}
													Integer memstatus = null;
															try {
																memstatus = new Integer(req.getParameter("memstatus").trim());
															} catch (NumberFormatException e) {
																memstatus = 0;
																errorMsgs.add("請填數字");
															}
													MemberService memberSvc = new MemberService();
														byte[] memImg = null;
														Part part = req.getPart("memImg");
																if (part.getSize() != 0) {
																	InputStream is = part.getInputStream();
																	memImg = new byte[is.available()];
																	is.read(memImg);
																	is.close();
																}else {
																	MemberVO memberVO = memberSvc.getOneMember(memNo);
																	memImg = memberVO.getMemImg();
																	memberVO.setMemImg(memImg);
													            }			
													MemberVO memberVO = new MemberVO();
													memberVO.setMemNo(memNo);
													memberVO.setMemName(memName);
													memberVO.setMemAccount(memAccount);
													memberVO.setMemPassword(memPassword);
													memberVO.setMemMail(memMail);
													memberVO.setMemWallet(memWallet);
													memberVO.setMemstatus(memstatus);
													memberVO.setMemImg(memImg);
													
													
													if (!errorMsgs.isEmpty()) {
														req.setAttribute("MemberVO", memberVO); // 含有輸入格式錯誤的empVO物件,也存入req
														RequestDispatcher failureView = req
																.getRequestDispatcher("/back-end/Member/uptate_member_input.jsp");
														failureView.forward(req, res);
														return; //程式中斷
													}
///***************************2.開始修改資料******************************/
//							
						//							MemberService memberSvc = new MemberService();
													memberSvc.updateMember(memNo,memName,memAccount,memPassword,memMail,memWallet,memstatus,memImg);
						
													memberVO = memberSvc.getOneMember(memNo);
						
///***************************3.修改完成,準備轉交(Send the Success view)*******/
													req.setAttribute("MemberVO", memberVO);
						
													String url = "/back-end/Member/listOneMember.jsp";
													RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
													successView.forward(req, res);
//
///***************************其他可能的錯誤處理****************************/
													} catch (Exception e) {
														errorMsgs.add("修改資料失敗:"+e.getMessage());
														RequestDispatcher failureView = req
																.getRequestDispatcher("/back-end/Member/uptate_member_input.jsp");
														failureView.forward(req, res);
													}
													}
///***************************完成一個流程*****************************/
					
					if ("updateFront".equals(action)) { 
						
												List<String> errorMsgs = new LinkedList<String>();
												req.setAttribute("errorMsgs", errorMsgs);
											
												try {
///***************************1.接收請求參數 - 輸入格式的錯誤處理************/
//													<還需要加判斷>
												Integer memNo = new Integer(req.getParameter("memNo").trim());
												String memName = req.getParameter("memName").trim();              
												String memAccount = req.getParameter("memAccount").trim();
												String memPassword = req.getParameter("memPassword").trim();      
												String memMail = req.getParameter("memMail").trim();
												Integer memWallet = new Integer(req.getParameter("memWallet").trim());
												Integer memstatus = new Integer(req.getParameter("memstatus").trim());
												MemberService memberSvc = new MemberService();                           
													byte[] memImg = null;
													Part part = req.getPart("memImg");
															if (part.getSize() != 0) {
																InputStream is = part.getInputStream();
																memImg = new byte[is.available()];
																is.read(memImg);
																is.close();
															}else {
																MemberVO memberVO = memberSvc.getOneMember(memNo);
																memImg = memberVO.getMemImg();
																memberVO.setMemImg(memImg);
												            }
															
												MemberVO memberVO = new MemberVO();
												memberVO.setMemNo(memNo);
												memberVO.setMemName(memName);
												memberVO.setMemAccount(memAccount);
												memberVO.setMemPassword(memPassword);
												memberVO.setMemMail(memMail);
												memberVO.setMemWallet(memWallet);
												memberVO.setMemstatus(memstatus);
												memberVO.setMemImg(memImg);
																							
												if (!errorMsgs.isEmpty()) {
													req.setAttribute("MemberVO", memberVO);
													RequestDispatcher failureView = req
															.getRequestDispatcher("/back-end/Member/uptate_member_input.jsp");
													failureView.forward(req, res);
													return; 
												}
///***************************2.開始修改資料******************************/
												
											memberSvc.updateMember(memNo,memName,memAccount,memPassword,memMail,memWallet,memstatus,memImg);
				
											memberVO = memberSvc.getOneMember(memNo);
											
///***************************3.修改完成,準備轉交(Send the Success view)*******/
											req.setAttribute("MemberVO", memberVO);
					
											String url = "/front-end/Member_Login/front_listOneMember.jsp";
											RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
											successView.forward(req, res);
											
///***************************其他可能的錯誤處理****************************/
											} catch (Exception e) {
												errorMsgs.add("修改資料失敗:"+e.getMessage());
												RequestDispatcher failureView = req
														.getRequestDispatcher("/back-end/Member/uptate_member_input.jsp");
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
//							
///***************************2.開始刪除資料**************************/
													MemberJDBCDAO memberJDBCDAO = new MemberJDBCDAO();
													memberJDBCDAO.delete(memNo);
													List<MemberVO> list = memberJDBCDAO.getAll();
													req.setAttribute("list", list);
//							
///***************************3.刪除完成,準備轉交(Send the Success view)****/								
													String url = "/back-end/Member/listAllMember.jsp";
													RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
													successView.forward(req, res);
//							
///***************************其他可能的錯誤處理************************/
												} catch (Exception e) {
													errorMsgs.add("刪除資料失敗:"+e.getMessage());
													RequestDispatcher failureView = req
															.getRequestDispatcher("/back-end/Member/listAllMember.jsp");
													failureView.forward(req, res);
													}
												}
/***************************完成一個流程****************************/			
		
					if ("insert".equals(action)) { 
						
											List<String> errorMsgs = new LinkedList<String>();
											req.setAttribute("errorMsgs", errorMsgs);
					
											try {
						
///***********************1.接收請求參數 - 輸入格式的錯誤處理***************/
//						
				//							<名稱>
											String memName = req.getParameter("memName").trim();
											String memNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
											if (memName == null || memName.trim().isEmpty()) {
												errorMsgs.add("會員姓名: 請勿空白");
											} 
											else if (!memName.trim().matches(memNameReg)) { // 以下練習正則(規)表示式(regular-expression)
												errorMsgs.add("會員姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
											}
				//							<帳號>	
											String memAccount = req.getParameter("memAccount").trim();	
											String memAccountReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
											if (memAccount == null || memAccount.trim().isEmpty()) {
												errorMsgs.add("會員帳號請勿空白");
											}
											else if (!memAccount.trim().matches(memAccountReg)) { 
											    errorMsgs.add("會員帳號: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
											}
				//							<密碼>
											String memPassword = req.getParameter("memPassword").trim();	
											String memPasswordReg = "^[(a-zA-Z0-9)]{2,12}$";
											if (memPassword == null || memPassword.trim().isEmpty()) {
												errorMsgs.add("會員密碼請勿空白");
											}
											else if (!memPassword.trim().matches(memPasswordReg)) { 
											    errorMsgs.add("會員密碼: 只能是英文字母、數字 , 且長度必需在2到10之間");
											}	
											
				//							<mail>
											String memMail = req.getParameter("memMail").trim();	
											String memMailReg = "^[(a-zA-Z0-9@_)]{2,10}$";
											if (memPassword == null || memPassword.trim().isEmpty()) {
												errorMsgs.add("會員信箱請勿空白");
											}
											else if (!memPassword.trim().matches(memPasswordReg)) { 
											    errorMsgs.add("會員信箱: 只能是英文字母、數字和@_ , 且長度必需在2到10之間");
											}	
				//							<錢包>
				//							Integer memWallet = null;
				//							try {
				//								memWallet = new Integer(req.getParameter("memWallet").trim());
				//								memWallet= 0;
				//							} catch (NumberFormatException e) {
				//								errorMsgs.add("請填數字,");
				//							}
				
				//							<狀態>
				//							Integer memstatus = null;
				//							try {
				//								memstatus = new Integer(req.getParameter("memStatus").trim());
				//							} catch (NumberFormatException e) {
				//								memstatus = 0;
				//								errorMsgs.add("memstatus請填數字");
				//							}
											
				//							<頭像>
											byte[] memImg = null;
											Part part = req.getPart("memImg");
											InputStream is = part.getInputStream();
											memImg = new byte[is.available()];
											is.read(memImg);
											is.close();
											
											MemberVO memberVO = new MemberVO();
											memberVO.setMemName(memName);
											memberVO.setMemAccount(memAccount);
											memberVO.setMemPassword(memPassword);
											memberVO.setMemMail(memMail);
				//							memberVO.setMemWallet(memWallet);
				//							memberVO.setMemstatus(memstatus);
											memberVO.setMemImg(memImg);
										
											MemberService memberSvc = new MemberService();
											List<MemberVO> list = memberSvc.getAll();
											if (list.contains(memberVO)) {     							//contains集合的方法，比較VO裡面的值有沒有重複
												errorMsgs.add("帳號請勿重複");
											}
											
											if (!errorMsgs.isEmpty()) {					
												req.setAttribute("MemberVO", memberVO); 
												RequestDispatcher failureView = req
														.getRequestDispatcher("/front-end/Member_Login/login.jsp");
												failureView.forward(req, res);
												return;
											}
				
///***************************2.開始新增資料****************************/
																
											memberVO = memberSvc.addMember(memName, memAccount,memPassword,memMail,memImg);
						
//											List<MemberVO> list = memberSvc.getAll();
											MemberVO getmemNo = list.get(list.size()-1);
											req.setAttribute("MemberVO", getmemNo);
											req.setAttribute("list", list);

///***************************3.新增完成,準備轉交(Send the Success view)****/
											String url = "/front-end/Member_Login/listOneMember2.jsp";
											RequestDispatcher successView = req.getRequestDispatcher(url); 			// 新增成功後轉交listAllEmp.jsp
											successView.forward(req, res);				
				
///***************************其他可能的錯誤處理************************/
											}catch (Exception e) {
												errorMsgs.add(e.getMessage());
												RequestDispatcher failureView = req
														.getRequestDispatcher("/front-end/Member_Login/login.jsp");
												failureView.forward(req, res);
											}
											}
				
/***************************完成一個流程***************************/		
					if ("get_Login".equals(action)) { 																					// 來自login.登入的請求
		
										List<String> errorMsgs = new LinkedList<String>();
										req.setAttribute("errorMsgs", errorMsgs);
										try {
/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
											String strAccount = req.getParameter("memAccount");
				
											if (strAccount == null || (strAccount.trim()).isEmpty()) {
												errorMsgs.add("請輸入帳號");
											}
										
											if (!errorMsgs.isEmpty()) {
												RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Member_Login/login.jsp");
												failureView.forward(req, res);
												return;
											}
							
										String memAccount = null;
											try {
													memAccount = new String(strAccount);
											} catch (Exception e) {
													errorMsgs.add("帳號不正確");
												}
										
										if (!errorMsgs.isEmpty()) {
												RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Member_Login/login.jsp");
												failureView.forward(req, res);
												return;// 程式中斷
											}
								
											String strPassword = req.getParameter("memPassword");
											if (strPassword == null || (strPassword.trim()).isEmpty()) {
												errorMsgs.add("請輸入密碼");
											}
											
											if (!errorMsgs.isEmpty()) {
												RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Member_Login/login.jsp");
												failureView.forward(req, res);
												return;// 程式中斷
											}
								
											String memPassword = null;
											try {
												memPassword = new String(strPassword);
											} catch (Exception e) {
												errorMsgs.add("密碼不正確");
												}
											
											if (!errorMsgs.isEmpty()) {
												RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Member_Login/login.jsp");
												failureView.forward(req, res);
												return;
											}
							
				
/*************************** 2.開始查詢資料 *****************************************/
										MemberService memSvc = new MemberService();
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
										req.setAttribute("MemberVO", memberVO); 
										HttpSession session = req.getSession(); 			 //丟置前台登入資訊給大家
										session.setAttribute("MemberVO", memberVO); 
										String url = "/front-end/index.jsp";
										res.sendRedirect(req.getContextPath() + url);
										
/*************************** 其他可能的錯誤處理 *************************************/
										} catch (Exception e) {
											errorMsgs.add("無法取得資料:" + e.getMessage());
											RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Member_Login/login.jsp");
											failureView.forward(req, res);
										}
										}
/***************************完成一個流程****************************/	
					if ("delete".equals(action)) { 

										List<String> errorMsgs = new LinkedList<String>();
										req.setAttribute("errorMsgs", errorMsgs);
						
										try {
/***************************1.接收請求參數**************************/
										Integer memNo = new Integer(req.getParameter("memNo"));
					
/***************************2.開始刪除資料**************************/
										MemberJDBCDAO memberJDBCDAO = new MemberJDBCDAO();
										memberJDBCDAO.delete(memNo);
										
										List<MemberVO> list = memberJDBCDAO.getAll();
										req.setAttribute("list", list);
					
/***************************3.刪除完成,準備轉交(Send the Success view)****/								
										String url = "/back-end/Board/listAllBoard.jsp";
										RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
										successView.forward(req, res);
					
/***************************其他可能的錯誤處理************************/
										} catch (Exception e) {
											errorMsgs.add("刪除資料失敗:"+e.getMessage());
											RequestDispatcher failureView = req
													.getRequestDispatcher("/back-end/Member/listAllMember.jsp");
											failureView.forward(req, res);
										}
										}
/***************************完成一個流程****************************/		
					if ("signup".equals(action)) { 
				
								List<String> errorMsgs = new LinkedList<String>();
								req.setAttribute("errorMsgs", errorMsgs);

								try {
/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
//									<姓名>
									String memName = req.getParameter("memName");
									String memNameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
									if (memName == null || memName.trim().isEmpty()) {
										errorMsgs.add("會員姓名: 請勿空白");
									} else if (!memName.trim().matches(memNameReg)) { 
										errorMsgs.add("會員姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
									}

//									<帳號>
									String memAccount = req.getParameter("memAccount").trim();
									String memAccountReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
									if (memAccount == null || memAccount.trim().isEmpty()) {
										errorMsgs.add("會員帳號: 請勿空白");
									} else if (!memAccount.trim().matches(memAccountReg)) { // 以下練習正則(規)表示式(regular-expression)
										errorMsgs.add("會員帳號: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
									}

//									<密碼>
									String memPassword = req.getParameter("memPassword").trim();
									if (memPassword == null || memPassword.trim().isEmpty()) {
										errorMsgs.add("密碼請勿空白");
									}
								
//									<mail>
									String memMail = req.getParameter("memMail").trim();
									if (memMail == null || memMail.trim().isEmpty()) {
										errorMsgs.add("信箱請勿空白");
									}
									
//									<頭像>
									byte[] memImg = null;
									Part part = req.getPart("memImg");
									InputStream is = part.getInputStream();
									memImg = new byte[is.available()];
									is.read(memImg);
									is.close();
			
									MemberVO memberVO = new MemberVO();
									memberVO.setMemAccount(memAccount);
									memberVO.setMemName(memName);
									memberVO.setMemPassword(memPassword);
									memberVO.setMemMail(memMail);
									memberVO.setMemImg(memImg);
									
//									<查詢有無重複帳號>				
									MemberService memSvc = new MemberService();
									List<MemberVO> list = memSvc.getAll();
									if (list.contains(memberVO)) {
										errorMsgs.add("帳號請勿重複");
									}
								
//									<錯誤資料顯示在登入畫面>
									if (!errorMsgs.isEmpty()) {
										req.setAttribute("MemberVO", memberVO); 
										RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Member_Login/login.jsp");
										failureView.forward(req, res);
										return;
									}
	
/*************************** 2.開始修改資料 *****************************************/

									
									String memuuid = UUID.randomUUID().toString();
									
									memberVO = memSvc.addSignUp(memName, memAccount, memPassword, memMail, memImg,memuuid);
				
									 MailService2 mailSvc = new MailService2();
								        String subject = "會員註冊驗證通知";
								        
								        String link = "http://localhost:8081/Seenema/Member/member.do?action=updateStatus&memuuid="+memberVO.getMemuuid();
				
										mailSvc.sendMail(memberVO.getMemMail(), subject, mailSvc.getMessageText(memberVO.getMemName(),link));
									
/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
									String url = "/front-end/Member_Login/login_success_page.jsp";  
									
//									req.setAttribute("list", list);
									RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交login_success_page.jsp.jsp
									successView.forward(req, res);
									
/*************************** 其他可能的錯誤處理 **********************************/
				
								} catch (Exception e) {
									errorMsgs.add(e.getMessage());
									RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Member_Login/login.jsp");
									failureView.forward(req, res);
								}
				

							}		
/***************************完成一個流程****************************/				
					if ("updateStatus".equals(action)) { //更新狀態,還無法用

									List<String> errorMsgs = new LinkedList<String>();
									req.setAttribute("errorMsgs", errorMsgs);
					
									try {
										/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
										String memuuid = new String(req.getParameter("memuuid"));
									
										MemberVO memberVO = new MemberVO();	
										memberVO.setMemstatus(1);
										memberVO.setMemuuid(memuuid);
					
/*************************** 2.開始修改資料 *****************************************/
										
										MemberService memberSvc = new MemberService();	
										memberSvc.updateStatus(memberVO);
					
/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
										String url = "/front-end/Member_Login/login_success_page.jsp";
										RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交login_success_page.jsp.jsp
										successView.forward(req, res);
/*************************** 其他可能的錯誤處理 **********************************/
					
									} catch (Exception e) {
										errorMsgs.add(e.getMessage());
										RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Member_Login/login.jsp");
										failureView.forward(req, res);
									}
					
									}
/***************************完成一個流程****************************************/		
					if ("forgot_password".equals(action)) {																 // 忘記密碼

										List<String> errorMsgs = new LinkedList<String>();
										req.setAttribute("errorMsgs", errorMsgs);
										try {
/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
										String Account = req.getParameter("memAccount");

										if (Account == null || (Account.trim()).isEmpty()) {
											errorMsgs.add("請輸入帳號");
										}
										
										if (!errorMsgs.isEmpty()) {
											RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Member_Login/forgot_password.jsp");
											failureView.forward(req, res);
											return;// 程式中斷
										}
						
										String memAccount = null;
										try {
											memAccount = new String(Account);
										} catch (Exception e) {
											errorMsgs.add("帳號不正確");
										}
										
										if (!errorMsgs.isEmpty()) {
											RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Member_Login/forgot_password.jsp");
											failureView.forward(req, res);
											return;// 程式中斷
										}
						
										String Mail = req.getParameter("memMail");
										if (Mail == null || (Mail.trim()).isEmpty()) {
											errorMsgs.add("請輸入信箱");
										}
								
										if (!errorMsgs.isEmpty()) {
											RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Member_Login/forgot_password.jsp");
											failureView.forward(req, res);
											return;// 程式中斷
										}
						
										String memMail = null;
										try {
											memMail = new String(Mail);
										} catch (Exception e) {
											errorMsgs.add("信箱不正確");
										}
										
										if (!errorMsgs.isEmpty()) {
											RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Member_Login/forgot_password.jsp");
											failureView.forward(req, res);
											return;// 程式中斷
										}
/*************************** 2.開始查詢資料 *****************************************/
										MemberService memberSvc = new MemberService();
										MemberVO memberVO = memberSvc.getOneAccountMail(memAccount, memMail);
										
										if (memberVO == null) {
											errorMsgs.add("無此帳號信箱,請重新輸入");
										}
																			
										if (!errorMsgs.isEmpty()) {
											RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Member_Login/forgot_password.jsp");
											failureView.forward(req, res);
											return;// 程式中斷
										}
										
										String memuuid = UUID.randomUUID().toString();        
									        
									        memberVO.setMemuuid(memuuid);
									        memberSvc.updateUuid(memberVO);
								       
									        MailService2 mailSvc = new MailService2();       				//寄信
									        String subject = "忘記啟用通知";
									        String link = "http://localhost:8081/Seenema/Member/member.do?action=forgot_update&memuuid="+memberVO.getMemuuid();
									        mailSvc.sendMail(memberVO.getMemMail(), subject, mailSvc.getMessageText2(memberVO.getMemName(),link));
											
/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
										String success = "修改連結已成功寄出!!!";
										req.setAttribute("success", success);
										String url = "/front-end/Member_Login/front_uptate_letter.jsp";  //回到首頁才對!!
										RequestDispatcher successView = req.getRequestDispatcher(url);
										successView.forward(req, res);
										
/*************************** 其他可能的錯誤處理 *************************************/
										} catch (Exception e) {
											errorMsgs.add("無法取得資料:" + e.getMessage());
											RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Member_Login/login.jsp");
											failureView.forward(req, res);
										}
										}		
/***************************完成一個流程****************************/		
					if ("forgot_update".equals(action)) { 
													List<String> errorMsgs = new LinkedList<String>();
													req.setAttribute("errorMsgs", errorMsgs);
									
													try {
/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
														String memuuid = new String(req.getParameter("memuuid"));
									
/*************************** 2.開始修改資料 *****************************************/
														
														MemberService memberSvc = new MemberService();
														MemberVO memberVO = new MemberVO();	
														memberVO = memberSvc.getAllForUuid(memuuid);
														req.setAttribute("MemberVO", memberVO);
														
/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
														
														String url = "/front-end/Member_Login/front_forgot_Memuptate.jsp";
														RequestDispatcher successView = req.getRequestDispatcher(url); // login_success.jsp
														successView.forward(req, res);
									
/*************************** 其他可能的錯誤處理 **********************************/
									
													} catch (Exception e) {
														errorMsgs.add(e.getMessage());
														RequestDispatcher failureView = req.getRequestDispatcher("/front-end/Member_Login/login.jsp");
														failureView.forward(req, res);
														}
									
													}
			
/***************************完成一個流程****************************/			
			
//			if ("listMembers_ByCompositeQuery".equals(action)) { 											// 來自select_page.jsp的複合查詢請求
//				List<String> errorMsgs = new LinkedList<String>();
//				req.setAttribute("errorMsgs", errorMsgs);
//
//				try {
//					
///***************************1.將輸入資料轉為Map**********************************/ 
//					//採用Map<String,String[]> getParameterMap()的方法 
//					//注意:an immutable java.util.Map 
//					Map<String, String[]> map = req.getParameterMap();
//					
///***************************2.開始複合查詢***************************************/
//					MemberServic memberSvc = new MemberServic();
//					List<MemberServic> list  = memberSvc.getAll(map);
//					
///***************************3.查詢完成,準備轉交(Send the Success view)************/
//					req.setAttribute("listEmps_ByCompositeQuery", list); // 資料庫取出的list物件,存入request
//					RequestDispatcher successView = req.getRequestDispatcher("/emp/listEmps_ByCompositeQuery.jsp"); // 成功轉交listEmps_ByCompositeQuery.jsp
//					successView.forward(req, res);
//					
///***************************其他可能的錯誤處理**********************************/
//				} catch (Exception e) {
//					errorMsgs.add(e.getMessage());
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/select_page.jsp");
//					failureView.forward(req, res);
//				}
//			}				
			
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
