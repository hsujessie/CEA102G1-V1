package com.admin.model;

import java.util.List;


public interface AdmDAO_interface {
	public void insert(AdmVO admVO);
	public void update(AdmVO admVO);
	public void updateNoImg(AdmVO admVO);
	
	public List<AdmVO> getAll();
	
	public AdmVO findByprimaryKey(Integer admNo);
}
