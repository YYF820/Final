package ua.nure.hanzha.SummaryTask4.servlet;

import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.RequestAttribute;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.mail.MailHelper;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 02/08/15.
 */
public class ConfirmAccountMailServlet extends HttpServlet {

    private static final String SESSION_ATTRIBUTE_CONFIRM_LINK = "confirmLink";
    private static final String REQUEST_ATTRIBUTE_IS_MESSAGE_SENT = "isMessageSent";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = (String) request.getAttribute(RequestAttribute.FIRST_NAME);
        String lastName = (String) request.getAttribute(SessionAttribute.LAST_NAME);
        String patronymic = (String) request.getAttribute(SessionAttribute.PATRONYMIC);
        String accountName = (String) request.getAttribute(SessionAttribute.ACCOUNT_NAME);
        String verifyLink = (String) request.getAttribute(SESSION_ATTRIBUTE_CONFIRM_LINK);
        String subjectMail = createSubject();
        String messageMail = createMessage(firstName, lastName, patronymic, verifyLink);
        try {
            MailHelper.sendMail(accountName, subjectMail, messageMail);
            request.setAttribute(REQUEST_ATTRIBUTE_IS_MESSAGE_SENT, true);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.CONFIRM_ACCOUNT_MESSAGE_SENT_HTML);
            requestDispatcher.forward(request, response);
        } catch (MessagingException e) {
            request.setAttribute(REQUEST_ATTRIBUTE_IS_MESSAGE_SENT, false);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.CONFIRM_ACCOUNT_MESSAGE_SENT_HTML);
            requestDispatcher.forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(REQUEST_ATTRIBUTE_IS_MESSAGE_SENT, false);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.CONFIRM_ACCOUNT_MESSAGE_SENT_HTML);
        requestDispatcher.forward(request, response);
    }

    private String createSubject() {
        return "Подтверждение E-mail учетной записи UniversityAlpha.com";
    }

    private String createMessage(String firstName, String lastName, String patronymic, String confirmLink) {
        return "Здравствуйте, " + lastName + " " + firstName + " " + patronymic + ".\n\n"
                + "Добро пожаловать на UniversityAlpha.com\n"
                + "Если Вы еще не подтвердили действительность своего E-mail, настоятельно рекомендуем Вам сделать это сейчас.\n\n"
                + " " + confirmLink;
    }
}
