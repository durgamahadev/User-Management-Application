package com.ashokit.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ashokit.app.entity.UnlockEntry;
import com.ashokit.app.service.UnlockAppService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("This End-Point is design to check login user account is ready to use or not ")
@RestController
public class UnlockController {
	Logger LOGGER = LoggerFactory.getLogger(UnlockController.class);

	@Autowired
	UnlockAppService unlockService;

	@ApiOperation(httpMethod = "POST", value = "This End-Point Unlock new user", notes = "Using this end-point user can unlock newly open account", response = String.class)
	@PostMapping("/unlockUser")
	public ResponseEntity<String> unlockUser(@RequestBody UnlockEntry unlockEntry) {
		LOGGER.debug("-----unlockUser(...)   : Start Execution");
		if (unlockEntry.getTemporaryPassword().equalsIgnoreCase(unlockEntry.getNewPassword())) {
			LOGGER.warn("-----unlockUser(...)   :Temporary password & new password should not match");
			return new ResponseEntity<>("Temporary password & new password should not match", HttpStatus.BAD_REQUEST);
		}
		if (unlockEntry.getNewPassword().equals(unlockEntry.getConfirmPassword())) {
			LOGGER.debug("-----unlockUser(...)   :new password and confirm password are equal");
			if (unlockService.unlockUser(unlockEntry)) {
				LOGGER.info("-----unlockUser(...)   : unlock user successfully");
				return new ResponseEntity<>("Account unlocked, please proceed with login", HttpStatus.OK);
			}
		}
		LOGGER.warn("-----unlockUser(...)   : Confirm password & new password should match ");
		return new ResponseEntity<>("Confirm password & new password should match", HttpStatus.BAD_REQUEST);
	}
}
