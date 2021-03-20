package com.session.model;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
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
		List<SesVO> list = dao.findDistinctSesDate();
		List<SesVO> seslist = new ArrayList<SesVO>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");		
		java.util.Date now = new java.util.Date();
		java.sql.Date today = new java.sql.Date(now.getTime());		
		String strToday = dateFormat.format(today);      
		
		for (SesVO sesDate: list){
    		String strSesDate = dateFormat.format(sesDate.getSesDate());     
    		if(strSesDate.equals(strToday)) {
    			seslist.add(sesDate);
    		}
        }
		return seslist;
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
	
	public boolean isAlreadyChoose(String chooseSeatNo, Integer sesNo) {
		SesVO sesVO = dao.findByPrimaryKey(sesNo);
		String orgSeatStatus = sesVO.getSesSeatStatus();
		String orgSeatNo = sesVO.getSesSeatNo();
		
		for (int i = 0; i < chooseSeatNo.length() ; i +=3) {
			String oneSeatNo = chooseSeatNo.substring(i, i + 3);
			int index = orgSeatNo.indexOf(oneSeatNo) / 3;
			
			if (orgSeatStatus.charAt(index) == '1') {
				return true;
			}
		}
		return false;
	}
}
