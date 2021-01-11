package com.lti.dto;

import com.lti.dto.Status.StatusType;

public class ForgotPasswordDto extends Status{
	
	private StatusType status;
	private String message;
	private int otp;
	public StatusType getStatus() {
		return status;
	}
	public void setStatus(StatusType status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public int getOtp() {
		return otp;
	}
	public void setOtp(int otp) {
		this.otp = otp;
	}
	
	
	
}