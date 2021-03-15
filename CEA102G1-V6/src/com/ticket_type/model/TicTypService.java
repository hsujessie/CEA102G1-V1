package com.ticket_type.model;

import java.util.*;

public class TicTypService {
	
	private TicTypDAO_interface dao;
	
	public TicTypService () {
		dao = new TicTypDAO();
	}
	
	public TicTypVO addTicket_type(Integer movver_no ,Integer ide_no ,Integer tictyp_price) {
		
		TicTypVO ticket_typeVO = new TicTypVO();
		
		ticket_typeVO.setMovver_no(movver_no);
		ticket_typeVO.setIde_no(ide_no);
		ticket_typeVO.setTictyp_price(tictyp_price);
		dao.insert(ticket_typeVO);
		
		return ticket_typeVO;		
	}
	
	public TicTypVO updateTicket_type(Integer tictyp_no,Integer movver_no ,Integer ide_no ,Integer tictyp_price) {

		TicTypVO ticket_typeVO = new TicTypVO();

		ticket_typeVO.setTictyp_no(tictyp_no);
		ticket_typeVO.setMovver_no(movver_no);
		ticket_typeVO.setIde_no(ide_no);
		ticket_typeVO.setTictyp_price(tictyp_price);
		dao.update(ticket_typeVO);

		return ticket_typeVO;
	}

	public void deleteTicket_type(Integer tictyp_no) {
		dao.delete(tictyp_no);
	}

	public TicTypVO getOneTicket_type(Integer tictyp_no) {
		return dao.findByPrimaryKey(tictyp_no);
	}

	public List<TicTypVO> getAll() {
		return dao.getAll();
	}
	
	public Set<TicTypCartVO> getTicTypCart(Map<Integer, Integer> ticTypMap) {
		return dao.getTicTypCart(ticTypMap);
	}
	
	
	public List<TicTypVO> getTicTypsByMovVerNo(Integer movVerNo) {
		return dao.getTicTypsByMovVerNo(movVerNo);
	}
}
