package com.food_list.model;

import java.sql.Connection;
import java.util.LinkedList;
import java.util.List;

import com.food.model.FooCartVO;

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
	
	public List<FooLisVO> getByOrdMasNo(Integer ordMasNo) {
		return dao.findByOrdMasNo(ordMasNo);
	}
	
	public List<FooCartVO> convertToFooCart(List<FooLisVO> list) {
		List<FooCartVO> fooCart = new LinkedList<FooCartVO>();
		FooCartVO fooCartVO = null;
		
		for (FooLisVO fooLisVO : list) {
			fooCartVO = new FooCartVO();
			
			fooCartVO.setFooNo(fooLisVO.getFooNo());
			fooCartVO.setFooCount(fooLisVO.getFooLisCount());
			fooCartVO.setFooPrice(fooLisVO.getFooLisPrice());
			
			fooCart.add(fooCartVO);
		}
		return fooCart;
	}
}
