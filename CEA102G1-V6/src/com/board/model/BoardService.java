package com.board.model;

import java.sql.Date;
import java.util.List;

public class BoardService {

	private BoardDAO_interface dao;

	public BoardService() {
		dao = new BoardJDBCDAO();
	}

	public BoardVO addBoard(Integer boatypNo, String boaContent) {

		BoardVO boardVO = new BoardVO();

		
		boardVO.setBoatypNo(boatypNo);
		boardVO.setBoaContent(boaContent);
		
		
		dao.insert(boardVO);

		return boardVO;
	}

	public BoardVO updateBoard(Integer boaNo, Integer boatypNo, String boaContent) {

		BoardVO boardVO = new BoardVO();
		
		boardVO.setBoaNo(boaNo);
		boardVO.setBoatypNo(boatypNo);
		boardVO.setBoaContent(boaContent);

		
		dao.update(boardVO);

		return boardVO;
		
	}

	public void deleteBoard(Integer boaNo) {
		dao.delete(boaNo);
	}

	public BoardVO getOneBoard(Integer boaNo) {
		return dao.findByPrimaryKey(boaNo);
	}

	public List<BoardVO> getAll() {
		return dao.getAll();
	}
	

}
