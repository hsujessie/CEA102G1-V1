package com.theater.model;

import java.util.*;
public interface TheaterDAO_interface {
	
	public void insert(TheaterVO theaterVO);
	public void update(TheaterVO theaterVO);
	public void delete(Integer the_no);
	public TheaterVO findByPrimaryKey(Integer the_no);
	public List<TheaterVO> getAll();
}
