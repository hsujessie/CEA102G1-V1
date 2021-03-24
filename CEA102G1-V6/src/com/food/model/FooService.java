package com.food.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class FooService {
	public FooDAO_interface dao;
	
	public FooService() {
		dao = new FooDAO();
	}
	
	public FooVO addFoo(String fooName, Integer fooCatNo, byte[] fooImg, Integer fooPrice) {
		FooVO fooVO = new FooVO();
		
		fooVO.setFooName(fooName);
		fooVO.setFooCatNo(fooCatNo);
		fooVO.setFooImg(fooImg);
		fooVO.setFooPrice(fooPrice);
		dao.insert(fooVO);
		
		return fooVO;
	}
	
	public List<FooVO> getAll() {
		return dao.getAll();
	}
	
	public FooVO updateFoo(Integer fooNo, String fooName, Integer fooCatNo, byte[] fooImg, Integer fooPrice, Integer fooStatus) {
		FooVO fooVO = new FooVO();
		
		fooVO.setFooNo(fooNo);
		fooVO.setFooName(fooName);
		fooVO.setFooCatNo(fooCatNo);
		fooVO.setFooImg(fooImg);
		fooVO.setFooPrice(fooPrice);
		fooVO.setFooStatus(fooStatus);
		dao.update(fooVO);
		
		return fooVO;
	}
	
	public FooVO updateFooNoImg(Integer fooNo, String fooName, Integer fooCatNo, Integer fooPrice, Integer fooStatus) {
		FooVO fooVO = new FooVO();
		
		fooVO.setFooNo(fooNo);
		fooVO.setFooName(fooName);
		fooVO.setFooCatNo(fooCatNo);
		fooVO.setFooPrice(fooPrice);
		fooVO.setFooStatus(fooStatus);
		dao.updateNoImg(fooVO);
		
		return fooVO;
	}
	
	public void changeFooStatus(Integer fooNo, Integer fooStatus) {
		dao.changeStatus(fooNo, fooStatus);
	}
	
	public FooVO getOneFoo(Integer fooNo) {
		return dao.findByprimaryKey(fooNo);
	}
	
	public Set<FooVO> getFoosByFooStatus(Integer fooStatus) {
		return dao.getFoosByFooStatus(fooStatus);
	}
	
	public Set<FooCartVO> getFooCart(Map<Integer, Integer> foodMap) {
		return dao.getFooCart(foodMap);
	}
	
	public List<FooVO> getAll(Map<String, String[]> map) {
		return dao.getAll(map);
	}
}
