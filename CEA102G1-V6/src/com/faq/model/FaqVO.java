package com.faq.model;

public class FaqVO implements java.io.Serializable{
	private Integer faq_no;
	private Integer faqtyp_no;
	private String faq_question;
	private String faq_answer;
	
	public Integer getFaq_no() {
		return faq_no;
	}
	public void setFaq_no(Integer faq_no) {
		this.faq_no = faq_no;
	}
	public Integer getFaqtyp_no() {
		return faqtyp_no;
	}
	public void setFaqtyp_no(Integer faqtyp_no) {
		this.faqtyp_no = faqtyp_no;
	}
	public String getFaq_question() {
		return faq_question;
	}
	public void setFaq_question(String faq_question) {
		this.faq_question = faq_question;
	}
	public String getFaq_answer() {
		return faq_answer;
	}
	public void setFaq_answer(String faq_answer) {
		this.faq_answer = faq_answer;
	}
}
