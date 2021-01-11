package com.lti.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
//import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "fm_product_1") 
public class Product 
{
	@Id
	@SequenceGenerator(name="seq_fm_product",initialValue = 200001,allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seq_fm_product")
	private long productId;
	private String productName;
	private String productDescription;
	private double price;
	private String productImageUrl;
	private String productType;
	
	@OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
	@JsonIgnore
	List<FrequentlyAskedQuestion> faqs;
	
	@OneToMany(mappedBy = "product",cascade = CascadeType.ALL)
	@JsonIgnore
	List<ProductPurchased> productsPurchased;

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getProductImageUrl() {
		return productImageUrl;
	}

	public void setProductImageUrl(String productImageUrl) {
		this.productImageUrl = productImageUrl;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public List<FrequentlyAskedQuestion> getFaqs() {
		return faqs;
	}

	public void setFaqs(List<FrequentlyAskedQuestion> faqs) {
		this.faqs = faqs;
	}

	public List<ProductPurchased> getProductsPurchased() {
		return productsPurchased;
	}

	public void setProductsPurchased(List<ProductPurchased> productsPurchased) {
		this.productsPurchased = productsPurchased;
	}
	
	
}
