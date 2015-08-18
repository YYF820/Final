package ua.nure.hanzha.SummaryTask4.servlet.callable.checkQuestion;

import ua.nure.hanzha.SummaryTask4.bean.MailInfoResetPasswordBean;
import ua.nure.hanzha.SummaryTask4.bean.MailInfoVerifyAccountBean;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.RequestAttribute;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.db.util.HashUtilities;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.exception.PropertiesDuplicateException;
import ua.nure.hanzha.SummaryTask4.util.TicketsWriterReaderUtilities;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 18/08/15.
 */
public class CheckQuestionOperationsMap {

    private static final String COMMAND_VERIFY_ACCOUNT = "verifyAccount";
    private static final String COMMAND_RESET_PASSWORD = "resetPassword";

    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String PATRONYMIC = "patronymic";
    private static final String ACCOUNT_NAME = "accountName";

    private static CheckQuestionOperationsMap checkQuestionOperationsMap;
    private static Map<String, CheckQuestionCallable> checkQuestionCallableMap;

    private CheckQuestionOperationsMap() {

    }

    public static CheckQuestionOperationsMap getInstance() {
        if (checkQuestionOperationsMap == null) {
            checkQuestionOperationsMap = new CheckQuestionOperationsMap();
        }
        return checkQuestionOperationsMap;
    }

    public static CheckQuestionCallable getCheckQuestionCallable(String command) {
        return checkQuestionCallableMap.get(command);
    }

    public static void initCheckQuestionCallableMap(final HttpSession session, final HttpServletRequest request,
                                                    final HttpServletResponse response) {
        checkQuestionCallableMap = new HashMap<>();
        checkQuestionCallableMap.put(
                COMMAND_VERIFY_ACCOUNT,
                new CheckQuestionCallable() {
                    @Override
                    public void call() throws ServletException, IOException {
                        User user = (User) session.getAttribute(SessionAttribute.USER_FOR_VERIFY_ACCOUNT_RESET_PASSWORD);
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
                    }
                }
        );
        checkQuestionCallableMap.put(
                COMMAND_RESET_PASSWORD,
                new CheckQuestionCallable() {
                    @Override
                    public void call() throws ServletException, IOException {
                        session.setMaxInactiveInterval(10 * 60);
                        Long counterBadTicketInserts = 0L;
                        String ticketResetPassword = HashUtilities.randomPassword(6).toUpperCase();
                        String hashTicketResetPassword = HashUtilities.createHash(ticketResetPassword);
                        session.setAttribute(SessionAttribute.CHECK_TICKET_HASH_TICKET_RESET_PASSWORD, hashTicketResetPassword);
                        session.setAttribute(SessionAttribute.CHECK_TICKET_COUNTER_BAD_TICKET_INSERTS, counterBadTicketInserts);
                        User user = (User) session.getAttribute(SessionAttribute.USER_FOR_VERIFY_ACCOUNT_RESET_PASSWORD);
                        Map<String, String> extractedUserInfo = extractInfo(user);
                        prepareInfoRecoverPassword(
                                request,
                                extractedUserInfo.get(FIRST_NAME),
                                extractedUserInfo.get(LAST_NAME),
                                extractedUserInfo.get(PATRONYMIC),
                                extractedUserInfo.get(ACCOUNT_NAME),
                                ticketResetPassword
                        );
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.MAIL_SENDER_SERVLET);
                        requestDispatcher.forward(request, response);
                    }
                }
        );
    }

    private static void prepareInfoVerifyEmail(HttpServletRequest request, String firstName,
                                               String lastName, String patronymic, String accountName) {
        String ticket = generateTicket();
        String verifyLink = "http://localhost:8080/verifyAccount.do?ticket=" + ticket;
        boolean flagSuccessTicket = false;
        if (TicketsWriterReaderUtilities.containsValue(accountName)) {
            TicketsWriterReaderUtilities.removeAllValues(accountName);
        }
        while (!flagSuccessTicket) {
            try {
                TicketsWriterReaderUtilities.writePair(ticket, accountName);
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

    private static void prepareInfoRecoverPassword(HttpServletRequest request, String firstName,
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

    private static Map<String, String> extractInfo(User user) {
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put(FIRST_NAME, user.getFirstName());
        userInfo.put(LAST_NAME, user.getLastName());
        userInfo.put(PATRONYMIC, user.getPatronymic());
        userInfo.put(ACCOUNT_NAME, user.getEmail());
        return userInfo;
    }

    private static String generateTicket() {
        return HashUtilities.randomPassword(40);
    }
}
