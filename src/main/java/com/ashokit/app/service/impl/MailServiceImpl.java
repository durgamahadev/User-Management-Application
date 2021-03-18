package com.ashokit.app.service.impl;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.ashokit.app.service.MailService;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender sender;

	@Override
	public boolean sendMail(String to, String body, String subject) {
		MimeMessage mimeMessage = null;
		MimeMessageHelper mimeMessageHelper = null;
		try {
			mimeMessage = sender.createMimeMessage();
			mimeMessageHelper = new MimeMessageHelper(mimeMessage);
			mimeMessageHelper.setTo(to);
			mimeMessageHelper.setSubject(subject);
			mimeMessageHelper.setText(body, true);
			sender.send(mimeMessage);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

}
