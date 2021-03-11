package com.board.model;
import java.sql.Date;

public class BoardVO implements java.io.Serializable{
	
	private Integer boaNo;
	private Integer boatypNo;
	private String boaContent;
	private Date boaTime;
	
	
	public Integer getBoaNo() {
		return boaNo;
	}
	public void setBoaNo(Integer boaNo) {
		this.boaNo = boaNo;
	}
	public Integer getBoatypNo() {
		return boatypNo;
	}
	public void setBoatypNo(Integer boatypNo) {
		this.boatypNo = boatypNo;
	}
	public String getBoaContent() {
		return boaContent;
	}
	public void setBoaContent(String boaContent) {
		this.boaContent = boaContent;
	}
	public Date getBoaTime() {
		return boaTime;
	}
	public void setBoaTime(Date boaTime) {
		this.boaTime = boaTime;
	}
	
	
	
}
