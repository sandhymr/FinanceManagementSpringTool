package com.lti.dto;
public class FaqDto {
	private long faqId;
	private String questions;
	private String answer;
	private long productId;
	public long getFaqId() {
		return faqId;
	}
	public void setFaqId(long faqId) {
		this.faqId = faqId;
	}
	public String getQuestions() {
		return questions;
	}
	public void setQuestions(String questions) {
		this.questions = questions;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	
}