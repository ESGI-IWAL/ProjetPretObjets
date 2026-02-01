package com.aliw.pretemoica;

import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

class MailpitTest {

    @Test
    void testSendEmail() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("localhost");
        mailSender.setPort(1025);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@pretemoica.com");
        message.setTo("test@example.com");
        message.setSubject("Test Mailpit");
        message.setText("Ceci est un test depuis un test unitaire simple");

        mailSender.send(message);

        System.out.println("Email envoyé ! Vérifiez Mailpit sur http://localhost:8025");
    }
}