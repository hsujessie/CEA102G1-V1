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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((memAccount == null) ? 0 : memAccount.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberVO other = (MemberVO) obj;
		if (memAccount == null) {
			if (other.memAccount != null)
				return false;
		} else if (!memAccount.equals(other.memAccount))
			return false;
		return true;
	}	
	
	
	
	
	
	
	
	
	
	
}
