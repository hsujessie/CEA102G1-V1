package com.client_service.model;

import java.util.List;

public class Client_serviceService {
	private Client_serviceDAO_interface dao;
	
	public Client_serviceService() {
		dao = new Client_serviceDAO();
	}
	
	public Client_serviceVO addClient_service(Integer mem_no , String cliser_content, Integer cliser_who) {
		
		Client_serviceVO client_serviceVO= new Client_serviceVO();
		
		client_serviceVO.setMem_no(mem_no);
		client_serviceVO.setCliser_content(cliser_content);
		client_serviceVO.setCliser_who(cliser_who);
		dao.insert(client_serviceVO);
		
		return client_serviceVO;
	}
	
	public Client_serviceVO getOneClient_service(Integer cliser_no) {
		return dao.findByPrimaryKey(cliser_no);
	}
	
	public List<Client_serviceVO> getClient_serviceByMem_no(Integer mem_no){
		return dao.getClient_serviceByMem_no(mem_no);
	}
}
