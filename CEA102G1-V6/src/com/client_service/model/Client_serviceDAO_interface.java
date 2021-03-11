package com.client_service.model;

import java.util.List;


public interface Client_serviceDAO_interface {
	public void insert(Client_serviceVO client_serviceVO);
	public Client_serviceVO findByPrimaryKey(Integer cliser_no);
	public List<Client_serviceVO> getClient_serviceByMem_no(Integer mem_no);
}
