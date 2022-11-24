package com.github.maciejiwan.emailSender.entity;

import lombok.Getter;
import lombok.Setter;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class PasswordAuth {
    @Getter
    @Setter
    private String username;

    @Getter
    @Setter
    private String password;

    public PasswordAuth(String username, String password) {
        setUsername(username);
        setPassword(password);
    }

    private String toBase64String(String text) {
        return Base64.getEncoder().encodeToString(
                text.getBytes(StandardCharsets.UTF_8)
        );
    }

    public String getUsernameAsBase64String() {
        return toBase64String(getUsername());
    }

    public String getPasswordAsBase64String() {
        return toBase64String(getPassword());
    }
}
