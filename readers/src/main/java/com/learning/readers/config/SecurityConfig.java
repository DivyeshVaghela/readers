package com.learning.readers.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.learning.readers.service.CustomUserDetailsService;

@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder getBCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean(name="mailSender")
	public JavaMailSender getMailSender() {
		
		JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();
		
		javaMailSender.setHost("smtp.gmail.com");
		javaMailSender.setUsername("complaintbook.mail@gmail.com");
		javaMailSender.setPassword("ComplaintBook-52");
		
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.smtp.socketFactory.port", "465");
		javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		javaMailProperties.put("mail.smtp.socketFactory.fallback", "false");
		javaMailProperties.put("mail.smtp.port", "465");
		javaMailProperties.put("mail.smtp.ssl.enable", "true");
		javaMailProperties.put("mail.smtp.starttls.enable", "true");
		javaMailProperties.put("mail.debug", "true");
	
		javaMailSender.setJavaMailProperties(javaMailProperties);
		
		return javaMailSender;
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(new BCryptPasswordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		
		//http.csrf().disable();
		
		
		/*.antMatchers("/secured/**")
			.authenticated()
		.antMatchers("/reader/**")
			.hasAuthority("READER")
		.antMatchers("/assets/**")
			.permitAll()
		.anyRequest()
			.permitAll()
		.and()
		.formLogin()
			.loginPage("/login")
			.permitAll()
		.and()
		.logout()
			.logoutUrl("/logout")
			.permitAll()
		.and();
		.exceptionHandling()
			.accessDeniedPage("/access-denied");*/
		
		http.authorizeRequests()
			.antMatchers("/assets/**")
				.permitAll()
			.antMatchers("/signup-reader")
				.permitAll()
			.antMatchers("/forgot-password/**")
				.anonymous()
			.antMatchers("/**")
				.authenticated()
				
			.and()
			.formLogin()
				.loginPage("/login")
				.permitAll()
			.and()
			.logout()
				.logoutUrl("/logout")
				.permitAll()
			.and()
				.exceptionHandling()
					.accessDeniedPage("/access-denied");
	}

	@Bean(name="messageSource")
	public ResourceBundleMessageSource getMessageSource() {
		
		ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
		resourceBundleMessageSource.setBasename("readers-message");
		
		return resourceBundleMessageSource;
	}
	
	/*private PasswordEncoder getPasswordEncoder() {
		return new PasswordEncoder() {
			
			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				// TODO Auto-generated method stub
				return rawPassword.equals(encodedPassword);
			}
			
			@Override
			public String encode(CharSequence rawPassword) {
				// TODO Auto-generated method stub
				return rawPassword.toString();
			}
		};
	}*/

	
}
