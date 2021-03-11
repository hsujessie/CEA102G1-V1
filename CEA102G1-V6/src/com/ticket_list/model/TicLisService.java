package com.ticket_list.model;

import java.sql.Connection;

public class TicLisService {
	private TicLisDAO_interface dao;
	
	public TicLisService() {
		dao = new TicLisDAO();
	}
	
	public void addFooLis(TicLisVO ticLisVO, Connection con) {
		dao.insert(ticLisVO, con);
	}
}
