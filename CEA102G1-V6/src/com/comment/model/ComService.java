package com.comment.model;

import java.sql.Timestamp;
import java.util.List;

public class ComService {
	private ComDAO_interface dao;
	
	public ComService() {
		dao = new ComDAO();
	}
	
	public ComVO addCom(Integer movNo, Integer memNo, Timestamp comTime, String comContent, Integer comStatus) {
		ComVO comVO = new ComVO();
		
		comVO.setMovNo(movNo);
		comVO.setMemNo(memNo);
		comVO.setComTime(comTime);
		comVO.setComContent(comContent);
		comVO.setComStatus(comStatus);		
		dao.insert(comVO);
		
		return comVO;
	}
	
	public ComVO updateCom(Integer comStatus, Integer comNo) {
		ComVO comVO = new ComVO();
		
		comVO.setComStatus(comStatus);	
		comVO.setComNo(comNo);
		dao.update(comVO);
		
		return comVO;	
	}
	
	public ComVO getOneCom(Integer comNo) {
		return dao.findByPrimaryKey(comNo);
	}
	
	public List<ComVO> getAll(){
		return dao.getAll();
	}
}
