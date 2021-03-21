package com.func.model;

import java.util.List;

public interface FunDAO_interface {
	public List<FunVO> getAll();
	public FunVO findByPrimarykey(Integer funNo);
}
