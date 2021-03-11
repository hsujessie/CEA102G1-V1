package com.food_list.model;

import java.sql.Connection;

public class FooLisService {
	private FooLisDAO_interface dao;
	
	public FooLisService() {
		dao = new FooLisDAO();
	}
	
	public void addFooLis(FooLisVO fooLisVO, Connection con) {
		dao.insert(fooLisVO, con);
	}
}
