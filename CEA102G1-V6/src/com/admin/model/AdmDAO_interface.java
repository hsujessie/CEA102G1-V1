package com.admin.model;

import java.util.List;
import java.util.Map;

import com.admin_auth.model.AdmAutVO;


public interface AdmDAO_interface {
	public void insertWithAuth(AdmVO admVO, String[] funNoArray);
	public void update(AdmVO admVO, String[] funNoArray);
	public void updateNoImg(AdmVO admVO, String[] funNoArray);
	
	public List<AdmVO> getAll();
	public List<AdmVO> getAll(Map<String, String[]> map);
	public List<AdmAutVO> getAuthsByAdmNo(Integer admNo);
	public AdmVO findByprimaryKey(Integer admNo);
	public AdmVO allowAdmin(String admAccount, String admPassword);
	
	public boolean checkRepeat(String admAccount);
}
