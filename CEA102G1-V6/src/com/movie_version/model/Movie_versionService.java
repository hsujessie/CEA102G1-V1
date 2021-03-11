package com.movie_version.model;

import java.util.*;
import com.theater.model.*;
import com.ticket_type.model.Ticket_typeVO;
public class Movie_versionService {
	
	private Movie_versionDAO_interface dao;
	
	public Movie_versionService() {
		dao = new Movie_versionDAO();
	}
	
	public Movie_versionVO addMovie_version( String movver_name) {
		Movie_versionVO movie_versionVO = new Movie_versionVO();
		
		movie_versionVO.setMovver_name(movver_name);
		dao.insert(movie_versionVO);
		
		return movie_versionVO;
		
	}
	
	public Movie_versionVO updateMovie_version( Integer movver_no , String movver_name) {
		
		Movie_versionVO movie_versionVO = new Movie_versionVO();
		
		movie_versionVO.setMovver_name(movver_name);
		movie_versionVO.setMovver_no(movver_no);
		dao.update(movie_versionVO);
		
		return dao.FindByPrimaryKey(movver_no);
		
		
	}
	
	public Movie_versionVO getOneMovie_version(Integer movver_no) {
		return dao.FindByPrimaryKey(movver_no);
	}
	
	public Set<TheaterVO> getTheatersByMovver_no(Integer movver_no) {
		return dao.getTheatersByMovver_no(movver_no);
	}
	public Set<Ticket_typeVO> getTicket_typeByMovver_no(Integer movver_no) {
		return dao.getTicket_typeByMovver_no(movver_no);
	}
	
	public List<Movie_versionVO> getall(){
		return dao.getall();
	}
}
