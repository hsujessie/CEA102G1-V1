package com.satisfaction.model;

import java.util.List;

public interface SatDAO_interface {
	public void insert(SatVO satVO);
	public void update(SatVO satVO);
    public SatVO findByPrimaryKey(Integer movNo, Integer memNo);
    public List<SatVO> getAll();
    public Double getSatRatingAvg(Integer movNo);
}
