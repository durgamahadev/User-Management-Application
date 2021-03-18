package com.ashokit.app.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ashokit.app.constant.UserServiceConstants;
import com.ashokit.app.entity.UnlockEntry;
import com.ashokit.app.entity.User;
import com.ashokit.app.exceptions.DaoException;
import com.ashokit.app.exceptions.UserServiceException;
import com.ashokit.app.repository.UserServiceRepository;
import com.ashokit.app.service.UnlockAppService;
import com.ashokit.app.utill.PasswordUtil;

@Service
public class UnlockAppServiceImpl implements UnlockAppService {
	Logger LOGGER = LoggerFactory.getLogger(UnlockAppServiceImpl.class);
	@Autowired
	UserServiceRepository repository;

	@Override
	public boolean unlockUser(UnlockEntry unlockEntry) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		LOGGER.debug("-------unlockUser(..)  :execution start ");
		User user = null;
		try {
			user = repository.findByEmail(unlockEntry.getEmail());
			// checking temporary password is available or not
			if (user != null
					&& PasswordUtil.decryptPassword(user.getPassword()).equals(unlockEntry.getTemporaryPassword())) {
				user.setPassword(PasswordUtil.encryptPassword(unlockEntry.getNewPassword()));
				user.setStatus(UserServiceConstants.ACCOUNT_UNLOCK_STATUS);
				user.setUpdatedDate(dateFormat.parse(dateFormat.format(new Date())));
				repository.save(user);
				LOGGER.info("-------unlockUser(..)  : user account unlock successfully");
				return true;
			} else {
				LOGGER.warn("-------unlockUser(..)  : Temporary Password Not valid");
				throw new UserServiceException("Temporary Password not valid");
			}
		} catch (Exception e) {
			LOGGER.error("-------unlockUser(..)  : System error happen CAUSE -> ", e);
			throw new DaoException(e);
		}
	}

}
