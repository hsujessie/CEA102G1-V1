package com.theater.model;

import java.util.*;
public class TheService {
	
	private TheDAO_interface dao;
	
	public TheService() {
		dao = new TheDAO();
	}

	public TheVO addTheater(Integer movver_no,String the_seat,String the_seatno) {
		
		TheVO theaterVO = new TheVO();
		
		theaterVO.setMovver_no(movver_no);
		theaterVO.setThe_seat(the_seat);
		theaterVO.setThe_seatno(the_seatno);
		dao.insert(theaterVO);
		
		return theaterVO;
	}

	public TheVO updateTheater( Integer the_no,Integer movver_no,String the_seat,String the_seatno) {
		
		TheVO theaterVO = new TheVO();
		
		theaterVO.setThe_no(the_no);
		theaterVO.setMovver_no(movver_no);
		theaterVO.setThe_seat(the_seat);
		theaterVO.setThe_seatno(the_seatno);
		dao.update(theaterVO);
		
		return dao.findByPrimaryKey(the_no);
	}

	public void deleteTheater(Integer the_no) {
		dao.delete(the_no);
	}

	public TheVO getOneTheater(Integer the_no) {
		return dao.findByPrimaryKey(the_no);
	}

	public List<TheVO> getAll() {
		return dao.getAll();
	}
	
	
}
