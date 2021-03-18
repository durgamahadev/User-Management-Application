package com.ashokit.app.service.impl;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ashokit.app.constant.UserServiceConstants;
import com.ashokit.app.entity.City;
import com.ashokit.app.entity.Country;
import com.ashokit.app.entity.State;
import com.ashokit.app.entity.User;
import com.ashokit.app.exceptions.DaoException;
import com.ashokit.app.exceptions.UserServiceException;
import com.ashokit.app.repository.CityRepository;
import com.ashokit.app.repository.CountryRepository;
import com.ashokit.app.repository.StateRepository;
import com.ashokit.app.repository.UserServiceRepository;
import com.ashokit.app.service.MailService;
import com.ashokit.app.service.RegistrationAppService;
import com.ashokit.app.utill.PasswordUtil;
import com.ashokit.app.utill.UserServiceUtil;

@Service
public class RegistrationAppServiceImpl implements RegistrationAppService {
	private static final String EMAIL_SUBJECT = "Unlock Account";
	private final URL UNLOCK_ACCOUNT_BODY_FILE_URL = this.getClass().getClassLoader()
			.getResource("unlock_account_mail_body.txt");
	private static final Logger LOGGER = LoggerFactory.getLogger(RegistrationAppServiceImpl.class);
	public static String randomPassword = "";
	@Autowired
	private UserServiceRepository repository;
	@Autowired
	private CountryRepository countryRepo;
	@Autowired
	private StateRepository stateRepo;
	@Autowired
	private CityRepository cityRepo;
	@Autowired
	MailService mailService;

	@Override
	public boolean isUniqeEmail(String email) {
		LOGGER.debug("-----isUniqeEmail(...)  :Execution started ");
		User user = null;
		try {
			user = repository.findByEmail(email);

			if (user == null) {
				LOGGER.info("-----isUniqeEmail(...)  : " + email + " is a unique email");
				return true;
			}
		} catch (Exception e) {
			LOGGER.error("-----isUniqeEmail(...)  : System/DB error CAUSE--> ", e);
			throw new DaoException(e);
		}
		return false;
	}

	@Override
	@Transactional
	public boolean register(User newUser) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

		LOGGER.debug("-----register(...)  :Execution started ");
		try {
			newUser.setStatus(UserServiceConstants.ACCOUNT_LOCK_STATUS);
			randomPassword = PasswordUtil.randomPassword();
			newUser.setPassword(PasswordUtil.encryptPassword(randomPassword));
			LOGGER.info("-----register(...)  :Temporary Encrypted passwprd :-->  " + randomPassword);
			newUser.setCreatedDate(dateFormat.parse(dateFormat.format(new Date())));
			User savedUser = repository.save(newUser);
			boolean isSendMail = mailService.sendMail(savedUser.getEmail(),
					UserServiceUtil.getBodyFromFile(savedUser, UNLOCK_ACCOUNT_BODY_FILE_URL), EMAIL_SUBJECT);
			if (isSendMail) {
				LOGGER.info("-----register(...)  :Mail send successfully");
				LOGGER.info("-----register(...)  :New User Registration successfully");
				return true;
			}
			return false;

		} catch (Exception e) {
			LOGGER.error("-----register(...)  : System/DB error CAUSE--> ", e);
			throw new DaoException(e);
		}
	}

	@Override
	public Map<Integer, String> getAllCountry() {
		LOGGER.debug("-----getAllCountry(...)  :Execution started ");
		Map<Integer, String> countryMap = null;
		try {
			countryMap = countryRepo.findAll().stream()
					.collect(Collectors.toMap(Country::getCountryId, Country::getCountryName));
		} catch (Exception e) {
			LOGGER.error("-----getAllCountry(...)  : System/DB error CAUSE--> ", e);
			throw new DaoException(e);
		}
		if (countryMap.isEmpty()) {
			LOGGER.warn("-----getAllCountry(...)  : Country data is not available in system");
			throw new UserServiceException("Country data is not available in system");
		}
		LOGGER.info("-----getAllCountry(...)  : All Country Fetch successfully ");
		return countryMap;
	}

	@Override
	public Map<Integer, String> getAllState(Integer countryId) {
		LOGGER.debug("-----getAllState(...)  :Execution started ");
		Map<Integer, String> stateMap = null;
		try {
			stateMap = stateRepo.findByCountryId(countryId).stream()
					.collect(Collectors.toMap(State::getStateId, State::getStateName));
		} catch (Exception e) {
			LOGGER.error("-----getAllState(...)  : System/DB error CAUSE--> ", e);
			throw new DaoException(e);
		}
		if (stateMap.isEmpty()) {
			LOGGER.warn("-----getAllState(...)  : state data is not available on countryId :" + countryId);
			throw new UserServiceException("State data is not available on countryId :" + countryId);
		}
		LOGGER.info("-----getAllState(...)  : All State Fetch successfully ");
		return stateMap;
	}

	@Override
	public Map<Integer, String> getAllCity(Integer stateId) {
		LOGGER.debug("-----getAllCity(...)  :Execution started ");
		Map<Integer, String> cityMap = null;
		try {
			cityMap = cityRepo.findByStateId(stateId).stream()
					.collect(Collectors.toMap(City::getCityId, City::getCityName));
		} catch (Exception e) {
			LOGGER.error("-----getAllCity(...)  : System/DB error CAUSE--> ", e);
			throw new DaoException(e);
		}
		if (cityMap.isEmpty()) {
			LOGGER.warn("-----getAllCity(...)  : City data is not available on stateId : " + stateId);
			throw new UserServiceException("City data is not availableon stateId " + stateId);
		}
		LOGGER.info("-----getAllCity(...)  : All City Fetch successfully ");
		return cityMap;
	}

}
