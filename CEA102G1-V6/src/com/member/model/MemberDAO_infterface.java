package com.member.model;

import java.util.*;


import com.member.model.MemberVO;

public interface MemberDAO_infterface {
	public void insert(MemberVO memberVO);
    public void update(MemberVO memberVO);
    public void update2(MemberVO memberVO);
//    public void updateFront(MemberVO memberVO);
    public void updateUuid(MemberVO memberVO);
    public Boolean delete(Integer memNo);
    public MemberVO findByPrimaryKey(Integer memNo);
//    public MemberVO findByPrimaryKey2(Integer memNo);
//    public MemberVO findByPrimaryKey3(Integer memNo);
    public MemberVO findByMemAccount(String memAccount,String memPassword);
    public MemberVO findByMemAccountMail(String memAccount,String memMail);
    public MemberVO findByMemUuid(String memUuid);
    public List<MemberVO> getAll();
    public MemberVO insertsignup(MemberVO memberVO);
    public void updateStatus(MemberVO memberVO);
    public void changeStatus(Integer memNo, Integer memStatus);
    public List<MemberVO> getAll(Map<String, String[]> map); 

}
