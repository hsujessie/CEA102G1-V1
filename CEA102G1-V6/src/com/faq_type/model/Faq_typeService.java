package com.faq_type.model;

import java.util.*;

import com.faq.model.*;

public class Faq_typeService {
	
	private Faq_typeDAO_interface dao;
	
	public Faq_typeService() {
		dao = new Faq_typeDAO();
	}
	
	public Faq_typeVO addFaq_type(String faqtyp_name) {
		Faq_typeVO faq_typeVO = new Faq_typeVO();
		
		faq_typeVO.setFaqtyp_name(faqtyp_name);
		dao.insert(faq_typeVO);
		
		return faq_typeVO;
		
	}
	
	public Faq_typeVO updateFaq_type( Integer faqtyp_no , String faqtyp_name) {
		
		Faq_typeVO faq_typeVO = new Faq_typeVO();
		
		faq_typeVO.setFaqtyp_name(faqtyp_name);
		faq_typeVO.setFaqtyp_no(faqtyp_no);
		dao.update(faq_typeVO);
		
		return dao.findByPrimaryKey(faqtyp_no);
		
		
	}
	
	public Faq_typeVO getOneFaq_type(Integer faqtyp_no) {
		return dao.findByPrimaryKey(faqtyp_no);
	}
	
	public Set<FaqVO> getFaqsByFaqtyp_no(Integer faqtyp_no) {
		return dao.getFaqsByFaqtyp_no(faqtyp_no);
	}
	
	public List<Faq_typeVO> getall(){
		return dao.getAll();
	}
	
	
	
}
