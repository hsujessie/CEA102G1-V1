package com.food.model;

import java.io.Serializable;
import java.util.Arrays;

public class FooVO implements Serializable {
	private Integer fooNo; 
	private String fooName;
	private Integer fooCatNo;
	private byte[] fooImg;
	private Integer fooPrice;
	private Integer fooStatus;
	
	public FooVO() {};
	
	public Integer getFooNo() {
		return fooNo;
	}
	public void setFooNo(Integer fooNo) {
		this.fooNo = fooNo;
	}
	public String getFooName() {
		return fooName;
	}
	public void setFooName(String fooName) {
		this.fooName = fooName;
	}
	public Integer getFooCatNo() {
		return fooCatNo;
	}
	public void setFooCatNo(Integer fooCatNo) {
		this.fooCatNo = fooCatNo;
	}
	public byte[] getFooImg() {
		return fooImg;
	}
	public void setFooImg(byte[] fooImg) {
		this.fooImg = fooImg;
	}
	public Integer getFooPrice() {
		return fooPrice;
	}
	public void setFooPrice(Integer fooPrice) {
		this.fooPrice = fooPrice;
	}
	public Integer getFooStatus() {
		return fooStatus;
	}
	public void setFooStatus(Integer fooStatus) {
		this.fooStatus = fooStatus;
	}

	public String getFooImgParam() {
		return "?columnName=foo_img&tableName=food&fieldName=foo_no&fieldValue=" + fooNo;
	}
	
	
}
