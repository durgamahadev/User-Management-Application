package com.ashokit.app.utill;

import java.util.Random;

import java.security.spec.KeySpec;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import org.apache.commons.codec.binary.Base64;

public final class PasswordUtil {
	private static final String UNICODE_FORMAT = "UTF8";
	public static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
	private static KeySpec ks;
	private static SecretKeyFactory skf;
	private static Cipher cipher;
	private static byte[] arrayBytes;
	private static String myEncryptionKey;
	private static String myEncryptionScheme;
	private static SecretKey key;
	private static boolean isOnInitCalled = false;

	private PasswordUtil() {

	}

	private static void onInit() {
		try {
			myEncryptionKey = "ThisIsSpartaThisIsSparta";
			myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
			arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
			ks = new DESedeKeySpec(arrayBytes);
			skf = SecretKeyFactory.getInstance(myEncryptionScheme);
			cipher = Cipher.getInstance(myEncryptionScheme);
			key = skf.generateSecret(ks);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String randomPassword() {
		int start = 35;
		int end = 122;
		int passwordLength = 12;
		Random random = new Random();
		String randomPassword = random.ints(start, end + 1)
				// true
				.filter(i -> (((i >= 35 && i <= 38) || (i >= 48 && i <= 57))
						|| ((i >= 64 && i <= 90) || (i >= 97 && i <= 122))))
				// # $ % & 1-9 @ A-Z a-z
				.limit(passwordLength)
				.collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append).toString();
		return randomPassword;
	}

	public static String encryptPassword(String plainTextPassword) {
		String encryptedString = null;
		if (!isOnInitCalled) {
			onInit();
		}
		try {

			cipher.init(Cipher.ENCRYPT_MODE, key);
			byte[] plainText = plainTextPassword.getBytes(UNICODE_FORMAT);
			byte[] encryptedText = cipher.doFinal(plainText);
			encryptedString = new String(Base64.encodeBase64(encryptedText));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return encryptedString;
	}

	public static String decryptPassword(String encodedEncryptPassword) {
		String decryptedText = null;
		if (!isOnInitCalled) {
			onInit();
		}
		try {
			cipher.init(Cipher.DECRYPT_MODE, key);
			byte[] encryptedText = Base64.decodeBase64(encodedEncryptPassword);
			byte[] plainText = cipher.doFinal(encryptedText);
			decryptedText = new String(plainText);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return decryptedText;
	}

}
