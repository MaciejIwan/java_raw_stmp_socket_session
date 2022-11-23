package com.github.maciejiwan.emailSender;

import com.github.maciejiwan.emailSender.entity.Email;
import com.github.maciejiwan.emailSender.entity.MailServerCredentials;
import com.github.maciejiwan.emailSender.exception.SendEmailException;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public abstract class EmailSender {
    protected MailServerCredentials serverCredentials;

    EmailSender(String credentialsFilePath) {
        loadEmailServerCredentials(credentialsFilePath);
    }

    public void loadEmailServerCredentials(String credentialsFilePath) {
        try {

            BufferedReader bufferedReader = new BufferedReader(new FileReader(credentialsFilePath));
            Gson gson = new Gson();
            this.serverCredentials = gson.fromJson(bufferedReader, MailServerCredentials.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void sendEmail(Email email) throws SendEmailException;
}
