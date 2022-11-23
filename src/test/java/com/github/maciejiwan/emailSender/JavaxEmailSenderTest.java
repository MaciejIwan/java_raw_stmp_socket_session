package com.github.maciejiwan.emailSender;

import com.github.maciejiwan.emailSender.entity.Email;
import com.github.maciejiwan.emailSender.EmailSender;
import com.github.maciejiwan.emailSender.JavaxEmailSender;
import com.github.maciejiwan.emailSender.exception.SendEmailException;
import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class JavaxEmailSenderTest {

    String credentialPath = Objects.requireNonNull(getClass().getClassLoader().getResource("credentials.json")).getPath();
    @Test
    public void itShouldSendEmailToRealInbox() throws SendEmailException {
        // given
        EmailSender javaxEmailSender = new JavaxEmailSender(credentialPath);
        Email email = new Email("maciejiwan@op.pl", "maciejiwan@op.pl", "Testing Subject", "Test Mail");

        //when then
        javaxEmailSender.sendEmail(email);
    }

    @Test
    public void itShouldThrowInvalidAddressException() {
        // given
        EmailSender javaxEmailSender = new JavaxEmailSender(credentialPath);
        Email email = new Email("maciejiwan", "maciejiwan@op.pl", "Testing Subject", "Test Mail");

        //when
        Throwable thrown = catchThrowable(() -> javaxEmailSender.sendEmail(email));

        // then
        assertThat(thrown)
                .isInstanceOf(SendEmailException.class)
                .hasMessageContaining("Invalid Addresses");
    }
}