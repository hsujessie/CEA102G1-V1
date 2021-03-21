package com.func.model;

import java.util.List;

public class FunService {
	private FunDAO_interface dao;
	
	public FunService() {
		dao = new FunDAO();
	}
	
	public List<FunVO> getAll() {
		return dao.getAll();
	}
	
	public FunVO getOneFun(Integer funNo) {
		return dao.findByPrimarykey(funNo);
	}
}
