package com.ashokit.app.exceptions;

public class UserServiceException extends RuntimeException {

	private static final long serialVersionUID = 4350671957155251223L;

	public UserServiceException(String string) {
		super(string);
	}

	public UserServiceException(Exception e) {
		super(e);
	}

}
