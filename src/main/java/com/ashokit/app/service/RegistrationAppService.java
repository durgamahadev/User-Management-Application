package com.ashokit.app.service;

import java.util.Map;

import com.ashokit.app.entity.User;

public interface RegistrationAppService {

	boolean isUniqeEmail(String email);

	boolean register(User newUser);

	Map<Integer, String> getAllCountry();

	Map<Integer, String> getAllState(Integer countryId);

	Map<Integer, String> getAllCity(Integer stateId);


}
