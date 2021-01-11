package com.lti.entity;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="fm_transaction_1")
public class Transaction {

	@Id
	@SequenceGenerator(name = "seq_fm_transaction",allocationSize = 1,initialValue = 600001)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_fm_transaction")
	private long transactionId;
	private double amountPaid;
	private LocalDate transactionDate;
	
	@ManyToOne
	@JoinColumn(name = "userId")
	User user;	
	
	@ManyToOne
	@JoinColumn(name = "productPurchasedId")
    ProductPurchased productPurchased;
	
	@ManyToOne
	@JoinColumn(name = "cardId")
	Card card;

	public long getTransactionId() {
		return transactionId;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public ProductPurchased getProductPurchased() {
		return productPurchased;
	}

	public void setProductPurchased(ProductPurchased productPurchased) {
		this.productPurchased = productPurchased;
	}

	public Card getCard() {
		return card;
	}

	public void setCard(Card card) {
		this.card = card;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}
	
	
}
