package com.admin.model;

import java.util.List;

public class AdmService {
	public AdmDAO_interface dao;
	
	public AdmService() {
		dao = new AdmDAO();
	}
	
	public AdmVO addAdm(String admName, byte[] admImg, String admAccount, String admPassword) {
		AdmVO admVO = new AdmVO();
		
		admVO.setAdmName(admName);
		admVO.setAdmImg(admImg);
		admVO.setAdmAccount(admAccount);
		admVO.setAdmPassword(admPassword);
		dao.insert(admVO);
		
		return admVO;
	}
	
	public List<AdmVO> getAll() {
		return dao.getAll();
	}
}
