package com.vdb.controller;

import com.vdb.model.Email;
import com.vdb.service.EmailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@Slf4j
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody Email email)
     {
         emailService.sendEmail(email);

         log.info("@@@@@@@@@@@@@Trying to send email {}", email.getEmailBody());

         return ResponseEntity.ok("Email Sent Successfully");
     }

}
