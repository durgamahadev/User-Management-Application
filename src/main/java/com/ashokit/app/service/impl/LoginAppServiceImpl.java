package com.ashokit.app.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.app.constant.UserServiceConstants;
import com.ashokit.app.entity.LoginEntry;
import com.ashokit.app.entity.User;
import com.ashokit.app.exceptions.DaoException;
import com.ashokit.app.exceptions.UserServiceException;
import com.ashokit.app.repository.UserServiceRepository;
import com.ashokit.app.service.LoginAppService;
import com.ashokit.app.utill.PasswordUtil;

@Service
public class LoginAppServiceImpl implements LoginAppService {
	Logger LOGGER = LoggerFactory.getLogger(LoginAppServiceImpl.class);

	@Autowired
	private UserServiceRepository repository;

	@Override
	public String loginCheck(LoginEntry loginEntry) {
		User user = null;
		LOGGER.debug("---loginCheck(..) :Execution Started");
		// TODO:Execution pending
		try {
			user = repository.findByEmail(loginEntry.getEmailId());
			// check email and password is a valid credential or not
		
		} catch (Exception e) {
			LOGGER.error("---loginCheck(..) :System/DB error CAUSE--> ", e);
			throw new DaoException(e);
		}
		if (user != null && loginEntry.getPassword().equals(PasswordUtil.decryptPassword(user.getPassword()))) {
			if (user.getStatus().equals(UserServiceConstants.ACCOUNT_LOCK_STATUS)) {
				// If user provide valid credential but account in lock stage
				LOGGER.warn("---loginCheck(..) :Account is Locked");
				throw new UserServiceException("Your Account is Locked");
			}
			LOGGER.info("---loginCheck(..) : Valid Credential");
			return "Welcome To AshokIT ";
		} else {
			LOGGER.warn("---loginCheck(..) : Invalid Email id or Password");
			throw new UserServiceException("Invalid Email id or Password");
		}
	}

}
