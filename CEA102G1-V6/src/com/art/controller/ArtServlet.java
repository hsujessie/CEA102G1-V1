package com.art.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.art.model.ArtService;
import com.art.model.ArtVO;
import com.member.model.MemberService;

import oracle.net.aso.a;
import oracle.net.aso.e;
import redis.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.SortingParams;


public class ArtServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ArtServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		System.out.println("action---"+action);
		
		//redis
		JedisPool pool = JedisUtil.getJedisPool();
		
		//article.jsp呼叫，新增文章
		if("newArt".equals(action)) {
			ArtVO artVO = new ArtVO();
			request.setAttribute("artVO", artVO);
			HttpSession session = request.getSession();
			
			if(request.getSession().getAttribute("memNo") != null) {
				
				/*====================轉送至新增文章===================*/
				String url = "/front-end/article/newArticle.jsp";
				RequestDispatcher newArticle = request.getRequestDispatcher(url);			
				newArticle.forward(request, response);				
			}else {
				session.setAttribute("location", request.getContextPath()+"/front-end/article/article.jsp");
				String location = (String) session.getAttribute("location");
				String url = request.getContextPath()+"/front-end/Member_Login/login.jsp";
				response.sendRedirect(url);
			}
		}
		
		//newArticle.jsp呼叫
		if("addArt".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			request.setAttribute("errorMsgs", errorMsgs);
			
			/*====================請求參數，輸入格式錯誤處理===================*/
			try {
				String artTitle = request.getParameter("artTitle");
				if(artTitle == null || (artTitle.trim()).length() == 0) {
					errorMsgs.add("文章標題請勿空白。");
				}
				System.out.println("artTitle："+ artTitle);
				
				String artContent = request.getParameter("editor");
				if(artContent == null || artContent.trim().length() == 0) {
					errorMsgs.add("文章內容請勿空白。");
				}
				String artMovTypeSelect = request.getParameter("artMovTypeSelect");
				System.out.println("artMovTypeSelect:"+artMovTypeSelect);
				if(artMovTypeSelect == null || artMovTypeSelect.trim().length() == 0) {
					errorMsgs.add("請選擇電影類型。");
				}
				
				ArtVO artVO = new ArtVO();
				artVO.setArtTitle(artTitle);
				artVO.setArtContent(artContent);
				artVO.setMovType(artMovTypeSelect);
				System.out.println("artVO.getArtContent:"+artContent);
				
				if(!errorMsgs.isEmpty()) {
					request.setAttribute("artVO", artVO);
					String url = "/front-end/article/newArticle.jsp";
					RequestDispatcher failureView = request.getRequestDispatcher(url);			
					failureView.forward(request, response);
					return; //程式中斷
				}
				
				System.out.println("artContent："+ artContent);
				HttpSession session = request.getSession();
				Integer memNo = Integer.parseInt(String.valueOf(session.getAttribute("memNo")));
				System.out.println("memNo："+memNo);

				/*====================新增文章===================*/				
				ArtService artSvc = new ArtService();
				artVO = artSvc.insertArt(memNo, artTitle, artContent, artMovTypeSelect);
				System.out.println("insert new an art");				
				
				Integer artNo = artVO.getArtNo();
				session.setAttribute("artNo", artNo);
				
				//Bootstrap_modal
				String openModal="openModal";
				session.setAttribute("openModal",openModal );
				
				//addSuccess
				session.setAttribute("addSuccess", "addSuccess");
				System.out.println("addArt_ok");	
								
				/*====================重導至單篇文章===================*/			
				String url = request.getContextPath()+"/front-end/article/article.jsp";
//				RequestDispatcher showArticle = request.getRequestDispatcher(url);
//				showArticle.forward(request, response);
				response.sendRedirect(url);
				
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				String url = "/front-end/article/newArticle.jsp";
				RequestDispatcher failureView = request.getRequestDispatcher(url);			
				failureView.forward(request, response);
			}
		}
		
		//ArticleContent.jsp / ArticleContent.file呼叫
		if("select_Upadte_One_Art".equals(action)) {
			/*====================請求參數===================*/		
			Integer artNo = new Integer(request.getParameter("artNo"));
			String artManageUpdate = request.getParameter("artManageUpdate");
			System.out.println("select_Upadte_One_Art(artNo)："+artNo);
			System.out.println("select_Upadte_One_Art(artManageUpdate)："+artManageUpdate);
			
			/*====================查詢資料===================*/			
			ArtService artSvc = new ArtService();
			ArtVO artVO = artSvc.getOneArt(artNo);
			
			/*====================轉送至updateArt.jsp===================*/			
			request.setAttribute("artVO", artVO);
			request.setAttribute("artManageUpdate", artManageUpdate);
			RequestDispatcher updateArticle = request.getRequestDispatcher("/front-end/article/updateArticle.jsp");
			updateArticle.forward(request, response);
			return;
		}
		
		//updateArt.jsp呼叫
		if("updateArt".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			request.setAttribute("errorMsgs", errorMsgs);
			System.out.println("errorMsgs.size():"+errorMsgs.size());

			try {
				/*====================請求參數===================*/
				HttpSession session = request.getSession();
				String artManageUpdate = null;
				if(session.getAttribute("artManageUpdate") != null) {
					artManageUpdate = (String) session.getAttribute("artManageUpdate");
					System.out.println("artManageUpdate:"+artManageUpdate);					
				}
				
				Integer artNo = new Integer(request.getParameter("artNo").trim());
				System.out.println("updateArt_error: artNo ok");
				
				String artTitle = request.getParameter("artTitle");
				System.out.println("artTitle:"+request.getParameter("artTitle"));
				if(artTitle == null || artTitle.trim().length() == 0) {
					errorMsgs.add("文章標題請勿空白。");
					System.out.println("文章標題請勿空白。");
				}
				
				String artContent = request.getParameter("editor");
				System.out.println("artContent:"+request.getParameter("editor"));
				if(artContent == null || artContent.trim().length() == 0) {
					errorMsgs.add("文章內容請勿空白。");
					System.out.println("文章內容請勿空白。");
				}
				
				String artMovTypeSelect = request.getParameter("artMovTypeSelect");
				System.out.println("artMovTypeSelect:"+artMovTypeSelect);
				if(artMovTypeSelect == null || artMovTypeSelect.trim().length() == 0) {
					errorMsgs.add("請選擇電影類型。");
				}
				
				ArtVO artVO = new ArtVO();
				artVO.setArtNo(artNo);
				artVO.setArtTitle(artTitle);
				artVO.setArtContent(artContent);
				artVO.setMovType(artMovTypeSelect);
				System.out.println("new artVO ok");
				
				if(!errorMsgs.isEmpty()) {
					System.out.println("errorMsgs is not empty");
					request.setAttribute("artVO", artVO);
					RequestDispatcher updateArticle = request.getRequestDispatcher("/front-end/article/updateArticle.jsp");
					updateArticle.forward(request, response);
					return; //程式中斷
				}
				/*====================查詢資料，修改文章===================*/			
				ArtService artSvc = new ArtService();
				System.out.println("updateArt_artNo："+request.getParameter("artNo"));
				System.out.println("updateArt_artTitle："+request.getParameter("artTitle"));
				System.out.println("updateArt_editor："+request.getParameter("editor"));
				artVO = artSvc.updateArt(artNo, artTitle, artContent, artMovTypeSelect);
				
				//Bootstrap_modal
				String openModal="openModal";
				session.setAttribute("openModal",openModal );
				
				session.setAttribute("artNo", artNo);
				//updateSuccess
				session.setAttribute("updateSuccess", "updateSuccess");
				System.out.println("updateArt_ok");	
				
				/*====================轉送至文章===================*/	
				if(artManageUpdate != null) {
					session.removeAttribute("artManageUpdate");
					String url = request.getContextPath()+"/front-end/article/articleManage.jsp";
					System.out.println(url);
					response.sendRedirect(url);
					return;
				}else {
					String url = request.getContextPath()+"/front-end/article/article.jsp";
					System.out.println(url);
//					RequestDispatcher showUpdateArticle = request.getRequestDispatcher(url);			
//					showUpdateArticle.forward(request, response);
					response.sendRedirect(url);
					return;
				}
				
			} catch (Exception e) {
				System.out.println("e.getMessage():"+e.getMessage());
				errorMsgs.add(e.getMessage());
				String url = "/front-end/article/updateArticle.jsp";
				RequestDispatcher failureView = request.getRequestDispatcher(url);			
				failureView.forward(request, response);
			}
		}
		
		//Using AJAX start
		//article.jsp呼叫，show all article with AJAX or show an article with AJAX
		if("art_Show_By_AJAX".equals(action)) {
			JSONArray array = new JSONArray();
			//建立redis連線
			Jedis jedis = pool.getResource();
			jedis.auth("123456");
			
			if(request.getParameter("artNo") == null) {
				System.out.println("artNo == null");
				ArtService artSvc = new ArtService();
				MemberService memSvc = new MemberService();
				List<ArtVO> list = artSvc.getAll();	
				/*==============放入JSONObject==============*/
				for (ArtVO artVO : list) {
					JSONObject obj = new JSONObject();
					try {
						//第一次新增
						if(jedis.hexists("artNo:"+artVO.getArtNo(), "clickTimes") == false) {
							Integer clickTimesN = 0;
							String clickTimes = String.valueOf(clickTimesN);
							jedis.hset("artNo:"+artVO.getArtNo(), "movType", artVO.getMovType());
							jedis.hset("artNo:"+artVO.getArtNo(), "clickTimes", clickTimes);							
						}

						//把該movType的artNo新增至redis，判斷個別分類文章Top3時使用
						jedis.sadd("movType:"+artVO.getMovType(), String.valueOf(artVO.getArtNo()));
						//新增artNo至redis，判斷全部文章Top3時使用
						jedis.sadd("all:artNo", String.valueOf(artVO.getArtNo()));
						
						obj.put("artNo", artVO.getArtNo());
						obj.put("memName", memSvc.getOneMember((artVO.getMemNo())).getMemName());
						obj.put("artTitle", artVO.getArtTitle());
						obj.put("artContent", artVO.getArtContent());
						obj.put("artTime", artVO.getArtTime());
						obj.put("artMovType", artVO.getMovType());
						array.put(obj);
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				System.out.println("=================art_Show_All_By_AJAX==============");
				
			} else {				
				System.out.println("artNo != null");
				
				JSONObject obj = new JSONObject();
				ArtService artSvc = new ArtService();
				MemberService memSvc = new MemberService();
				
				try {
					System.out.println("artNo:"+request.getParameter("artNo"));
					/*====================請求參數===================*/	
					Integer artNo = Integer.parseInt(request.getParameter("artNo"));
					ArtVO artVO = artSvc.getOneArt(artNo);
					
					System.out.println(jedis.hget("artNo:"+artVO.getArtNo(), "clickTimes"));
					
					//新增點擊次數熱門度
					Integer clickTimesN = new Integer(jedis.hget("artNo:"+artVO.getArtNo(), "clickTimes"));
					clickTimesN++;
					String clickTimes = String.valueOf(clickTimesN);
					jedis.hset("artNo:"+artVO.getArtNo(), "clickTimes", clickTimes);
					
					System.out.println("artNo:"+artVO.getArtNo());
					/*==============放入JSONObject==============*/
					obj.put("artNo", artVO.getArtNo());
					obj.put("memName", memSvc.getOneMember((artVO.getMemNo())).getMemName());
					obj.put("memNo", artVO.getMemNo());
					obj.put("artTitle", artVO.getArtTitle());
					obj.put("artContent", artVO.getArtContent());
					obj.put("artTime", artVO.getArtTime());
					obj.put("artMovType", artVO.getMovType());
					obj.put("artReplyno", artVO.getArtReplyno());
					
					array.put(obj);
					System.out.println("=================art_Show_One_By_AJAX==============");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			jedis.close();
			
			/*==============傳回=============*/
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();
		}
		
		//列出前三熱門文章
		if("artTopThree_Show_By_AJAX".equals(action)) {
			JSONArray array = new JSONArray();
			HttpSession session = request.getSession();
			ArtService artSvc = new ArtService();
			MemberService memSvc = new MemberService();
			
			/*====================從redis取前三筆點擊率===================*/
			//建立redis連線
			Jedis jedis = pool.getResource();
			jedis.auth("123456");
			String artMovType = request.getParameter("artMovType");
			System.out.println("artMovType:"+artMovType);
			List<String> resultArt = null;
			
			if(artMovType == null || artMovType.trim().length() == 0) {
				//查Top3
				resultArt = jedis.sort("all:artNo", new SortingParams().by("artNo:*->clickTimes").desc().limit(0, 3));
				System.out.println("查Top3:"+resultArt);

			}else {
				//依電影分類查Top3
				resultArt = jedis.sort("movType:"+artMovType, new SortingParams().by("artNo:*->clickTimes").desc().limit(0, 3));
				System.out.println("依電影分類查Top3:"+resultArt);
			}

			for(String artNo : resultArt) {
				JSONObject obj = new JSONObject();
				System.out.println("artNo:"+Integer.parseInt(artNo));
				ArtVO artVO = artSvc.getOneArt(Integer.parseInt(artNo));
				
				try {
					obj.put("artNo", artVO.getArtNo());
					obj.put("memName", memSvc.getOneMember((artVO.getMemNo())).getMemName());
					obj.put("memNo", artVO.getMemNo());
					obj.put("artTitle", artVO.getArtTitle());
					obj.put("artContent", artVO.getArtContent());
					obj.put("artTime", artVO.getArtTime());
					obj.put("artMovType", artVO.getMovType());
					obj.put("artReplyno", artVO.getArtReplyno());
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				array.put(obj);
			}
			jedis.close();
			
			/*==============傳回=============*/
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();
		}
		
		//依電影分類列出熱門文章
		if("movTypeHotArticle".equals(action)) {
			JSONArray array = new JSONArray();
			ArtService artSvc = new ArtService();
			MemberService memSvc = new MemberService();
			
			String movType = request.getParameter("movType");
			
			/*====================從redis取前三筆點擊率===================*/
			//建立redis連線
			Jedis jedis = pool.getResource();
			jedis.auth("123456");

			//查Top3
			List<String> resultArt = jedis.sort("movType:"+movType, new SortingParams().by("artNo:*->clickTimes").desc().limit(0, 3));
			System.out.println("依電影分類查Top3:"+resultArt);
			
			for(String artNo : resultArt) {
				JSONObject obj = new JSONObject();
				ArtVO artVO = artSvc.getOneArt(Integer.parseInt(artNo));
				
				try {
					obj.put("artNo", artVO.getArtNo());
					obj.put("memName", memSvc.getOneMember((artVO.getMemNo())).getMemName());
					obj.put("memNo", artVO.getMemNo());
					obj.put("artTitle", artVO.getArtTitle());
					obj.put("artContent", artVO.getArtContent());
					obj.put("artTime", artVO.getArtTime());
					obj.put("artMovType", artVO.getMovType());
					obj.put("artReplyno", artVO.getArtReplyno());
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
				array.put(obj);
				
			}
			jedis.close();
			
			/*==============傳回=============*/
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();
		}
		
		//article.jsp呼叫，複合查詢
		if("find_By_CompositeQuery_Use_AJAX".equals(action)) {
			JSONArray array = new JSONArray();
			
			/*====================請求參數===================*/
			HttpSession session = request.getSession();
			Map<String, String[]> map = (Map<String, String[]>) session.getAttribute("map");
			System.out.println("request.getParameter(\"whichPage\"):"+request.getParameter("whichPage"));
			if(request.getParameter("whichPage") == null) {
				HashMap<String, String[]> hashMap = new HashMap<String, String[]>(request.getParameterMap());
				session.setAttribute("map", hashMap);
				map = hashMap;
			}
			System.out.println("artServlet_map:" + map);
			
			/*====================複合查詢===================*/	
			ArtService artSvc = new ArtService();
			System.out.println("artSvc.getAll(map):" + artSvc.getAll(map));
			List<ArtVO> list = artSvc.getAll(map);
			List<Integer> artNoList = new ArrayList<Integer>();
			session.setAttribute("artVOCompositeQuery", list);
			
			System.out.println("---over getAll(map) method---");
			System.out.println("ArtServlet_List:" + list);
			System.out.println("list.size()"+list.size());
			
			/*==============放入JSONObject==================*/
			for (ArtVO artVO : list) {
				JSONObject obj = new JSONObject();
				MemberService memSvc = new MemberService();
				try {
					obj.put("artNo", artVO.getArtNo());
					obj.put("memNo", artVO.getMemNo());
					obj.put("memName", memSvc.getOneMember((artVO.getMemNo())).getMemName());
					obj.put("artTitle", artVO.getArtTitle());
					obj.put("artContent", artVO.getArtContent());
					obj.put("artTime", artVO.getArtTime());
					obj.put("artMovType", artVO.getMovType());
					
					array.put(obj);
					artNoList.add(artVO.getArtNo());
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			session.setAttribute("artNoList", artNoList);
			System.out.println("複合查詢artNoList:"+artNoList);
			System.out.println("=================art_Show_By_CompositeQuery==============");
			
			/*==============傳回=============*/
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();
		}
		
		//article.jsp呼叫，查電影類別
		if("find_MoveType_By_AJAX".equals(action)) {
			JSONArray array = new JSONArray();
			ArtService artSvc = new ArtService();
			
			List<String> movTypeList = artSvc.getAllMoveType();
			System.out.println("movTypeList:"+movTypeList);
			/*==============放入JSONObject==============*/
			int count = 0;
			JSONObject obj = new JSONObject();

			for (int i=0; i < movTypeList.size(); i++) {

				if(i % 2 == 0) {
					obj = new JSONObject();
					try {
						obj.put("artMovType", movTypeList.get(i));
						System.out.println("XXXXXXX"+movTypeList.get(i)+"XXXXXXX");
					} catch (JSONException e) {
						e.printStackTrace();
					}					
				}else {
					try {
						obj.put("movTypeIndex", movTypeList.get(i));
						System.out.println("XXXXXXX"+movTypeList.get(i)+"XXXXXXX");
					} catch (JSONException e) {
						e.printStackTrace();
					}
					array.put(obj);
					
					count++;
					System.out.println("movTypeList put times:"+count);
				}
			}
			System.out.println("=================find_MoveType_By_AJAX==============");
			
			/*==============傳回=============*/
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();
		}
	}
}
