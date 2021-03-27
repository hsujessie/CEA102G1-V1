package com.theater.model;

import java.util.*;
public interface TheDAO_interface {
	
	public Integer insert(TheVO theaterVO);
	public void update(TheVO theaterVO);
	public void delete(Integer the_no);
	public TheVO findByPrimaryKey(Integer the_no);
	public List<TheVO> getAll();
}
