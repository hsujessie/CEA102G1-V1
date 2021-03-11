package com.food_cate.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.food.model.FooVO;
import com.food_cate.model.FooCatService;

public class FooCatServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("listFoos_ByFooCatNo_fromFoo".equals(action) || ("listFoos_ByFooCatNo_fromFooCate").equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer fooCatNo = new Integer(req.getParameter("fooCatNo"));

				/*************************** 2.開始查詢資料 ****************************************/
				FooCatService fooCatSvc = new FooCatService();
				Set<FooVO> set = fooCatSvc.getFoosByFooCatNo(fooCatNo);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("listFoos_ByFooCatNo", set);    // 資料庫取出的set物件,存入request
				String url = null;
				if ("listFoos_ByFooCatNo_fromFoo".equals(action))
					url = "/back-end/fooCat/listFoosByFooCatNo.jsp";        
				else if ("listFoos_ByFooCatNo_fromFooCate".equals(action))
					url = "/back-end/fooCat/listAllFooCat.jsp";              

				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 ***********************************/
			} catch (Exception e) {
				throw new ServletException(e);
			}
		}
	}
}
