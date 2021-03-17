package com.order_master.model;

import java.util.List;
import java.util.Set;

import com.food.model.FooCartVO;
import com.ticket_type.model.TicTypCartVO;

public interface OrdMasDAO_interface {
	public void insertWithDetail(OrdMasVO ordMasVO, Set<TicTypCartVO> ticTypCartSet, Set<FooCartVO> fooCartSet);
	
	public List<OrdMasVO> getAll();
	public List<OrdMasVO> findByMemVO(Integer memNo);
	public OrdMasVO findByprimarykey(Integer ordMasNo);
}
