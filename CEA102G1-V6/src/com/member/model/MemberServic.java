package com.member.model;

import java.util.List;

import com.member.model.*;

public class MemberServic {

	private MemberDAO_infterface dao;

	public MemberServic() {
		dao = new MemberJDBCDAO();
	}
	
	public MemberVO addMember(String memName, String memAccount,String memPassword,String memMail) {
//		,Integer memWallet,Integer memststus,byte[] memImg
		MemberVO memberVO = new MemberVO();

		
		memberVO.setMemName(memName);
		memberVO.setMemAccount(memAccount);
		memberVO.setMemPassword(memPassword);
		memberVO.setMemMail(memMail);
//		memberVO.setMemWallet(memWallet);
//		memberVO.setMemststus(memststus);
//		memberVO.setMemImg(memImg);
	
		dao.insert(memberVO);

		return memberVO;
	}

	public MemberVO updateMember(Integer memNo,String memName, String memAccount,String memPassword,String memMail) {
//		,Integer memWallet,Integer memststus,byte[] memImg
		MemberVO memberVO = new MemberVO();
		
		memberVO.setMemNo(memNo);
		memberVO.setMemName(memName);
		memberVO.setMemAccount(memAccount);
		memberVO.setMemPassword(memPassword);
		memberVO.setMemMail(memMail);
//		memberVO.setMemWallet(memWallet);
//		memberVO.setMemststus(memststus);
//		memberVO.setMemImg(memImg);
//		depositVO.setDepTime(depTime);
		
		dao.update(memberVO);

		return memberVO;
		
	}

	public void deleteBoard(Integer memNo) {
		dao.delete(memNo);
	}

	public MemberVO getOneMember(Integer memNo) {
		return dao.findByPrimaryKey(memNo);
	}
	public MemberVO getOneAccount(String memAccount, String memPassword) {
		return dao.findByMemAccount(memAccount,memPassword);
	}

	public List<MemberVO> getAll() {
		return dao.getAll();
	}
}
