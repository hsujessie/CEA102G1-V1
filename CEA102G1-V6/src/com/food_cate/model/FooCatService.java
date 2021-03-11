package com.food_cate.model;

import java.util.List;
import java.util.Set;

import com.food.model.FooVO;

public class FooCatService {
	private FooCatDAO_interface dao;
	
	public FooCatService() {
		dao = new FooCatDAO();
	}
	
	public FooCatVO addFooCat(String fooCatName) {
		
		FooCatVO fooCatVO = new FooCatVO();
		
		fooCatVO.setFooCatName(fooCatName);
		dao.insert(fooCatVO);
		
		return fooCatVO;
	}
	
	public List<FooCatVO> getAll() {
		return dao.getAll();
	}
	
	public FooCatVO getOneFooCat(Integer fooCatNo) {
		return dao.findByprimaryKey(fooCatNo);
	}
	
	public Set<FooVO> getFoosByFooCatNo(Integer fooCatNo) {
		return dao.getFoosByFooCatNo(fooCatNo);
	}
}
