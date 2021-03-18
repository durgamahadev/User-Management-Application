package com.ashokit.app.exceptions;

public class DaoException extends RuntimeException {

	private static final long serialVersionUID = 592640209325500490L;

	public DaoException(String message) {
		super(message);
	}

	public DaoException(Throwable cause) {
		super(cause);
	}

}
