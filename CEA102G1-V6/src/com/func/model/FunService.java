package com.func.model;

import java.util.Set;

public class FunService {
	private FunDAO_interface dao;
	
	public FunService() {
		dao = new FunDAO();
	}
	
	public Set<FunVO> getAll() {
		return dao.getAll();
	}
}
