package com.movie.model;

import java.util.List;
import java.util.Map;

public interface MovDAO_interface {
	public void insert(MovVO movVO);
	public void update(MovVO movVO);
    public MovVO findByPrimaryKey(Integer movno);
    public List<MovVO> getAll();
    public void updateMovpos(MovVO movVO);
    public void updateMovtra(MovVO movVO);
    public List<MovVO> getAll(Map<String, String[]> map); //複合查詢
}
