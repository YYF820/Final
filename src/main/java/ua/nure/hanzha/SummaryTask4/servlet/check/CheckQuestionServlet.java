package ua.nure.hanzha.SummaryTask4.servlet.check;

import ua.nure.hanzha.SummaryTask4.bean.MailInfoResetPasswordBean;
import ua.nure.hanzha.SummaryTask4.bean.MailInfoVerifyAccountBean;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.RequestAttribute;
import ua.nure.hanzha.SummaryTask4.db.util.PasswordHash;
import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.exception.PropertiesDuplicateException;
import ua.nure.hanzha.SummaryTask4.exception.ServletSystemException;
import ua.nure.hanzha.SummaryTask4.util.TicketsWriterReader;
import ua.nure.hanzha.SummaryTask4.validation.Validation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 03/08/15.
 */
public class CheckQuestionServlet extends HttpServlet {

    private static final String EMPTY_PARAM = "";

    private static final String PARAM_SCHOOL_NUMBER = "school";

    private static final String SESSION_ATTRIBUTE_COMMAND = "command";
    private static final String SESSION_ATTRIBUTE_ENTRANT_FOR_VERIFY_ACCOUNT_RESET_PASSWORD = "entrantForVerifyAccountResetPassword";
    private static final String SESSION_ATTRIBUTE_USER_FOR_VERIFY_ACCOUNT_RESET_PASSWORD = "userForVerifyAccountResetPassword";
    private static final String SESSION_ATTRIBUTE_HASH_TICKET_RESET_PASSWORD = "hashTicketRecoverPassword";
    private static final String SESSION_ATTRIBUTE_COUNTER_BAD_TICKET_INSERTS = "counterBadTicketInserts";

    private static final String REQUEST_ATTRIBUTE_IS_SCHOOL_CORRECT = "isSchoolCorrect";

