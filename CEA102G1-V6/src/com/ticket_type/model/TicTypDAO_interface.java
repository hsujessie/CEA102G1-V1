package com.ticket_type.model;

import java.util.*;


public interface TicTypDAO_interface {
    public void insert(TicTypVO ticket_typeVO);
    public void update(TicTypVO ticket_typeVO);
    public void delete(Integer tictyp_no);
    public TicTypVO findByPrimaryKey(Integer tictyp_no);
    public List<TicTypVO> getAll();
    
    public List<TicTypVO> getTicTypsByMovVerNo(Integer movVerNo);
    public Set<TicTypCartVO> getTicTypCart(Map<Integer, Integer> ticTypMap);
    
}
