package com.lti.dto;

import java.time.LocalDate;
import java.time.LocalTime;

public class BuyProductDto extends Status {

	private String productName;
	private double amount_paid;
	private double amount_remaining;
	private double product_price;
	private int emiScheme;
	private long userId;
	private long productId;
	private long productPurchasedId;
	private double emi;
	private LocalDate transaction_date;
	private LocalTime transaction_time;
	private boolean emiCompleted=false;;
	
	
	

	public boolean isEmiCompleted() {
		return emiCompleted;
	}
	public void setEmiCompleted(boolean emiCompleted) {
		this.emiCompleted = emiCompleted;
	}
	public LocalDate getTransaction_date() {
		return transaction_date;
	}
	public void setTransaction_date(LocalDate transaction_date) {
		this.transaction_date = transaction_date;
	}
	public LocalTime getTransaction_time() {
		return transaction_time;
	}
	public void setTransaction_time(LocalTime transaction_time) {
		this.transaction_time = transaction_time;
	}
	public double getEmi() {
		return emi;
	}
	public void setEmi(double emi) {
		this.emi = emi;
	}
	public long getProductPurchasedId() {
		return productPurchasedId;
	}
	public void setProductPurchasedId(long productPurchasedId) {
		this.productPurchasedId = productPurchasedId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public double getAmount_paid() {
		return amount_paid;
	}
	public void setAmount_paid(double amount_paid) {
		this.amount_paid = amount_paid;
	}
	public double getAmount_remaining() {
		return amount_remaining;
	}
	public void setAmount_remaining(double amount_remaining) {
		this.amount_remaining = amount_remaining;
	}
	public double getProduct_price() {
		return product_price;
	}
	public void setProduct_price(double product_price) {
		this.product_price = product_price;
	}
	public int getEmiScheme() {
		return emiScheme;
	}
	public void setEmiScheme(int emiScheme) {
		this.emiScheme = emiScheme;
	}
	public long getUserId() {
		return userId;
	}
	public void setUserId(long userId) {
		this.userId = userId;
	}
	public long getProductId() {
		return productId;
	}
	public void setProductId(long productId) {
		this.productId = productId;
	}
	
	
}
