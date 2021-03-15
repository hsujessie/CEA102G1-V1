package com.admin.model;

import java.util.List;

public class AdmService {
	public AdmDAO_interface dao;
	
	public AdmService() {
		dao = new AdmDAO();
	}
	
	public AdmVO addAdm(String admName, byte[] admImg, String admAccount, String admPassword, String admMail) {
		AdmVO admVO = new AdmVO();
		
		admVO.setAdmName(admName);
		admVO.setAdmImg(admImg);
		admVO.setAdmAccount(admAccount);
		admVO.setAdmPassword(admPassword);
		admVO.setAdmMail(admMail);
		dao.insert(admVO);
		
		return admVO;
	}
	
	public List<AdmVO> getAll() {
		return dao.getAll();
	}
}
