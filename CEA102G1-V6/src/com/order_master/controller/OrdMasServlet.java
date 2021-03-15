package com.order_master.controller;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.food.model.FooCartVO;
import com.food.model.FooService;
import com.food.model.FooVO;
import com.session.model.SesService;
import com.ticket_list.model.TicLisVO;
import com.ticket_type.model.TicTypCartVO;
import com.ticket_type.model.TicTypService;

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
			Set<TicTypCartVO> ticTypCartSet = ticTypSvc.getTicTypCart(ticTypMap);
			
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
			
			/***************************2.開始修改資料***************************************/
				SesService sesSvc = new SesService();
				List<String> list = sesSvc.updateSeatStatus(chooseSeatNo, sesNo);
				
				HttpSession session = req.getSession();
				Set<TicTypCartVO> ticTypCartSet = (Set<TicTypCartVO>)session.getAttribute("ticTypCartSet");
				int i = 0;
				for (TicTypCartVO ticTypCartVO : ticTypCartSet) {
					ticTypCartVO.setSesSeatNo(list.get(i));
					i++;
				}
			/***************************3.修改完成,準備轉交(Send the Success view)***********/
				String url = "/front-end/ordMas/OrderConfirm.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/ordMas/SelectSeat.jsp");
				failureView.forward(req, res);
			}
		}
		
	}

}
