package com.expectation.model;

import java.util.List;

public interface ExpDAO_interface {
	public void insert(ExpVO expVO);
	public void update(ExpVO expVO);
    public ExpVO findByPrimaryKey(Integer movNo, Integer memNo);
    public List<ExpVO> getAll();
    public Double getExpRatingAvg(Integer movNo);
}
