package com.member.model;

import java.util.List;


import com.member.model.MemberVO;

public interface MemberDAO_infterface {
	public void insert(MemberVO memberVO);
    public void update(MemberVO memberVO);
    public Boolean delete(Integer memNo);
    public MemberVO findByPrimaryKey(Integer memNo);
    public MemberVO findByMemAccount(String memAccount,String memPassword);
    public List<MemberVO> getAll();
}
