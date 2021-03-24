package com.food.model;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface FooDAO_interface {
	public void insert(FooVO fooVO);
	public void update(FooVO fooVO);
	public void updateNoImg(FooVO fooVO);
	public void changeStatus(Integer fooNo, Integer fooStatus);
	
	public List<FooVO> getAll();
	public List<FooVO> getAll(Map<String, String[]> map);
	public FooVO findByprimaryKey(Integer fooNo);
	public Set<FooVO> getFoosByFooStatus(Integer fooStatus);
	
	public Set<FooCartVO> getFooCart(Map<Integer, Integer> foodMap);
}
