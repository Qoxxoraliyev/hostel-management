package com.hostel.hostel.management.service;

import com.hostel.hostel.management.entity.User;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;

@Service
public class MailService {

    private static final Logger log = LoggerFactory.getLogger(MailService.class);

    private final JavaMailSender mailSender;

    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendActivationEmail(User user) {
        String subject = "Account activation";
        String activationUrl =
                "http://localhost:8080/api/activate?key=" + user.getActivationKey();

        String content = """
                Hello %s,

                Please activate your account using the link below:
                %s
                """.formatted(user.getLogin(), activationUrl);

        sendEmail(user.getEmail(), subject, content, false);
    }

    @Async
    public void sendPasswordResetMail(User user) {
        String subject = "Password Reset Request";
        String resetUrl = "http://localhost:8080/reset-password?key=" + user.getResetKey();

        String content = """
            Hello %s,

            You requested a password reset. Click the link below to reset your password:
            %s
            """.formatted(user.getLogin(), resetUrl);

        sendEmail(user.getEmail(), subject, content, false);
    }


    @Async
    public void sendEmail(String to, String subject, String content, boolean isHtml) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper =
                    new MimeMessageHelper(message, false, StandardCharsets.UTF_8.name());

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, isHtml);
            helper.setFrom("noreply@hostel.uz");

            mailSender.send(message);
            log.info("Email sent to {}", to);

        } catch (MailException | MessagingException e) {
            log.error("Failed to send email to {}", to, e);
        }
    }
}
