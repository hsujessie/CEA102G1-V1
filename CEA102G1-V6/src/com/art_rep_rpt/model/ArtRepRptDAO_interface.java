package com.art_rep_rpt.model;

import java.util.List;
import java.util.Map;

public interface ArtRepRptDAO_interface {
	public void insert(ArtRepRptVO artRepRptVO);
	public void update(ArtRepRptVO artRepRptVO);
	public ArtRepRptVO findByPrimaryKey(Integer artRepRptNo);
	public List<ArtRepRptVO> findByArtRepNo(Integer artRepNo);
	public List<ArtRepRptVO> getAll();
	public List<ArtRepRptVO> getAll(Map<String, String[]> map);

}
