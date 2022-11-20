package com.github.maciejiwan.emailSender;

import com.github.maciejiwan.emailSender.exception.SendEmailException;

public abstract class EmailSender {
    protected static final String EMAIL = "";
    protected static final String EMAIL_PASSWORD = "";
    protected static final String SMTP_HOST = "smtp.poczta.onet.pl";
    protected static final String SMTP_PORT = "465";

    public abstract void sendEmail(Email email) throws SendEmailException;
}
