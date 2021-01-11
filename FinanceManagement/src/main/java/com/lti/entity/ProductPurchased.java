package com.lti.entity;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name="fm_product_purchased_1")
public class ProductPurchased {
	
	@Id
	@SequenceGenerator(name = "seq_fm_product_purchased",allocationSize = 1,initialValue = 500001)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_fm_product_purchased")
	private long productPurchasedId;
	private LocalDate productPurchasedDate;
	private int emiScheme;
	private double amount_remaining;
	
	@ManyToOne
	@JoinColumn(name = "userId")
     User user;
	
	@ManyToOne
	@JoinColumn(name = "productId")
    Product product;
	
	@OneToMany(mappedBy = "productPurchased",cascade = CascadeType.ALL)
	List<Transaction> transactions;

	public long getProductPurchasedId() {
		return productPurchasedId;
	}

	public void setProductPurchasedId(long productPurchasedId) {
		this.productPurchasedId = productPurchasedId;
	}

	public LocalDate getProductPurchasedDate() {
		return productPurchasedDate;
	}

	public void setProductPurchasedDate(LocalDate productPurchasedDate) {
		this.productPurchasedDate = productPurchasedDate;
	}

	public int getEmiScheme() {
		return emiScheme;
	}

	public void setEmiScheme(int emiScheme) {
		this.emiScheme = emiScheme;
	}

	public double getAmount_remaining() {
		return amount_remaining;
	}

	public void setAmount_remaining(double amount_remaining) {
		this.amount_remaining = amount_remaining;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public void setTransactions(List<Transaction> transactions) {
		this.transactions = transactions;
	}

	
	
		
}
