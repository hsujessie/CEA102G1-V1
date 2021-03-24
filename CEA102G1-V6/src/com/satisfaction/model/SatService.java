package com.satisfaction.model;

import java.util.List;

public class SatService {
	private SatDAO_interface dao;
	
	public SatService() {
		dao = new SatDAO();
	}
	
	public SatVO addSat(Integer movNo, Integer memNo, Integer satRating) {
		SatVO satVO = new SatVO();
		
		satVO.setMovNo(movNo);
		satVO.setMemNo(memNo);
		satVO.setSatRating(satRating);
		dao.insert(satVO);
		
		return satVO;
	}
	
	public SatVO updateSat(Integer movNo, Integer memNo, Integer satRating) {
		SatVO satVO = new SatVO();
		
		satVO.setMovNo(movNo);
		satVO.setMemNo(memNo);
		satVO.setSatRating(satRating);
		dao.update(satVO);
		
		return satVO;
	}
	
	public SatVO getOneSat(Integer movNo, Integer memNo) {
		SatVO satObj =  dao.findByPrimaryKey(movNo,memNo);
		System.out.println("satObj= " +  satObj);
		return satObj;
	}
	
	public List<SatVO> getAll(){
		return dao.getAll();
	}

    public Double getSatRatingAvg(Integer movNo){
		 return dao.getSatRatingAvg(movNo);
    }
}
