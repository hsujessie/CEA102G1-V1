package com.admin_auth.model;

import java.sql.Connection;

public interface AdmAutDAO_interface {
	public void insert(AdmAutVO admAutVO, Connection con);
	public void deleteByAdmNo(Integer admNo, Connection con);
	
	public boolean checkAdmAut(AdmAutVO admAutVO);
}
