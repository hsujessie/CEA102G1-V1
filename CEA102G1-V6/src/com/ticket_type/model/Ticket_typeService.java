package com.ticket_type.model;

import java.util.*;

public class Ticket_typeService {
	
	private Ticket_typeDAO_interface dao;
	
	public Ticket_typeService () {
		dao = new Ticket_typeDAO();
	}
	
	public Ticket_typeVO addTicket_type(Integer movver_no ,Integer ide_no ,Integer tictyp_price) {
		
		Ticket_typeVO ticket_typeVO = new Ticket_typeVO();
		
		ticket_typeVO.setMovver_no(movver_no);
		ticket_typeVO.setIde_no(ide_no);
		ticket_typeVO.setTictyp_price(tictyp_price);
		dao.insert(ticket_typeVO);
		
		return ticket_typeVO;		
	}
	
	public Ticket_typeVO updateTicket_type(Integer tictyp_no,Integer movver_no ,Integer ide_no ,Integer tictyp_price) {

		Ticket_typeVO ticket_typeVO = new Ticket_typeVO();

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

	public Ticket_typeVO getOneTicket_type(Integer tictyp_no) {
		return dao.findByPrimaryKey(tictyp_no);
	}

	public List<Ticket_typeVO> getAll() {
		return dao.getAll();
	}
	
	public Set<TicTypCartVO> getTicTypCart(Map<Integer, Integer> ticTypMap) {
		return dao.getTicTypCart(ticTypMap);
	}
	
	
	public List<Ticket_typeVO> getTicTypsByMovVerNo(Integer movVerNo) {
		return dao.getTicTypsByMovVerNo(movVerNo);
	}
}
