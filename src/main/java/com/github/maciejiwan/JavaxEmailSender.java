package com.github.maciejiwan;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class JavaxEmailSender extends EmailSender {
    private Properties properties;
    private Session session;

    public JavaxEmailSender() {
        properties = getProperties();
        session = getSession();
    }

    private static Properties getProperties() {
        Properties properties = new Properties();

        properties.put("mail.smtp.host", SMTP_HOST);
        properties.put("mail.smtp.port", SMTP_PORT);
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
                return new PasswordAuthentication(EMAIL, EMAIL_PASSWORD);
            }
        });
    }

    @Override
    public void sendEmail(Email email) throws SendEmailException {
        try {
            Message message = createMessage(email);
            Transport.send(message);
            System.out.println("Email sent");
        } catch (MessagingException e) {
            //todo deal with Exception better
            throw new SendEmailException(e.getMessage());
        }
    }

    private Message createMessage(Email email) throws MessagingException {
        Message message = new MimeMessage(this.session);
        message.setFrom(new InternetAddress(EMAIL));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(email.getRecipientEmail()));
        message.setSubject(email.getSubject());
        message.setText(email.getTextContent());
        return message;
    }
}
