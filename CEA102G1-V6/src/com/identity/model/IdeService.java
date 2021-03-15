package com.identity.model;

import java.util.*;

import com.ticket_type.model.*;

public class IdeService {
	
	private IdeDAO_interface dao;
	
	public IdeService() {
		dao = new IdeDAO();
	}
	
	public List<IdeVO> getAll() {
		return dao.getAll();
	}

	public IdeVO getOneDept(Integer ide_no) {
		return dao.findByPrimaryKey(ide_no);
	}

	public Set<TicTypVO> getTicket_typesByIde_no(Integer ide_no) {
		return dao.getTicket_typesByIde_no(ide_no);
	}

	public void deleteIdentity(Integer ide_no) {
		dao.delete(ide_no);
	}	
}
