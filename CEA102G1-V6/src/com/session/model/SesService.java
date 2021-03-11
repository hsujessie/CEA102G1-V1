package com.session.model;

import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SesService {
	private SesDAO_interface dao;
	
	public SesService() {
		dao = new SesDAO();
	}
	
	public SesVO addSes(Integer movNo, Integer theNo, Date sesDate, Time sesTime, String sesSeatStatus, String sesSeatNo) {
		SesVO sesVO = new SesVO();
		sesVO.setMovNo(movNo);
		sesVO.setTheNo(theNo);
		sesVO.setSesDate(sesDate);
		sesVO.setSesTime(sesTime);
		sesVO.setSesSeatStatus(sesSeatStatus);
		sesVO.setSesSeatNo(sesSeatNo);
		dao.insert(sesVO);
		
		return sesVO;
	}
	
	
	public SesVO updateSes(Integer theNo, Date sesDate, Time sesTime, Integer sesNo) {
		SesVO sesVO = new SesVO();
		sesVO.setTheNo(theNo);
		sesVO.setSesDate(sesDate);
		sesVO.setSesTime(sesTime);
		sesVO.setSesNo(sesNo);
		dao.update(sesVO);
		
		return sesVO;
	}
	
	public SesVO getOneSes(Integer sesNo) {
		return dao.findByPrimaryKey(sesNo);
	}
	
	public List<SesVO> getAll(){
		return dao.getAll();
	}

	public List<SesVO> getAll(Map<String, String[]> map) {
		return dao.getAll(map);
	}

	public List<SesVO> getMoviesBySesDate(Date sesDate) {
		return dao.findMoviesBySesDate(sesDate);
	}

	public List<SesVO> getDistinctSesDate() {
		return dao.findDistinctSesDate();
	}
	
	public List<String> updateSeatStatus(String chooseSeatNo, Integer sesNo) {
		SesVO sesVO = dao.findByPrimaryKey(sesNo);
		
		String orgSeatStatus = sesVO.getSesSeatStatus();
		StringBuilder sb = new StringBuilder(orgSeatStatus);
		String orgSeatNo = sesVO.getSesSeatNo();
		
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < chooseSeatNo.length() ; i +=3) {
			String oneSeatNo = chooseSeatNo.substring(i, i + 3);
			list.add(oneSeatNo);
			
			int index = orgSeatNo.indexOf(oneSeatNo) / 3;
			sb.setCharAt(index, '1');
		}
		dao.updateSeatStatus(sesNo, sb.toString());
		
		return list;
	}
}
