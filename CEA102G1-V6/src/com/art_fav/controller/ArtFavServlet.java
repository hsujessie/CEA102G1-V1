package com.art_fav.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.art.model.ArtService;
import com.art_fav.model.ArtFavService;
import com.art_fav.model.ArtFavVO;
import com.member.model.MemberService;


public class ArtFavServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
           
    public ArtFavServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		System.out.println("action---"+action);

		//ArtIndex.jsp呼叫，新增收藏
		if("addArtFav".equals(action)) {
			JSONArray array = new JSONArray();
			HttpSession session = request.getSession();
			
			/*====================請求參數===================*/
			System.out.println(request.getParameter("artNo")); 
			Integer artNo = Integer.parseInt(request.getParameter("artNo"));
			Integer memNo = Integer.parseInt(String.valueOf(session.getAttribute("memNo")));
			
			/*====================新增資料===================*/
			ArtFavService artFavSvc = new ArtFavService();
			artFavSvc.insertArtFav(artNo, memNo);
			
			/*==============傳回=============*/
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();			
			
		}
		
		//ArtIndex.jsp呼叫，刪除收藏
		if("deleteArtFav".equals(action)) {
			JSONArray array = new JSONArray();
			HttpSession session = request.getSession();
			
			/*====================請求參數===================*/
			System.out.println("request.getParameter(\"artNo\")"+request.getParameter("artNo")); 
			Integer artNo = Integer.parseInt(request.getParameter("artNo"));
			System.out.println("session.getAttribute(\"memNo\")"+session.getAttribute("memNo"));
			Integer memNo = Integer.parseInt(String.valueOf(session.getAttribute("memNo")));
			
			/*====================新增資料===================*/
			ArtFavService artFavSvc = new ArtFavService();
			artFavSvc.deleteArtFav(artNo, memNo);
						
			/*==============傳回=============*/
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();			
			
		}
		
		//ArtIndex.jsp呼叫，檢查是否為收藏
		if("isArtFav".equals(action)) {
			JSONArray array = new JSONArray();
			HttpSession session = request.getSession();
			
			/*====================請求參數===================*/
			Integer artNo = Integer.parseInt(request.getParameter("artNo"));
			System.out.println(session.getAttribute("memNo"));
			if(session.getAttribute("memNo") != null) {
				Integer memNo = Integer.parseInt(String.valueOf(session.getAttribute("memNo")));				
			
			/*====================查詢資料===================*/
			ArtFavService artFavSvc = new ArtFavService();
			JSONObject obj = new JSONObject();
			
			/*==============放入JSONObject==============*/
			if(artFavSvc.getOneArtFav(artNo, memNo) == null) {
				try {
					obj.put("isArtFav", false);
					obj.put("artNo", artNo);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}else {
				try {
					obj.put("isArtFav", true);
					obj.put("artNo", artNo);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			array.put(obj);
			}
			/*==============傳回=============*/
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();
		}
		
		//收藏複合查詢
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
			ArtFavService artFavSvc = new ArtFavService();
			System.out.println("artFavSvc.getAll(map):" + artFavSvc.getAll(map));
			List<ArtFavVO> list = artFavSvc.getAll(map);
			System.out.println("---over getAll(map) method---");
			System.out.println("ArtFavServlet_List:" + list);
			System.out.println("list.size()"+list.size());
			
			/*==============放入JSONObject==================*/
			for (ArtFavVO artFavVO : list) {
				JSONObject obj = new JSONObject();
				MemberService memSvc = new MemberService();
				ArtService artSvc = new ArtService();
				
				try {
					obj.put("artNo", artFavVO.getArtNo());
					obj.put("memNo", artFavVO.getMemNo());
					obj.put("memName", memSvc.getOneMember((artFavVO.getMemNo())).getMemName());
					obj.put("artFavTime", artFavVO.getArtFavTime());
					obj.put("artTitle", artSvc.getOneArt(artFavVO.getArtNo()).getArtTitle());
					obj.put("artContent", artSvc.getOneArt(artFavVO.getArtNo()).getArtContent());
					obj.put("artMovType", artSvc.getOneArt(artFavVO.getArtNo()).getMovType());
					
					array.put(obj);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			System.out.println("=================artFav_Show_By_CompositeQuery==============");
			
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
