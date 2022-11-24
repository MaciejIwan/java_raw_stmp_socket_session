package com.github.maciejiwan.emailSender;

import com.github.maciejiwan.emailSender.entity.Email;
import com.github.maciejiwan.emailSender.exception.SendEmailException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class JavaxEmailSender extends EmailSender {
    private final Properties properties;
    private final Session session;

    public JavaxEmailSender() {
        properties = getProperties();
        session = getSession();
    }

    public JavaxEmailSender(String credentialsPath) {
        super(credentialsPath);
        properties = getProperties();
        session = getSession();
    }

    private Properties getProperties() {
        Properties properties = new Properties();

        properties.put("mail.smtp.host", getServerCredentials().getSmtpHost());
        properties.put("mail.smtp.port", getServerCredentials().getSmtpPort());
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.starttls.required", "true");
        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        return properties;
    }

    private Session getSession() {
        return Session.getDefaultInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(getServerCredentials().getEmail(), getServerCredentials().getEmailPassword());
            }
        });
    }

    @Override
    public void sendEmail(Email email) throws SendEmailException {
        try {
            MimeMessage message = createMessage(email);
            Transport.send(message);
            System.out.println("Email sent");
        } catch (MessagingException e) {
            throw new SendEmailException(e.getMessage());
        }
    }

    private MimeMessage createMessage(Email email) throws MessagingException {
        MimeMessage message = new MimeMessage(this.session);
        message.setFrom(new InternetAddress(email.getAuthorEmail()));
        message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(email.getRecipientEmail()));
        message.setSubject(email.getSubject());
        message.setText(email.getTextContent());
        return message;
    }
}
