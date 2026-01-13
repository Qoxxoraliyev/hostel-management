package com.hostel.hostel.management.service;

import com.hostel.hostel.management.entity.User;

public interface MailService {

    void sendActivationEmail(User user);

    void sendCreationEmail(User user);

    void sendPasswordResetMail(User user);

    void sendEmail(String to, String subject, String content, boolean isHtml);

}
