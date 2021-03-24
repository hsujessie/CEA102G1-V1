package com.admin_auth.model;

import java.sql.Connection;
import java.util.List;

import com.func.model.FunVO;

public interface AdmAutDAO_interface {
	public void insert(AdmAutVO admAutVO, Connection con);
	public void deleteByAdmNo(Integer admNo, Connection con);
	
	public List<String> getAdmFun(Integer admNo);
	public boolean checkAdmAut(AdmAutVO admAutVO);
}
