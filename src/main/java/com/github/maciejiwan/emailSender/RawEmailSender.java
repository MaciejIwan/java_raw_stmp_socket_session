package com.github.maciejiwan.emailSender;

import com.github.maciejiwan.emailSender.entity.Email;
import com.github.maciejiwan.emailSender.entity.Message;
import com.github.maciejiwan.emailSender.entity.PasswordAuthenticator;
import com.github.maciejiwan.emailSender.exception.SendEmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RawEmailSender extends EmailSender {

    private static final Logger logger = LoggerFactory.getLogger(RawEmailSender.class);

    private final serverConnectionHandler serverConnectionHandler;

    public RawEmailSender() {
        this.serverConnectionHandler = new serverConnectionHandler();
    }

    @Override
    public void sendEmail(Email email) throws SendEmailException {
        try {
            Message message = createMessage(email, getPasswordAuthentication());
            serverConnectionHandler.openConnection();
            serverConnectionHandler.sendMessageToSmtpServer(message);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new SendEmailException(e.getMessage());
        } finally {
            serverConnectionHandler.closeConnection();
        }
    }

    private PasswordAuthenticator getPasswordAuthentication() {
        return new PasswordAuthenticator(getServerCredentials().getEmail(), getServerCredentials().getEmailPassword());
    }

    private Message createMessage(Email email, PasswordAuthenticator passwordAuthenticator) {
        Message message = new Message(passwordAuthenticator);

        message.setFrom(email.getAuthorEmail());
        message.setTo(email.getRecipientEmail());
        message.setSubject(email.getSubject());
        message.setTextContent(email.getTextContent());

        return message;
    }

    class serverConnectionHandler {
        private static final Logger logger = LoggerFactory.getLogger(serverConnectionHandler.class);
        private DataOutputStream dataOutputStream = null;
        private BufferedReader bufferedInputReader = null;
        private SSLSocket sslSocket = null;
        private boolean isConnectionOpen = false;
        private static final Pattern statusCodePattern = Pattern.compile("(\\d+).*");


        public serverConnectionHandler() {
        }

        void openConnection() throws ConnectException {
            SSLSocketFactory sslSocketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
            try {
                sslSocket = (SSLSocket) sslSocketFactory.createSocket(getServerCredentials().getSmtpHost(), getServerCredentials().getSmtpPort());
                bufferedInputReader = new BufferedReader(new InputStreamReader(sslSocket.getInputStream()));
                dataOutputStream = new DataOutputStream(sslSocket.getOutputStream());
                isConnectionOpen = true;
            } catch (IOException e) {
                throw new ConnectException(e.getMessage());
            }

        }

        public void sendMessageToSmtpServer(Message message) throws SendEmailException {

            try {

                sendRequest("EHLO ", getServerCredentials().getSmtpHost(), 9);

                sendRequest("AUTH LOGIN", 1);
                sendRequest(message.getPasswordAuthenticator().getUsernameAsBase64String(), 1);
                sendRequest(message.getPasswordAuthenticator().getPasswordAsBase64String(), 1);

                sendRequest("MAIL FROM:", message.getFrom(), 1);

                sendRequest("RCPT TO:", message.getTo(), 1);

                sendRequest("DATA", 1);
                sendRequest("Subject:", message.getSubject(), 0);
                sendRequest(message.getTextContent(), 0);
                sendRequest(".", 0);

                sendRequest("QUIT", 1);
            } catch (IOException e) {
                throw new SendEmailException(e.getMessage());
            }

        }

        private void sendRequest(String message, int exceptedServerResponses) throws IOException, SendEmailException {
            sendRequest("", message, exceptedServerResponses);
        }

        private void sendRequest(String label, String message, int exceptedServerResponses) throws IOException, SendEmailException {
            if (!isConnectionOpen)
                throw new SendEmailException("Connection should be open before mail sending");

            String command = label + message + "\r\n";
            dataOutputStream.writeBytes(command);
            logger.info("CLIENT: " + command);

            for (int i = 0; i < exceptedServerResponses; i++) {
                String serverResponse = bufferedInputReader.readLine();
                logger.info("SERVER : " + serverResponse);
                validateServerResponse(serverResponse);
            }
        }

        private void validateServerResponse(String serverResponse) throws SendEmailException {
            Matcher m = statusCodePattern.matcher(serverResponse);
            m.find();
            int statusCode = Integer.parseInt(m.group(1));

            if (statusCode < 200 || statusCode >= 400)
                throw new SendEmailException("server error");
        }

        public void closeConnection() {


            if (dataOutputStream != null) {
                try {
                    dataOutputStream.close();
                    dataOutputStream = null;
                } catch (IOException e) {
                }
            }

            if (bufferedInputReader != null) {
                try {
                    bufferedInputReader.close();
                    bufferedInputReader = null;
                } catch (IOException e) {
                }
            }

            if (sslSocket != null) {
                try {
                    sslSocket.close();
                    sslSocket = null;
                } catch (IOException e) {
                }
            }
            isConnectionOpen = false;
        }

    }
}
