package com.client_service.controller;

import java.io.IOException;
import java.util.*;
import javax.websocket.*;
import javax.websocket.server.*;

import org.json.JSONObject;

import java.util.concurrent.ConcurrentHashMap;

import com.client_service.model.*;
import com.google.gson.Gson;
import com.member.model.*;
import com.admin.model.*;



@ServerEndpoint("/ServiceWS/{identity}/{identityID}")
public class ServiceWS {
	private static Map<String, Session> sessionsMapForWaitingMem = new ConcurrentHashMap<>();
	private static Map<String, Session> sessionsMapForMem = new ConcurrentHashMap<>();
	private static Map<String, Session> sessionsMapForAdm = new ConcurrentHashMap<>();
	Gson gson = new Gson();

	@OnOpen
	public void onOpen(@PathParam("identity") String identity,@PathParam("identityID") String identityID, Session userSession) throws IOException {
		if (identity.equals("member")) {
			String memNo = identityID;
			Collection<Session> adminSessions = sessionsMapForAdm.values();
			if (adminSessions.size() > 0) {
				sessionsMapForMem.put(memNo, userSession);
				Set<String> memNos = sessionsMapForMem.keySet();
				Set<String> admNos = sessionsMapForAdm.keySet();
				State stateMessage = new State("open", memNo, memNos);
				String stateMessageJson = gson.toJson(stateMessage);
				for(Session session: adminSessions) {
					if(session.isOpen()) {
						session.getAsyncRemote().sendText(stateMessageJson);
						for(String admNo: admNos) {
							if(sessionsMapForAdm.get(admNo).equals(session)) {
								JSONObject jsonObj = new JSONObject();
								AdmService admSvc = new AdmService();
								String admName = admSvc.getOneAdm(admNo).getAdmName();
								jsonObj.put("admID", admNo);
								jsonObj.put("admName", admName);
								jsonObj.put("type", "open");
								jsonObj.put("message", "您好，我是"+admName+"，很高興為您服務，請問有什麼我能幫您的呢？");
								userSession.getAsyncRemote().sendText(jsonObj.toString());
							}
						}
					}
				}								
			} else {
				sessionsMapForWaitingMem.put(memNo,userSession);
				JSONObject jsonObj = new JSONObject();
				jsonObj.put("type", "noAdminOnline");
				userSession.getAsyncRemote().sendText(jsonObj.toString());
			}
			
		}
		if (identity.equals("admin")) {
			String admNo = identityID;
			sessionsMapForAdm.put(admNo,userSession);
			Set<String> memNosForWait = sessionsMapForWaitingMem.keySet();
			Collection<Session> sessionsForWaitingMems = sessionsMapForWaitingMem.values();
			if(sessionsMapForWaitingMem.size()>0) {
				for(String memNo:memNosForWait) {
					Session session = sessionsMapForWaitingMem.get(memNo);
					JSONObject jsonObj = new JSONObject();
					AdmService admSvc = new AdmService();
					String admName = admSvc.getOneAdm(admNo).getAdmName();
					jsonObj.put("admID", admNo);
					jsonObj.put("admName", admName);
					jsonObj.put("type", "open");
					jsonObj.put("message", "您好讓您久等了，我是"+admName+"，很高興為您服務，請問有什麼我能幫您的呢？");
					session.getAsyncRemote().sendText(jsonObj.toString());
					sessionsMapForMem.put(memNo,session);
					sessionsMapForWaitingMem.remove(memNo);
				}
			}
			
			Set<String> memNos = sessionsMapForMem.keySet();
			if(memNos.size() > 0) {
				State stateMessage = new State();
				stateMessage.setMemNos(memNos);
				stateMessage.setType("openAdm");
				userSession.getAsyncRemote().sendText(gson.toJson(stateMessage));
			}
		}
	}

	@OnMessage
	public void onMessage(Session userSession, String message) {
			ChatMessage cmsg = gson.fromJson(message, ChatMessage.class);
			String sender = cmsg.getSender();
			String receiver = cmsg.getReceiver();
			String action = cmsg.getType();
			message = new JSONObject(cmsg).toString();
			
			
			if ("history".equals(action)) {
				List<String> historyData = JedisHandleMessage.getHistoryMsg(sender, receiver);
				String historyMsg = gson.toJson(historyData);
				ChatMessage cmHistory = new ChatMessage("history", sender, receiver, historyMsg);
				if (userSession != null && userSession.isOpen()) {
					userSession.getAsyncRemote().sendText(gson.toJson(cmHistory));
					return;
				}
			}
			if (receiver.contains("adm")) { //會員發送給客服人員
				Session admSession = sessionsMapForAdm.get(receiver.split("-")[1]);
				if (admSession != null && admSession.isOpen()) {
					admSession.getAsyncRemote().sendText(message);
					userSession.getAsyncRemote().sendText(message);
					JedisHandleMessage.saveChatMessage(sender, receiver, message);
				}
			} else { //客服人員發送給會員
				Session memSession = sessionsMapForMem.get(receiver.split("-")[0]);
				if (memSession != null && memSession.isOpen()) {
					memSession.getAsyncRemote().sendText(message);
					userSession.getAsyncRemote().sendText(message);
					JedisHandleMessage.saveChatMessage(sender, receiver, message);
				}
			}
	}

	@OnError
	public void onError(Session userSession, Throwable e) {
		System.out.println("Error: " + e.toString());
		e.printStackTrace();
	}

	@OnClose
	public void onClose(Session userSession, CloseReason reason) {
		String closeMemberID = null;
		
		if (sessionsMapForMem.values().contains(userSession)) { //使用者離線
			Set<String> memNos = sessionsMapForMem.keySet();
			for (String memNo : memNos) {
				if (sessionsMapForMem.get(memNo).equals(userSession)) {
					closeMemberID = memNo;
					sessionsMapForMem.remove(memNo);
					break;
				}
			}
			if (closeMemberID != null) {
				State stateMessage = new State("close", closeMemberID, memNos);
				String stateMessageJson = gson.toJson(stateMessage);
				Collection<Session> admSessions = sessionsMapForAdm.values();
				for (Session session : admSessions) {
					session.getAsyncRemote().sendText(stateMessageJson);
				}
			}
		} else { //會員離線
			Set<String> admNos = sessionsMapForAdm.keySet();
			for (String admNo : admNos) {
				if (sessionsMapForAdm.get(admNo).equals(userSession)) {
					sessionsMapForAdm.remove(admNo);
					break;
				}
			}
		}
	}
}