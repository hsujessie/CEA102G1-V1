package com.order_master.model;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.food.model.FooCartVO;
import com.food_list.model.FooLisVO;
import com.ticket_list.model.TicLisVO;
import com.ticket_type.model.TicTypCartVO;

public class OrdMasService {
	private OrdMasDAO_interface dao;
	
	public OrdMasService() {
		dao = new OrdMasDAO();
	}
	
	public void insertWithDetail(Integer memNo, Integer sesNo, Set<FooCartVO> fooCartSet, Set<TicTypCartVO> ticTypCartSet) {
		OrdMasVO ordMasVO = new OrdMasVO();
		
		int ordMasPrice = 0;
		for (FooCartVO fooCartVO : fooCartSet) {
			ordMasPrice += (fooCartVO.getFooPrice() * fooCartVO.getFooCount());
		}
		for (TicTypCartVO ticTypCartVO : ticTypCartSet) {
			ordMasPrice += (ticTypCartVO.getTicLisPrice() * ticTypCartVO.getTicTypCount());
		}
		ordMasVO.setMemNo(memNo);
		ordMasVO.setSesNo(sesNo);
		ordMasVO.setOrdMasPrice(ordMasPrice);
		
		dao.insertWithDetail(ordMasVO, ticTypCartSet, fooCartSet);
	}
	
	public List<OrdMasVO> getAll() {
		return dao.getAll();
	}
}
