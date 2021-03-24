package com.art_fav.model;

import java.util.List;
import java.util.Map;


public class ArtFavService {
	
	private ArtFavDAO_interface dao;
	
	public ArtFavService() {
		dao = new ArtFavDAO();
	}
	
	public ArtFavVO insertArtFav(Integer artNo, Integer memNo) {
		ArtFavVO artFavVO = new ArtFavVO();
		
		artFavVO.setArtNo(artNo);
		artFavVO.setMemNo(memNo);
		dao.insert(artFavVO);
		
		return artFavVO;
	}
	
	public void deleteArtFav(Integer artNo, Integer memNo) {
		dao.delete(artNo, memNo);
		
	}
	
	public ArtFavVO getOneArtFav(Integer artNo, Integer memNo) {
		return dao.findByPrimaryKey(artNo, memNo);
	}
	
	public List<ArtFavVO> findByMenNo(Integer memNo){
		return dao.findByMenNo(memNo);
	}
	public List<ArtFavVO> getAll(Map<String, String[]> map){
		System.out.println("artSvc_map:" + map);
		return dao.getAll(map);
	}
}
