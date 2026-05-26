package com.tejastomar.journalapp.service;

import com.tejastomar.journalapp.services.EmailService;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class EmailServiceTests {

    @Autowired
    private EmailService emailService;

    @Test
    void testSendMail(){
        emailService.sendEmail("tomar.tejas28@gmail.com","Testing java mail sender", "aap kaise hain?");
    }
}
