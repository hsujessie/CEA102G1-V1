package com.movie.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.expectation.model.ExpService;
import com.expectation.model.ExpVO;
import com.movie.model.MovService;
import com.movie.model.MovVO;
import com.satisfaction.model.SatService;
import com.satisfaction.model.SatVO;

@MultipartConfig()
public class MovServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;  
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		doPost(req,res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		// 來自select_page.jsp的請求
		if("getOne_For_Display".equals(action)) { 
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數*****************************************/
				Integer movno = new Integer(req.getParameter("movno"));
				
				/***************************2.開始查詢資料*****************************************/
				MovService movSvc = new MovService();
				MovVO movVO = movSvc.getOneMov(movno);
				if (movVO == null) {
					errorMsgs.add("查無資料");
				}
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/movie/select_page.jsp");
					failureView.forward(req, res);
					return;
				}
					
				SatService satSvc = new SatService();
				List<SatVO> satlist = satSvc.getAll();
				Double satSum = satlist.stream().filter(sat -> sat.getMovNo().equals(movno)).mapToDouble(sat -> sat.getSatRating()).sum();   //滿意度總分
				Integer satPeo = (int) satlist.stream().filter(sat -> sat.getMovNo().equals(movno)).mapToInt(sat -> sat.getMemNo()).count(); //滿意度評價人數
				
				ExpService expSvc = new ExpService();
				List<ExpVO> explist = expSvc.getAll();
				
				Double expSum = explist.stream().filter(exp -> exp.getMovNo().equals(movno)).mapToDouble(exp -> exp.getExpRating()).sum();   //期待度總分	
				Integer expPeo = (int) explist.stream().filter(exp -> exp.getMovNo().equals(movno)).mapToInt(exp -> exp.getMemNo()).count(); //期待度評價人數				
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("movVO", movVO);
				req.setAttribute("satSum", satSum);
				req.setAttribute("satPeo", satPeo);
				req.setAttribute("movVO", movVO);
				req.setAttribute("expSum", expSum);
				req.setAttribute("expPeo", expPeo);
				String url = "/back-end/movie/listOneMovie.jsp";
				
				String fromFrontend = req.getParameter("fromFrontend");
				if("true".equals(fromFrontend) ) {
					url = "/front-end/movies/movies_subpage.jsp";
				}
				
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureVoew = req.getRequestDispatcher("/back-end/movie/select_page.jsp");
				failureVoew.forward(req,res);
			}
		}
		
		// 來自listAllMovie.jsp的請求 - for displaying pictures
		if ("get_One_MovPos".equals(action)) {
			res.setContentType("img/jpg");
			Integer movno = new Integer(req.getParameter("movno").trim());
			MovService movSvc = new MovService();
			MovVO movVO = movSvc.getOneMov(movno);
			byte[] movpos = movVO.getMovpos();
			
			if(movpos!=null) {
				res.getOutputStream().write(movpos);
				res.getOutputStream().flush();
				return;
			}
		}
		// 來自listAllMovie.jsp的請求 - for displaying trailers
		if ("get_One_MovTra".equals(action)) {
			res.setContentType("video/mp4");
			Integer movno = new Integer(req.getParameter("movno").trim());
			MovService movSvc = new MovService();
			MovVO movVO = movSvc.getOneMov(movno);
			byte[] movtra = movVO.getMovtra();
			
			if(movtra!=null) {
				res.getOutputStream().write(movtra);
				res.getOutputStream().flush();
				return;
			}				
		}
				
		// 來自addMovie.jsp的請求 
		if ("insert".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs",errorMsgs);
			
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				String movname = req.getParameter("movname").trim();
				if(movname == null || movname.length() == 0) {
					errorMsgs.put("movname"," 電影名稱 請勿空白");
				}
				
				//多選checkbox
				String[] movverStr = req.getParameterValues("movver");	
		        String movver = "";
		        if (movverStr == null || movverStr.length == 0) {
					errorMsgs.put("movver"," 請選擇電影種類");
		        }else {
					movver = appendStr(movverStr);
		        }
				
				//單選下拉選單
				String movtype = req.getParameter("movtype");
				if(movtype == null || movtype.trim().length() == 0) {
					errorMsgs.put("movtype"," 請選擇電影類型");
				}

				//多選checkbox
				String[] movlanStr = req.getParameterValues("movlan");	
		        String movlan = "";
		        if (movlanStr == null || movlanStr.length == 0) {
					errorMsgs.put("movlan"," 請選擇電影語言");
		        }else {
		        	movlan = appendStr(movlanStr);
		        }					
		        
				java.sql.Date movondate = null;
				try {
					movondate = java.sql.Date.valueOf(req.getParameter("movondate").trim());
				} catch (IllegalArgumentException e) {
					movondate = new java.sql.Date(System.currentTimeMillis());				
					errorMsgs.put("movondate"," 請輸入上映日期");
				}

				java.sql.Date movoffdate = null;
				try {
					movoffdate = java.sql.Date.valueOf(req.getParameter("movondate").trim());
					
				} catch (IllegalArgumentException e) {
					movoffdate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.put("movoffdate"," 請輸入下檔日期");
				}	

				//單選下拉選單
				Integer movdurat = new Integer(req.getParameter("movdurat").trim());
				
				//單選下拉選單
				String movrating = req.getParameter("movrating").trim();
				
				
				String movditor = req.getParameter("movditor").trim();
				if(movditor == null || movditor.length() == 0) {
					errorMsgs.put("movditor"," 導演資料 請勿空白");
				}
				
				String movcast = req.getParameter("movcast").trim();
				if(movcast == null || movcast.length() == 0) {
					errorMsgs.put("movcast"," 演員資料 請勿空白");
				}
				
				String movdes = req.getParameter("movdes").trim();
				if(movdes == null || movdes.equals("")) {
					errorMsgs.put("movdes"," 電影簡介 請勿空白");
				}

				byte[] movpos = null;
				Part movposPart = req.getPart("movpos");
				/* 新增時，若沒寫以下判斷，沒有選圖片時，還是會寫入一個奇怪的東西，在修改時，會顯示一個怪小圖 */
				if(movposPart.getContentType() != null && movposPart.getContentType().indexOf("image") >= 0) {  // movposPart.getContentType() 印出image/jpeg
					 InputStream movposis = movposPart.getInputStream();
					 movpos = new byte[movposis.available()];
					 movposis.read(movpos);
					 movposis.close();
				}

				byte[] movtra = null;
				Part movtraPart = req.getPart("movtra");
				if(movtraPart.getContentType() != null && movtraPart.getContentType().indexOf("video") >= 0) {	// movtraPart.getContentType() 印出video/mp4	
					InputStream movtrais = movtraPart.getInputStream();
					movtra = new byte[movtrais.available()];
					movtrais.read(movtra);
					movtrais.close();				
				}
				
				
				// Here're parameters for sending back to the front page, if there were errors   
				MovVO movVO = new MovVO();
				movVO.setMovname(movname);
				movVO.setMovver(movver);
				movVO.setMovtype(movtype);
				movVO.setMovlan(movlan);
				movVO.setMovondate(movondate);
				movVO.setMovoffdate(movoffdate);
				movVO.setMovdurat(movdurat);
				movVO.setMovrating(movrating);
				movVO.setMovditor(movditor);
				movVO.setMovcast(movcast);
				movVO.setMovdes(movdes);
				movVO.setMovpos(movpos);
				movVO.setMovtra(movtra);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					System.out.println("errorMsgs= " + errorMsgs);
					req.setAttribute("movVO", movVO);        
					RequestDispatcher failureView = req.getRequestDispatcher("back-end/movie/addMovie.jsp");
					failureView.forward(req, res);
					return;
				}
				
				
				/***************************2.開始新增資料***************************************/				
				MovService movSvc = new MovService();
				movSvc.addMov(movname, movver, movtype, movlan, movondate, movoffdate, movdurat, movrating, movditor, movcast, movdes, movpos, movtra);
					
				/***************************3.新增完成,準備轉交(Send the Success view)***********/				
				String addSuccess = "【  " + movname + " 】" + "新增成功";
				req.setAttribute("addSuccess", addSuccess);	
				
				String url = "/back-end/movie/listAllMovie.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);	
				
				/***************************其他可能的錯誤處理**********************************/
			}catch (Exception e) {
				System.out.println("Exception: " + e.getMessage());
				return;
			}
		} 
		
		 // 來自listAllMovie.jsp的請求
		if ("getOne_For_Update".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");
			
			try {
				/***************************1.接收請求參數****************************************/
				Integer movno = new Integer(req.getParameter("movno").trim());
				
				/***************************2.開始查詢資料****************************************/
				MovService movSvc = new MovService();
				MovVO movVO = movSvc.getOneMov(movno);
			
				//先split電影種類字串，再把值送到update_movie_input.jsp
				String movverStrs = movVO.getMovver();
	            String[] movverToken = null;
	            movverToken = token(movverStrs, movverToken);
								
				//先split電影語言字串，再把值送到update_movie_input.jsp
				String movlanStrs = movVO.getMovlan();
	            String[] movlanToken = null;
	            movlanToken = token(movlanStrs, movlanToken);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
	            if(requestURL.equals("/back-end/movie/listMovies_ByCompositeQuery.jsp")){
					HttpSession session = req.getSession();
					Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
					List<MovVO> list  = movSvc.getAll(map);
					req.setAttribute("listMovies_ByCompositeQuery",list); //  複合查詢, 資料庫取出的list物件,存入
				}
	            
	            req.setAttribute("movVO", movVO);  
				req.setAttribute("movverToken", movverToken);    
				req.setAttribute("movlanToken", movlanToken);

				RequestDispatcher successView = req.getRequestDispatcher("/back-end/movie/update_movie_input.jsp");
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.put("Exception"," 無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		
		
		// 來自update_movie_input.jsp的請求
		if ("update".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer movno = new Integer(req.getParameter("movno").trim());

				MovService movSvc = new MovService();
				MovVO movVO = movSvc.getOneMov(movno);
				
				//先split字串，再把值送到update_movie_input.jsp
				String movverStrs = movVO.getMovver();
				String[] movverToken = null;
				String movlanStrs = movVO.getMovlan();
		        String[] movlanToken = null;

				//多選checkbox
				String[] movverStr = req.getParameterValues("movver");
		        String movver = "";
		        //多選checkbox
				String[] movlanStr = req.getParameterValues("movlan");
		        String movlan = "";
		        if (movverStr == null || movverStr.length == 0) {
					errorMsgs.put("movver"," 請選擇電影種類");

					//先split字串，再把值送到update_movie_input.jsp
					//若沒有再setToken，jsp會抓不到movverToken或movlanToken，會無法保留原始被勾選狀態。
			        if (movverStr != null) {  
						movverToken = token(movverStrs, movverToken);
						req.setAttribute("movverToken", movverToken);	
					}
					if (movlanStr != null ) {
						movlanToken = token(movlanStrs, movlanToken);	  
						req.setAttribute("movlanToken", movlanToken); 	
					}
		        }else {
					movver = appendStr(movverStr);
		        }
		        
		        if (movlanStr == null || movlanStr.length == 0) {
					errorMsgs.put("movlan"," 請選擇電影語言"); 

					//先split字串，再把值送到update_movie_input.jsp
			        if (movverStr != null) {  
						movverToken = token(movverStrs, movverToken);
						req.setAttribute("movverToken", movverToken);	
					}
					if (movlanStr != null ) {
						movlanToken = token(movlanStrs, movlanToken);	  
						req.setAttribute("movlanToken", movlanToken); 	
					} 
		        }else {
		        	movlan = appendStr(movlanStr);
		        }				

		        
				String movname = req.getParameter("movname").trim();   
				if(movname == null || movname.length() == 0) {
					errorMsgs.put("movname"," 電影名稱 請勿空白");
					
					//先split字串，再把值送到update_movie_input.jsp 
			        if (movverStr != null) {  
						movverToken = token(movverStrs, movverToken);
						req.setAttribute("movverToken", movverToken);	
					}
					if (movlanStr != null ) {
						movlanToken = token(movlanStrs, movlanToken);	  
						req.setAttribute("movlanToken", movlanToken); 	
					}
				}				
				
				//單選下拉選單
				String movtype = req.getParameter("movtype");

				java.sql.Date movondate = null;
				try {
					movondate = java.sql.Date.valueOf(req.getParameter("movondate").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.put("movondate"," 請輸入上映日期!");
					
					//先split字串，再把值送到update_movie_input.jsp
			        if (movverStr != null) {  
						movverToken = token(movverStrs, movverToken);
						req.setAttribute("movverToken", movverToken);	
					}
					if (movlanStr != null ) {
						movlanToken = token(movlanStrs, movlanToken);	  
						req.setAttribute("movlanToken", movlanToken); 	
					}
				}

				java.sql.Date movoffdate = null;
				try {
					movoffdate = java.sql.Date.valueOf(req.getParameter("movoffdate").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.put("movoffdate"," 請輸入下檔日期!");
					
					//先split字串，再把值送到update_movie_input.jsp
			        if (movverStr != null) {  
						movverToken = token(movverStrs, movverToken);
						req.setAttribute("movverToken", movverToken);	
					}
					if (movlanStr != null ) {
						movlanToken = token(movlanStrs, movlanToken);	  
						req.setAttribute("movlanToken", movlanToken); 	
					}
				}
				
				//單選下拉選單
				Integer movdurat = new Integer(req.getParameter("movdurat").trim());
				
				//單選下拉選單
				String movrating = req.getParameter("movrating").trim();
				
				String movditor = req.getParameter("movditor").trim();
				if(movditor == null || movditor.length() == 0) {
					errorMsgs.put("movditor"," 導演資料 請勿空白");
					
					//先split字串，再把值送到update_movie_input.jsp
			        if (movverStr != null) {  
						movverToken = token(movverStrs, movverToken);
						req.setAttribute("movverToken", movverToken);	
					}
					if (movlanStr != null ) {
						movlanToken = token(movlanStrs, movlanToken);	  
						req.setAttribute("movlanToken", movlanToken); 	
					}
				}
				
				String movcast = req.getParameter("movcast").trim();
				if(movcast == null || movcast.length() == 0) {
					errorMsgs.put("movcast"," 演員資料 請勿空白");
					
					//先split字串，再把值送到update_movie_input.jsp
			        if (movverStr != null) {  
						movverToken = token(movverStrs, movverToken);
						req.setAttribute("movverToken", movverToken);	
					}
					if (movlanStr != null ) {
						movlanToken = token(movlanStrs, movlanToken);	  
						req.setAttribute("movlanToken", movlanToken); 	
					}
				}

				String movdes = req.getParameter("movdes").trim();
				if(movdes == null || movdes.equals("")) {
					errorMsgs.put("movdes"," 電影簡介 請勿空白");
					
					//先split字串，再把值送到update_movie_input.jsp
			        if (movverStr != null) {  
						movverToken = token(movverStrs, movverToken);
						req.setAttribute("movverToken", movverToken);	
					}
					if (movlanStr != null ) {
						movlanToken = token(movlanStrs, movlanToken);	  
						req.setAttribute("movlanToken", movlanToken); 	
					}
				}

				
				/* 修改時，contentType有找到image時，才update，原本既有的圖才不會不見。 */
				Part movposPart = req.getPart("movpos");	
				byte[] movpos = movVO.getMovpos();
				if(movposPart.getContentType() != null && movposPart.getContentType().indexOf("image") >= 0) {  // movposPart.getContentType() 印出image/jpeg
				//if(movposPart != null) {	//這樣判斷是不對的，因為即便沒東西，會回傳這個 application/octet-stream	 			
					
					InputStream movposis = movposPart.getInputStream();
					movpos = new byte[movposis.available()];
					movposis.read(movpos);
					movposis.close();
					
					movSvc.updateMovpos(movpos, movno);
				}
				
				/* 修改時，contentType有找到video時，才update，原本既有的影片才不會不見。 */
				Part movtraPart = req.getPart("movtra");
				byte[] movtra = movVO.getMovtra();
				if(movtraPart.getContentType() != null && movtraPart.getContentType().indexOf("video") >= 0) {	// movtraPart.getContentType() 印出video/mp4			
					InputStream movtrais = movtraPart.getInputStream();
					movtra = new byte[movtrais.available()];
					movtrais.read(movtra);
					movtrais.close();				
					
					movSvc.updateMovtra(movtra, movno);
				}
				

	            // Here're parameters for sending back to the front page, if there were errors   
				movVO.setMovno(movno);
				movVO.setMovname(movname);
				movVO.setMovver(movver);
				movVO.setMovtype(movtype);
				movVO.setMovlan(movlan);
				movVO.setMovondate(movondate);
				movVO.setMovoffdate(movoffdate);
				movVO.setMovdurat(movdurat);
				movVO.setMovrating(movrating);
				movVO.setMovditor(movditor);
				movVO.setMovcast(movcast);
				movVO.setMovdes(movdes);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					System.out.println("errorMsgs= " + errorMsgs);
					req.setAttribute("movVO", movVO);        
					RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始修改資料*****************************************/
				movVO = movSvc.updateMov(movname, movver, movtype, movlan, movondate, movoffdate, movdurat, movrating, movditor, movcast, movdes, movno);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/	
				if(requestURL.equals("/back-end/movie/listMovies_ByCompositeQuery.jsp")){
					HttpSession session = req.getSession();
					Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
					List<MovVO> list  = movSvc.getAll(map);
					req.setAttribute("listMovies_ByCompositeQuery",list); //  複合查詢, 資料庫取出的list物件,存入
				}
				
				String updateSuccess = "【  " + movname + " 】" + "修改成功";
				req.setAttribute("updateSuccess", updateSuccess);

				String url = requestURL;
				if(requestURL.equals("/back-end/movie/update_movie_input.jsp")){
					url = "/back-end/movie/listAllMovie.jsp";
				}
				
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			}catch (Exception e) {
				errorMsgs.put("Exception","修改資料失敗:" + e.getMessage());
				
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}

		// 來自select_page.jsp的請求---複合查詢
		if("listMovies_ByCompositeQuery".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				HttpSession session = req.getSession();
				Map<String, String[]> map = (Map<String,String[]>)session.getAttribute("map");
				if(req.getParameter("whichPage") == null) {
					HashMap<String, String[]> map1 = new HashMap<String, String[]>(req.getParameterMap());
					
					session.setAttribute("map",map1);
					map = map1;
				}
				System.out.println("map.size()= " + map.size());

			/***************************2.開始複合查詢***************************************/
			MovService movSvc = new MovService();
			List<MovVO> list  = movSvc.getAll(map);
			
			/***************************3.查詢完成,準備轉交(Send the Success view)************/
			req.setAttribute("listMovies_ByCompositeQuery", list); // 資料庫取出的list物件,存入request
			RequestDispatcher successView = req.getRequestDispatcher("/back-end/movie/listMovies_ByCompositeQuery.jsp"); // 成功轉交listMovies_ByCompositeQuery.jsp
			successView.forward(req, res);
			
			/***************************其他可能的錯誤處理**********************************/
			}catch(Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/movie/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		

		// 來自frontend movies.jsp的請求
		if("now_Showing".equals(action)) {
			MovService movSvc = new MovService();
			List<MovVO> list = movSvc.getAll();
		    MovVO movObj = null;
			Date date = new Date();
			List<MovVO> nowShowingList = new ArrayList<MovVO>();
			for(int i = 0; i < list.size(); i++) {
				movObj = (MovVO)list.get(i);
				if (movObj.getMovondate().before(date) || movObj.getMovondate().equals(date)) {
					if(date.before(movObj.getMovoffdate())) {
					    nowShowingList.add(movObj);
					    req.setAttribute("nowShowing", nowShowingList);
					}
				}
			}
			RequestDispatcher successView = req.getRequestDispatcher("/front-end/movies/movies.jsp");
			successView.forward(req, res);
		}
		
		if("comming_Soon".equals(action)) {
			MovService movSvc = new MovService();
			List<MovVO> list = movSvc.getAll();
		    MovVO movObj = null;
			Date date = new Date();
			List<MovVO> commingSoonList = new ArrayList<MovVO>();
			for(int i = 0; i < list.size(); i++) {
				movObj = (MovVO)list.get(i);
				if (movObj.getMovondate().after(date)) {
				    commingSoonList.add(movObj);
				    req.setAttribute("commingSoon", commingSoonList);
				}
			}
			RequestDispatcher successView = req.getRequestDispatcher("/front-end/movies/movies.jsp");
			successView.forward(req, res);
		}
	
	} 
	
	public String[] token(String str, String[] token){
		if(str != null) {
			token = str.split(",");
		}
		return token;
	}
	
	public String appendStr(String[] str) {	
		String resultStr = null;
        StringBuilder strSb = new StringBuilder();
        
        for (int i = 0; i < str.length; i++) {
        	if(i == 0) {
        		strSb = strSb.append(str[i].trim());
        	}else {
        		strSb = strSb.append(","+str[i].trim());
        	}
        	resultStr =  strSb.toString();
        }
		return resultStr;
	}
}
