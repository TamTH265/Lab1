/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tam.supportMethods;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author hoang
 */
public class EmailSending {

    private final String userEmail;

    public EmailSending(String userEmail) {
        this.userEmail = userEmail;
    }

    public boolean sendMail(int randomVerifyingCode) {
        boolean isSuccess = false;
        String email = "anhlapronevn33@gmail.com";
        String password = "Kaikunihiko265";

        Properties theProperties = new Properties();

        theProperties.put("mail.smtp.auth", "true");
        theProperties.put("mail.smtp.starttls.enable", "true");
        theProperties.put("mail.smtp.host", "smtp.gmail.com");
        theProperties.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(theProperties, new javax.mail.Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(email, password);
            }
        });

        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(email));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(userEmail));
            message.setSubject("Email Verification Code");
            message.setText("Your verifying code: " + randomVerifyingCode);

            Transport.send(message);
            isSuccess = true;

        } catch (MessagingException e) {
            System.out.println("Error at SendingEmail.java: " + e);
        }
        return isSuccess;
    }
}
