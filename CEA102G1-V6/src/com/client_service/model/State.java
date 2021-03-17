package com.client_service.model;

import java.util.Set;

public class State {
	private String type;
	private String memNo;
	private Set<String> memNos;
	
	public State() {
	}
	
	public State(String type, String memNo, Set<String> memNos) {
		super();
		this.type=type;
		this.memNo=memNo;
		this.memNos=memNos;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMemNo() {
		return memNo;
	}
	public void setMemNo(String memNo) {
		this.memNo = memNo;
	}
	public Set<String> getMemNos() {
		return memNos;
	}
	public void setMemNos(Set<String> memNos) {
		this.memNos = memNos;
	}
}
