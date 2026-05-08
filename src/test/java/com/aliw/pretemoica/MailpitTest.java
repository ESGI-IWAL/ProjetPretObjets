package com.aliw.pretemoica;

import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import org.junit.jupiter.api.Test;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

class MailpitTest {

    @Test
    void testSendEmail() {
        assumeTrue(isMailpitAvailable(), "Mailpit n'est pas démarré sur localhost:1025");

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("localhost");
        mailSender.setPort(1025);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@pretemoica.com");
        message.setTo("test@example.com");
        message.setSubject("Test Mailpit");
        message.setText("Ceci est un test depuis un test unitaire simple");

        assertDoesNotThrow(() -> mailSender.send(message));

        System.out.println("Email envoyé ! Vérifiez Mailpit sur http://localhost:8025");
    }

    private boolean isMailpitAvailable() {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress("localhost", 1025), 500);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
}