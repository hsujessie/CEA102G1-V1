package com.client_service.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;
import com.client_service.model.*;

public class Client_serviceServlet {
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
