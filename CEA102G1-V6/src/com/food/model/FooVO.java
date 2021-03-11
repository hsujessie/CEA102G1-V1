package com.food.model;

import java.io.Serializable;
import java.util.Arrays;

public class FooVO implements Serializable {
	private Integer fooNo; 
	private String fooName;
	private Integer fooCatNo;
	private String fooIntro;
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
	public String getFooIntro() {
		return fooIntro;
	}
	public void setFooIntro(String fooIntro) {
		this.fooIntro = fooIntro;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fooCatNo == null) ? 0 : fooCatNo.hashCode());
		result = prime * result + Arrays.hashCode(fooImg);
		result = prime * result + ((fooIntro == null) ? 0 : fooIntro.hashCode());
		result = prime * result + ((fooName == null) ? 0 : fooName.hashCode());
		result = prime * result + ((fooNo == null) ? 0 : fooNo.hashCode());
		result = prime * result + ((fooPrice == null) ? 0 : fooPrice.hashCode());
		result = prime * result + ((fooStatus == null) ? 0 : fooStatus.hashCode());
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
		FooVO other = (FooVO) obj;
		if (fooCatNo == null) {
			if (other.fooCatNo != null)
				return false;
		} else if (!fooCatNo.equals(other.fooCatNo))
			return false;
		if (!Arrays.equals(fooImg, other.fooImg))
			return false;
		if (fooIntro == null) {
			if (other.fooIntro != null)
				return false;
		} else if (!fooIntro.equals(other.fooIntro))
			return false;
		if (fooName == null) {
			if (other.fooName != null)
				return false;
		} else if (!fooName.equals(other.fooName))
			return false;
		if (fooNo == null) {
			if (other.fooNo != null)
				return false;
		} else if (!fooNo.equals(other.fooNo))
			return false;
		if (fooPrice == null) {
			if (other.fooPrice != null)
				return false;
		} else if (!fooPrice.equals(other.fooPrice))
			return false;
		if (fooStatus == null) {
			if (other.fooStatus != null)
				return false;
		} else if (!fooStatus.equals(other.fooStatus))
			return false;
		return true;
	}
	
	
}
