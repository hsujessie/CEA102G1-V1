package com.order_master.model;

import java.util.List;
import java.util.Set;

import com.food.model.FooCartVO;
import com.ticket_type.model.TicTypCartVO;

public interface OrdMasDAO_interface {
	public OrdMasVO insertWithDetail(OrdMasVO ordMasVO, List<TicTypCartVO> ticTypCartSet, Set<FooCartVO> fooCartSet);
	
	public List<OrdMasVO> getAll();
	public List<OrdMasVO> findByMemVO(Integer memNo);
	public OrdMasVO findByprimarykey(Integer ordMasNo);
	
	public void changeStatus(Integer ordMasNo, Integer ordMasStatus);
}
