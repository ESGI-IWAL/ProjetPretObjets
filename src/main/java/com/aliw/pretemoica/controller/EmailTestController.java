package com.aliw.pretemoica.controller;

import org.springframework.web.bind.annotation.*;
import com.aliw.pretemoica.service.EmailService;

@RestController
@RequestMapping("/api/test")
public class EmailTestController {

    private final EmailService emailService;

    public EmailTestController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/send-email")
    public String sendTestEmail(@RequestParam String email) {
        emailService.sendTestEmail(
                email,
                "Test Email",
                "Ceci est un email de test depuis Pretemoica"
        );
        return "Email envoyé ! Vérifiez Mailpit sur http://localhost:8025";
    }
}
