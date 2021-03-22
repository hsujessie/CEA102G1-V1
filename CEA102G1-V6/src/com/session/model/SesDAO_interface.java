package com.session.model;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface SesDAO_interface {
	public void insert(SesVO sesVO);
	public void update(SesVO sesVO);
    public SesVO findByPrimaryKey(Integer sesNo);
    public List<SesVO> getAll();
    public List<SesVO> getAll(Map<String, String[]> map);  //複合查詢
    public List<SesVO> findMoviesByDate(Date date);
    public List<SesVO> findSesTimeByMovNoAndDate(Integer movNo,Date sesDate);
    
    public void updateSeatStatus(Integer sesNo, String sesSeatStatus);
}
