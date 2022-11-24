package com.github.maciejiwan.emailSender.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MailServerCredentials {
    private String email;
    private String emailPassword;
    private String smtpHost;
    private Integer smtpPort;
}
