package com.springCommunity.scheduler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.springCommunity.service.AdminService;
import com.springCommunity.service.NaverEmailService;
import com.springCommunity.vo.EmailVO;

@Component
public class EmailScheduler {
	@Autowired
    private AdminService adminService;

    @Autowired
    private NaverEmailService naverEmailService;

    
    //@Scheduled(cron = "0 0/1 * * * ?")
    /*
    @Scheduled(fixedRate = 1000) 
    public void log() {
        System.out.println("스케줄러 실행 확인: " + LocalDateTime.now());
    }
    */
    //@Scheduled(fixedRate = 5000) 
    @Scheduled(cron = "0 0/1 * * * ?")
    public void email() {
    	System.out.println("이메일 스케쥴러 실행");
        List<EmailVO> unSendEmails = adminService.selectEmails();
        
        if(unSendEmails == null || unSendEmails.isEmpty()) {
            System.out.println("발송할 이메일이 없습니다.");
            return;
        }
        
        for(EmailVO email : unSendEmails) {
            try {
                naverEmailService.sendNaverEmail(
                		  email.getEmail_to()
                		, email.getEmail_title()
                		, email.getEmail_content());
                email.setEmail_state(1);
                System.out.println("이메일스케쥴러 발송 성공");
            }catch (MailException e) {
                e.printStackTrace();
                email.setEmail_state(2);
                System.out.println("이메일스케쥴러 발송 실패");
            }
            adminService.updateEmailState(email);
        }
    }
}