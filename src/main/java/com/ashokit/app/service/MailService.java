package com.ashokit.app.service;

public interface MailService {
	public boolean sendMail(String to, String body, String subject);
}
