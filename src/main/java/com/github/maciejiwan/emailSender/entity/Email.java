package com.github.maciejiwan.emailSender.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class Email {
    private String authorEmail;
    private String recipientEmail;
    private String subject;
    private String textContent;
}
