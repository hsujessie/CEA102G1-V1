package com.admin.model;

import java.util.List;

import com.admin_auth.model.AdmAutVO;

public class AdmService {
	public AdmDAO_interface dao;
	
	public AdmService() {
		dao = new AdmDAO();
	}
	
	public AdmVO addAdm(String admName, byte[] admImg, String admAccount, String admPassword, String admMail, String[] funNoArray) {
		AdmVO admVO = new AdmVO();
		
		admVO.setAdmName(admName);
		admVO.setAdmImg(admImg);
		admVO.setAdmAccount(admAccount);
		admVO.setAdmPassword(admPassword);
		admVO.setAdmMail(admMail);
		dao.insertWithAuth(admVO, funNoArray);
		
		return admVO;
	}

	public AdmVO updateAdm(Integer admNo, String admName, byte[] admImg, String admAccount, String admPassword, String admMail, Integer admStatus, String[] funNoArray) {
		AdmVO admVO = new AdmVO();
		
		admVO.setAdmNo(admNo);
		admVO.setAdmName(admName);
		admVO.setAdmImg(admImg);
		admVO.setAdmAccount(admAccount);
		admVO.setAdmPassword(admPassword);
		admVO.setAdmMail(admMail);
		admVO.setAdmStatus(admStatus);
		dao.update(admVO, funNoArray);
		
		return admVO;
	}
	
	public List<AdmVO> getAll() {
		return dao.getAll();
	}
	
	public AdmVO getOneAdm(Integer admNo) {
		return dao.findByprimaryKey(admNo);
	}
	
	public List<AdmAutVO> getAuthsByAdmNo(Integer admNo) {
		return dao.getAuthsByAdmNo(admNo);
	}
	
}
