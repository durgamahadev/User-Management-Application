package com.ashokit.app.service.impl;

import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.app.entity.User;
import com.ashokit.app.exceptions.DaoException;
import com.ashokit.app.exceptions.UserServiceException;
import com.ashokit.app.repository.UserServiceRepository;
import com.ashokit.app.service.ForgetPasswordService;
import com.ashokit.app.service.MailService;
import com.ashokit.app.utill.UserServiceUtil;

@Service
public class ForgetPasswordServiceImpl implements ForgetPasswordService {
	private static final String EMAIL_SUBJECT = "Forgot Password";
	private final URL UNLOCK_ACCOUNT_BODY_FILE_URL = this.getClass().getClassLoader()
			.getResource("forgot_password_mail_body.txt");
	Logger LOGGER = LoggerFactory.getLogger(ForgetPasswordServiceImpl.class);
	@Autowired
	UserServiceRepository repository;
	@Autowired
	MailService mailService;

	@Override
	public boolean getPasswordByEmail(String email) {
		User user = null;
		LOGGER.debug("-----getPasswordByEmail(...) :Execution Started ");
		try {
			user = repository.findByEmail(email);
		} catch (Exception e) {
			LOGGER.error("-----getPasswordByEmail(...) : System/DB error CAUSE--> ", e);
			throw new DaoException(e);
		}
		if (user == null) {
			LOGGER.warn("-----getPasswordByEmail(...) :Email not exist");
			throw new UserServiceException("Email not exist");
		}

		boolean isMailSend = mailService.sendMail(user.getEmail(),
				UserServiceUtil.getBodyFromFile(user, UNLOCK_ACCOUNT_BODY_FILE_URL), EMAIL_SUBJECT);
		if (isMailSend) {
			LOGGER.info("-----getPasswordByEmail(...) :mail send successfully");
			return true;
		}
		return false;
	}

}
