package com.github.maciejiwan;

import com.github.maciejiwan.emailSender.Email;
import com.github.maciejiwan.emailSender.EmailSender;
import com.github.maciejiwan.emailSender.JavaxEmailSender;
import com.github.maciejiwan.emailSender.exception.SendEmailException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;

class JavaxEmailSenderTest {


    @Test
    public void itShouldSendEmailToRealInbox() throws SendEmailException {
        // given
        EmailSender javaxEmailSender = new JavaxEmailSender();
        Email email = new Email("maciejiwan@op.pl", "Testing Subject", "Test Mail");

        //when then
        javaxEmailSender.sendEmail(email);
    }

    @Test
    public void itShouldThrowInvalidAddressException() {
        // given
        EmailSender javaxEmailSender = new JavaxEmailSender();
        Email email = new Email("maciejiwan", "Testing Subject", "Test Mail");

        //when
        Throwable thrown = catchThrowable(() -> javaxEmailSender.sendEmail(email));

        // then
        assertThat(thrown)
                .isInstanceOf(SendEmailException.class)
                .hasMessageContaining("Invalid Addresses");
    }
}