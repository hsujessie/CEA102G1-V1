package com.deposit.model;
import java.sql.Date;

public class DepositVO {
	
	private Integer depNo;
	private Integer memNo;
	private Integer depAmount;
	private Date depTime;
	
	
	public Integer getDepNo() {
		return depNo;
	}
	public void setDepNo(Integer depNo) {
		this.depNo = depNo;
	}
	public Integer getMemNo() {
		return memNo;
	}
	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}
	public Integer getDepamount() {
		return depAmount;
	}
	public void setDepamount(Integer depAmount) {
		this.depAmount = depAmount;
	}
	public Date getDepTime() {
		return depTime;
	}
	public void setDepTime(Date depTime) {
		this.depTime = depTime;
	}
	
	
	

}
