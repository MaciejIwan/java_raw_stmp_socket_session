package com.github.maciejiwan.emailSender;

import com.github.maciejiwan.emailSender.entity.Email;
import com.github.maciejiwan.emailSender.entity.MailServerCredentials;
import com.github.maciejiwan.emailSender.exception.SendEmailException;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

class EmailSenderTest {

    @Test
    void itShouldLoadEmailServerCredentials() {
        //given
        MailServerCredentials expectedCredentials = MailServerCredentials.builder()
                .email("email@domain.com")
                .emailPassword("password123")
                .smtpHost("smtp.poczta.onet.pl")
                .smtpPort(465)
                .build();


        //when
        EmailSender emailSender = new EmailSender("credentials.json.example") {
            @Override
            public void sendEmail(Email email) throws SendEmailException {
            }
        };
        MailServerCredentials actualCredentials = emailSender.getServerCredentials();

        //then
        assertThat(actualCredentials).isEqualTo(expectedCredentials);
    }
}