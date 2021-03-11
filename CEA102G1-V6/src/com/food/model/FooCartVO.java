package com.food.model;

import java.io.Serializable;

public class FooCartVO implements Serializable {
	private Integer fooNo;
	private String fooName;
	private Integer fooCount;
	private Integer fooPrice;
	
	public FooCartVO() {}

	public Integer getFooNo() {
		return fooNo;
	}

	public void setFooNo(Integer fooNo) {
		this.fooNo = fooNo;
	}

	public Integer getFooCount() {
		return fooCount;
	}

	public void setFooCount(Integer fooCount) {
		this.fooCount = fooCount;
	}

	public Integer getFooPrice() {
		return fooPrice;
	}

	public void setFooPrice(Integer fooPrice) {
		this.fooPrice = fooPrice;
	}

	public String getFooName() {
		return fooName;
	}

	public void setFooName(String fooName) {
		this.fooName = fooName;
	}

	
	
	
}
