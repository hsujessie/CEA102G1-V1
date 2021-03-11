package com.board_type.model;

import java.sql.Date;
import java.util.List;

public class BoardTypeService {

	private BoardTypeJDBCDAO dao;

	public BoardTypeService() {
		dao = new BoardTypeJDBCDAO();
	}

	public BoardTypeVO addEmp(Integer boatypNo, String boatypName) {

		BoardTypeVO boardTypeVO = new BoardTypeVO();

		
		boardTypeVO.setBoatypNo(boatypNo);
		boardTypeVO.setBoatypName(boatypName);
		
		
		dao.insert(boardTypeVO);

		return boardTypeVO;
	}

	public BoardTypeVO updateBoard(Integer boatypNo, String boatypName) {

		BoardTypeVO boardTypeVO = new BoardTypeVO();
		
		boardTypeVO.setBoatypNo(boatypNo);
		boardTypeVO.setBoatypName(boatypName);
		
		
		dao.update(boardTypeVO);

		return boardTypeVO;
	}

	public void deleteBoard(Integer boatypNo) {
		dao.delete(boatypNo);
	}

	public BoardTypeVO getOneBoatype(Integer boatypNo) {
		return dao.findByPrimaryKey(boatypNo);
	}

	public List<BoardTypeVO> getAll() {
		return dao.getAll();
	}
}
