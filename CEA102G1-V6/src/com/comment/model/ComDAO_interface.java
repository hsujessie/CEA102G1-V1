package com.comment.model;

import java.util.List;

public interface ComDAO_interface {
	public void insert(ComVO comVO);
	public void update(ComVO comVO);
    public ComVO findByPrimaryKey(Integer comNo);
    public List<ComVO> getAll();
}
