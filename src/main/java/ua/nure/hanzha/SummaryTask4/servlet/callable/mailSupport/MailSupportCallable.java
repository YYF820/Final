package ua.nure.hanzha.SummaryTask4.servlet.callable.mailSupport;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 18/08/15.
 */
public interface MailSupportCallable {

    void call() throws IOException, ServletException;
}
