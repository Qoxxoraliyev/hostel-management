package com.hostel.hostel.management.service;

import com.hostel.hostel.management.entity.User;

import java.util.List;

public interface MailService {

    void sendActivationEmail(User user);

    void sendCreationEmail(User user);

    void sendPasswordResetMail(User user);

    void sendEmail(String to, String subject, String content, boolean isHtml);

    void sendBulkEmail(List<User> users,String subject,String content);

}
