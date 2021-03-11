package com.food_list.model;

import java.io.Serializable;

public class FooLisVO implements Serializable{
	private Integer ordMasNo;
	private Integer fooNo;
	private Integer fooLisCount;
	private Integer fooLisPrice;
	
	public FooLisVO() {}

	public Integer getOrdMasNo() {
		return ordMasNo;
	}

	public void setOrdMasNo(Integer ordMasNo) {
		this.ordMasNo = ordMasNo;
	}

	public Integer getFooNo() {
		return fooNo;
	}

	public void setFooNo(Integer fooNo) {
		this.fooNo = fooNo;
	}

	public Integer getFooLisCount() {
		return fooLisCount;
	}

	public void setFooLisCount(Integer fooLisCount) {
		this.fooLisCount = fooLisCount;
	}

	public Integer getFooLisPrice() {
		return fooLisPrice;
	}

	public void setFooLisPrice(Integer fooLisPrice) {
		this.fooLisPrice = fooLisPrice;
	}
	
}
