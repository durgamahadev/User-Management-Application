package com.ashokit.app.entity;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@Setter
@Getter
@ToString
public class ApiError {

	private int statusCode;
	private String errorMessage;
	private Date date;

	public ApiError(int statusCode, String errorMessage, Date date) {
		this.statusCode=statusCode;
		this.errorMessage=errorMessage;
		this.date=date;
	}

}
