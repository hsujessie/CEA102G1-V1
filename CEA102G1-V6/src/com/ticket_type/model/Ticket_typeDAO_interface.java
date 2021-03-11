package com.ticket_type.model;

import java.util.*;


public interface Ticket_typeDAO_interface {
    public void insert(Ticket_typeVO ticket_typeVO);
    public void update(Ticket_typeVO ticket_typeVO);
    public void delete(Integer tictyp_no);
    public Ticket_typeVO findByPrimaryKey(Integer tictyp_no);
    public List<Ticket_typeVO> getAll();
    
    public List<Ticket_typeVO> getTicTypsByMovVerNo(Integer movVerNo);
    public Set<TicTypCartVO> getTicTypCart(Map<Integer, Integer> ticTypMap);
    
}
