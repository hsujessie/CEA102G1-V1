package com.admin_auth.model;

public class AdmAutService {
	private AdmAutDAO_interface dao;
	
	public AdmAutService() {
		dao = new AdmAutDAO();
	}
	
	public AdmAutVO addAdmAut(Integer admNo, Integer funNo) {
		AdmAutVO admAutVO = new AdmAutVO();
		
		admAutVO.setAdmNo(admNo);
		admAutVO.setFunNo(funNo);
		
		return admAutVO;
	}
	
	public void deleteAdmAut(Integer admNo) {
		dao.delete(admNo);
	}
}
