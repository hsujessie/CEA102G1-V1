package com.ticket_list.model;

import java.sql.Connection;
import java.util.Map;
import java.util.Set;

import com.ticket_type.model.Ticket_typeVO;

public interface TicLisDAO_interface {
	public void insert(TicLisVO ticLisVO, Connection con);
}
