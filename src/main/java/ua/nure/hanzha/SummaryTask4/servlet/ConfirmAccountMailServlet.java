package ua.nure.hanzha.SummaryTask4.servlet;

import ua.nure.hanzha.SummaryTask4.bean.MailInfoBean;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.RequestAttribute;
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

    private static final String REQUEST_ATTRIBUTE_IS_MESSAGE_SENT = "isMessageSent";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        MailInfoBean mailInfoBean = (MailInfoBean) request.getAttribute(RequestAttribute.MAIL_INFO_BEAN);
        String firstName = mailInfoBean.getFirstName();
        String lastName = mailInfoBean.getLastName();
        String patronymic = mailInfoBean.getPatronymic();
        String accountName = mailInfoBean.getAccountName();
        String verifyLink = mailInfoBean.getVerifyLink();
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
