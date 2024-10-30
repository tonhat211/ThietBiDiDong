package service;


import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.sql.DataSource;
import java.util.Properties;

public class EmailService implements IJavaMail {
    @Override
    public boolean send(String to, String subject, String messageContent) {
        // Get properties object
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true"); // Bật TLS
        props.put("mail.smtp.host", EmailProperty.HOST_NAME); // Tên máy chủ, ví dụ: smtp.gmail.com
//        props.put("mail.smtp.port", EmailProperty.TLS_PORT); // Cổng TLS
        props.put("mail.smtp.port", 587); // Cổng TLS

        // get Session
        Session session = javax.mail.Session.getDefaultInstance(props, new javax.mail.Authenticator() {
            protected javax.mail.PasswordAuthentication getPasswordAuthentication()  {
                return new javax.mail.PasswordAuthentication(EmailProperty.APP_EMAIL,EmailProperty.APP_PASSWORD);
            }
        });

        // compose message
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(EmailProperty.APP_EMAIL));
            ((MimeMessage) message).setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(messageContent);
//            message.setContent(messageContent, "text/html");

            // send message
            Transport.send(message);
            return true;
        } catch (javax.mail.MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        String to ="21130463@st.hcmuaf.edu.vn";
        String subject="Đăng ký nhận thư thành công";
//        String message = "<!DOCTYPE html>\n" +
//                "<html lang=\"en\">\n" +
//                "<head>\n" +
//                "    <meta charset=\"UTF-8\">\n" +
//                "    <title>Mail</title>\n" +
//                "</head>\n" +
//                "<body>\n" +
//                "<div style=\"text-align: center; font-family: 'Roboto', sans-serif;\">\n" +
//                "    <h1 style=\"color: #ffc021\">ThietBiDiDong</h1>\n" +
//                "    <p>Xin chào, mã xác minh tài khoản ThietBiDiDong của bạn là</p>\n" +
//                "    <h2 style=\"color: #ff623d\">1 2 3 4 5</h2>\n" +
//                "    <p>Có hiệu lực trong vòng 5 phút, KHÔNG chia sẻ mã này với bất kì ai, kể cả nhân viên ThietBiDiDong</p>\n" +
//                "</div>\n" +
//                "</body>\n" +
//                "</html>";
        String message = " hello";
        IJavaMail emailService = new EmailService();
        emailService.send(to,subject,message);
    }
}


