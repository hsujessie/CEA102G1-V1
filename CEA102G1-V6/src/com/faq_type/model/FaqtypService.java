package com.faq_type.model;

import java.util.*;

import com.faq.model.*;

public class FaqtypService {
	
	private FaqtypDAO_interface dao;
	
	public FaqtypService() {
		dao = new FaqtypDAO();
	}
	
	public FaqtypVO addFaq_type(String faqtyp_name) {
		FaqtypVO faq_typeVO = new FaqtypVO();
		
		faq_typeVO.setFaqtyp_name(faqtyp_name);
		dao.insert(faq_typeVO);
		
		return faq_typeVO;
		
	}
	
	public FaqtypVO updateFaq_type( Integer faqtyp_no , String faqtyp_name) {
		
		FaqtypVO faq_typeVO = new FaqtypVO();
		
		faq_typeVO.setFaqtyp_name(faqtyp_name);
		faq_typeVO.setFaqtyp_no(faqtyp_no);
		dao.update(faq_typeVO);
		
		return dao.findByPrimaryKey(faqtyp_no);
		
		
	}
	
	public FaqtypVO getOneFaq_type(Integer faqtyp_no) {
		return dao.findByPrimaryKey(faqtyp_no);
	}
	
	public Set<FaqVO> getFaqsByFaqtyp_no(Integer faqtyp_no) {
		return dao.getFaqsByFaqtyp_no(faqtyp_no);
	}
	
	public List<FaqtypVO> getall(){
		return dao.getAll();
	}
	
	
	
}
