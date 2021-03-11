package com.comment_report.model;
import java.util.List;

import com.session.model.SesVO;

public interface ComRepDAO_interface {
	public void insert(ComRepVO comRepVO);
	public void update(ComRepVO comRepVO);
    public ComRepVO findByPrimaryKey(Integer comRepNo);
    public List<ComRepVO> getAll();
    public List<ComRepVO> findComRepByComReStatus(Integer comReStatus);
}