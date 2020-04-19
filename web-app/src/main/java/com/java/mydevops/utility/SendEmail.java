package com.java.mydevops.utility;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.java.mydevops.dto.EmailDto;

public class SendEmail {
public SendEmail() {}
	
	public void userApprovedEmail(EmailDto emailDto) {
		
		HtmlEmail email = new HtmlEmail();
		try {
		email.setHostName("smtp.gmail.com");
		email.setSmtpPort(587);
		email.setAuthenticator(new DefaultAuthenticator("webfantasyipl@gmail.com", "webtoolstest"));
		email.setSSLOnConnect(false);
		email.setStartTLSEnabled(true);
		//From:
		email.setFrom("webfantasyipl@gmail.com");
		//To:
		email.addTo(emailDto.getEmail());
		//Subject:
		email.setSubject("Feedback recieved");
		// set the html message
		email.setHtmlMsg("<html><body><h2>Hello "+emailDto.getEmail()+"</h2><br><br><h3>We appreciate your feedback, Thank you</h3><br>Reply:<br>"+emailDto.getResponse()+"<br>Best regards</body></html>");
        email.send();
		} catch (EmailException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