    private static final String COMMAND_VERIFY_ACCOUNT = "verifyAccount";
    private static final String COMMAND_RESET_PASSWORD = "resetPassword";

    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String PATRONYMIC = "patronymic";
    private static final String ACCOUNT_NAME = "accountName";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String schoolNumberFromQuestion = request.getParameter(PARAM_SCHOOL_NUMBER);
        request.setAttribute(RequestAttribute.SCHOOL, schoolNumberFromQuestion);
        boolean isSchoolNumberFromQuestionValid = checkValidationSchoolNumber(request, schoolNumberFromQuestion);
        boolean isSchoolNumberEmpty = checkEmptyFields(request, schoolNumberFromQuestion);
        if (!isSchoolNumberFromQuestionValid || isSchoolNumberEmpty) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.CHECK_QUESTION_HTML);
            requestDispatcher.forward(request, response);
        } else {
            HttpSession session = request.getSession(false);
            Entrant entrant = (Entrant) session.getAttribute(SESSION_ATTRIBUTE_ENTRANT_FOR_VERIFY_ACCOUNT_RESET_PASSWORD);
            int schoolNumberFromEntrant = entrant.getSchool();
            if (!(Integer.parseInt(schoolNumberFromQuestion) == schoolNumberFromEntrant)) {
                request.setAttribute(REQUEST_ATTRIBUTE_IS_SCHOOL_CORRECT, false);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.CHECK_QUESTION_HTML);
                requestDispatcher.forward(request, response);
            } else {
                String command = (String) session.getAttribute(SESSION_ATTRIBUTE_COMMAND);
                try {
                    switch (command) {
                        case COMMAND_VERIFY_ACCOUNT:
                            User user = (User) session.getAttribute(SESSION_ATTRIBUTE_USER_FOR_VERIFY_ACCOUNT_RESET_PASSWORD);
                            Map<String, String> extractedUserInfo = extractInfo(user);
                            prepareInfoVerifyEmail(
                                    request,
                                    extractedUserInfo.get(FIRST_NAME),
                                    extractedUserInfo.get(LAST_NAME),
                                    extractedUserInfo.get(PATRONYMIC),
                                    extractedUserInfo.get(ACCOUNT_NAME)
                            );
                            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.MAIL_SENDER_SERVLET);
                            requestDispatcher.forward(request, response);
                            break;
                        case COMMAND_RESET_PASSWORD:
                            session.setMaxInactiveInterval(60);
                            Long counterBadTicketInserts = 0L;
                            String ticketResetPassword = PasswordHash.randomPassword(6).toUpperCase();
                            String hashTicketResetPassword = PasswordHash.createHash(ticketResetPassword);
                            session.setAttribute(SESSION_ATTRIBUTE_HASH_TICKET_RESET_PASSWORD, hashTicketResetPassword);
                            session.setAttribute(SESSION_ATTRIBUTE_COUNTER_BAD_TICKET_INSERTS, counterBadTicketInserts);
                            user = (User) session.getAttribute(SESSION_ATTRIBUTE_USER_FOR_VERIFY_ACCOUNT_RESET_PASSWORD);
                            extractedUserInfo = extractInfo(user);
                            prepareInfoRecoverPassword(
                                    request,
                                    extractedUserInfo.get(FIRST_NAME),
                                    extractedUserInfo.get(LAST_NAME),
                                    extractedUserInfo.get(PATRONYMIC),
                                    extractedUserInfo.get(ACCOUNT_NAME),
                                    ticketResetPassword
                            );
                            requestDispatcher = request.getRequestDispatcher(Pages.MAIL_SENDER_SERVLET);
                            requestDispatcher.forward(request, response);
                            break;
                        default:
                            throw new ServletSystemException("unknown command...");
                    }
                } catch (ServletSystemException e) {
                    e.printStackTrace();
                    //TODO: add redirect to 500 page
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(Pages.CHECK_QUESTION_HTML);
    }

    private boolean checkValidationSchoolNumber(HttpServletRequest request, String schoolNumber) {
        boolean isAccountNameValid = Validation.validateSchool(schoolNumber);
        if (!isAccountNameValid) {
            request.setAttribute(RequestAttribute.IS_SCHOOL_VALID, false);
        }
        return isAccountNameValid;
    }

    private boolean checkEmptyFields(HttpServletRequest request, String schoolNumber) {
        boolean isSchoolNumberEmpty = false;
        if (schoolNumber.equals(EMPTY_PARAM)) {
            isSchoolNumberEmpty = true;
            request.setAttribute(RequestAttribute.IS_SCHOOL_EMPTY, true);
        }
        return isSchoolNumberEmpty;
    }

    private void prepareInfoVerifyEmail(HttpServletRequest request, String firstName,
                                        String lastName, String patronymic, String accountName) {
        String ticket = generateTicket();
        String verifyLink = "http://localhost:8080/verifyAccount.do?ticket=" + ticket;
        boolean flagSuccessTicket = false;
        if (TicketsWriterReader.containsValue(accountName)) {
            TicketsWriterReader.removeAllValues(accountName);
        }
        while (!flagSuccessTicket) {
            try {
                TicketsWriterReader.writePair(ticket, accountName);
                flagSuccessTicket = true;
            } catch (PropertiesDuplicateException e) {
                ticket = generateTicket();
            }
        }
        MailInfoVerifyAccountBean mailInfoVerifyAccountBean = new MailInfoVerifyAccountBean();
        mailInfoVerifyAccountBean.setFirstName(firstName);
        mailInfoVerifyAccountBean.setLastName(lastName);
        mailInfoVerifyAccountBean.setPatronymic(patronymic);
        mailInfoVerifyAccountBean.setAccountName(accountName);
        mailInfoVerifyAccountBean.setVerifyLink(verifyLink);
        request.setAttribute(RequestAttribute.MAIL_INFO_VERIFY_ACCOUNT_BEAN, mailInfoVerifyAccountBean);
    }

    private void prepareInfoRecoverPassword(HttpServletRequest request, String firstName,
                                            String lastName, String patronymic,
                                            String accountName, String ticketRecoverPassword) {

        MailInfoResetPasswordBean mailInfoResetPasswordBean = new MailInfoResetPasswordBean();
        mailInfoResetPasswordBean.setFirstName(firstName);
        mailInfoResetPasswordBean.setLastName(lastName);
        mailInfoResetPasswordBean.setPatronymic(patronymic);
        mailInfoResetPasswordBean.setAccountName(accountName);
        mailInfoResetPasswordBean.setTicketResetPassword(ticketRecoverPassword);

        request.setAttribute(RequestAttribute.MAIL_INFO_RESET_PASSWORD_BEAN, mailInfoResetPasswordBean);

    }

    private Map<String, String> extractInfo(User user) {
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put(FIRST_NAME, user.getFirstName());
        userInfo.put(LAST_NAME, user.getLastName());
        userInfo.put(PATRONYMIC, user.getPatronymic());
        userInfo.put(ACCOUNT_NAME, user.getEmail());
        return userInfo;
    }

    private String generateTicket() {
        return PasswordHash.randomPassword(40);
    }

}
