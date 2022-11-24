package com.github.maciejiwan;


import com.github.maciejiwan.emailSender.EmailSender;
import com.github.maciejiwan.emailSender.JavaxEmailSender;
import com.github.maciejiwan.emailSender.RawEmailSender;
import com.github.maciejiwan.emailSender.entity.Email;
import com.github.maciejiwan.emailSender.exception.SendEmailException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);
    private static final HashMap<String, EmailSender> emailSenders = new HashMap<>();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {

            initSenders();
            Email email = getEmail();
            String senderId = chooseEmailSender();
            sendEmailBySender(senderId, email);

        } catch (SendEmailException e) {
            logger.error(e.getMessage());
            System.out.println("The application crashed");
        }
    }

    private static String chooseEmailSender() {
        return getInput("Choose sender type. \n (1) RawSocket \n (2) Javax \n");
    }

    private static void sendEmailBySender(String senderId, Email email) throws SendEmailException {
        System.out.println("trying to send email");
        emailSenders.get(senderId).sendEmail(email);
        System.out.println("Email has been sent!");
    }

    private static void initSenders() {
        emailSenders.put("1", new RawEmailSender());
        emailSenders.put("2", new JavaxEmailSender());
    }

    private static Email getEmail() {
        return Email.builder()
                .authorEmail(emailSenders.get("1").getServerCredentials().getEmail())
                .recipientEmail(getInput("RecipientEmail: "))
                .subject("test email from Maciej Iwan Client")
                .textContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Phasellus odio lorem, elementum at felis in, congue eleifend sem. Nunc blandit ipsum sit amet lobortis luctus. Sed suscipit efficitur ante, vel ornare eros semper sit amet. Aenean vel efficitur ante, a molestie metus. Praesent a turpis lacinia, bibendum libero sed, pellentesque nulla. Vivamus id diam ut elit convallis faucibus. In a pharetra nisi. Sed ultrices viverra ultricies. Suspendisse rhoncus mollis tellus, ac viverra est ornare eget. Praesent laoreet odio eu hendrerit convallis. Morbi luctus, quam eget sodales suscipit, metus elit pretium nulla, vitae faucibus sem justo ac odio. Nam felis mi, condimentum quis sapien in, tincidunt ultricies elit. Phasellus nibh tellus, rhoncus id rhoncus nec, bibendum sagittis risus.")
                .build();
    }

    private static String getInput(String message) {
        System.out.println(message);
        return scanner.nextLine();
    }

}