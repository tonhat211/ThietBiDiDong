package service;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Properties;

public class EmailTest {
    public static void main(String[] args) {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", true);
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "sandbox.smtp.mailtrap.io");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "sandbox.smtp.mailtrap.io");


        Session session = Session.getInstance(prop, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("2003tonhat@gmail.com", "qfhb jdun rmec owlz");
            }
        });

        Message message = new MimeMessage(session);
        try {
            message.setFrom(new InternetAddress("2003tonhat@gmail.com"));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        try {
            message.setRecipients(
                    Message.RecipientType.TO, InternetAddress.parse("21130463@st.hcmuaf.edu.vn"));
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        try {
            message.setSubject("Mail Subject");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        String msg = "This is my first email using JavaMailer";

        MimeBodyPart mimeBodyPart = new MimeBodyPart();
        try {
            mimeBodyPart.setContent(msg, "text/html; charset=utf-8");
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        Multipart multipart = new MimeMultipart();
        try {
            multipart.addBodyPart(mimeBodyPart);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        try {
            message.setContent(multipart);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        try {
            Transport.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }


}
