package com.springCommunity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NaverEmailService {

    @Autowired
    @Qualifier("naverMailSender") 
    private JavaMailSender mailSender;  

    public void sendNaverEmail(String to, String subject, String content) throws MailException {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);
        message.setFrom("gyr0204@naver.com"); 

        mailSender.send(message);  // 메일 발송
    }
}