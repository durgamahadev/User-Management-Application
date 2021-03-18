package com.ashokit.app.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.ashokit.app.entity.ApiError;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(UserServiceException.class)
	public ResponseEntity<ApiError> handelUserServiceException(UserServiceException exception) {
		ApiError apiError = new ApiError(400, exception.getMessage(), new Date());
		return new ResponseEntity<>(apiError, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(DaoException.class)
	public ResponseEntity<ApiError> handelDaoException(DaoException exception) {
		ApiError apiError = new ApiError(500, exception.getMessage(), new Date());
		return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	

}
