package com.ticket_list.model;

import java.sql.Connection;
import java.util.List;

public class TicLisService {
	private TicLisDAO_interface dao;
	
	public TicLisService() {
		dao = new TicLisDAO();
	}
	
	public void addTicLis(Integer ordMasNo, Integer ticTypNo, String sesSeatNo, Integer ticTypPrice, Connection con) {
		TicLisVO ticLisVO = new TicLisVO();
		
		ticLisVO.setOrdMasNo(ordMasNo);
		ticLisVO.setTicTypNo(ticTypNo);
		ticLisVO.setSesSeatNo(sesSeatNo);
		ticLisVO.setTicTypPrice(ticTypPrice);
		dao.insert(ticLisVO, con);
	}
	
	public List<TicLisVO> getByOrdMasNo(Integer ordMasNo) {
		return dao.findByOrdMasNo(ordMasNo);
	}
}
