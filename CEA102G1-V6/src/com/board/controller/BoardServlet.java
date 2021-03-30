package com.board.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import com.board.model.BoardJDBCDAO;
import com.board.model.*;

import com.board.model.BoardVO;
import com.board_type.model.BoardTypeJDBCDAO;
import com.board_type.model.BoardTypeService;
import com.board_type.model.BoardTypeVO;




	public class BoardServlet extends HttpServlet {

		public void doGet(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException {
			doPost(req, res);
		}

		public void doPost(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException {

			req.setCharacterEncoding("UTF-8");
			String action = req.getParameter("action");
/*****************************************************************/			
					if ("getAllA".equals(action)) {
/***************************開始查詢資料 *****************************/
						BoardTypeService boardTypeService = new BoardTypeService();
						List<BoardTypeVO> boardTypeVOList = boardTypeService.getAll();
						
/****************查詢完成,準備轉交(Send the Success view)*************/
						req.setAttribute("boardTypeVOList", boardTypeVOList);
					
						String url = "/back-end/board/addBoard_new.jsp";
						RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交listAllEmp2_getFromSession.jsp
						successView.forward(req, res);
						return;
					}
		
/***************************完成一個流程***************************/
					if ("getAll".equals(action)) {
/***************************開始查詢資料 ***************************/
						BoardJNDIDAO dao = new BoardJNDIDAO();
						List<BoardVO> list = dao.getAll();
		
/****************查詢完成,準備轉交(Send the Success view)***************/
						HttpSession session = req.getSession();
						
						req.setAttribute("list", list); 
						
						String url = "/back-end/board/listAllBoard_new.jsp";
						RequestDispatcher successView = req.getRequestDispatcher(url);
						successView.forward(req, res);
						return;
					}
/***************************完成一個流程***************************/
					if ("getOne_For_Display".equals(action)) { 
						List<String> errorMsgs = new LinkedList<String>();
						
						req.setAttribute("errorMsgs", errorMsgs);

						try {
/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
							String str = req.getParameter("boaNo");
							if (str == null || (str.trim()).length() == 0) {
								errorMsgs.add("請輸入公告編號");
							}
							if (!errorMsgs.isEmpty()) {
								RequestDispatcher failureView = req
										.getRequestDispatcher("/back-end/board/listAllBoard_new.jsp");
								failureView.forward(req, res);
								return;
							}
							
							Integer boaNo = null;
							try {
								boaNo = new Integer(str);
							} catch (Exception e) {
								errorMsgs.add("公告編號格式不正確");
							}
							if (!errorMsgs.isEmpty()) {
								RequestDispatcher failureView = req
										.getRequestDispatcher("/back-end/board/listAllBoard_new.jsp");
								failureView.forward(req, res);
								return;
							}
/***************************2.開始查詢資料***********************/
							BoardJNDIDAO dao = new BoardJNDIDAO();
							List<BoardVO> boardNolist = dao.findByPrimaryKey2(boaNo);
							if (boardNolist == null) {
								errorMsgs.add("查無資料");
							}
							
							if (!errorMsgs.isEmpty()) {
								RequestDispatcher failureView = req
										.getRequestDispatcher("/back-end/board/listAllBoard_new.jsp");
								failureView.forward(req, res);
								return;
							}
/**************3.查詢完成,準備轉交(Send the Success view)*************/
							req.setAttribute("BoardNolist", boardNolist);
//							req.setAttribute("BoardVO", boardVO);
							String url = "/back-end/board/listOneBoard_new.jsp";
							RequestDispatcher successView = req.getRequestDispatcher(url);
							successView.forward(req, res);
/***************************其他可能的錯誤處理************************/
						} catch (Exception e) {
							errorMsgs.add("無法取得資料:" + e.getMessage());
							RequestDispatcher failureView = req
									.getRequestDispatcher("/back-end/board/listAllBoard_new.jsp");
							failureView.forward(req, res);
						}
					}
/***************************完成一個流程***************************/
					if ("get_For_Display".equals(action)) { 

								List<String> errorMsgs = new LinkedList<String>();
								
								req.setAttribute("errorMsgs", errorMsgs);

								try {
		/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
									String str = req.getParameter("boaNo");

									Integer boaNo = new Integer(str);

									if (!errorMsgs.isEmpty()) {
										RequestDispatcher failureView = req
												.getRequestDispatcher("/front-end/board/allBoard_new.jsp");
										failureView.forward(req, res);
										return;
									}
		/***************************2.開始查詢資料***********************/
									BoardService boardSvcbox = new BoardService();
									BoardVO boardVO = boardSvcbox.getOneBoard(boaNo);

									if (boardVO == null) {
										errorMsgs.add("查無資料");
									}
									
									if (!errorMsgs.isEmpty()) {
										RequestDispatcher failureView = req
												.getRequestDispatcher("/front-end/board/allBoard_new.jsp");
										failureView.forward(req, res);
										return;
									}
		/**************3.查詢完成,準備轉交(Send the Success view)*************/
									req.setAttribute("BoardVO", boardVO);
									
									String url = "/front-end/board/listOneBoard_new.jsp";
									RequestDispatcher successView = req.getRequestDispatcher(url);
									successView.forward(req, res);
									
		/***************************其他可能的錯誤處理************************/
								} catch (Exception e) {
									errorMsgs.add("無法取得資料:" + e.getMessage());
									RequestDispatcher failureView = req
											.getRequestDispatcher("/front-end/board/allBoard_page.jsp");
									failureView.forward(req, res);
								}
							}
		/***************************完成一個流程***************************/
					if ("insert".equals(action)) {   
						
						List<String> errorMsgs = new LinkedList<String>();
						req.setAttribute("errorMsgs", errorMsgs);

						try {
							BoardVO boardVOError = new BoardVO(); 
/***********************1.接收請求參數 - 輸入格式的錯誤處理***************/
							String boatypNo = req.getParameter("boatypNo");
							String boaContent = req.getParameter("boaContent").trim();
							if (boaContent == null || boaContent.trim().length() == 0) {
								errorMsgs.add("公告內容請勿空白");
							} 
							BoardVO boardVO = new BoardVO();
							boardVO.setBoatypNo(Integer.parseInt(boatypNo));
							boardVO.setBoaContent(boaContent);

							if (!errorMsgs.isEmpty()) {					
								req.setAttribute("BoardVO", boardVO); 
								RequestDispatcher failureView = req
										.getRequestDispatcher("/back-end/board/addBoard_new.jsp");
								failureView.forward(req, res);
								return;
							}
							
/***************************2.開始新增資料****************************/
							BoardService boardSvc = new BoardService();							//使用BoardService的addBoard方法將值傳到前端
							boardVO = boardSvc.addBoard(Integer.parseInt(boatypNo), boaContent);
/***************************3.新增完成,準備轉交(Send the Success view)****/
							String url = "/back-end/board/listAllBoard_new.jsp";
							RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
							successView.forward(req, res);				
							
/***************************其他可能的錯誤處理************************/
						} catch (Exception e) {
							errorMsgs.add(e.getMessage());
							RequestDispatcher failureView = req
									.getRequestDispatcher("/back-end/board/addBoard_new.jsp");
							failureView.forward(req, res);
						}
					}
/***************************完成一個流程***************************/			
					if ("delete".equals(action)) { 

						List<String> errorMsgs = new LinkedList<String>();
						req.setAttribute("errorMsgs", errorMsgs);

						try {
/***************************1.接收請求參數**************************/
							Integer boaNo = new Integer(req.getParameter("boaNo"));
							
/***************************2.開始刪除資料**************************/
							BoardJNDIDAO boardJNDIDAO = new BoardJNDIDAO();
							boardJNDIDAO.delete(boaNo);
							
							List<BoardVO> list = boardJNDIDAO.getAll();
							req.setAttribute("list", list);
							
/***************************3.刪除完成,準備轉交(Send the Success view)****/								
							String url = "/back-end/board/listAllBoard.jsp";
							RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
							successView.forward(req, res);
							
/***************************其他可能的錯誤處理************************/
						} catch (Exception e) {
							errorMsgs.add("刪除資料失敗:"+e.getMessage());
							RequestDispatcher failureView = req
									.getRequestDispatcher("/back-end/board/listAllBoard.jsp");
							failureView.forward(req, res);
						}
					}
/***************************完成一個流程****************************/						
					if ("getOne_For_Update".equals(action)) { 

						List<String> errorMsgs = new LinkedList<String>();
						req.setAttribute("errorMsgs", errorMsgs);
						
						try {
/***************************1.接收請求參數***************************/
							Integer boaNo = new Integer(req.getParameter("boaNo"));
/***************************2.開始查詢資料***************************/
							
							BoardService boardService = new BoardService();
							BoardVO boardVO = boardService.getOneBoard(boaNo);
							BoardTypeService boardTypeService = new BoardTypeService();
							List<BoardTypeVO> boardTypeVOList = boardTypeService.getAll();
/***************************3.查詢完成,準備轉交(Send the Success view)****/

							req.setAttribute("BoardVO", boardVO);         
							req.setAttribute("boardTypeVOList", boardTypeVOList);
							String url = "/back-end/board/uptateOne_Board_new.jsp";
							
							RequestDispatcher successView = req.getRequestDispatcher(url);
							successView.forward(req, res);

/***************************其他可能的錯誤處理*************************/
						} catch (Exception e) {
							
							errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
							RequestDispatcher failureView = req
									.getRequestDispatcher("/back-end/board/listAllBoard_new.jsp");
							failureView.forward(req, res);
						}
					}
/***************************完成一個流程*****************************/				
					
					if ("update".equals(action)) { 
						List<String> errorMsgs = new LinkedList<String>();
						req.setAttribute("errorMsgs", errorMsgs);
					
						try {
/***************************1.接收請求參數 - 輸入格式的錯誤處理************/
							Integer boaNo = new Integer(req.getParameter("boaNo").trim());
							Integer boatypNo = new Integer(req.getParameter("boatypNo").trim());
							String boaContent = req.getParameter("boaContent").trim();
							if (boaContent == null || boaContent.trim().length() == 0) {
								errorMsgs.add("公告請勿空白");
							}	
							BoardVO boardVO = new BoardVO();
							boardVO.setBoaNo(boaNo);
							boardVO.setBoatypNo(boatypNo);
							boardVO.setBoaContent(boaContent);
							
							if (!errorMsgs.isEmpty()) {

								req.setAttribute("BoardVO", boardVO); // 含有輸入格式錯誤的empVO物件,也存入req
								RequestDispatcher failureView = req
										.getRequestDispatcher("/back-end/board/uptateOne_Board_new.jsp");
								failureView.forward(req, res);
								return; 
							}
/***************************2.開始修改資料******************************/
							BoardService boardSvc = new BoardService();
							boardSvc.updateBoard(boaNo,boatypNo,boaContent);
							boardVO = boardSvc.getOneBoard(boaNo);
/***************************3.修改完成,準備轉交(Send the Success view)*******/
							req.setAttribute("BoardVO", boardVO);
							String url = "/back-end/board/uptateOne_Board_new2.jsp";
							RequestDispatcher successView = req.getRequestDispatcher(url); 
							successView.forward(req, res);
/***************************其他可能的錯誤處理****************************/
						} 
						catch (Exception e) {

							errorMsgs.add("修改資料失敗:"+e.getMessage());
							RequestDispatcher failureView = req
									.getRequestDispatcher("/back-end/board/uptateOne_Board_new.jsp");
							failureView.forward(req, res);
						}
					}
/***************************完成一個流程***************************/
					if ("getOne_For_Display2".equals(action)) { 
					
						List<String> errorMsgs = new LinkedList<String>();
						req.setAttribute("errorMsgs", errorMsgs);

						try {
/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
							String str = req.getParameter("boatypNo");

							if (!errorMsgs.isEmpty()) {
								RequestDispatcher failureView = req
										.getRequestDispatcher("/back-end/board/listAllBoard_new.jsp");
								failureView.forward(req, res);
								return;
							}
							
							Integer boatypNo = null;
							try {
								boatypNo = new Integer(str);
							} catch (Exception e) {
								errorMsgs.add("公告編號格式不正確");
							}
							
							if (!errorMsgs.isEmpty()) {
								RequestDispatcher failureView = req
										.getRequestDispatcher("/back-end/board/listAllBoard_new.jsp");
								failureView.forward(req, res);
								return;
							}
							
/***************************2.開始查詢資料***********************/
							BoardJNDIDAO dao = new BoardJNDIDAO();
							List<BoardVO> boatypNolist = dao.findByFK(boatypNo);
							
							if (boatypNolist == null) {
								errorMsgs.add("查無資料");
							}
							
							if (!errorMsgs.isEmpty()) {
								RequestDispatcher failureView = req
										.getRequestDispatcher("/back-end/board/listAllBoard_new.jsp");
								failureView.forward(req, res);
								return;
							}
/**************3.查詢完成,準備轉交(Send the Success view)*************/
							req.setAttribute("BoatypNolist", boatypNolist);
							String url = "/back-end/BoardType/listOneBoard_new2.jsp";
							RequestDispatcher successView = req.getRequestDispatcher(url);
							successView.forward(req, res);

/***************************其他可能的錯誤處理************************/
						} catch (Exception e) {
							errorMsgs.add("無法取得資料:" + e.getMessage());
							RequestDispatcher failureView = req
									.getRequestDispatcher("/back-end/BoardType/listAllBoard_new.jsp");
							failureView.forward(req, res);
						}
					}
/***************************完成一個流程***************************/					
					if ("get_For_Display2".equals(action)) { 
						
						List<String> errorMsgs = new LinkedList<String>();
						req.setAttribute("errorMsgs", errorMsgs);

						try {
/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
							String str = req.getParameter("boatypNo");
						
							Integer	boatypNo = new Integer(str);
							
							if (!errorMsgs.isEmpty()) {
								RequestDispatcher failureView = req
										.getRequestDispatcher("/front-end/board/allBoard_page.jsp");
								failureView.forward(req, res);
								return;
							}
							
/***************************2.開始查詢資料***********************/
							BoardJNDIDAO dao = new BoardJNDIDAO();
							List<BoardVO> boatypNolist = dao.findByFK(boatypNo);
							
							if (boatypNolist == null) {
								errorMsgs.add("查無資料");
							}
							
							if (!errorMsgs.isEmpty()) {
								RequestDispatcher failureView = req
										.getRequestDispatcher("/front-end/board/allBoard_page.jsp");
								failureView.forward(req, res);
								return;
							}
/**************3.查詢完成,準備轉交(Send the Success view)*************/
							req.setAttribute("BoatypNolist", boatypNolist);
							String url = "/front-end/board/listOneBoard_new2.jsp";
							RequestDispatcher successView = req.getRequestDispatcher(url);
							successView.forward(req, res);

/***************************其他可能的錯誤處理************************/
						} catch (Exception e) {
							errorMsgs.add("無法取得資料:" + e.getMessage());
							RequestDispatcher failureView = req
									.getRequestDispatcher("/front-end/board/allBoard_page.jsp");
							failureView.forward(req, res);
						}
					}
/***************************完成一個流程***************************/	
//==============================================================			
	 }
}

	

