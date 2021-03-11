package com.movie_version.model;

import java.util.*;
import com.theater.model.*;
import com.ticket_type.model.Ticket_typeVO;
public interface Movie_versionDAO_interface {
	public void insert(Movie_versionVO movie_versionVO);
	public void update(Movie_versionVO movie_versionVO);
	public void delete(Integer movver_no);
	public Movie_versionVO FindByPrimaryKey(Integer movver_no);
	public List<Movie_versionVO> getall(); 
	public Set<TheaterVO> getTheatersByMovver_no(Integer movver_no);
	public Set<Ticket_typeVO> getTicket_typeByMovver_no(Integer movver_no);
}
