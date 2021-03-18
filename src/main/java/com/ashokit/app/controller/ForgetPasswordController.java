package com.ashokit.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.app.service.ForgetPasswordService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("This End-Point is design for helping client to get back their existing password")
@RestController
public class ForgetPasswordController {
	Logger LOGGER = LoggerFactory.getLogger(ForgetPasswordController.class);

	@Autowired
	private ForgetPasswordService forgetPasswordService;


	@ApiOperation(httpMethod = "GET", value = "This End-Point is help user to get back their password back", notes = "Using this end-point user can get back their password by providing register email id", response = String.class)
	@GetMapping("/forgotPassword")
	public ResponseEntity<String> forgotPassword(@RequestParam String email) {
		LOGGER.debug("----forgotPassword(...)  : Execution Started");
		if (!email.trim().isEmpty()) {
			 boolean isSuccess = forgetPasswordService.getPasswordByEmail(email);
			if (isSuccess) {
				LOGGER.info("----forgotPassword(...)  : Password fetch successfully");
				return new ResponseEntity<>("password is sended to you email..!", HttpStatus.OK);
			}
		}
		LOGGER.warn("----forgotPassword(...)  : Email can't be empty");
		return new ResponseEntity<>("Email can't be empty", HttpStatus.BAD_REQUEST);
	}

}
