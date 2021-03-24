package com.art_rep_rpt.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.art.model.ArtDAO;
import com.art.model.ArtService;
import com.art_rep.model.ArtRepService;
import com.art_rep_rpt.model.ArtRepRptService;
import com.art_rep_rpt.model.ArtRepRptVO;
import com.member.model.MemberService;


public class ArtRepRptServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ArtRepRptServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String action = request.getParameter("action");
		System.out.println("action---"+action);
		
		//ArtIndex.jsp呼叫，新增留言檢舉
		if("addRepRpt".equals(action)) {
			JSONArray array = new JSONArray();
			HttpSession session = request.getSession();
			
			/*====================請求參數===================*/
			Integer artRepNo = Integer.parseInt(request.getParameter("artRepNo"));
			Integer memNo = Integer.parseInt(String.valueOf(session.getAttribute("memNo")));
			String artRepRptReson = request.getParameter("artRepRptReson");
			System.out.println("artRepRptReson:"+request.getParameter("artRepRptReson"));
			
			/*====================新增資料===================*/
			ArtRepRptService artRepRptSvc = new ArtRepRptService();
			artRepRptSvc.insertArtRepRpt(artRepNo, memNo, artRepRptReson);
			System.out.println("insertArtRepRpt ok!");
			
			/*==============傳回=============*/
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			PrintWriter out = response.getWriter();
			out.write(array.toString());
			out.flush();
			out.close();			
		}
		
		//articlereport.jsp呼叫，列出全部留言檢舉
		if("listAllArticleReplyReport".equals(action)) {
			JSONArray array = new JSONArray();
			
			ArtService artSvc = new ArtService();
			ArtRepService artRepSvc = new ArtRepService();
			ArtRepRptService artRepRptSvc = new ArtRepRptService();
			MemberService memSvc = new MemberService();
			List<ArtRepRptVO> list = artRepRptSvc.getAll();
			
			/*==============放入JSONObject==============*/
			for(ArtRepRptVO artRepRptVO : list) {
				
				JSONObject obj = new JSONObject();
				try { 
					obj.put("artRepNo", artRepRptVO.getArtRepNo());
					obj.put("artTitle", artSvc.getOneArt(artRepRptVO.getArtRepNo()).getArtTitle());
					obj.put("memName", memSvc.getOneMember(artSvc.getOneArt(artRepRptVO.getArtRepNo()).getMemNo()).getMemName());
					obj.put("artRepRptNo", artRepRptVO.getArtRepRptNo());
					obj.put("artRepRptReson", artRepRptVO.getArtRepRptReson());
					obj.put("artRepRptTime", artRepRptVO.getArtRepRptTime());
					
					if(artRepRptVO.getArtRepRptStatus() == 0) {
						obj.put("artRepRptStatusButton", "確認檢舉");
					}else {
						obj.put("artRepRptStatusButton", "已檢舉");
					}
					obj.put("artRepRptStatus", artRepRptVO.getArtRepRptStatus());
					obj.put("reportMemName", memSvc.getOneMember(artRepRptVO.getMemNo()).getMemName());
					obj.put("artRepContent", artRepSvc.getOneArtRep(artRepRptVO.getArtRepNo()).getArtRepContent());
				} catch (JSONException e) {
					e.printStackTrace();
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
		
		//articlereport.jsp呼叫，更新留言檢舉
		if("changeArticleReplyReport".equals(action)) {
			JSONArray array = new JSONArray();
			
			/*====================請求參數===================*/
			Integer artRepNo = Integer.parseInt(request.getParameter("artRepNo"));
			Integer artRepRptNo = Integer.parseInt(request.getParameter("artRepRptNo"));
			System.out.println("artRepNo:"+artRepNo);
			System.out.println("artRepRptNo:"+artRepRptNo);
			
			/*====================修改資料===================*/
			ArtRepService artRepSvc = new ArtRepService();
			Integer artRepStatus = artRepSvc.getOneArtRep(artRepNo).getArtRepStatus();
			if(artRepStatus == 0) {
				artRepSvc.updateStatus(artRepNo, 1);
			}else {
				artRepSvc.updateStatus(artRepNo, 0);
			}
			System.out.println("新的ArtRepStatus:"+artRepSvc.getOneArtRep(artRepNo).getArtRepStatus());
			
			ArtRepRptService artRepRptSvc = new ArtRepRptService();
			Integer artRepRptStatus = artRepRptSvc.getOneArtRepRpt(artRepRptNo).getArtRepRptStatus();
			if(artRepRptStatus == 0) {
				artRepRptSvc.updateArtRepRpt(artRepRptNo, 1);
			}else {
				artRepRptSvc.updateArtRepRpt(artRepRptNo, 0);
			}
			System.out.println("新的ArtRepRptStatus"+artRepRptSvc.getOneArtRepRpt(artRepRptNo).getArtRepRptStatus());
			
			/*==============放入JSONObject==============*/
				JSONObject obj = new JSONObject();
				try { 
					if(artRepRptSvc.getOneArtRepRpt(artRepRptNo).getArtRepRptStatus() == 0) {
						obj.put("artRepRptStatusButton", "確認檢舉");
						obj.put("artRepRptStatus", artRepRptSvc.getOneArtRepRpt(artRepRptNo).getArtRepRptStatus());
						System.out.println("artRepRptStatusButton: 確認檢舉");
					}else {
						obj.put("artRepRptStatusButton", "已檢舉");
						obj.put("artRepRptStatus", artRepRptSvc.getOneArtRepRpt(artRepRptNo).getArtRepRptStatus());
						System.out.println("artRepRptStatusButton: 已檢舉");
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
				array.put(obj);		
			
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
