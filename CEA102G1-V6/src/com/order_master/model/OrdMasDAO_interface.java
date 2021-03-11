package com.order_master.model;

import java.util.Set;

import com.food_list.model.FooLisVO;
import com.ticket_list.model.TicLisVO;

public interface OrdMasDAO_interface {
	public void insertWithDetail(OrdMasVO ordMasVO, Set<TicLisVO> ticketCart, Set<FooLisVO> fooCart);
	
	public Set<OrdMasVO> getAll();
	public OrdMasVO findByprimarykey(Integer ordMasNo);
}
