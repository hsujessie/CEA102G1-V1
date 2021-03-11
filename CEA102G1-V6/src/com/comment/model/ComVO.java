package com.comment.model;

import java.sql.Timestamp;

public class ComVO implements java.io.Serializable{
	private Integer comNo;
	private Integer movNo;
	private Integer memNo;
	private Timestamp comTime;
	private String comContent;
	private Integer comStatus;
	
	public ComVO() {}

	public Integer getComNo() {
		return comNo;
	}

	public void setComNo(Integer comNo) {
		this.comNo = comNo;
	}

	public Integer getMovNo() {
		return movNo;
	}

	public void setMovNo(Integer movNo) {
		this.movNo = movNo;
	}

	public Integer getMemNo() {
		return memNo;
	}

	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}

	public Timestamp getComTime() {
		return comTime;
	}

	public void setComTime(Timestamp comTime) {
		this.comTime = comTime;
	}

	public String getComContent() {
		return comContent;
	}

	public void setComContent(String comContent) {
		this.comContent = comContent;
	}

	public Integer getComStatus() {
		return comStatus;
	}

	public void setComStatus(Integer comStatus) {
		this.comStatus = comStatus;
	}
}
