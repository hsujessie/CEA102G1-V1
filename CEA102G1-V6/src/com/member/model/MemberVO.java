package com.member.model;

import java.sql.Date;

public class MemberVO {

	private Integer memNo;
	private String memName;
	private String memAccount;
	private String memPassword;
	private String memMail;
	private Integer memWallet;
	private Integer memstatus;
	private byte[] memImg;
	private String memuuid;
	public Integer getMemNo() {
		return memNo;
	}
	public void setMemNo(Integer memNo) {
		this.memNo = memNo;
	}
	public String getMemName() {
		return memName;
	}
	public void setMemName(String memName) {
		this.memName = memName;
	}
	public String getMemAccount() {
		return memAccount;
	}
	public void setMemAccount(String memAccount) {
		this.memAccount = memAccount;
	}
	public String getMemPassword() {
		return memPassword;
	}
	public void setMemPassword(String memPassword) {
		this.memPassword = memPassword;
	}
	public String getMemMail() {
		return memMail;
	}
	public void setMemMail(String memMail) {
		this.memMail = memMail;
	}
	public Integer getMemWallet() {
		return memWallet;
	}
	public void setMemWallet(Integer memWallet) {
		this.memWallet = memWallet;
	}
	public Integer getMemstatus() {
		return memstatus;
	}
	public void setMemstatus(Integer memstatus) {
		this.memstatus = memstatus;
	}
	public byte[] getMemImg() {
		return memImg;
	}
	public void setMemImg(byte[] memImg) {
		this.memImg = memImg;
	}
	public String getMemuuid() {
		return memuuid;
	}
	public void setMemuuid(String memuuid) {
		this.memuuid = memuuid;
	}
	
	
	
	
	
	
	
	
	
	
	
}
