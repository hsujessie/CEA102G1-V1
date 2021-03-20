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
	
	public OrdMasVO insertWithDetail(Integer memNo, Integer sesNo, Set<FooCartVO> fooCartSet, List<TicTypCartVO> ticTypCartSet) {
		OrdMasVO ordMasVO = new OrdMasVO();
		
		int ordMasPrice = 0;
		for (FooCartVO fooCartVO : fooCartSet) {
			ordMasPrice += (fooCartVO.getFooPrice() * fooCartVO.getFooCount());
		}
		for (TicTypCartVO ticTypCartVO : ticTypCartSet) {
			ordMasPrice += ticTypCartVO.getTicLisPrice();
		}
		ordMasVO.setMemNo(memNo);
		ordMasVO.setSesNo(sesNo);
		ordMasVO.setOrdMasPrice(ordMasPrice);
		
		return dao.insertWithDetail(ordMasVO, ticTypCartSet, fooCartSet);
	}
	
	public List<OrdMasVO> getAll() {
		return dao.getAll();
	}
	
	public List<OrdMasVO> getByMemNo(Integer memNo) {
		return dao.findByMemVO(memNo);
	}
	
	public OrdMasVO getOneOrdMas(Integer ordMasNo) {
		return dao.findByprimarykey(ordMasNo);
	}
	
	public void changeStatus(Integer ordMasNo, Integer ordMasStatus) {
		dao.changeStatus(ordMasNo, ordMasStatus);
	}
}
