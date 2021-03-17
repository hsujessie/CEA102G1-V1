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
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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


@MultipartConfig()
public class SesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;  
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		doPost(req,res);
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException{
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		System.out.println("action:"+action);

		
		if("getOne_For_Display".equals(action)) { 
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

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
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureVoew = req.getRequestDispatcher("/back-end/session/listAllSession.jsp");
				failureVoew.forward(req,res);
			}
		}

		if("listSessions_ByCompositeQuery".equals(action)) {
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
			SesService sesSvc = new SesService();
			List<SesVO> list  = sesSvc.getAll(map);
			
			/***************************3.查詢完成,準備轉交(Send the Success view)************/
			req.setAttribute("listSessions_ByCompositeQuery", list); // 資料庫取出的list物件,存入request
			
			RequestDispatcher successView = req.getRequestDispatcher("/back-end/session/listSessions_ByCompositeQuery.jsp"); // 成功轉交listSessions_ByCompositeQuery.jsp
			successView.forward(req, res);
			
			/***************************其他可能的錯誤處理**********************************/
			}catch(Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/session/listAllSession.jsp");
				failureView.forward(req, res);
			}
		}
	
	
		if ("insert".equals(action)) {
            Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs",errorMsgs);

            try {
                /***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
                 Integer movNo = new Integer(req.getParameter("movNo").trim());
	             System.out.println("movNo= " + movNo);
                 
                 String[] theNoArr = req.getParameterValues("theNo");
                 Integer theNo = null;
                 if (theNoArr == null || theNoArr.length == 0) {
                	 errorMsgs.put("theNo"," 請選擇廳院");
                     System.out.println("theNo is empty!");
                 }
                 
                 String sesDateBegin = req.getParameter("sesDateBegin").trim();
	             String sesDateEnd = req.getParameter("sesDateEnd").trim();            
	             List<String> sesDateList = null;
	             java.sql.Date sesDate = null;
            	 try {
	            	 sesDateList = getDates(sesDateBegin,sesDateEnd);
	             } catch (ParseException e) {
					e.printStackTrace();
	             }             
	             String[] sesDateArr = new String[sesDateList.size()];
	             sesDateArr = sesDateList.toArray(sesDateArr);
	             
	             
	             Time sesTime = null;   
	             String[] sesTimeArr = req.getParameterValues("sesTime");	             
	             List<LocalTime> sesTimeListEven = new ArrayList<LocalTime>();
	             List<LocalTime> sesTimeListOdd = new ArrayList<LocalTime>();
	             Duration diff = null;
	             if (sesTimeArr == null || sesTimeArr.length == 0) {
					   errorMsgs.put("sesTime"," 請選擇電影時間");
	                   System.out.println("sesTime is empty!");
	             }else {
	            	 
	            	/* =====================================================================
	            	                          場次時間間距，錯誤驗證
	            	   =====================================================================*/
	            	 if(sesTimeArr.length > 0) {
						System.out.println("if= " + sesTimeArr.length);
						for(int j = 0; j < sesTimeArr.length; j++) {	
							if(j%2 == 0) {                                                                   //取到的時間格式為"10:00AM"，需轉為24小時制格式，才能取時間的difference
								sesTimeListEven.add(java.time.LocalTime.parse(convertTimes(sesTimeArr[j]))); //將偶數索引值的時間轉換後，存進list
							}
							if(j%2 != 0) {
								sesTimeListOdd.add(java.time.LocalTime.parse(convertTimes(sesTimeArr[j])));  //將奇數索引值的時間轉換後，存進list
							}
						}
						
						for(int i = 0; i < sesTimeListEven.size(); i++) {
							System.out.println("Even List: " + sesTimeListEven.get(i));
							System.out.println("Odd  List: " + sesTimeListOdd.get(i));
							diff = Duration.between(sesTimeListEven.get(i),sesTimeListOdd.get(i));  //將兩個list裡面的時間相減
							System.out.println("diff= " + diff.toHours()); 
							if(diff.toHours() < 2 && diff.toHours() > 0) {   // 判斷 > 0 為了避免 10:00AM - 12:00AM 的相差等於 -10 負數
								errorMsgs.put("sesTime"," 間距不可少於2小時");  							
							}
						}
						
						
					}else {
						System.out.println("else= " +  + sesTimeArr.length);
						sesTime = Time.valueOf(java.time.LocalTime.parse(convertTimes(sesTimeArr[0])));
					}
	             }

	             // Here're parameters for sending back to the front page, if there were errors   
             	 SesVO sesVO = new SesVO();
                 sesVO.setSesNo(movNo);
                 sesVO.setTheNo(theNo);
                 sesVO.setSesDate(sesDate);
                 sesVO.setSesTime(sesTime);

	             // Send the use back to the form, if there were errors   
	             if (!errorMsgs.isEmpty()) {
					  req.setAttribute("sesVO", sesVO);
					  String url = "/back-end/session/addSession.jsp";
					  RequestDispatcher failureView = req.getRequestDispatcher(url);
					  failureView.forward(req, res);
					  return;
	             }
              
            
	           SesService sesSvc = new SesService();
               for(int i = 0; i < sesDateArr.length; i++) {
	                 sesDate = Date.valueOf(sesDateArr[i]);  
                   for(int j = 0; j < sesTimeArr.length; j++) {
						sesTime = Time.valueOf(java.time.LocalTime.parse(convertTimes(sesTimeArr[j])));
                       for(int k = 0; k < theNoArr.length; k++) {                       
                           theNo = new Integer(theNoArr[k]);
                           /***************************2.開始新增資料***************************************/   
                           sesSvc.addSes(movNo, theNo, sesDate, sesTime, null, null);      //暫時寫死
                       }
                   }
               }
                                         
	                                      
              /***************************3.新增完成,準備轉交(Send the Success view)***********/                
              String addSuccess = "【 場次 】" + "新增成功";
              req.setAttribute("addSuccess", addSuccess);    
              
              String url = "/back-end/session/listAllSession.jsp";
              RequestDispatcher successView = req.getRequestDispatcher(url);
              successView.forward(req, res);    
               
               /***************************其他可能的錯誤處理**********************************/
			}catch (Exception e) {
				System.out.println("Exception= " + e.getMessage());
				
	            errorMsgs.put("Exception",e.getMessage());
	            String url = "/back-end/session/addSession.jsp";
	            RequestDispatcher failureView = req.getRequestDispatcher(url);
	            failureView.forward(req, res);
	        }		
		}
		
		// 來自listAllSession.jsp的請求
		if ("getOne_For_Update".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			String requestURL = req.getParameter("requestURL");
		
			try {
				/***************************1.接收請求參數****************************************/
				Integer sesNo = new Integer(req.getParameter("sesNo").trim());
				System.out.println("sesNo= " + sesNo);
				
				/***************************2.開始查詢資料****************************************/
				SesService sesSvc = new SesService();
				SesVO sesVO = sesSvc.getOneSes(sesNo);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				if(requestURL.equals("/back-end/session/listSessions_ByCompositeQuery.jsp")){
					HttpSession session = req.getSession();
					Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
					List<SesVO> list  = sesSvc.getAll(map);
					req.setAttribute("listSessions_ByCompositeQuery",list); // 複合查詢, 資料庫取出的list物件,存入
					Boolean cssForListSessionsByCompositeQuery = true;
					req.setAttribute("cssForListSessionsByCompositeQuery",cssForListSessionsByCompositeQuery);
				}
	            
	            req.setAttribute("sesVO", sesVO);
	
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/session/update_session_input.jsp");
				successView.forward(req, res);
	
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.put("Exception"," 無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
			}
		}
		
		// 來自update_session_input.jsp的請求
		if ("update".equals(action)) {
			Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
			
			String requestURL = req.getParameter("requestURL");
			
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer sesNo = new Integer(req.getParameter("sesNo").trim());
	            System.out.println("sesNo= " + sesNo);

                Integer theNo = new Integer(req.getParameter("theNo").trim());
	            System.out.println("theNo= " + theNo);
                
                String sesDateStr = req.getParameter("sesDate").trim();
	            java.sql.Date sesDate = null;
                sesDate = Date.valueOf(sesDateStr);  
          
	            Time sesTime = null;
                String sesTimeStr = req.getParameter("sesTime").trim();  
                sesTime = Time.valueOf(java.time.LocalTime.parse(convertTimes(sesTimeStr)));  
                       
	    	    /***************************2.開始修改資料*****************************************/ 
 	            SesService sesSvc = new SesService();
	            sesSvc.updateSes(theNo, sesDate, sesTime, sesNo);
	            
				/***************************3.修改完成,準備轉交(Send the Success view)*************/	
				if(requestURL.equals("/back-end/session/listSessions_ByCompositeQuery.jsp")){
					HttpSession session = req.getSession();
					Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
					List<SesVO> list  = sesSvc.getAll(map);
					req.setAttribute("listSessions_ByCompositeQuery",list); // 複合查詢, 資料庫取出的list物件,存入
				}
				
				String updateSuccess = "【 場次 】" + "修改成功";
				req.setAttribute("updateSuccess", updateSuccess);
				
				String url = requestURL;
				if(requestURL.equals("/back-end/session/update_session_input.jsp")){
					url = "/back-end/session/listAllSession.jsp";
				}

				System.out.println("url= " + url);
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			}catch (Exception e) {
				String errMsg = "修改資料失敗 " + e.getMessage();
				System.out.println(errMsg);
				req.setAttribute("errMsg", errMsg);
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
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
                       
	    	    /***************************2.開始修改資料*****************************************/ 
 	            SesService sesSvc = new SesService();
				List<SesVO> list  =  sesSvc.getMoviesBySesDate(sesDate);
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
        
        List<String> Datelist = new ArrayList<String>();
        Datelist.add(format.format(calBegin.getTime()));
        
        
        /* ====================================================================================
     		* whether dateEnd is after calBegin
     		* if it's true -> calBegin will be plus a day via using「 Calendar.DAY_OF_MONTH 」 
     	======================================================================================= */
        while (format.parse(dateEnd).after(calBegin.getTime()))  {
            calBegin.add(Calendar.DAY_OF_MONTH, 1);   // DAY_OF_MONTH 取出當前月的第幾天
            Datelist.add(format.format(calBegin.getTime()));
        }
        return Datelist;
    }
	
	public List<String> getTimes(String times) {
        List<String> TimeList = new ArrayList<String>();
        TimeList.add(times);
		return TimeList;
	}
	
	
    /* ====================================================================================
     	* 新增場次，場次時間間距，錯誤驗證。
 		* 需把前台來的時間格式改為24小時制，才可判斷，場次時間之間是否少於2hr。
 	======================================================================================= */
	public static String convertTimes(String twelveHourTime) throws ParseException {
		DateFormat twenty_tf = new SimpleDateFormat("hh:mm a", Locale.ENGLISH);
		DateFormat twenty_four_tf = new SimpleDateFormat("HH:mm");
		return twenty_four_tf.format(twenty_tf.parse(twelveHourTime));
	}
}
