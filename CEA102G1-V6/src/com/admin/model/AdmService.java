package com.admin.model;

import java.util.List;

import com.admin_auth.model.AdmAutVO;

import jdbc.util.admin_password.Password;
import jdbc.util.sendEmail.MailService;

public class AdmService {
	public AdmDAO_interface dao;
	
	public AdmService() {
		dao = new AdmDAO();
	}
	
	public AdmVO addAdm(String admName, byte[] admImg, String admAccount, String admMail, String[] funNoArray) {
		AdmVO admVO = new AdmVO();
		
		String realAdmPassword = Password.generateRandomPassword();
		String fakeAdmPassword = Password.passwordEncoder(realAdmPassword);
		
		admVO.setAdmName(admName);
		admVO.setAdmImg(admImg);
		admVO.setAdmAccount(admAccount);
		admVO.setAdmPassword(fakeAdmPassword);
		admVO.setAdmMail(admMail);
		dao.insertWithAuth(admVO, funNoArray);
		
		String to = "jacky55444@gmail.com";
		String subject = "密碼通知";
		String messageText =admName + "您好, " + "請謹記此密碼: " + realAdmPassword;
		MailService.sendMail(to, subject, messageText);
		
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
	
	public AdmVO updateAdmNoImg(Integer admNo, String admName, String admAccount, String admPassword, String admMail, Integer admStatus, String[] funNoArray) {
		AdmVO admVO = new AdmVO();
		
		admVO.setAdmNo(admNo);
		admVO.setAdmName(admName);
		admVO.setAdmAccount(admAccount);
		admVO.setAdmPassword(admPassword);
		admVO.setAdmMail(admMail);
		admVO.setAdmStatus(admStatus);
		dao.updateNoImg(admVO, funNoArray);
		
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
	
	public AdmVO allowAdmin(String admAccount, String admPassword) {
		
		return dao.allowAdmin(admAccount, Password.passwordEncoder(admPassword));
	}
}
