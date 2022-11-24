package com.github.maciejiwan.emailSender.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Properties;

@Getter
@Setter
public
class Message {

    private Properties properties;
    private final PasswordAuth passwordAuth;
    private String from;
    private String textContent;
    private String subject;
    private String to;

    public void setFrom(String from) {
        this.from = "<" + from + ">";
    }

    public void setTo(String to) {
        this.to = "<" + to + ">";
    }

    public Message(PasswordAuth passwordAuth) {
        this.passwordAuth = passwordAuth;
    }
}
