package com.member.model;

import java.util.List;
import java.util.Map;

import com.member.model.*;

public class MemberService {

	private MemberDAO_infterface dao;

	public MemberService() {
		dao = new MemberJNDIDAO();
	}
	
	public MemberVO addMember(String memName, String memAccount,String memPassword,String memMail,byte[] memImg) {

		MemberVO memberVO = new MemberVO();
		memberVO.setMemName(memName);
		memberVO.setMemAccount(memAccount);
		memberVO.setMemPassword(memPassword);
		memberVO.setMemMail(memMail);
//		memberVO.setMemWallet(memWallet);
//		memberVO.setMemstatus(memstatus);
		memberVO.setMemImg(memImg);
	
		dao.insert(memberVO);

		return memberVO;
	}

	public MemberVO updateMember(Integer memNo,String memName, String memAccount,String memPassword,String memMail,Integer memWallet,Integer memstatus,byte[] memImg ) {

		MemberVO memberVO = new MemberVO();
		memberVO.setMemNo(memNo);
		memberVO.setMemName(memName);
		memberVO.setMemAccount(memAccount);
		memberVO.setMemPassword(memPassword);
		memberVO.setMemMail(memMail);
		memberVO.setMemWallet(memWallet);
		memberVO.setMemstatus(memstatus);
		memberVO.setMemImg(memImg);
		
		 dao.update(memberVO);
		
		return memberVO;
		
	}
	public MemberVO updateMember2(Integer memNo,String memName, String memAccount,String memPassword,String memMail,Integer memWallet,Integer memstatus) {
		MemberVO memberVO = new MemberVO();
		memberVO.setMemNo(memNo);
		memberVO.setMemName(memName);
		memberVO.setMemAccount(memAccount);
		memberVO.setMemPassword(memPassword);
		memberVO.setMemMail(memMail);
		memberVO.setMemWallet(memWallet);
		memberVO.setMemstatus(memstatus);
		
		
		 dao.update2(memberVO);
		return memberVO;
		
	}

	public void deleteBoard(Integer memNo) {
		dao.delete(memNo);
	}

	public MemberVO getOneMember(Integer memNo) {
		
		return dao.findByPrimaryKey(memNo);
	}
//	public MemberVO getOneMember2(Integer memNo) {
//		
//		return dao.findByPrimaryKey3(memNo);
//	}
	public MemberVO getOneAccount(String memAccount, String memPassword) {
		return dao.findByMemAccount(memAccount,memPassword);
	}
	
	public MemberVO getOneAccountMail(String memAccount, String memMail) {
		return dao.findByMemAccountMail(memAccount,memMail);

	}

	public MemberVO getAllForUuid(String memUuid) {
		return dao.findByMemUuid(memUuid);

	}
	
	
	
	public List<MemberVO> getAll() {
		return dao.getAll();
	}
	
	public MemberVO addSignUp(String memName,String memAccount,String memPassword,String memMail,byte[] memImg,String memuuid) {

		MemberVO memberVO = new MemberVO();	
		
		memberVO.setMemName(memName);
		memberVO.setMemAccount(memAccount);
		memberVO.setMemPassword(memPassword);
		memberVO.setMemMail(memMail);
		memberVO.setMemImg(memImg);
		memberVO.setMemuuid(memuuid);

		memberVO = dao.insertsignup(memberVO);

		return memberVO;
	}
	
public void updateStatus(MemberVO memberVO) {
		
		dao.updateStatus(memberVO);		
	}

public void changeMemStatus(Integer memNo, Integer memstatus) {
	dao.changeStatus(memNo, memstatus);
}
	
public MemberVO updateUuid(MemberVO memberVO) {
	dao.updateUuid(memberVO);
	
	return memberVO;
}
	
public List<MemberVO> getAll(Map<String, String[]> map) {
	return dao.getAll(map);
}
	
	
	
	
	
	
	
	
	
	
	
//=============================================	
}
