package com.faq.model;

import java.util.*;

public class FaqService {
	
	private FaqDAO_interface dao;
	
	public FaqService() {
		dao = new FaqDAO();
	}
	
	public FaqVO addFaq(Integer faqtyp_no, String faq_question, String faq_answer) {
		FaqVO faqVO = new FaqVO();
		
		faqVO.setFaqtyp_no(faqtyp_no);
		faqVO.setFaq_question(faq_question);
		faqVO.setFaq_answer(faq_answer);
		dao.insert(faqVO);
		
		return faqVO;
	}
	
	public FaqVO updateFaq(Integer faq_no,Integer faqtyp_no, String faq_question, String faq_answer) {
		FaqVO faqVO = new FaqVO();
		
		faqVO.setFaq_no(faq_no);
		faqVO.setFaqtyp_no(faqtyp_no);
		faqVO.setFaq_question(faq_question);
		faqVO.setFaq_answer(faq_answer);
		dao.update(faqVO);
		
		return faqVO;
	}
	
	public void deleteFaq(Integer faq_no) {
		dao.delete(faq_no);
	}
	public FaqVO getOneFaq(Integer faq_no) {
		return dao.findByPrimaryKey(faq_no);
	}
	public List<FaqVO> getAll(){
		return dao.getAll();
	}
}
