package com.admin_auth.model;

import java.sql.Connection;

public class AdmAutService {
	private AdmAutDAO_interface dao;
	
	public AdmAutService() {
		dao = new AdmAutDAO();
	}
	
	public void addAdmAut(Integer admNo, Integer funNo, Connection con) {
		AdmAutVO admAutVO = new AdmAutVO();
		
		admAutVO.setAdmNo(admNo);
		admAutVO.setFunNo(funNo);
		dao.insert(admAutVO, con);
	}
	
	public void deleteAdmAut(Integer admNo, Connection con) {
		dao.deleteByAdmNo(admNo, con);
	}
	
	public boolean checkAdmAut(Integer admNo, Integer funNo) {
		AdmAutVO admAutVO = new AdmAutVO();
		
		admAutVO.setAdmNo(admNo);
		admAutVO.setFunNo(funNo);
		
		return dao.checkAdmAut(admAutVO);
	}
}
