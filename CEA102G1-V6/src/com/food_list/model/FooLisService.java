package com.food_list.model;

import java.sql.Connection;

public class FooLisService {
	private FooLisDAO_interface dao;
	
	public FooLisService() {
		dao = new FooLisDAO();
	}
	
	public void addFooLis(Integer ordMasNo, Integer fooNo, Integer fooLisCount, Integer fooLisPrice, Connection con) {
		FooLisVO fooLisVO = new FooLisVO();
		
		fooLisVO.setOrdMasNo(ordMasNo);
		fooLisVO.setFooNo(fooNo);
		fooLisVO.setFooLisCount(fooLisCount);
		fooLisVO.setFooLisPrice(fooLisPrice);
		dao.insert(fooLisVO, con);
	}
}
