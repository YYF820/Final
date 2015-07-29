package ua.nure.hanzha.SummaryTask4.mail;

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

/**
 * Created by faffi-ubuntu on 29/07/15.
 */
public class MailHelper {

    static final String username = "serverApp@summarytask4.epam";
    static final String password = "t9dnb2mq";

    public static void sendMail(String mail, String subject, String message)
            throws AddressException, MessagingException {
        Message msg = new MimeMessage(getSession());
        msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mail));
        msg.setSubject(subject);
        msg.setText(message);
        Transport.send(msg);
    }

    private static Session getSession() {
        return Session.getDefaultInstance(getProperties(),
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
    }

    private static Properties getProperties() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "localhost");
        properties.put("mail.smtp.socketFactory.port", "25");
        return properties;
    }

    public static void main(String[] args) throws MessagingException {
        MailHelper.sendMail("123@mail.ru", "Subject", "Message");
    }
}
