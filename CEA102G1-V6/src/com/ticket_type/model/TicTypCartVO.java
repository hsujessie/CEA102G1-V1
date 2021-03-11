package com.ticket_type.model;

import java.io.Serializable;

public class TicTypCartVO implements Serializable{
	private Integer ticTypNo;
	private Integer ideNo;
	private String sesSeatNo;
	private Integer ticTypCount;
	private Integer ticLisPrice;
	
	public TicTypCartVO() {}

	public Integer getTicTypNo() {
		return ticTypNo;
	}

	public void setTicTypNo(Integer ticTypNo) {
		this.ticTypNo = ticTypNo;
	}

	public String getSesSeatNo() {
		return sesSeatNo;
	}

	public void setSesSeatNo(String sesSeatNo) {
		this.sesSeatNo = sesSeatNo;
	}

	public Integer getTicLisPrice() {
		return ticLisPrice;
	}

	public void setTicLisPrice(Integer ticLisPrice) {
		this.ticLisPrice = ticLisPrice;
	}

	public Integer getIdeNo() {
		return ideNo;
	}

	public void setIdeNo(Integer ideNo) {
		this.ideNo = ideNo;
	}

	public Integer getTicTypCount() {
		return ticTypCount;
	}

	public void setTicTypCount(Integer ticTypCount) {
		this.ticTypCount = ticTypCount;
	}
	
	
}
