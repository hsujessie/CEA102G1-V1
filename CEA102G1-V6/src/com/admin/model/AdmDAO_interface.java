package com.admin.model;

import java.util.List;

import com.admin_auth.model.AdmAutVO;


public interface AdmDAO_interface {
	public void insertWithAuth(AdmVO admVO, String[] funNoArray);
	public void update(AdmVO admVO, String[] funNoArray);
	public void updateNoImg(AdmVO admVO);
	
	public List<AdmVO> getAll();
	public List<AdmAutVO> getAuthsByAdmNo(Integer admNo);
	public AdmVO findByprimaryKey(Integer admNo);
}
