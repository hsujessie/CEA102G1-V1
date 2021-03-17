package com.identity.model;

import java.util.*;
import com.ticket_type.model.*;

public interface IdeDAO_interface {
	public void insert(IdeVO identityVO);
    public void update(IdeVO identityVO);
    public void delete(Integer ide_no);
    public IdeVO findByPrimaryKey(Integer ide_no);
    public List<IdeVO> getAll();
    public Set<TicTypVO> getTicket_typesByIde_no(Integer ide_no);
}
