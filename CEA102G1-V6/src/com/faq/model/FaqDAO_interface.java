package com.faq.model;

import java.util.*;


public interface FaqDAO_interface {
    public void insert(FaqVO faqVO);
    public void update(FaqVO faqVO);
    public void delete(Integer faq_no);
    public FaqVO findByPrimaryKey(Integer faq_no);
    public List<FaqVO> getAll();
}
