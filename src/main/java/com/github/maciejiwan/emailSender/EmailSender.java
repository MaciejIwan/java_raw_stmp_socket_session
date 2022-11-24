package com.github.maciejiwan.emailSender;

import com.github.maciejiwan.emailSender.entity.Email;
import com.github.maciejiwan.emailSender.entity.MailServerCredentials;
import com.github.maciejiwan.emailSender.exception.SendEmailException;
import com.google.gson.Gson;
import lombok.Getter;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Objects;

public abstract class EmailSender {
    private static final String CREDENTIALS_DEFAULT_FILENAME = "credentials.json";
    @Getter
    private final MailServerCredentials serverCredentials;

    EmailSender() {
        this(CREDENTIALS_DEFAULT_FILENAME);
    }

    public EmailSender(String credentialsFilename) {
        String credentialPath = Objects.requireNonNull(getClass().getClassLoader().getResource(credentialsFilename)).getPath();
        serverCredentials = loadEmailServerCredentials(credentialPath);
    }

    public MailServerCredentials loadEmailServerCredentials(String credentialsFilePath) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(credentialsFilePath));
            Gson gson = new Gson();
            return gson.fromJson(bufferedReader, MailServerCredentials.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void sendEmail(Email email) throws SendEmailException;
}
