package ua.nure.hanzha.SummaryTask4.servlet.callable.mailSupport;

import org.apache.log4j.Logger;
import ua.nure.hanzha.SummaryTask4.bean.MailInfoToSupportBean;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.RequestAttribute;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.mail.MailHelperSupport;
import ua.nure.hanzha.SummaryTask4.util.ClassNameUtilities;
import ua.nure.hanzha.SummaryTask4.util.SessionCleanerUtilities;
import ua.nure.hanzha.SummaryTask4.util.ValidationUtilities;

import javax.mail.MessagingException;
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
public class MailSupportOperationsMap {


    private static final Logger LOGGER = Logger.getLogger(ClassNameUtilities.getCurrentClassName());
    private static final String COMMAND_WITH_ACCOUNT_NAME = "withAccountName";
    private static final String COMMAND_WITH_OUT_ACCOUNT_NAME = "withOutAccountName";

    private static final String PARAM_ACCOUNT_NAME = "accountName";
    private static final String PARAM_SUBJECT = "subject";
    private static final String PARAM_MESSAGE = "message";
    private static final String EMPTY_PARAM = "";

    private static final int MAX_SUBJECT_LENGTH = 50;
    private static final int MAX_MESSAGE_LENGTH = 350;

    private static MailSupportOperationsMap mailSupportOperationsMap;
    private static Map<String, MailSupportCallable> mailSupportCallableMap;

    private MailSupportOperationsMap() {

    }

    public static MailSupportOperationsMap getInstance() {
        if (mailSupportOperationsMap == null) {
            mailSupportOperationsMap = new MailSupportOperationsMap();
        }
        return mailSupportOperationsMap;
    }

    public static MailSupportCallable getMailSupportCallable(String command) {
        return mailSupportCallableMap.get(command);
    }

