package com.lti.entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "fm_frequently_asked_question_1") 
public class FrequentlyAskedQuestion {

	@Id
	@SequenceGenerator(name="seq_fm_faqs",initialValue = 400001,allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_fm_faqs")
	private long faqId;
	private String questions;
	private String answer;
	
	@ManyToOne
	@JoinColumn(name="productId")
	Product product;

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

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	
}
