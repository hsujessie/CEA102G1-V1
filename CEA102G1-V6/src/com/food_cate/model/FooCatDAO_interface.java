package com.food_cate.model;

import java.util.List;
import java.util.Set;

import com.food.model.FooVO;

public interface FooCatDAO_interface {
	public void insert(FooCatVO fooCatVO);
	public void update(FooCatVO fooCatVO);
	public List<FooCatVO> getAll();
	public FooCatVO findByprimaryKey(Integer fooCatNo);
	public Set<FooVO> getFoosByFooCatNo(Integer fooCatNo);
}
