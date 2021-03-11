package com.order_master.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class OrdMasVO implements Serializable {
	private Integer ordMasNo;
	private Integer memNo;
	private Integer sesNo;
	private Timestamp ordMasDate;
	private Integer ordMasPrice;
	private String ordMasGetNo;
	private Integer ordMasStatus;
	
	public OrdMasVO() {}

	public Integer getOrdMasNo() {
		return ordMasNo;
	}

	public void setOrdMasNo(Integer ordMasNo) {
		this.ordMasNo = ordMasNo;
	}

	public Integer getMemNo() {
		return memNo;
	}

	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}

	public Integer getSesNo() {
		return sesNo;
	}

	public void setSesNo(Integer sesNo) {
		this.sesNo = sesNo;
	}

	public Timestamp getOrdMasDate() {
		return ordMasDate;
	}

	public void setOrdMasDate(Timestamp ordMasDate) {
		this.ordMasDate = ordMasDate;
	}

	public Integer getOrdMasPrice() {
		return ordMasPrice;
	}

	public void setOrdMasPrice(Integer ordMasPrice) {
		this.ordMasPrice = ordMasPrice;
	}

	public String getOrdMasGetNo() {
		return ordMasGetNo;
	}

	public void setOrdMasGetNo(String ordMasGetNo) {
		this.ordMasGetNo = ordMasGetNo;
	}

	public Integer getOrdMasStatus() {
		return ordMasStatus;
	}

	public void setOrdMasStatus(Integer ordMasStatus) {
		this.ordMasStatus = ordMasStatus;
	}
	
}
