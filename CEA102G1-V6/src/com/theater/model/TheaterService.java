package com.theater.model;

import java.util.*;
public class TheaterService {
	
	private TheaterDAO_interface dao;
	
	public TheaterService() {
		dao = new TheaterDAO();
	}

	public TheaterVO addTheater(Integer movver_no,String the_seat,String the_seatno) {
		
		TheaterVO theaterVO = new TheaterVO();
		
		theaterVO.setMovver_no(movver_no);
		theaterVO.setThe_seat(the_seat);
		theaterVO.setThe_seatno(the_seatno);
		dao.insert(theaterVO);
		
		return theaterVO;
	}

	public TheaterVO updateTheater( Integer the_no,Integer movver_no,String the_seat,String the_seatno) {
		
		TheaterVO theaterVO = new TheaterVO();
		
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

	public TheaterVO getOneTheater(Integer the_no) {
		return dao.findByPrimaryKey(the_no);
	}

	public List<TheaterVO> getAll() {
		return dao.getAll();
	}
	
	
}
