package com.session.model;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

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
	
	
	public SesVO updateSes(Date sesDate, Time sesTime, Integer sesNo) {
		SesVO sesVO = new SesVO();
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

	public List<SesVO> getMoviesByDate(Date date) {
		return dao.findMoviesByDate(date);
	}


	public List<SesVO> sesDateEqualsToday() {     
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");		
		java.util.Date now = new java.util.Date();
		java.sql.Date today = new java.sql.Date(now.getTime());
		
		return getMoviesByDate(today);
	}
	
	public List<Integer> getDistinctMovNo() {     
		List<SesVO> list = sesDateEqualsToday();
		List<Integer> movNoList = new ArrayList<Integer>();
		
		for (SesVO sesLists : list){
			movNoList.add(sesLists.getMovNo());
	    }
		
		return movNoList.stream().distinct().collect(Collectors.toList());
	}
	
	public List<Date> getDistinctSesDate() {     
		List<SesVO> list = sesDateEqualsToday();
		List<Date> sesDateList = new ArrayList<Date>();
		
		for (SesVO sesLists : list){
			sesDateList.add(sesLists.getSesDate());
	    }
		return sesDateList.stream().distinct().collect(Collectors.toList());
	}

	
	public List<Integer> getDistinctMovNoBySearchDate(Date sesDate) {     
		List<SesVO> list = dao.findMoviesByDate(sesDate);
		List<Integer> movNoList = new ArrayList<Integer>();
		
		for (SesVO sesLists : list){
			movNoList.add(sesLists.getMovNo());
	    }
		
		return movNoList.stream().distinct().collect(Collectors.toList());
	}
	
	public List<Date> getDistinctSesDateBySearchDate(Date sesDate) {     
		List<SesVO> list = dao.findMoviesByDate(sesDate);
		List<Date> sesDateList = new ArrayList<Date>();
		
		for (SesVO sesLists : list){
			sesDateList.add(sesLists.getSesDate());
	    }
		return sesDateList.stream().distinct().collect(Collectors.toList());
	}
	
	public List<SesVO> getSesTimes(Integer movNo,Date sesDate) { 
		return dao.findSesTimeByMovNoAndDate(movNo,sesDate);
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
