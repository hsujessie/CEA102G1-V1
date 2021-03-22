package com.ticket_type.model;

import java.io.Serializable;

public class TicTypCartVO implements Serializable{
	private Integer ticTypNo;
	private Integer ideNo;
	private String sesSeatNo;
	private Integer ticTypCount;
	private Integer ticLisPrice;
	
	public TicTypCartVO() {}

	public Integer getTicTypNo() {
		return ticTypNo;
	}

	public void setTicTypNo(Integer ticTypNo) {
		this.ticTypNo = ticTypNo;
	}

	public String getSesSeatNo() {
		return sesSeatNo;
	}

	public void setSesSeatNo(String sesSeatNo) {
		this.sesSeatNo = sesSeatNo;
	}

	public Integer getTicLisPrice() {
		return ticLisPrice;
	}

	public void setTicLisPrice(Integer ticLisPrice) {
		this.ticLisPrice = ticLisPrice;
	}

	public Integer getIdeNo() {
		return ideNo;
	}

	public void setIdeNo(Integer ideNo) {
		this.ideNo = ideNo;
	}

	public Integer getTicTypCount() {
		return ticTypCount;
	}

	public void setTicTypCount(Integer ticTypCount) {
		this.ticTypCount = ticTypCount;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ticLisPrice == null) ? 0 : ticLisPrice.hashCode());
		result = prime * result + ((ticTypNo == null) ? 0 : ticTypNo.hashCode());
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
		TicTypCartVO other = (TicTypCartVO) obj;
		if (ticLisPrice == null) {
			if (other.ticLisPrice != null)
				return false;
		} else if (!ticLisPrice.equals(other.ticLisPrice))
			return false;
		if (ticTypNo == null) {
			if (other.ticTypNo != null)
				return false;
		} else if (!ticTypNo.equals(other.ticTypNo))
			return false;
		return true;
	}
	
	
}
