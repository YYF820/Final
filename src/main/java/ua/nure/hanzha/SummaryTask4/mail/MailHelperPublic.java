package ua.nure.hanzha.SummaryTask4.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 29/07/15.
 */
public class MailHelperPublic {

    private static final String username = "support@university.com";
    private static final String password = "t9dnb2mq";

    public static void sendMail(String mail, String subject, String message) throws MessagingException {
        MimeMessage msg = new MimeMessage(getSession());
        msg.setFrom(new InternetAddress(username));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
        msg.setSubject(subject, "UTF-8");
        msg.setHeader("Content-Type", "text/plain; charset=\"utf-8\"");
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setHeader("Content-Type", "text/html; charset=\"utf-8\"");
        msg.setText(message, "UTF-8");
        Transport.send(msg);
    }

    private static Session getSession() {
        return Session.getInstance(getProperties(),
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.transport.protocol", "smtp");
        properties.put("mail.smtp.host", "127.0.0.1");
        properties.put("mail.smtp.port", "12345");
        properties.put("mail.mime.charset", "UTF-8");
        return properties;
    }

}
