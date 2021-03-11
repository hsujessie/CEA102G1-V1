package com.comment_report.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ComRepVO implements Serializable{
	private Integer comRepNo;
	private Integer comNo;
	private Integer memNo;
	private String comRepReason;
	private Timestamp comRepTime;
	private Integer comRepStatus;
	
	public ComRepVO() {}

	public Integer getComRepNo() {
		return comRepNo;
	}

	public void setComRepNo(Integer comRepNo) {
		this.comRepNo = comRepNo;
	}

	public Integer getComNo() {
		return comNo;
	}

	public void setComNo(Integer comNo) {
		this.comNo = comNo;
	}

	public Integer getMemNo() {
		return memNo;
	}

	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}

	public String getComRepReason() {
		return comRepReason;
	}

	public void setComRepReason(String comRepReason) {
		this.comRepReason = comRepReason;
	}

	public Timestamp getComRepTime() {
		return comRepTime;
	}

	public void setComRepTime(Timestamp comRepTime) {
		this.comRepTime = comRepTime;
	}

	public Integer getComRepStatus() {
		return comRepStatus;
	}

	public void setComRepStatus(Integer comRepStatus) {
		this.comRepStatus = comRepStatus;
	}
}