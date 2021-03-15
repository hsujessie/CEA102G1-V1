package com.movie_version.model;

import java.util.*;
import com.theater.model.*;
import com.ticket_type.model.TicTypVO;
public class MovVerService {
	
	private MovVerDAO_interface dao;
	
	public MovVerService() {
		dao = new MovVerDAO();
	}
	
	public MovVerVO addMovie_version( String movver_name) {
		MovVerVO movie_versionVO = new MovVerVO();
		
		movie_versionVO.setMovver_name(movver_name);
		dao.insert(movie_versionVO);
		
		return movie_versionVO;
		
	}
	
	public MovVerVO updateMovie_version( Integer movver_no , String movver_name) {
		
		MovVerVO movie_versionVO = new MovVerVO();
		
		movie_versionVO.setMovver_name(movver_name);
		movie_versionVO.setMovver_no(movver_no);
		dao.update(movie_versionVO);
		
		return dao.FindByPrimaryKey(movver_no);
		
		
	}
	
	public MovVerVO getOneMovie_version(Integer movver_no) {
		return dao.FindByPrimaryKey(movver_no);
	}
	
	public Set<TheVO> getTheatersByMovver_no(Integer movver_no) {
		return dao.getTheatersByMovver_no(movver_no);
	}
	public Set<TicTypVO> getTicket_typeByMovver_no(Integer movver_no) {
		return dao.getTicket_typeByMovver_no(movver_no);
	}
	
	public List<MovVerVO> getall(){
		return dao.getall();
	}
}
