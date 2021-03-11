package com.identity.model;

import java.util.*;

import com.ticket_type.model.*;

public class IdentityService {
	
	private IdentityDAO_interface dao;
	
	public IdentityService() {
		dao = new IdentityDAO();
	}
	
	public List<IdentityVO> getAll() {
		return dao.getAll();
	}

	public IdentityVO getOneDept(Integer ide_no) {
		return dao.findByPrimaryKey(ide_no);
	}

	public Set<Ticket_typeVO> getTicket_typesByIde_no(Integer ide_no) {
		return dao.getTicket_typesByIde_no(ide_no);
	}

	public void deleteIdentity(Integer ide_no) {
		dao.delete(ide_no);
	}	
}
