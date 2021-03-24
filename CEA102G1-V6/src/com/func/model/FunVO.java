package com.func.model;

import java.io.Serializable;

public class FunVO implements Serializable{
	private Integer funNo;
	private String funName;
	
	public FunVO() {}

	public Integer getFunNo() {
		return funNo;
	}

	public void setFunNo(Integer funNo) {
		this.funNo = funNo;
	}

	public String getFunName() {
		return funName;
	}

	public void setFunName(String funName) {
		this.funName = funName;
	}

}
