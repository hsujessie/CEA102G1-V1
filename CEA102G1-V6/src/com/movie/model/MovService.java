package com.movie.model;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class MovService {
	
	private MovDAO_interface dao;
	
	public MovService() {
		dao = new MovDAO();
	}
	
	public MovVO addMov(String movname, String movver,String movtype, String movlan, Date movondate, Date movoffdate, Integer movdurat, String movrating, String movditor, String movcast, String movdes, byte[] movpos, byte[] movtra) {
		MovVO movVO = new MovVO();
		
		movVO.setMovname(movname);
		movVO.setMovver(movver);
		movVO.setMovtype(movtype);
		movVO.setMovlan(movlan);
		movVO.setMovondate(movondate);
		movVO.setMovoffdate(movoffdate);
		movVO.setMovdurat(movdurat);
		movVO.setMovrating(movrating);
		movVO.setMovditor(movditor);
		movVO.setMovcast(movcast);
		movVO.setMovdes(movdes);
		movVO.setMovpos(movpos);
		movVO.setMovtra(movtra);
		dao.insert(movVO);
		
		return movVO;
	}

	public MovVO updateMov(String movname, String movver,String movtype, String movlan, Date movondate, Date movoffdate, Integer movdurat, String movrating, String movditor, String movcast, String movdes, Integer movno) {	
		MovVO movVO = new MovVO();

		movVO.setMovname(movname);
		movVO.setMovver(movver);
		movVO.setMovtype(movtype);
		movVO.setMovlan(movlan);
		movVO.setMovondate(movondate);
		movVO.setMovoffdate(movoffdate);
		movVO.setMovdurat(movdurat);
		movVO.setMovrating(movrating);
		movVO.setMovditor(movditor);
		movVO.setMovcast(movcast);
		movVO.setMovdes(movdes);
		movVO.setMovno(movno);
		dao.update(movVO);
		
		return movVO;
	}
	
	public MovVO getOneMov(Integer movno) {
		return dao.findByPrimaryKey(movno);
	}
	
	public List<MovVO> getAll(){
		return dao.getAll();
	}
	
	public List<MovVO> getAll(Map<String, String[]> map) {
		return dao.getAll(map);
	}
	
	public MovVO updateMovpos(byte[] movpos, Integer movno) {
		MovVO movVO = new MovVO();
		
		movVO.setMovpos(movpos);
		movVO.setMovno(movno);
		dao.updateMovpos(movVO);
		
		return movVO;
	}
	
	public MovVO updateMovtra(byte[] movtra, Integer movno) {
		MovVO movVO = new MovVO();
		
		movVO.setMovtra(movtra);
		movVO.setMovno(movno);
		dao.updateMovtra(movVO);
		
		return movVO;
	}
}
