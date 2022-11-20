package com.github.maciejiwan;

import lombok.Data;

@Data
public class Email {
    private String authorEmail;
    private String recipientEmail;
    private String subject;
    private String textContent;

    public Email(String recipientEmail, String subject, String textContent) {
        this.authorEmail = EmailSender.EMAIL;
        this.recipientEmail = recipientEmail;
        this.subject = subject;
        this.textContent = textContent;
    }
}
