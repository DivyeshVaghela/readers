package com.learning.readers.test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.util.StringUtils;

import com.learning.readers.service.EmailNotificationService;

public class EmailNotificationServiceTestCase {

	private static AnnotationConfigApplicationContext context;
	private static EmailNotificationService emailNotificationService;
	
	@BeforeClass
	public static void init() {
		
		context = new AnnotationConfigApplicationContext();
		context.scan("com.learning.readers");
		context.refresh();
		
		emailNotificationService = context.getBean("emailNotificationService", EmailNotificationService.class);
	}
	

	//@Test
	public void sendEmailTest() {
		
		String message = "";
		try {
			
			File file = new File(Thread.currentThread().getContextClassLoader().getResource("emailTemplates/registration.html").getFile());
			String content = new String(Files.readAllBytes(file.toPath()));
			
			/*ClassLoader classLoader = EmailNotificationServiceTestCase.class.getClassLoader();
			ClassPathResource registrationResource = new ClassPathResource("emailTemplates/registration.html");
			String content = new String(Files.readAllBytes(Paths.get(registrationResource.getPath())));*/
			content = content.replaceAll("##username##", "Ganesh");
			
			message = content;
			
			try {
				emailNotificationService.sendMail("divyeshv789@gmail.com", "Test Message", message);
				System.out.println("Email Sent successfully");
			} catch (MessagingException e) {
				e.printStackTrace();
				System.out.println("Problem in sending mail");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//@Test
	public void sendEmailSimpleJava() {
		
		String from = "complaintbook.mail@gmail.com";
		String password = "ComplaintBook-52";
		String to = "divyeshv789@gmail.com";
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		
		//Get the Session
		Session session = Session.getDefaultInstance(props, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, password);
			}
		});
		
		//Compose message
		try {
			MimeMessage message = new MimeMessage(session);
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Simple Java Mail Trial");
			message.setText("This is a trial of simple Java Mail");
			
			Transport.send(message);
			System.out.println("Mail sent successfully");
			
		} catch (MessagingException exp) {
			System.out.println("Mail sending failed");
			exp.printStackTrace();
		}
	}
	
	@Test
	public void stringEmptyTest() {
		
		String testString = ""; 
		assertEquals("Is Null or Empty", true, StringUtils.isEmpty(testString));
	}
}
