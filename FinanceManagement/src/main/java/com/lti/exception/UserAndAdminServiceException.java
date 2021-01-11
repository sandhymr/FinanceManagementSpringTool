package com.lti.exception;

public class UserAndAdminServiceException extends RuntimeException{

	public UserAndAdminServiceException() {
		super();
	}

	public UserAndAdminServiceException(String message) {
		super(message);
	}
}
