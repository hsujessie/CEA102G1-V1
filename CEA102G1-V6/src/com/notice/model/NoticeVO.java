package com.notice.model;

import java.sql.Timestamp;

public class NoticeVO implements java.io.Serializable{
	private Integer not_no;
	private Integer mem_no;
	private String not_content;
	private Timestamp not_time;
	private Integer not_status;
	
	public Integer getNot_no() {
		return not_no;
	}
	public void setNot_no(Integer not_no) {
		this.not_no = not_no;
	}
	public Integer getMem_no() {
		return mem_no;
	}
	public void setMem_no(Integer mem_no) {
		this.mem_no = mem_no;
	}
	public String getNot_content() {
		return not_content;
	}
	public void setNot_content(String not_content) {
		this.not_content = not_content;
	}
	public Timestamp getNot_time() {
		return not_time;
	}
	public void setNot_time(Timestamp not_time) {
		this.not_time = not_time;
	}
	public Integer getNot_status() {
		return not_status;
	}
	public void setNot_status(Integer not_status) {
		this.not_status = not_status;
	}
	
	
}
