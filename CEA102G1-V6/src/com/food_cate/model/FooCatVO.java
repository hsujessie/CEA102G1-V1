package com.food_cate.model;

import java.io.Serializable;

public class FooCatVO implements Serializable {
	private Integer fooCatNo;
	private String fooCatName;
	
	public FooCatVO() {};
	
	public Integer getFooCatNo() {
		return fooCatNo;
	}
	public void setFooCatNo(Integer fooCatNo) {
		this.fooCatNo = fooCatNo;
	}
	public String getFooCatName() {
		return fooCatName;
	}
	public void setFooCatName(String fooCatName) {
		this.fooCatName = fooCatName;
	}
	
	
}
