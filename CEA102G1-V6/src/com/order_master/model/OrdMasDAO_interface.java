package com.order_master.model;

import java.util.Set;

import com.food.model.FooCartVO;
import com.ticket_type.model.TicTypCartVO;

public interface OrdMasDAO_interface {
	public void insertWithDetail(OrdMasVO ordMasVO, Set<TicTypCartVO> ticTypCartSet, Set<FooCartVO> fooCartSet);
	
	public Set<OrdMasVO> getAll();
	public OrdMasVO findByprimarykey(Integer ordMasNo);
}
