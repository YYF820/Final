package ua.nure.hanzha.SummaryTask4.servlet;

import ua.nure.hanzha.SummaryTask4.bean.MailInfoResetPasswordBean;
import ua.nure.hanzha.SummaryTask4.bean.MailInfoUpdatedPasswordBean;
import ua.nure.hanzha.SummaryTask4.bean.MailInfoVerifyAccountBean;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.RequestAttribute;
import ua.nure.hanzha.SummaryTask4.exception.ServletSystemException;
import ua.nure.hanzha.SummaryTask4.mail.MailHelper;

import javax.mail.MessagingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 02/08/15.
 */
public class MailSenderServlet extends HttpServlet {

    private static final String REQUEST_ATTRIBUTE_IS_MESSAGE_SENT = "isMessageSent";

    private static final String SESSION_ATTRIBUTE_COMMAND = "command";
    private static final String COMMAND_VERIFY_ACCOUNT = "verifyAccount";
    private static final String COMMAND_RESET_PASSWORD = "resetPassword";
    private static final String COMMAND_UPDATED_PASSWORD = "updatedPassword";

    private static final String REQUEST_ATTRIBUTE_IS_UPDATED_PASSWORD = "isUpdatedPassword";


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String command = (String) session.getAttribute(SESSION_ATTRIBUTE_COMMAND);
        switch (command) {
            case COMMAND_VERIFY_ACCOUNT:
                MailInfoVerifyAccountBean mailInfoVerifyAccountBean = (MailInfoVerifyAccountBean) request.getAttribute(RequestAttribute.MAIL_INFO_VERIFY_ACCOUNT_BEAN);
                String firstName = mailInfoVerifyAccountBean.getFirstName();
                String lastName = mailInfoVerifyAccountBean.getLastName();
                String patronymic = mailInfoVerifyAccountBean.getPatronymic();
                String accountName = mailInfoVerifyAccountBean.getAccountName();
                String verifyLink = mailInfoVerifyAccountBean.getVerifyLink();
                String subjectMail = createSubjectVerifyAccount();
                String messageMail = createMessageVerifyAccount(firstName, lastName, patronymic, verifyLink);
                try {
                    MailHelper.sendMail(accountName, subjectMail, messageMail);
                    request.setAttribute(REQUEST_ATTRIBUTE_IS_MESSAGE_SENT, true);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.VERIFY_ACCOUNT_MESSAGE_SENT_HTML);
                    requestDispatcher.forward(request, response);
                } catch (MessagingException e) {
                    request.setAttribute(REQUEST_ATTRIBUTE_IS_MESSAGE_SENT, false);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.VERIFY_ACCOUNT_MESSAGE_SENT_HTML);
                    requestDispatcher.forward(request, response);
                }
                break;
            case COMMAND_RESET_PASSWORD:
                MailInfoResetPasswordBean mailInfoResetPasswordBean = (MailInfoResetPasswordBean) request.getAttribute(RequestAttribute.MAIL_INFO_RESET_PASSWORD_BEAN);
                firstName = mailInfoResetPasswordBean.getFirstName();
                lastName = mailInfoResetPasswordBean.getLastName();
                patronymic = mailInfoResetPasswordBean.getPatronymic();
                accountName = mailInfoResetPasswordBean.getAccountName();
                String ticketRecoverPassword = mailInfoResetPasswordBean.getTicketResetPassword();
                subjectMail = createSubjectRecoverPassword();
                messageMail = createMessageRecoverPassword(firstName, lastName, patronymic, ticketRecoverPassword);
                try {
                    MailHelper.sendMail(accountName, subjectMail, messageMail);
                    request.setAttribute(REQUEST_ATTRIBUTE_IS_MESSAGE_SENT, true);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.RESET_PASSWORD_MESSAGE_SENT_HTML);
                    requestDispatcher.forward(request, response);
                } catch (MessagingException e) {
                    request.setAttribute(REQUEST_ATTRIBUTE_IS_MESSAGE_SENT, false);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.RESET_PASSWORD_MESSAGE_SENT_HTML);
                    requestDispatcher.forward(request, response);
                }
                break;
            case COMMAND_UPDATED_PASSWORD:
                MailInfoUpdatedPasswordBean mailInfoUpdatedPasswordBean = (MailInfoUpdatedPasswordBean) request.getAttribute(RequestAttribute.MAIL_INFO_UPDATED_PASSWORD_BEAN);
                firstName = mailInfoUpdatedPasswordBean.getFirstName();
                lastName = mailInfoUpdatedPasswordBean.getLastName();
                patronymic = mailInfoUpdatedPasswordBean.getPatronymic();
                accountName = mailInfoUpdatedPasswordBean.getAccountName();
                subjectMail = createSubjectUpdatedPassword();
                messageMail = createMessageUpdatedPassword(firstName, lastName, patronymic);
                try {
                    MailHelper.sendMail(accountName, subjectMail, messageMail);
                    request.setAttribute(REQUEST_ATTRIBUTE_IS_UPDATED_PASSWORD, true);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.RESET_PASSWORD_SUCCESS_HTML);
                    requestDispatcher.forward(request, response);
                } catch (MessagingException e) {
                    request.setAttribute(REQUEST_ATTRIBUTE_IS_MESSAGE_SENT, false);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.RESET_PASSWORD_MESSAGE_SENT_HTML);
                    requestDispatcher.forward(request, response);
                }
                break;
            default:
                try {
                    throw new ServletSystemException("bad comand .....");
                } catch (ServletSystemException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute(REQUEST_ATTRIBUTE_IS_MESSAGE_SENT, false);
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.VERIFY_ACCOUNT_MESSAGE_SENT_HTML);
        requestDispatcher.forward(request, response);
    }

    private String createSubjectVerifyAccount() {
        return "Подтверждение E-mail учетной записи UniversityAlpha.com";
    }

    private String createSubjectRecoverPassword() {
        return "Восстановление пароля учетной записи UniversityAlpha.com";
    }

    private String createSubjectUpdatedPassword() {
        return "Восстановление пароля прошло успешно.";
    }

    private String createMessageVerifyAccount(String firstName, String lastName, String patronymic, String confirmLink) {
        return "Здравствуйте, " + lastName + " " + firstName + " " + patronymic + ".\n\n"
                + "Добро пожаловать на UniversityAlpha.com\n"
                + "Если Вы еще не подтвердили действительность своего E-mail, настоятельно рекомендуем Вам сделать это сейчас.\n\n"
                + " " + confirmLink;
    }

    private String createMessageRecoverPassword(String firstName, String lastName, String patronymic, String ticketRecoverPassword) {
        return "Здравствуйте, " + lastName + " " + firstName + " " + patronymic + ".\n\n"
                + "Добро пожаловать на UniversityAlpha.com\n"
                + "Мы вам выслали код который вы должны ввести в форму на которую вы перешли\n\n"
                + " " + ticketRecoverPassword;
    }

    private String createMessageUpdatedPassword(String firstName, String lastName, String patronymic) {
        return "Здравствуйте, " + lastName + " " + firstName + " " + patronymic + ".\n\n"
                + "Вашей записи назначен новый пароль.\n\n"
                + "Спасибо что пользуетесь нашим сервисом.\n\n";
    }

}
