package com.comment_report.model;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.List;

public class ComRepService {
	private ComRepDAO_interface dao;
	
	public ComRepService() {
		dao = new ComRepDAO();
	}
	
	public ComRepVO addComRep(Integer comNo, Integer memNo, String comRepReason, Timestamp comRepTime, Integer comRepStatus) {
		ComRepVO comRepVO = new ComRepVO();
		
		comRepVO.setComNo(comNo);
		comRepVO.setMemNo(memNo);
		comRepVO.setComRepReason(comRepReason);
		comRepVO.setComRepTime(comRepTime);
		comRepVO.setComRepStatus(comRepStatus);
		dao.insert(comRepVO);
		
		return comRepVO;
	}
	
	public ComRepVO updateComRep(Integer comRepStatus, Integer comRepNo) {
		ComRepVO comRepVO = new ComRepVO();
		
		comRepVO.setComRepStatus(comRepStatus);	
		comRepVO.setComRepNo(comRepNo);
		dao.update(comRepVO);
		
		return comRepVO;	
	}
	
	public ComRepVO getOneComRep(Integer comRepNo) {
		return dao.findByPrimaryKey(comRepNo);
	}
	
	public List<ComRepVO> getAll(){
		return dao.getAll();
	}

	public List<ComRepVO> findComRepByComReStatus(Integer comRepStatus) {
		return dao.findComRepByComReStatus(comRepStatus);
	}
	
	public boolean isRepeatedComRep(Integer comNo, Integer memNo, String comRepReason) {
		Boolean result = true;
		Integer comrepNo = dao.findRepeatedComRep(comNo, memNo, comRepReason);
		
		if(comrepNo == null) {  // 資料庫無資料，代表無重複場次
			result = false;
		}
		
		return result;
	}
	
	
}
