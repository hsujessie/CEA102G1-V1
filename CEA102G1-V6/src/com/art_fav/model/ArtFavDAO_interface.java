package com.art_fav.model;

import java.util.List;
import java.util.Map;



public interface ArtFavDAO_interface {
	public void insert(ArtFavVO artFavVO);
	public void delete(Integer artNo, Integer memNo);
	public ArtFavVO findByPrimaryKey(Integer artNo, Integer memNo);
	public List<ArtFavVO> findByMenNo(Integer memNo);
	public List<ArtFavVO> getAll(Map<String, String[]> map);
}
