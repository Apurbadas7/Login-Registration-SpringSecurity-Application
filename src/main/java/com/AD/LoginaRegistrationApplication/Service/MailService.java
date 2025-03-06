package com.AD.LoginaRegistrationApplication.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailService {

    @Autowired
    JavaMailSender javaMailSender;

    @Value("$(spring.mail.username)")
    private String emailFromId;

    public boolean sendMail(String recipient, String body, String subject) {
        try {
            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setFrom(emailFromId);
            mailMessage.setTo(recipient);
            mailMessage.setText(body);
            mailMessage.setSubject(subject);

            javaMailSender.send(mailMessage);
            System.out.println("Mail sent successfully to: " + recipient);
            return true;
        } catch (Exception e) {
            System.out.println("Error while sending mail: " + e.getMessage());
            return false;
        }
    }

}
