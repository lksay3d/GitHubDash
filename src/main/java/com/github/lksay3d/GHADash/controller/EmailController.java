package com.github.lksay3d.GHADash.controller;

import com.github.lksay3d.GHADash.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {

    private final EmailService emailService;

    @Autowired
    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping("/sendEmail")
    public String sendEmail(@RequestParam(name = "to", required = true) String to, 
        @RequestParam(name = "subject", required = true) String subject, 
            @RequestParam(name = "text", required = true) String text) 
            {
                emailService.sendSimpleMessage(to, subject, text);
                return "Email sent successfully";
            }

}