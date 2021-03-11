package com.expectation.model;

public class ExpVO implements java.io.Serializable{
	private Integer movNo;
	private Integer memNo;
	private Integer expRating;
	
	public ExpVO() {}
	
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
	public Integer getExpRating() {
		return expRating;
	}
	public void setExpRating(Integer expRating) {
		this.expRating = expRating;
	}
}
