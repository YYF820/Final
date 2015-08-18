package ua.nure.hanzha.SummaryTask4.mail;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 18/08/15.
 */
public class MailHelperSupport {

    private static final String password = "123456789";
    private static final String mailSupport = "University@support.com";

    public static void sendMail(String subject, String message, String username) throws MessagingException {
        MimeMessage msg = new MimeMessage(getSession(username));
        msg.setFrom(new InternetAddress(username));
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailSupport));
        msg.setSubject(subject, "UTF-8");
        msg.setHeader("Content-Type", "text/plain; charset=\"utf-8\"");
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setHeader("Content-Type", "text/html; charset=\"utf-8\"");
        msg.setText(message, "UTF-8");
        Transport.send(msg);
    }

    private static Session getSession(final String username) {
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
        properties.put("mail.smtp.port", "12346");
        properties.put("mail.mime.charset", "UTF-8");
        return properties;
    }
}
