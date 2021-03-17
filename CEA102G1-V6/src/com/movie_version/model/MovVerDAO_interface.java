package com.movie_version.model;

import java.util.*;
import com.theater.model.*;
import com.ticket_type.model.TicTypVO;
public interface MovVerDAO_interface {
	public void insert(MovVerVO movie_versionVO);
	public void update(MovVerVO movie_versionVO);
	public void delete(Integer movver_no);
	public MovVerVO FindByPrimaryKey(Integer movver_no);
	public List<MovVerVO> getall(); 
	public Set<TheVO> getTheatersByMovver_no(Integer movver_no);
	public Set<TicTypVO> getTicket_typeByMovver_no(Integer movver_no);
}
