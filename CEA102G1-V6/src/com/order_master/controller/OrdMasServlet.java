package com.order_master.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.food.model.FooCartVO;
import com.food.model.FooService;
import com.food.model.FooVO;
import com.order_master.model.OrdMasService;
import com.order_master.model.OrdMasVO;
import com.session.model.SesService;
import com.ticket_list.model.TicLisVO;
import com.ticket_type.model.TicTypCartVO;
import com.ticket_type.model.TicTypService;

import jdbc.util.schedule.startSchedule;

public class OrdMasServlet extends HttpServlet {
       
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("go_select_seat".equals(action)) {
			
			Enumeration<String> enu = req.getParameterNames();
			Map<Integer, Integer> ticTypMap = new HashMap<Integer, Integer>(); 
			Map<Integer, Integer> foodMap = new HashMap<Integer, Integer>(); 
			Integer ticTypTotal = 0;
			/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
			while (enu.hasMoreElements()) {
				String name = enu.nextElement();
				
				if (name.indexOf("fooNo") != -1) {
					Integer fooNo = new Integer(name.substring(5));
					Integer fooCount = new Integer(req.getParameter(name));
					if (fooCount != 0) {
						foodMap.put(fooNo, fooCount);
					}
				} else if (name.indexOf("ticTypNo") != -1) {
					Integer ticTypNo = new Integer(name.substring(8));
					Integer ticTypCount = new Integer(req.getParameter(name));
					if (ticTypCount != 0) {
						ticTypMap.put(ticTypNo, ticTypCount);
						ticTypTotal += ticTypCount;
					}
				}
			}
			
			Integer sesNo = new Integer(req.getParameter("sesNo"));
			
			/***************************2.開始查詢資料***************************************/
			
			SesService sesSvc = new SesService();
			String sesSeatStatus = sesSvc.getOneSes(sesNo).getSesSeatStatus();
			
			FooService fooSvc = new FooService();
			Set<FooCartVO> fooCartSet = fooSvc.getFooCart(foodMap);
			
			TicTypService ticTypSvc = new TicTypService();
			List<TicTypCartVO> ticTypCartSet = ticTypSvc.getTicTypCart(ticTypMap);
			
			/***************************3.新增完成,準備轉交(Send the Success view)***********/
			HttpSession session = req.getSession();
			session.setAttribute("fooCartSet", fooCartSet);
			session.setAttribute("ticTypCartSet", ticTypCartSet);
			
			req.setAttribute("sesSeatStatus", sesSeatStatus);
			req.setAttribute("ticTypTotal", ticTypTotal);
			String url = "/front-end/ordMas/SelectSeat.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
			successView.forward(req, res);
		}
		
		if ("confirm_order".equals(action)) {
			
			LinkedList<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
			/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String chooseSeatNo = req.getParameter("chooseSeatNo");
				
				Integer sesNo = new Integer(req.getParameter("sesNo"));
				
				Integer ticTypTotal = new Integer(req.getParameter("ticTypTotal"));
				SesService sesSvc = new SesService();
				boolean result = sesSvc.isAlreadyChoose(chooseSeatNo, sesNo);
				String sesSeatStatus = sesSvc.getOneSes(sesNo).getSesSeatStatus();
				
				if (result) {
					errorMsgs.push("error");
					req.setAttribute("sesSeatStatus", sesSeatStatus);
					req.setAttribute("ticTypTotal", ticTypTotal);
					
					String url = "/front-end/ordMas/SelectSeat.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);
					return;
				}
				
			/***************************2.開始修改資料***************************************/
				List<String> list = sesSvc.updateSeatStatus(chooseSeatNo, sesNo, "lock_seat");
				Timer timer = startSchedule.start(20 * 1000, sesNo, chooseSeatNo);
				
				HttpSession session = req.getSession();
				List<TicTypCartVO> ticTypCartSet = (List<TicTypCartVO>)session.getAttribute("ticTypCartSet");
				int i = 0;
				for (TicTypCartVO ticTypCartVO : ticTypCartSet) {
					ticTypCartVO.setSesSeatNo(list.get(i));
					i++;
				}
			/***************************3.修改完成,準備轉交(Send the Success view)***********/
				session.setAttribute("timer", timer);
				String url = "/front-end/ordMas/OrderConfirm.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/ordMas/SelectSeat.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("check_out".equals(action)) {
			
			LinkedList<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
			/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
			Integer memNo = new Integer(req.getParameter("memNo"));
			Integer sesNo = new Integer(req.getParameter("sesNo"));
			
			HttpSession session = req.getSession();
			List<TicTypCartVO> ticTypCartSet = (List<TicTypCartVO>)session.getAttribute("ticTypCartSet");
			Set<FooCartVO> fooCartSet = (Set<FooCartVO>)session.getAttribute("fooCartSet");
			
			/***************************2.開始新增資料***************************************/
			OrdMasService ordMasSvc = new OrdMasService();
			OrdMasVO ordMasVO = ordMasSvc.insertWithDetail(memNo, sesNo, fooCartSet, ticTypCartSet);
			
			Timer timer = (Timer) session.getAttribute("timer");
			timer.cancel();
			/***************************3.修改完成,準備轉交(Send the Success view)***********/
			req.setAttribute("ordMasVO", ordMasVO);
			String url = "/front-end/ordMas/OrderComplete.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url);
			successView.forward(req, res);
			
			
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/ordMas/SelectSeat.jsp");
				failureView.forward(req, res);
			} 
			
		}
		
		if ("getOne_For_Display".equals(action)) {
			
			LinkedList<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String requestURL = req.getParameter("requestURL");
				Integer ordMasNo = new Integer(req.getParameter("ordMasNo"));
				
				/***************************2.開始查詢資料***************************************/
				OrdMasService ordMasSvc = new OrdMasService();
				
				//判斷是否會員自己查看明細
				String url = "/back-end/ordMas/listOneOrder.jsp"; //預設到取票頁面
				if ("/front-end/ordMas/listMemOrder.jsp".equals(requestURL)) {  
					url = "/front-end/ordMas/listOrderDetail.jsp"; //forward到 會員訂單明細(頁面有QRcode)
				} else {
					boolean result = ordMasSvc.isAlreadyGet(ordMasNo);
					if (result) {
						errorMsgs.add("此訂單已完成或已取消");
					} else {
					ordMasSvc.changeStatus(ordMasNo, 1); 
					}
				}
				
				OrdMasVO ordMasVO =ordMasSvc.getOneOrdMas(ordMasNo);
				/***************************3.查詢完成,準備轉交(Send the Success view)***********/
				
				req.setAttribute("ordMasVO", ordMasVO);
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
				
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/ordMas/listMemOrder.jsp");
				failureView.forward(req, res);
			} 
			
		}
		
		if ("change_status".equals(action)) {
			
			LinkedList<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				Integer ordMasNo = new Integer(req.getParameter("ordMasNo"));
				
				/***************************2.開始修改資料***************************************/
				OrdMasService ordMasSvc = new OrdMasService();
				ordMasSvc.changeStatus(ordMasNo, 2);
				
				/***************************3.修改完成,準備轉交(Send the Success view)***********/
				String url = req.getContextPath() + "/front-end/ordMas/listMemOrder.jsp";
				
				res.sendRedirect(url);
				
				
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/ordMas/listMemOrder.jsp");
				failureView.forward(req, res);
			} 
			
		}
		
	}

}
