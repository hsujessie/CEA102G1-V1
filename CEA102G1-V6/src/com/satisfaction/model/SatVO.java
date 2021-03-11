package com.satisfaction.model;

public class SatVO implements java.io.Serializable{
	private Integer movNo;
	private Integer memNo;
	private Integer satRating;
	
	public SatVO() {}
	
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
	public Integer getSatRating() {
		return satRating;
	}
	public void setSatRating(Integer satRating) {
		this.satRating = satRating;
	}
}
