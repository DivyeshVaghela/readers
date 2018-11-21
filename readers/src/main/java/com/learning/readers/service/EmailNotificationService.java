package com.learning.readers.service;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class EmailNotificationService {

	@Autowired
	private JavaMailSender mailSender;
	
	public void sendMail(String to, String subject, String message) throws MessagingException {

		System.out.println("to : " + to);
		
		MimeMessage mimeMessage = mailSender.createMimeMessage();
		
		MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
		mimeMessageHelper.setTo(to);
		mimeMessageHelper.setSubject(subject);
		mimeMessageHelper.setText(message, true);
		
		
		/*MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
			
			@Override
			public void prepare(MimeMessage mimeMessage) throws Exception {
				
				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
				mimeMessage.setSubject(subject);
				
				MimeMultipart mimeMultipart = new MimeMultipart();
				mimeMessage.setContent(mimeMultipart);
				
				MimeBodyPart mimeBodyPart = new MimeBodyPart();
				mimeBodyPart.setContent(message, "text/html");
				mimeMultipart.addBodyPart(mimeBodyPart);
			}
		};
		
		mailSender.send(mimeMessagePreparator);*/
		
		mailSender.send(mimeMessage);
		System.out.println("Mail Sent");
	}
}
