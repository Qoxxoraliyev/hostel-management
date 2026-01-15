package com.hostel.hostel.management.service.impl;

import com.hostel.hostel.management.entity.User;
import com.hostel.hostel.management.service.MailService;
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
import java.util.List;

@Service
public class MailServiceImpl implements MailService {

    private static final Logger log = LoggerFactory.getLogger(MailServiceImpl.class);

    private final JavaMailSender mailSender;

    public MailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }



    @Override
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


    @Override
    @Async
    public void sendCreationEmail(User user) {

        String subject = "Your account has been created";

        String content = """
                Hello %s,

                Your account has been created by admin.

                Login: %s
                Please activate your account using the activation email.

                Regards,
                Hostel Management
                """.formatted(user.getLogin(), user.getLogin());

        sendEmail(user.getEmail(), subject, content, false);
    }


    @Override
    @Async
    public void sendPasswordResetMail(User user) {

        String subject = "Password Reset Request";
        String resetUrl =
                "http://localhost:8080/reset-password?key=" + user.getResetKey();

        String content = """
                Hello %s,

                You requested a password reset. Click the link below to reset your password:
                %s
                """.formatted(user.getLogin(), resetUrl);

        sendEmail(user.getEmail(), subject, content, false);
    }



    @Override
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


    @Override
    @Async
    public void sendBulkEmail(List<User> users, String subject, String content) {
        users.parallelStream().forEach(user -> {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper =
                        new MimeMessageHelper(message, false, StandardCharsets.UTF_8.name());

                helper.setTo(user.getEmail());
                helper.setSubject(subject);
                helper.setText(content, false);
                helper.setFrom("noreply@hostel.uz");

                mailSender.send(message);
                log.info("Email sent to {}", user.getEmail());

            } catch (MailException | MessagingException e) {
                log.error("Failed to send email to {}", user.getEmail(), e);
            }
        });
    }


}
