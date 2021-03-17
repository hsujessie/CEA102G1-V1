package com.ticket_list.model;

import java.sql.Connection;
import java.util.List;


public interface TicLisDAO_interface {
	public void insert(TicLisVO ticLisVO, Connection con);
	
	public List<TicLisVO> findByOrdMasNo(Integer ordMasNo);
}