    public static void initMailSupportCallableMap(final HttpServletRequest request,
                                                  final HttpServletResponse response,
                                                  final HttpSession session) {
        mailSupportCallableMap = new HashMap<>();
        mailSupportCallableMap.put(
                COMMAND_WITH_OUT_ACCOUNT_NAME,
                new MailSupportCallable() {
                    @Override
                    public void call() throws IOException, ServletException {
                        cleanSessionPrg(session);
                        MailInfoToSupportBean mailInfoToSupportBean = getMailInfoToSupportBean(request);
                        User user = (User) session.getAttribute(SessionAttribute.ACCOUNT);
                        mailInfoToSupportBean.setAccountName(user.getEmail());
                        if (checkIsEmptyFields(session, response, mailInfoToSupportBean)) {
                            setUpAttr(session, mailInfoToSupportBean);
                            response.sendRedirect(Pages.SUPPORT_HTML);
                        } else if (!checkIsValidFields(session, response, mailInfoToSupportBean)) {
                            setUpAttr(session, mailInfoToSupportBean);
                            response.sendRedirect(Pages.SUPPORT_HTML);
                        } else {
                            try {
                                MailHelperSupport.sendMail(
                                        mailInfoToSupportBean.getSubject(),
                                        mailInfoToSupportBean.getMessage(),
                                        mailInfoToSupportBean.getAccountName()
                                );
                                cleanSession(session);
                                request.setAttribute(RequestAttribute.SUPPORT_IS_MESSAGE_SENT, true);
                                RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.SUPPORT_MESSAGE_SENT_HTML);
                                requestDispatcher.forward(request, response);
                            } catch (MessagingException e) {
                                setUpAttr(session, mailInfoToSupportBean);
                                LOGGER.info(
                                        "Couldn't send message from : " + mailInfoToSupportBean.getAccountName()
                                                + " to support, subject : " + mailInfoToSupportBean.getSubject()
                                                + " message : " + mailInfoToSupportBean.getMessage()
                                                + " exception : ", e);
                                request.setAttribute(RequestAttribute.SUPPORT_IS_MESSAGE_SENT, false);
                                RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.SUPPORT_MESSAGE_SENT_HTML);
                                requestDispatcher.forward(request, response);
                            }
                        }
                    }
                }
        );
        mailSupportCallableMap.put(
                COMMAND_WITH_ACCOUNT_NAME,
                new MailSupportCallable() {
                    @Override
                    public void call() throws IOException, ServletException {
                        cleanSessionPrg(session);
                        MailInfoToSupportBean mailInfoToSupportBean = getMailInfoToSupportBean(request);
                        mailInfoToSupportBean.setAccountName(request.getParameter(PARAM_ACCOUNT_NAME));
                        if (checkIsEmptyFields(session, response, mailInfoToSupportBean)) {
                            setUpAttr(session, mailInfoToSupportBean);
                            response.sendRedirect(Pages.SUPPORT_HTML);
                        } else if (!checkIsValidFields(session, response, mailInfoToSupportBean)) {
                            setUpAttr(session, mailInfoToSupportBean);
                            response.sendRedirect(Pages.SUPPORT_HTML);
                        } else {
                            try {
                                MailHelperSupport.sendMail(
                                        mailInfoToSupportBean.getSubject(),
                                        mailInfoToSupportBean.getMessage(),
                                        mailInfoToSupportBean.getAccountName()
                                );
                                cleanSession(session);
                                request.setAttribute(RequestAttribute.SUPPORT_IS_MESSAGE_SENT, true);
                                RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.SUPPORT_MESSAGE_SENT_HTML);
                                requestDispatcher.forward(request, response);
                            } catch (MessagingException e) {
                                LOGGER.info(
                                        "Couldn't send message from : " + mailInfoToSupportBean.getAccountName()
                                                + " to support, subject : " + mailInfoToSupportBean.getSubject()
                                                + " message : " + mailInfoToSupportBean.getMessage()
                                                + " exception : ", e);
                                request.setAttribute(RequestAttribute.SUPPORT_IS_MESSAGE_SENT, false);
                                RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.SUPPORT_MESSAGE_SENT_HTML);
                                requestDispatcher.forward(request, response);
                            }
                        }
                    }
                }
        );
    }

    private static MailInfoToSupportBean getMailInfoToSupportBean(HttpServletRequest request) {
        MailInfoToSupportBean mailInfoToSupportBean = new MailInfoToSupportBean();
        String subject = request.getParameter(PARAM_SUBJECT);
        String message = request.getParameter(PARAM_MESSAGE);
        mailInfoToSupportBean.setSubject(subject);
        mailInfoToSupportBean.setMessage(message);
        return mailInfoToSupportBean;
    }

    private static boolean checkIsEmptyFields(HttpSession session, HttpServletResponse response,
                                              MailInfoToSupportBean mailInfoToSupportBean) throws IOException {
        boolean isAnyEmptyField = false;
        if (mailInfoToSupportBean.getAccountName().equals(EMPTY_PARAM) ||
                mailInfoToSupportBean.getSubject().equals(EMPTY_PARAM) ||
                mailInfoToSupportBean.getMessage().equals(EMPTY_PARAM)) {
            isAnyEmptyField = true;
            session.setAttribute(SessionAttribute.SUPPORT_IS_ANY_EMPTY_FIELD, true);
        }
        return isAnyEmptyField;
    }

    private static boolean checkIsValidFields(HttpSession session, HttpServletResponse response,
                                              MailInfoToSupportBean mailInfoToSupportBean) throws IOException {
        boolean isValidFields = true;
        if (!ValidationUtilities.validateEmail(mailInfoToSupportBean.getAccountName())) {
            isValidFields = false;
            session.setAttribute(SessionAttribute.SUPPORT_IS_VALID_ACCOUNT_NAME, false);
        } else {
            session.setAttribute(SessionAttribute.SUPPORT_IS_VALID_ACCOUNT_NAME, true);
        }
        if (mailInfoToSupportBean.getSubject().length() > MAX_SUBJECT_LENGTH) {
            isValidFields = false;
            session.setAttribute(SessionAttribute.SUPPORT_IS_VALID_SUBJECT, false);
        } else {
            session.setAttribute(SessionAttribute.SUPPORT_IS_VALID_SUBJECT, true);
        }
        if (mailInfoToSupportBean.getMessage().length() > MAX_MESSAGE_LENGTH) {
            isValidFields = false;
            session.setAttribute(SessionAttribute.SUPPORT_IS_VALID_MESSAGE, false);
        } else {
            session.setAttribute(SessionAttribute.SUPPORT_IS_VALID_MESSAGE, true);
        }
        return isValidFields;
    }

    private static void setUpAttr(HttpSession session, MailInfoToSupportBean mailInfoToSupportBean) {
        session.setAttribute(SessionAttribute.SUPPORT_ACCOUNT_NAME, mailInfoToSupportBean.getAccountName());
        session.setAttribute(SessionAttribute.SUPPORT_SUBJECT, mailInfoToSupportBean.getSubject());
        session.setAttribute(SessionAttribute.SUPPORT_MESSAGE, mailInfoToSupportBean.getMessage());
    }

    private static void cleanSession(HttpSession session) {
        SessionCleanerUtilities.cleanAttributes(
                session,
                SessionAttribute.SUPPORT_IS_ANY_EMPTY_FIELD,
                SessionAttribute.SUPPORT_IS_VALID_ACCOUNT_NAME,
                SessionAttribute.SUPPORT_IS_VALID_SUBJECT,
                SessionAttribute.SUPPORT_IS_VALID_MESSAGE,
                SessionAttribute.SUPPORT_ACCOUNT_NAME,
                SessionAttribute.SUPPORT_SUBJECT,
                SessionAttribute.SUPPORT_MESSAGE
        );
    }

    private static void cleanSessionPrg(HttpSession session) {
        SessionCleanerUtilities.cleanAttributes(
                session,
                SessionAttribute.SUPPORT_IS_ANY_EMPTY_FIELD,
                SessionAttribute.SUPPORT_IS_VALID_ACCOUNT_NAME,
                SessionAttribute.SUPPORT_IS_VALID_SUBJECT,
                SessionAttribute.SUPPORT_IS_VALID_MESSAGE
        );
    }
}
