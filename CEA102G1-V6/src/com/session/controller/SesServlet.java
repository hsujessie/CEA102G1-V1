package com.session.controller;

import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.movie.model.MovService;
import com.movie.model.MovVO;
import com.session.model.SesService;
import com.session.model.SesVO;
import com.theater.model.TheService;
import com.theater.model.TheVO;


@MultipartConfig()
public class SesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;  
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		doPost(req,res);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if("getOne_For_Display".equals(action)) { 
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs",errorMsgs);

			try {
				/***************************1.接收請求參數*****************************************/
				Integer sesNo = new Integer(req.getParameter("sesNo"));
				Integer movNo = new Integer(req.getParameter("movNo"));
				
				/***************************2.開始查詢資料*****************************************/
				SesService sesSvc = new SesService();
				SesVO sesVO = sesSvc.getOneSes(sesNo);
				if (sesVO == null) {
					errorMsgs.add("查無資料");
				}
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/session/listAllSession.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				MovService movSvc = new MovService();
				MovVO movVO = movSvc.getOneMov(movNo);
				req.setAttribute("movVO", movVO);
				
				req.setAttribute("sesVO", sesVO);
				String url = "/back-end/session/listOneSession.jsp";
				
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch (Exception e) {
				errorMsgs.add("無法取得資料 " + e.getMessage());
				RequestDispatcher failureVoew = req.getRequestDispatcher("/back-end/session/listAllSession.jsp");
				failureVoew.forward(req,res);
			}
		}

		if("listSessions_ByCompositeQuery".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs",errorMsgs);
			
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
			SesService sesSvc = new SesService();
			List<SesVO> list  = sesSvc.getAll(map);
			
			/***************************3.查詢完成,準備轉交(Send the Success view)************/
			req.setAttribute("listSessions_ByCompositeQuery", list); // 資料庫取出的list物件,存入request
			
			RequestDispatcher successView = req.getRequestDispatcher("/back-end/session/listSessions_ByCompositeQuery.jsp"); // 成功轉交listSessions_ByCompositeQuery.jsp
			successView.forward(req, res);
			
			/***************************其他可能的錯誤處理**********************************/
			}catch(Exception e) {
				errorMsgs.add("查無資料 " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/session/listAllSession.jsp");
				failureView.forward(req, res);
			}
		}
	
	
		if ("insert".equals(action)) {
            String errorMsgs = null;
            String errorDateMsgs = null;
            String errorTimeMsgs = null;
            String errorSessionMsgs = null;
            try {
                /***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
                 Integer movNo = new Integer(req.getParameter("movNo").trim());
                 
                 String[] theNoArr = req.getParameterValues("theNo");
                 Integer theNo = null;
                 if (theNoArr == null || theNoArr.length == 0) {
                	 errorMsgs = "請選擇廳院";
                     System.out.println("theNo is empty!");
                 }
                 
                 String sesDateBegin = req.getParameter("sesDateBegin").trim();
	             String sesDateEnd = req.getParameter("sesDateEnd").trim();            
	             List<String> sesDateList = new ArrayList<String>();
	             java.sql.Date sesDate = null;
            	 try {
	            	 sesDateList = getDates(sesDateBegin,sesDateEnd);
	             } catch (ParseException e) {
					e.printStackTrace();
	             }             
	             String[] sesDateArr = new String[sesDateList.size()];
	             sesDateArr = sesDateList.toArray(sesDateArr);
	 	           
	 	           
	          /* =====================================================================
                 				           錯誤驗證：場次日期不可小於當日
				 =====================================================================*/	
	             DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	             java.util.Date date = new java.util.Date();
	             String strDate= dateFormat.format(date);  // 先把 java.util.Date format轉字串，才能parse，因為 parse(裡面要放字串)
	             java.util.Date parsedDate = dateFormat.parse(strDate);      // 要parse new Date()的格式，去除時間秒數，因為只要比日期而已。 若把秒數也比進去，即使同一天，也會是false，因為new Date帶有秒數，永遠比前台來的值還大。
	             java.util.Date dateBegin = dateFormat.parse(sesDateBegin);  // parse(String) to java.util.Date
	             java.util.Date dateEnd = dateFormat.parse(sesDateEnd);
	             
	             if (dateBegin.before(parsedDate) || dateBegin.equals(parsedDate) || dateEnd.before(parsedDate) || dateBegin.equals(parsedDate)) {  // use 「 .before() 」the type should be java.util.Date
	            	 errorDateMsgs = "場次日期有誤，不可為當日，或於當日之前";
	             }
	             
	                        	             
	             Time sesTime = null;   
	             String[] sesTimeArr = req.getParameterValues("sesTime");	  
	             List<LocalTime> sesTimeList = new ArrayList<LocalTime>();
	             Duration diff = null;
	             if (sesTimeArr == null || sesTimeArr.length == 0) {
					   errorMsgs = "請選擇電影時間";
	                   System.out.println("sesTime is empty!");
	             }else {
	            	 
	            	/* =====================================================================
	            	                       錯誤驗證：場次時間間距，不可少於2小時
	            	   =====================================================================*/
	            	 if (sesTimeArr.length > 1) {
						for(int j = 0; j < sesTimeArr.length; j++) {
							sesTimeList.add(java.time.LocalTime.parse(sesTimeArr[j]));	// 將時間陣列存進list
						}
						
						for (int i = 1; i < sesTimeList.size(); i++) {	
							diff = Duration.between(sesTimeList.get(i - 1),sesTimeList.get(i));  // 「get(i)」 minus 「get(i - 1)」的 difference 不能少於2
//							System.out.println("diff= " + diff.toHours()); 
							if (diff.toHours() < 2) {
								errorTimeMsgs = "間距不可少於2小時";  							
							}
						}
					}else {
						sesTime = Time.valueOf(java.time.LocalTime.parse(sesTimeArr[0]));
					}
	             }

	             
	             SesService sesSvc = new SesService();
		         TheService theSvc = new TheService();
				 List<Integer> theNoList = new ArrayList<Integer>();
				 for (int k = 0; k < theNoArr.length; k++) {
					theNoList.add(new Integer(theNoArr[k]));
				 }
		      /* =====================================================================
        	                   場次是否重複，錯誤驗證  //theNo、sesDate、sesTime
        	     ===================================================================== */
				 Boolean result = true;
				 for (int i = 0; i < sesDateArr.length; i++) {
	                 sesDate = Date.valueOf(sesDateArr[i]);  
                   for (int j = 0; j < sesTimeArr.length; j++) {
						sesTime = Time.valueOf(java.time.LocalTime.parse(sesTimeArr[j]));
                       for (int k = 0; k < theNoArr.length; k++) {                       
                            theNo = new Integer(theNoArr[k]); 
           				 	result = sesSvc.isRepeatedSession(theNo, sesDate, sesTime); 
                       }
                   	}
				 }
				 if(result != false) {  // 資料庫有資料，代表有重複場次
					errorSessionMsgs = "場次重複";  	
				 }
 	              	             
 	           
	           // Send the use back to the form, if there were errors   
	           if (errorMsgs != null || errorDateMsgs!= null || errorTimeMsgs != null || errorSessionMsgs != null) {
					req.setAttribute("movNo", movNo);
					req.setAttribute("sesDateBegin", sesDateBegin);
					req.setAttribute("sesDateEnd", sesDateEnd);
					req.setAttribute("sesTimeList", sesTimeList);
					req.setAttribute("theNoList", theNoList);
					req.setAttribute("errorDateMsgs",errorDateMsgs);
					req.setAttribute("errorTimeMsgs",errorTimeMsgs);
					req.setAttribute("errorSessionMsgs",errorSessionMsgs);
					 
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/session/addSession.jsp");
					failureView.forward(req, res);
					return;
	           }
              
           
               for (int i = 0; i < sesDateArr.length; i++) {
	                 sesDate = Date.valueOf(sesDateArr[i]);  
                   for (int j = 0; j < sesTimeArr.length; j++) {
						sesTime = Time.valueOf(java.time.LocalTime.parse(sesTimeArr[j]));
                       for (int k = 0; k < theNoArr.length; k++) {                       
                            theNo = new Integer(theNoArr[k]);
                            TheVO theVO = theSvc.getOneTheater(theNo);
                           /***************************2.開始新增資料***************************************/   
                            sesSvc.addSes(movNo, theNo, sesDate, sesTime, theVO.getThe_seat(), theVO.getThe_seatno());
                       }
                   }
               }
                                         
	                                      
              /***************************3.新增完成,準備轉交(Send the Success view)***********/                
              String addSuccess = "【 場次 】" + "新增成功";
              req.setAttribute("addSuccess", addSuccess);    
              
              RequestDispatcher successView = req.getRequestDispatcher("/back-end/session/listAllSession.jsp");
              successView.forward(req, res);    
               
               /***************************其他可能的錯誤處理**********************************/
			}catch (Exception e) {
	            errorMsgs = "Exception " + e.getMessage();
	            RequestDispatcher failureView = req.getRequestDispatcher("/back-end/session/addSession.jsp");
	            failureView.forward(req, res);
	        }		
		}
		
		// 來自listAllSession.jsp的請求
		if ("getOne_For_Update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");
		
			try {
				/***************************1.接收請求參數****************************************/
				Integer sesNo = new Integer(req.getParameter("sesNo").trim());
				
				/***************************2.開始查詢資料****************************************/
				SesService sesSvc = new SesService();
				SesVO sesVO = sesSvc.getOneSes(sesNo);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				if(requestURL.equals("/back-end/session/listSessions_ByCompositeQuery.jsp")){
					HttpSession session = req.getSession();
					Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
					List<SesVO> list  = sesSvc.getAll(map);
					req.setAttribute("listSessions_ByCompositeQuery",list); // 複合查詢, 資料庫取出的list物件,存入
				}
	            
	            req.setAttribute("sesVO", sesVO);
	
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/session/update_session_input.jsp");
				successView.forward(req, res);
	
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料 " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/session/listAllSession.jsp");
				failureView.forward(req, res);
			}
		}
		
		// 來自update_session_input.jsp的請求
		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer sesNo = new Integer(req.getParameter("sesNo").trim());
				Integer theNo = new Integer(req.getParameter("theNo").trim());
				Integer movNo = new Integer(req.getParameter("movNo").trim());
				
                String sesDateStr = req.getParameter("sesDate").trim();
	            java.sql.Date sesDate = null;
                sesDate = Date.valueOf(sesDateStr);  
          
	            Time sesTime = null;
                String sesTimeStr = req.getParameter("sesTime").trim();  
                sesTime = Time.valueOf(java.time.LocalTime.parse(convertTimes(sesTimeStr))); //取到的時間格式為"10:00AM"，需轉為24小時制格式 
                

				// Here're parameters for sending back to the front page, if there were errors   
                SesVO sesVO = new SesVO();
                sesVO.setSesNo(sesNo);
                sesVO.setMovNo(movNo);
                sesVO.setTheNo(theNo);
                sesVO.setSesDate(sesDate);
                sesVO.setSesTime(sesTime);
                
             /* =====================================================================
                				場次是否重複，錯誤驗證  //theNo、sesDate、sesTime
			    ===================================================================== */
 	            SesService sesSvc = new SesService(); 
 	            String errorSessionMsgs = null;
                Boolean result = true;
				result = sesSvc.isRepeatedSession(theNo, sesDate, sesTime); 
				if(result != false) {  // 資料庫有資料，代表有重複場次
					errorSessionMsgs = "場次重複";  	
				}
			    	             
			 
				// Send the use back to the form, if there were errors   
				if (errorSessionMsgs != null) {
					req.setAttribute("errorSessionMsgs",errorSessionMsgs);
					req.setAttribute("sesVO",sesVO);
					 
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/session/update_session_input.jsp");
					failureView.forward(req, res);
					return;
				}
				
	    	    /***************************2.開始修改資料*****************************************/ 
	            sesSvc.updateSes(sesDate, sesTime, sesNo);
	            
				/***************************3.修改完成,準備轉交(Send the Success view)*************/	
				if(requestURL.equals("/back-end/session/listSessions_ByCompositeQuery.jsp")){
					HttpSession session = req.getSession();
					Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
					List<SesVO> list  = sesSvc.getAll(map);
					req.setAttribute("listSessions_ByCompositeQuery",list); // 複合查詢, 資料庫取出的list物件,存入
				}
				
				String updateSuccess = "【 場次 】" + "修改成功";
				req.setAttribute("updateSuccess", updateSuccess);
				
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/session/listAllSession.jsp");
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			}catch (Exception e) {
				errorMsgs.add("修改資料失敗 " + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/session/listAllSession.jsp");
				failureView.forward(req, res);
			}			
		}
		
		// 來自frontend sessions.jsp的請求
		if ("searchSesDate".equals(action)) {
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
                 String sesDateStr = req.getParameter("sesDate").trim();     
	             java.sql.Date sesDate = null;
	             sesDate = Date.valueOf(sesDateStr);    
                 System.out.println(sesDate); 
	    	    /***************************2.開始修改資料*****************************************/ 
 	            SesService sesSvc = new SesService();
				List<SesVO> list  =  sesSvc.getMoviesByDate(sesDate);
				
				req.setAttribute("getMovies_BySesDate",list); 
	            
				/***************************3.修改完成,準備轉交(Send the Success view)*************/		            
				RequestDispatcher successView = req.getRequestDispatcher("/front-end/sessions/sessions.jsp");
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			}catch (Exception e) {
				System.out.println("搜尋資料失敗 " + e.getMessage());
				return;
			}		
		}
		
				
	}
	
	/*=======================================================================
	 	* 取出 dateBegin 到 dateEnd 內的所有時間，因新增場次時，可以選擇日期範圍。
	 	* ex: Monday to Thursday，的某幾場場次時間是相同的，每一筆都需寫進資料庫。
	 =========================================================================*/
	public List<String> getDates(String dateBegin, String dateEnd) throws ParseException, java.text.ParseException {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
 
        /* ===================================
         	* parse String dateBegin to Date 
         ===================================== */
        Calendar calBegin = Calendar.getInstance();
        calBegin.setTime(format.parse(dateBegin));
 

        /* ===================================
         	* parse String dateEnd to Date 
         ===================================== */
        Calendar calEnd = Calendar.getInstance();
        calEnd.setTime(format.parse(dateEnd));
        
        List<String> dateList = new ArrayList<String>();
        dateList.add(format.format(calBegin.getTime()));
        
        
        /* ====================================================================================
     		* whether dateEnd is after calBegin
     		* if it's true -> calBegin will be plused a day via using「 Calendar.DAY_OF_MONTH 」 
     	======================================================================================= */
        while (format.parse(dateEnd).after(calBegin.getTime()))  {
            calBegin.add(Calendar.DAY_OF_MONTH, 1);   // DAY_OF_MONTH 取出當前月的第幾天
            dateList.add(format.format(calBegin.getTime()));
        }
        return dateList;
    }
	
	public List<String> getTimes(String times) {
        List<String> timeList = new ArrayList<String>();
        timeList.add(times);
		return timeList;
	}
	
	
    /* ====================================================================================
 		* 需把 update_session_input.jsp 來的時間格式改為24小時制，才可寫進資料庫。
 	======================================================================================= */
	public static String convertTimes(String twelveHourTime) throws ParseException {
		DateFormat twenty_tf = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
		DateFormat twenty_four_tf = new SimpleDateFormat("HH:mm");
		return twenty_four_tf.format(twenty_tf.parse(twelveHourTime));
	}
}
