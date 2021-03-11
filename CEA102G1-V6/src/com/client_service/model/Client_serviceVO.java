package com.client_service.model;

import java.sql.Timestamp;

public class Client_serviceVO implements java.io.Serializable{
	private Integer cliser_no;
	private Integer mem_no;
	private Integer cliser_who;
	private Timestamp cliser_time;
	private String cliser_content;
	public Integer getCliser_no() {
		return cliser_no;
	}
	public void setCliser_no(Integer cliser_no) {
		this.cliser_no = cliser_no;
	}
	public Integer getMem_no() {
		return mem_no;
	}
	public void setMem_no(Integer mem_no) {
		this.mem_no = mem_no;
	}
	public Integer getCliser_who() {
		return cliser_who;
	}
	public void setCliser_who(Integer cliser_who) {
		this.cliser_who = cliser_who;
	}
	public Timestamp getCliser_time() {
		return cliser_time;
	}
	public void setCliser_time(Timestamp cliser_time) {
		this.cliser_time = cliser_time;
	}
	public String getCliser_content() {
		return cliser_content;
	}
	public void setCliser_content(String cliser_content) {
		this.cliser_content = cliser_content;
	}
}
