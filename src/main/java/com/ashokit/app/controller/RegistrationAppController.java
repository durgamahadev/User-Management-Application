package com.ashokit.app.controller;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.app.entity.User;
import com.ashokit.app.exceptions.UserServiceException;
import com.ashokit.app.service.RegistrationAppService;
import com.ashokit.app.utill.UserServiceUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@Api("The End-Point is design to take new user data,validate,parse and save in our sysytem")
@RequestMapping("/register")
public class RegistrationAppController {
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationAppController.class);
	@Autowired
	private RegistrationAppService registrationService;


	@ApiOperation(httpMethod = "POST", value = "This End-Point is used for register new user", notes = "Using this end-point we can register new user based on one unregister email for new user to register ", response = String.class)
	@PostMapping("/signup")
	public ResponseEntity<String> registerUser(
			@ApiParam(value = "", required = true, type = "") @RequestBody User newUser) {
		LOGGER.debug("-----registerUser(...) :execution start");
		if (UserServiceUtil.isValidEntity(newUser)) {
			if (registrationService.register(newUser)) {
				LOGGER.info("-----registerUser(...) :registration success");
				return new ResponseEntity<>("Please check your email to unlock account", HttpStatus.CREATED);
			}
		}
		LOGGER.warn("-----registerUser(...) :Entered data is not valid");
		return new ResponseEntity<>("Entered data is not valid,Entered again..! ", HttpStatus.BAD_REQUEST);
	}

	@ApiOperation(httpMethod = "GET", value = "This End-Point check email is unique or not", notes = "Using this end-point we can check provided email is unregister or not based we can register new user", response = String.class)
	@GetMapping("/uniqueEmail")
	public ResponseEntity<String> isUniqeEmail(@RequestParam(name = "email") String email) {
		LOGGER.debug("-------isUniqueEmail(...)  : Execution statred");
		if (!email.trim().isEmpty()) {
			if (UserServiceUtil.isValidEmail(email)) {
				if (!registrationService.isUniqeEmail(email)) {
					LOGGER.warn("-------isUniqueEmail(...)  : Email is already exist");
					return new ResponseEntity<>("Email is already registered,provide new one", HttpStatus.BAD_REQUEST);
				} else {
					LOGGER.info("-------isUniqueEmail(...)  : Email is unique");
					return new ResponseEntity<>("Unique one", HttpStatus.OK);
				}
			} else {
				LOGGER.warn("-------isUniqueEmail(...)  : Email is Not Valid");
				return new ResponseEntity<>("Email is Not Valid", HttpStatus.BAD_REQUEST);
			}
		}
		LOGGER.warn("-------isUniqueEmail(...)  : Email field can't be empty");
		return new ResponseEntity<>("Email field can't be empty,need a email id", HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ApiOperation(httpMethod = "GET", value = "This End-Point return List of Country", notes = "Using this end-point we can get List of country as key-value pair,key is country id & value is country name", response = Map.class)
	@GetMapping("/getAllCountry")
	public ResponseEntity<Map<Integer, String>> getAllCountry() {
		LOGGER.debug("getAllCountry()  :Execution Started");
		Map<Integer, String> countryMap = registrationService.getAllCountry();
		System.out.println(countryMap);
		LOGGER.info("-----getAllCountry()  :all Country fetch successfully");
		return new ResponseEntity<>(countryMap, HttpStatus.OK);
	}

	@ApiOperation(httpMethod = "GET", value = "This End-Point return List of State", notes = "Using this end-point we can get List of State as key-value pair,key is state id & value is state name based country id", response = Map.class)
	@GetMapping("/getAllState/{countryId}")
	public ResponseEntity<Map<Integer, String>> getAllState(@ApiParam() @PathVariable Integer countryId) {
		LOGGER.debug("getAllState(...)  :Execution Started");
		Map<Integer, String> stateMap = null;

		if (countryId != null && countryId != 0) {
			stateMap = registrationService.getAllState(countryId);
			LOGGER.info("-----getAllState(...)  :all state fetch successfully based on countryId -> " + countryId);
			return new ResponseEntity<>(stateMap, HttpStatus.OK);
		}
		LOGGER.warn("-----getAllState(...)  : CountryId is empty/zero");
		throw new UserServiceException("CountryId is empty/zero");
	}

	@ApiOperation(httpMethod = "GET", value = "This End-Point return List of City", notes = "Using this end-point we can get List of City as key-value pair,key is city id & value is city name based state id", response = Map.class)
	@GetMapping("/getAllCity/{stateId}")
	public ResponseEntity<Map<Integer, String>> getAllCity(@PathVariable Integer stateId) {
		LOGGER.debug("getAllCity(...)  :Execution Started");
		Map<Integer, String> cityMap = registrationService.getAllCity(stateId);
		LOGGER.info("-----getAllCity(...)  :all city fetch successfully based on stateId -> " + stateId);
		return new ResponseEntity<>(cityMap, HttpStatus.OK);
	}

}
