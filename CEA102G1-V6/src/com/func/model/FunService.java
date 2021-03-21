package com.func.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

import com.admin_auth.model.AdmAutVO;

public class FunService {
	private FunDAO_interface dao;
	
	public FunService() {
		dao = new FunDAO();
	}
	
	public List<FunVO> getAll() {
		return dao.getAll();
	}
	
	public FunVO getOneFun(Integer funNo) {
		return dao.findByPrimarykey(funNo);
	}
	
	public JSONArray getFunName(List<AdmAutVO> list) {
		JSONArray jsonArray = new JSONArray();
		FunVO funVO = null;
		
		for (AdmAutVO admAutVO : list) {
			funVO = dao.findByPrimarykey(admAutVO.getFunNo());
			String funName = funVO.getFunName();
			
			jsonArray.put(funName);
		}
		
		return jsonArray;
	}
}
