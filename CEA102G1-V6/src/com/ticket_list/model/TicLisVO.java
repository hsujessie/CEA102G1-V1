package com.ticket_list.model;

import java.io.Serializable;

public class TicLisVO implements Serializable {
	private Integer ticLisNo;
	private Integer ordMasNo;
	private Integer ticTypNo;
	private String sesSeatNo;
	private Integer ticLisPrice;
	
	public TicLisVO() {}

	public Integer getTicLisNo() {
		return ticLisNo;
	}

	public void setTicLisNo(Integer ticLisNo) {
		this.ticLisNo = ticLisNo;
	}

	public Integer getOrdMasNo() {
		return ordMasNo;
	}

	public void setOrdMasNo(Integer ordMasNo) {
		this.ordMasNo = ordMasNo;
	}

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
	
}
