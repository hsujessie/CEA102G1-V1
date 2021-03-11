package com.admin_auth.model;

import java.io.Serializable;

public class AdmAutVO implements Serializable {
	private Integer admNo;
	private Integer funNo;
	
	public AdmAutVO() {}

	public Integer getAdmNo() {
		return admNo;
	}

	public void setAdmNo(Integer admNo) {
		this.admNo = admNo;
	}

	public Integer getFunNo() {
		return funNo;
	}

	public void setFunNo(Integer funNo) {
		this.funNo = funNo;
	}
	
	
}
