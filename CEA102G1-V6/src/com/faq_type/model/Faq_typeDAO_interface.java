package com.faq_type.model;

import java.util.*;

import com.faq.model.FaqVO;



public interface Faq_typeDAO_interface {
    public void insert(Faq_typeVO faq_typeVO);
    public void update(Faq_typeVO faq_typeVO);
    public void delete(Integer faqtyp_no);
    public Faq_typeVO findByPrimaryKey(Integer faqtyp_no);
    public List<Faq_typeVO> getAll();
	public Set<FaqVO> getFaqsByFaqtyp_no(Integer faqtyp_no);
}
