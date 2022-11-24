package com.github.maciejiwan.emailSender;

import com.github.maciejiwan.emailSender.entity.Email;
import com.github.maciejiwan.emailSender.exception.SendEmailException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class JavaxEmailSenderTest {

    @Test
    public void itShouldSendEmailToRealInbox() throws SendEmailException {
        // given
        EmailSender javaxEmailSender = new JavaxEmailSender();
        Email email = new Email(javaxEmailSender.getServerCredentials().getEmail(), javaxEmailSender.getServerCredentials().getEmail(), "Javax test email", "Hello world its my first email. Lorem impsum, ipsum lorem i takie sprawy");

        //when then
        javaxEmailSender.sendEmail(email);
    }

    @Test
    public void itShouldThrowInvalidAddressException() {
        // given
        EmailSender javaxEmailSender = new JavaxEmailSender();
        Email email = new Email("maciejiwan", javaxEmailSender.getServerCredentials().getEmail(), "Javax test email", "Hello world its my first email. Lorem impsum, ipsum lorem i takie sprawy");

        //when
        Throwable thrown = catchThrowable(() -> javaxEmailSender.sendEmail(email));

        // then
        assertThat(thrown)
                .isInstanceOf(SendEmailException.class)
                .hasMessageContaining("Invalid Addresses");
    }
}