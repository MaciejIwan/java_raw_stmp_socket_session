package com.github.maciejiwan.emailSender;

import com.github.maciejiwan.emailSender.entity.Email;
import com.github.maciejiwan.emailSender.exception.SendEmailException;
import org.junit.jupiter.api.Test;

class RawEmailSenderTest {

    @Test
    void sendEmailTest() throws SendEmailException {
        EmailSender emailSender = new RawEmailSender();
        Email email = new Email(emailSender.getServerCredentials().getEmail(), emailSender.getServerCredentials().getEmail(), "Raw STMP test email", "Hello world its my first email. Lorem impsum, ipsum lorem i takie sprawy");

        //when then
        emailSender.sendEmail(email);
    }
}