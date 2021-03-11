package com.identity.model;

import java.util.*;
import com.ticket_type.model.*;

public interface IdentityDAO_interface {
	public void insert(IdentityVO identityVO);
    public void update(IdentityVO identityVO);
    public void delete(Integer ide_no);
    public IdentityVO findByPrimaryKey(Integer ide_no);
    public List<IdentityVO> getAll();
    public Set<Ticket_typeVO> getTicket_typesByIde_no(Integer ide_no);
}
