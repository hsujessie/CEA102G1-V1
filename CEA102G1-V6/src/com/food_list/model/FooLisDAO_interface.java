package com.food_list.model;

import java.sql.Connection;
import java.util.List;

public interface FooLisDAO_interface {
	public void insert(FooLisVO fooLisVO, Connection con);
	
	public List<FooLisVO> findByOrdMasNo(Integer ordMasNo);
}
