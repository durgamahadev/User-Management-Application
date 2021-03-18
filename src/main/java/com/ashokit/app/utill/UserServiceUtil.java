package com.ashokit.app.utill;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.ashokit.app.entity.User;

public class UserServiceUtil {
	private static final String GENDER_MALE = "male";
	private static final String GENDER_FEMALE = "female";

	public static boolean isValidEntity(User newUser) {

		if (!newUser.getFirstName().trim().equals("")) {
			if (!newUser.getLastName().trim().equals("")) {
				if (isValidEmail(newUser.getEmail())) {
					if (isValidPhoneNumber(newUser.getPhoneNumber())) {
						if (!newUser.getDob().equals(new Date())) {
							if (newUser.getGender().trim().equals(GENDER_MALE)
									|| newUser.getGender().trim().equals(GENDER_FEMALE)) {
								if (newUser.getCountry()>0) {
									if (newUser.getState()>0) {
										if (newUser.getCity()>0) {
											return true;
										}

									}

								}

							}

						}

					}

				}
			}
		}

		return false;
	}

	private static boolean isValidPhoneNumber(Long phoneNumber) {
		if (phoneNumber != null && phoneNumber != 0) {
			if (String.valueOf(phoneNumber).length() == 10) {
				return true;
			}
		}
		return false;
	}

	public static boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";
		Pattern pat = Pattern.compile(emailRegex);
		if (email == null)
			return false;
		return pat.matcher(email).matches();
	}

	public static String getBodyFromFile(User user, URL fileUrl) {
		List<String> replacedLines = null;
		String mailBody = "";

		Path filePath;
		try {
			filePath = Paths.get(fileUrl.toURI());

			try (Stream<String> lines = Files.lines(filePath)) {
				replacedLines = lines
						.map(line -> line.replace("{FNAME}", user.getFirstName()).replace("{LNAME}", user.getLastName())
								.replace("{TEMP_PSW}", PasswordUtil.decryptPassword(user.getPassword()))
								.replace("{USER_EMAIL}", user.getEmail()))
						.collect(Collectors.toList());
				mailBody = String.join("", replacedLines);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (URISyntaxException e) {
			e.printStackTrace();
		}

		return mailBody;

	}

}
