package com.github.maciejiwan.emailSender;

import com.github.maciejiwan.emailSender.entity.Email;
import com.github.maciejiwan.emailSender.entity.Message;
import com.github.maciejiwan.emailSender.entity.PasswordAuth;
import com.github.maciejiwan.emailSender.exception.SendEmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RawEmailSender extends EmailSender {

    private static final Logger logger = LoggerFactory.getLogger(RawEmailSender.class);

    private final Transfer transfer;

    public RawEmailSender() {
        this.transfer = new Transfer(getServerCredentials());
    }

    @Override
    public void sendEmail(Email email) throws SendEmailException {
        try {
            Message message = createMessage(email, getPasswordAuthentication());
            transfer.openConnection();
            transfer.sendMessageToSmtpServer(message);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new SendEmailException(e.getMessage());
        } finally {
            transfer.closeConnection();
        }
    }

    private PasswordAuth getPasswordAuthentication() {
        return new PasswordAuth(getServerCredentials().getEmail(), getServerCredentials().getEmailPassword());
    }

    private Message createMessage(Email email, PasswordAuth passwordAuth) {
        Message message = new Message(passwordAuth);

        message.setFrom(email.getAuthorEmail());
        message.setTo(email.getRecipientEmail());
        message.setSubject(email.getSubject());
        message.setTextContent(email.getTextContent());

        return message;
    }

}
