package com.java.eventsystem.tddeventsystem;

public interface EmailService {
    void sendEmail(String recipientEmail, String subject, String body);
}