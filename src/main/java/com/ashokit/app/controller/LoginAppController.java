package com.ashokit.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.app.entity.LoginEntry;
import com.ashokit.app.service.LoginAppService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api("The End-Point is design to enter/used our application by providing valid credential")
@RequestMapping("/login")
public class LoginAppController {
	private static final Logger LOGGER = LoggerFactory.getLogger(LoginAppController.class);
	@Autowired
	LoginAppService loginService;

	@PostMapping("/signin")
	@ApiOperation(httpMethod = "POST", value = "This End-Point is an entry point for user", notes = "Using this end-point we can check the login entry is coming from valid user or not and user account is unlock or not", response = String.class)
	public ResponseEntity<String> loginCheck(
			@ApiParam(value = "We pass login details such as username,password", type = "Object") @RequestBody LoginEntry loginEntry) {
		String msg ="";
		LOGGER.debug("----loginCheck()  :Execution Started");
		if (isLoginEntryValid(loginEntry)) {
			msg = loginService.loginCheck(loginEntry);
			LOGGER.info("----loginCheck()  : "+msg);
			return new ResponseEntity<>(msg,HttpStatus.OK);
		}else {
			LOGGER.warn("----loginCheck()  : Email id /Password should not empty ");
			return new ResponseEntity<>("Email id /Password should not empty ",HttpStatus.BAD_REQUEST);
		}
	}

	private Boolean isLoginEntryValid(LoginEntry loginEntry) {
		if (!loginEntry.getEmailId().isEmpty()) {
			if (!loginEntry.getPassword().isEmpty()) {
				return true;
			}
		}
		return false;
	}

}
