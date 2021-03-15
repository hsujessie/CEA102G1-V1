package com.admin.model;

import java.io.Serializable;

public class AdmVO implements Serializable {
	private Integer admNo;
	private String admName;
	private byte[] admImg;
	private String admAccount;
	private String admPassword;
	private String admMail;
	private Integer admStatus;
	
	public AdmVO() {}

	public Integer getAdmNo() {
		return admNo;
	}

	public void setAdmNo(Integer admNo) {
		this.admNo = admNo;
	}

	public String getAdmName() {
		return admName;
	}

	public void setAdmName(String admName) {
		this.admName = admName;
	}

	public byte[] getAdmImg() {
		return admImg;
	}

	public void setAdmImg(byte[] admImg) {
		this.admImg = admImg;
	}

	public String getAdmAccount() {
		return admAccount;
	}

	public void setAdmAccount(String admAccount) {
		this.admAccount = admAccount;
	}

	public String getAdmPassword() {
		return admPassword;
	}

	public void setAdmPassword(String admPassword) {
		this.admPassword = admPassword;
	}

	public Integer getAdmStatus() {
		return admStatus;
	}

	public void setAdmStatus(Integer admStatus) {
		this.admStatus = admStatus;
	}

	public String getAdmMail() {
		return admMail;
	}

	public void setAdmMail(String admMail) {
		this.admMail = admMail;
	}
	
	public String getAdmImgParam() {
		return "?columnName=adm_img&tableName=administrator&fieldName=adm_no&fieldValue=" + admNo;
	}

}
