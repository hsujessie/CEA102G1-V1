package com.faq_type.model;

import java.util.*;

import com.faq.model.FaqVO;



public interface FaqtypDAO_interface {
    public void insert(FaqtypVO faq_typeVO);
    public void update(FaqtypVO faq_typeVO);
    public void delete(Integer faqtyp_no);
    public FaqtypVO findByPrimaryKey(Integer faqtyp_no);
    public List<FaqtypVO> getAll();
	public Set<FaqVO> getFaqsByFaqtyp_no(Integer faqtyp_no);
}
