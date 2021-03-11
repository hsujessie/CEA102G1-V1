package com.board_type.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import com.board.model.*;
import com.board_type.model.*;


public class BoardTypeServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");



	
		
		
		
		
		
		
	
	
	}
}
