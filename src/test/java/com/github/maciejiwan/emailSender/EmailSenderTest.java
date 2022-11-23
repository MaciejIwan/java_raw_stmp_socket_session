package com.github.maciejiwan.emailSender;

import com.github.maciejiwan.emailSender.entity.Email;
import com.github.maciejiwan.emailSender.entity.MailServerCredentials;
import com.github.maciejiwan.emailSender.exception.SendEmailException;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

class EmailSenderTest {

    @Test
    void itShouldLoadEmailServerCredentials() {
        //given
        MailServerCredentials expectedCredentials = MailServerCredentials.builder()
                .email("email@domain.com")
                .emailPassword("password123")
                .smtpHost("smtp.poczta.onet.pl")
                .smtpPort("465")
                .build();
        String credentialPath = Objects.requireNonNull(getClass().getClassLoader().getResource("credentials.json.example")).getPath();

        //when
        EmailSender emailSender = new EmailSender(credentialPath) {@Override public void sendEmail(Email email) throws SendEmailException {}};
        System.out.println(emailSender.serverCredentials.getEmail());

        //then
        assertThat(emailSender.serverCredentials).isEqualTo(expectedCredentials);
    }
}