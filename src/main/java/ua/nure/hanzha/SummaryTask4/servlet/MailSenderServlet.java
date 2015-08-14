package ua.nure.hanzha.SummaryTask4.servlet;

import ua.nure.hanzha.SummaryTask4.bean.*;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.RequestAttribute;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.EntrantFinalSheet;
import ua.nure.hanzha.SummaryTask4.exception.ServletSystemException;
import ua.nure.hanzha.SummaryTask4.mail.MailHelper;

import javax.mail.MessagingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 02/08/15.
 */
public class MailSenderServlet extends HttpServlet {

    private static final String COMMAND_VERIFY_ACCOUNT = "verifyAccount";
    private static final String COMMAND_RESET_PASSWORD = "resetPassword";
    private static final String COMMAND_UPDATED_PASSWORD = "updatedPassword";
    private static final String ADMIN_COMMAND_BAN_USER = "adminCommandBanUser";
    private static final String ADMIN_COMMAND_UN_BAN_USER = "adminCommandUnBanUser";
    private static final String ADMIN_COMMAND_NOTIFY_ENTRANTS_WHO_PASSED = "notifyEntrantsWhoPassed";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String command = (String) session.getAttribute(SessionAttribute.COMMAND);
        switch (command) {
            case COMMAND_VERIFY_ACCOUNT:
                MailInfoVerifyAccountBean mailInfoVerifyAccountBean = (MailInfoVerifyAccountBean)
                        request.getAttribute(RequestAttribute.MAIL_INFO_VERIFY_ACCOUNT_BEAN);
                System.out.println(1);
                String firstName = mailInfoVerifyAccountBean.getFirstName();
                String lastName = mailInfoVerifyAccountBean.getLastName();
                String patronymic = mailInfoVerifyAccountBean.getPatronymic();
                String accountName = mailInfoVerifyAccountBean.getAccountName();
                String verifyLink = mailInfoVerifyAccountBean.getVerifyLink();
                String subjectMail = createSubjectVerifyAccount();
                String messageMail = createMessageVerifyAccount(firstName, lastName, patronymic, verifyLink);
                try {
                    MailHelper.sendMail(accountName, subjectMail, messageMail);
                    session.invalidate(); // invalidate session to clean all data in registration form.
                    request.getSession(true).setAttribute(SessionAttribute.VERIFY_ACCOUNT_IS_MESSAGE_SENT, true);
                    response.sendRedirect(Pages.VERIFY_ACCOUNT_MESSAGE_SENT_HTML);
                } catch (MessagingException e) {
                    session.invalidate(); // invalidate session to clean all data in registration form.
                    request.getSession(true).setAttribute(SessionAttribute.VERIFY_ACCOUNT_IS_MESSAGE_SENT, true);
                    response.sendRedirect(Pages.VERIFY_ACCOUNT_MESSAGE_SENT_HTML);
                }
                break;
            case COMMAND_RESET_PASSWORD:
                MailInfoResetPasswordBean mailInfoResetPasswordBean = (MailInfoResetPasswordBean)
                        request.getAttribute(RequestAttribute.MAIL_INFO_RESET_PASSWORD_BEAN);
                firstName = mailInfoResetPasswordBean.getFirstName();
                lastName = mailInfoResetPasswordBean.getLastName();
                patronymic = mailInfoResetPasswordBean.getPatronymic();
                accountName = mailInfoResetPasswordBean.getAccountName();
                String ticketRecoverPassword = mailInfoResetPasswordBean.getTicketResetPassword();
                subjectMail = createSubjectRecoverPassword();
                messageMail = createMessageRecoverPassword(firstName, lastName, patronymic, ticketRecoverPassword);
                try {
                    MailHelper.sendMail(accountName, subjectMail, messageMail);
                    session.setAttribute(SessionAttribute.CHECK_TICKET_IS_MESSAGE_SENT, true);
                    response.sendRedirect(Pages.RESET_PASSWORD_MESSAGE_SENT_HTML);
                } catch (MessagingException e) {
                    session.setAttribute(SessionAttribute.CHECK_TICKET_IS_MESSAGE_SENT, false);
                    response.sendRedirect(Pages.RESET_PASSWORD_MESSAGE_SENT_HTML);
                }
                break;
            case COMMAND_UPDATED_PASSWORD:
                MailInfoUpdatedPasswordOrBlockedBean mailInfoUpdatedPasswordOrBlockedBean = (MailInfoUpdatedPasswordOrBlockedBean)
                        request.getAttribute(RequestAttribute.MAIL_INFO_UPDATED_PASSWORD_OR_BLOCKED_BEAN);
                firstName = mailInfoUpdatedPasswordOrBlockedBean.getFirstName();
                lastName = mailInfoUpdatedPasswordOrBlockedBean.getLastName();
                patronymic = mailInfoUpdatedPasswordOrBlockedBean.getPatronymic();
                accountName = mailInfoUpdatedPasswordOrBlockedBean.getAccountName();
                subjectMail = createSubjectUpdatedPassword();
                messageMail = createMessageUpdatedPassword(firstName, lastName, patronymic);
                try {
                    MailHelper.sendMail(accountName, subjectMail, messageMail);
                    session.setAttribute(SessionAttribute.RESET_PASSWORD_IS_UPDATED_PASSWORD, true);
                    response.sendRedirect(Pages.RESET_PASSWORD_SUCCESS_HTML);
                } catch (MessagingException e) {
                    request.setAttribute(SessionAttribute.RESET_PASSWORD_IS_UPDATED_PASSWORD, false);
                    response.sendRedirect(Pages.RESET_PASSWORD_SUCCESS_HTML);
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
        HttpSession session = request.getSession(false);
        String command = (String) session.getAttribute(SessionAttribute.COMMAND);
        switch (command) {
            case ADMIN_COMMAND_BAN_USER:
                MailInfoUpdatedPasswordOrBlockedBean mailInfoUpdatedPasswordOrBlockedBean = (MailInfoUpdatedPasswordOrBlockedBean)
                        request.getAttribute(RequestAttribute.MAIL_INFO_UPDATED_PASSWORD_OR_BLOCKED_BEAN);
                int currentPage = (int) session.getAttribute(SessionAttribute.CURRENT_PAGE);
                String firstName = mailInfoUpdatedPasswordOrBlockedBean.getFirstName();
                String lastName = mailInfoUpdatedPasswordOrBlockedBean.getLastName();
                String patronymic = mailInfoUpdatedPasswordOrBlockedBean.getPatronymic();
                String accountName = mailInfoUpdatedPasswordOrBlockedBean.getAccountName();
                String subjectMail = createSubjectBannedAccount();
                String messageMail = createMessageBannedAccount(firstName, lastName, patronymic);
                try {
                    MailHelper.sendMail(accountName, subjectMail, messageMail);
                    response.sendRedirect(Pages.ENTRANTS_ADMIN_SERVLET + "?page=" + currentPage);
                } catch (MessagingException e) {
                    response.sendRedirect(Pages.ENTRANTS_ADMIN_SERVLET + "?page=" + currentPage);
                }
                break;
            case ADMIN_COMMAND_UN_BAN_USER:
                mailInfoUpdatedPasswordOrBlockedBean = (MailInfoUpdatedPasswordOrBlockedBean)
                        request.getAttribute(RequestAttribute.MAIL_INFO_UPDATED_PASSWORD_OR_BLOCKED_BEAN);
                currentPage = (int) session.getAttribute(SessionAttribute.CURRENT_PAGE);
                firstName = mailInfoUpdatedPasswordOrBlockedBean.getFirstName();
                lastName = mailInfoUpdatedPasswordOrBlockedBean.getLastName();
                patronymic = mailInfoUpdatedPasswordOrBlockedBean.getPatronymic();
                accountName = mailInfoUpdatedPasswordOrBlockedBean.getAccountName();
                subjectMail = createSubjectUnBannedAccount();
                messageMail = createMessageUnBannedAccount(firstName, lastName, patronymic);
                try {
                    MailHelper.sendMail(accountName, subjectMail, messageMail);
                    response.sendRedirect(Pages.ENTRANTS_ADMIN_SERVLET + "?page=" + currentPage);
                } catch (MessagingException e) {
                    response.sendRedirect(Pages.ENTRANTS_ADMIN_SERVLET + "?page=" + currentPage);
                }
                break;
            case ADMIN_COMMAND_NOTIFY_ENTRANTS_WHO_PASSED:
                UniversityFinalSheetBean universityFinalSheetBean =
                        (UniversityFinalSheetBean) request.getAttribute(RequestAttribute.UNIVERSITY_FINAL_SHEET_BEAN);
                List<FacultyFinalSheetBean> allFacultiesFinalSheetBeanList = universityFinalSheetBean.getFacultiesFinalSheetBean();
                for (FacultyFinalSheetBean facultyFinalSheetBean : allFacultiesFinalSheetBeanList) {
                    List<EntrantFinalSheetBean> budgetEntrantFinalSheetBeanList = facultyFinalSheetBean.getBudgetEntrants();
                    for (EntrantFinalSheetBean entrantFinalSheetBean : budgetEntrantFinalSheetBeanList) {
                        String facultyName = facultyFinalSheetBean.getFacultyName();
                        firstName = entrantFinalSheetBean.getFirstName();
                        lastName = entrantFinalSheetBean.getLastName();
                        patronymic = entrantFinalSheetBean.getPatronymic();
                        accountName = entrantFinalSheetBean.getAccountName();
                        subjectMail = createSubjectEnterUniversity();
                        messageMail = createMessageCongratulationsBudgetEntrant(firstName, lastName, patronymic, facultyName);
                        try {
                            MailHelper.sendMail(accountName, subjectMail, messageMail);
                        } catch (MessagingException e) {
                            //TODO: LOG4J
                        }
                    }
                    List<EntrantFinalSheetBean> contractEntrantFinalSheetBeanList = facultyFinalSheetBean.getContractEntrants();
                    for (EntrantFinalSheetBean entrantFinalSheetBean : contractEntrantFinalSheetBeanList) {
                        String facultyName = facultyFinalSheetBean.getFacultyName();
                        firstName = entrantFinalSheetBean.getFirstName();
                        lastName = entrantFinalSheetBean.getLastName();
                        patronymic = entrantFinalSheetBean.getPatronymic();
                        accountName = entrantFinalSheetBean.getAccountName();
                        subjectMail = createSubjectEnterUniversity();
                        messageMail = createMessageCongratulationsContractEntrant(firstName, lastName, patronymic, facultyName);
                        try {
                            MailHelper.sendMail(accountName, subjectMail, messageMail);
                        } catch (MessagingException e) {
                            //TODO: LOG4J
                        }
                    }
                }
                List<EntrantFinalSheetBean> notPassedEntrants = universityFinalSheetBean.getNotPassedEntrants();
                for (EntrantFinalSheetBean notPassedEntrant : notPassedEntrants) {
                    firstName = notPassedEntrant.getFirstName();
                    lastName = notPassedEntrant.getLastName();
                    patronymic = notPassedEntrant.getPatronymic();
                    accountName = notPassedEntrant.getAccountName();
                    subjectMail = createSubjectEnterUniversity();
                    messageMail = createMessageNotPassedEntrant(firstName, lastName, patronymic);
                    try {
                        MailHelper.sendMail(accountName, subjectMail, messageMail);
                    } catch (MessagingException e) {
                        //TODO: LOG4J
                    }
                }
                response.sendRedirect(Pages.INDEX_HTML);
                break;
            default:
                try {
                    response.sendRedirect(Pages.INDEX_HTML);
                    throw new ServletSystemException("bad comand .....");
                } catch (ServletSystemException e) {
                    e.printStackTrace();
                }
                break;
        }

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

    private String createSubjectBannedAccount() {
        return "Ваша запись была заблокирована.";
    }

    private String createSubjectUnBannedAccount() {
        return "Ваша запись была разблокирована.";
    }

    private String createSubjectEnterUniversity() {
        return "Информация о поступлении";
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

    private String createMessageBannedAccount(String firstName, String lastName, String patronymic) {
        return "Здравствуйте, " + lastName + " " + firstName + " " + patronymic + ".\n\n"
                + "Ваша запись была заблокирована, администрацией.\n\n"
                + "Свяжитесь с поддержкой для выявления причины.\n\n";
    }

    private String createMessageUnBannedAccount(String firstName, String lastName, String patronymic) {
        return "Здравствуйте, " + lastName + " " + firstName + " " + patronymic + ".\n\n"
                + "Ваша запись была разблокирована.\n\n"
                + "Спасибо что пользуетесь нашей системой.\n\n";
    }

    private String createMessageCongratulationsBudgetEntrant(String firstName, String lastName, String patronymic, String facultyName) {
        return "Уважаемый " + lastName + " " + firstName + " " + patronymic + ".\n" +
                "Поздравляем Вас с зачислением на бюджетное место дневной формы обучения на факультете" + facultyName + "!\n" +
                "Желаем успехов в учебе!\n\n" +
                "С уважение University.";
    }

    private String createMessageCongratulationsContractEntrant(String firstName, String lastName, String patronymic, String facultyName) {
        return "Уважаемый " + lastName + " " + firstName + " " + patronymic + ".\n" +
                "Cообщаем Вам что в этом году Вы набрали недостаточное количество баллов для зачисления на бюджетное место дневной формы обучения.\n" +
                "Предлагаем Вам воспользоваться контрактной формой обучения на факультете" + facultyName + ". " +
                "По всем вопросам просим обращаться в приемную комиссию.\n\n" +
                "С уважение University.";
    }

    private String createMessageNotPassedEntrant(String firstName, String lastName, String patronymic) {
        return "Уважаемый " + lastName + " " + firstName + " " + patronymic + ".\n" +
                "Cообщаем Вам, что в этом году Вы набрали недостаточное количество баллов для поступления в University.\n" +
                "По всем вопросам просим обращаться в приемную комиссию.\n\n" +
                "С уважение University.";
    }

}
