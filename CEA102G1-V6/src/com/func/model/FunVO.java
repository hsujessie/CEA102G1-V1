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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funName == null) ? 0 : funName.hashCode());
		result = prime * result + ((funNo == null) ? 0 : funNo.hashCode());
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
		FunVO other = (FunVO) obj;
		if (funName == null) {
			if (other.funName != null)
				return false;
		} else if (!funName.equals(other.funName))
			return false;
		if (funNo == null) {
			if (other.funNo != null)
				return false;
		} else if (!funNo.equals(other.funNo))
			return false;
		return true;
	}
	
}
