package ua.nure.hanzha.SummaryTask4.servlet.callable.mailPublic;

import ua.nure.hanzha.SummaryTask4.bean.*;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.RequestAttribute;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.mail.MailHelperPublic;
import ua.nure.hanzha.SummaryTask4.util.MailMessagesUtilities;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 16/08/15.
 */
public class MailPublicOperationsMap {

    private static final String COMMAND_VERIFY_ACCOUNT = "verifyAccount";
    private static final String COMMAND_RESET_PASSWORD = "resetPassword";
    private static final String COMMAND_UPDATED_PASSWORD = "updatedPassword";
    private static final String ADMIN_COMMAND_BAN_USER = "adminCommandBanUser";
    private static final String ADMIN_COMMAND_UN_BAN_USER = "adminCommandUnBanUser";
    private static final String ADMIN_COMMAND_NOTIFY_ENTRANTS_WHO_PASSED = "notifyEntrantsWhoPassed";

    private static MailPublicOperationsMap mailPublicOperationsMap;
    private static Map<String, MailPublicCallable> mailCallableMap;

    private MailPublicOperationsMap() {

    }

    public static MailPublicCallable getMailCallable(String command) {
        return mailCallableMap.get(command);
    }


    public static MailPublicOperationsMap getInstance() {
        if (mailPublicOperationsMap == null) {
            mailPublicOperationsMap = new MailPublicOperationsMap();
        }
        return mailPublicOperationsMap;
    }

    public static void initMailCallabelMap(final HttpSession session, final HttpServletRequest request, final HttpServletResponse response) {
        mailCallableMap = new HashMap<>();
        mailCallableMap.put(
                COMMAND_VERIFY_ACCOUNT,
                new MailPublicCallable() {
                    @Override
                    public void call() throws IOException {
                        MailInfoVerifyAccountBean mailInfoVerifyAccountBean = (MailInfoVerifyAccountBean)
                                request.getAttribute(RequestAttribute.MAIL_INFO_VERIFY_ACCOUNT_BEAN);
                        String firstName = mailInfoVerifyAccountBean.getFirstName();
                        String lastName = mailInfoVerifyAccountBean.getLastName();
                        String patronymic = mailInfoVerifyAccountBean.getPatronymic();
                        String accountName = mailInfoVerifyAccountBean.getAccountName();
                        String verifyLink = mailInfoVerifyAccountBean.getVerifyLink();
                        String subjectMail = MailMessagesUtilities.createSubjectVerifyAccount();
                        String messageMail = MailMessagesUtilities.createMessageVerifyAccount(firstName, lastName, patronymic, verifyLink);
                        try {
                            MailHelperPublic.sendMail(accountName, subjectMail, messageMail);
                            session.setAttribute(SessionAttribute.VERIFY_ACCOUNT_IS_MESSAGE_SENT, true);
                            response.sendRedirect(Pages.VERIFY_ACCOUNT_MESSAGE_SENT_HTML);
                        } catch (MessagingException e) {
                            e.printStackTrace();
                            session.setAttribute(SessionAttribute.VERIFY_ACCOUNT_IS_MESSAGE_SENT, false);
                            response.sendRedirect(Pages.VERIFY_ACCOUNT_MESSAGE_SENT_HTML);
                        }
                    }
                }
        );
        mailCallableMap.put(
                COMMAND_RESET_PASSWORD,
                new MailPublicCallable() {
                    @Override
                    public void call() throws IOException {
                        MailInfoResetPasswordBean mailInfoResetPasswordBean = (MailInfoResetPasswordBean)
                                request.getAttribute(RequestAttribute.MAIL_INFO_RESET_PASSWORD_BEAN);
                        String firstName = mailInfoResetPasswordBean.getFirstName();
                        String lastName = mailInfoResetPasswordBean.getLastName();
                        String patronymic = mailInfoResetPasswordBean.getPatronymic();
                        String accountName = mailInfoResetPasswordBean.getAccountName();
                        String ticketRecoverPassword = mailInfoResetPasswordBean.getTicketResetPassword();
                        String subjectMail = MailMessagesUtilities.createSubjectRecoverPassword();
                        String messageMail = MailMessagesUtilities.createMessageRecoverPassword(firstName, lastName, patronymic, ticketRecoverPassword);
                        try {
                            MailHelperPublic.sendMail(accountName, subjectMail, messageMail);
                            session.setAttribute(SessionAttribute.CHECK_TICKET_IS_MESSAGE_SENT, true);
                            response.sendRedirect(Pages.RESET_PASSWORD_MESSAGE_SENT_HTML);
                        } catch (MessagingException e) {
                            e.printStackTrace();
                            session.setAttribute(SessionAttribute.CHECK_TICKET_IS_MESSAGE_SENT, false);
                            response.sendRedirect(Pages.RESET_PASSWORD_MESSAGE_SENT_HTML);
                        }
                    }
                }
        );
        mailCallableMap.put(
                COMMAND_UPDATED_PASSWORD,
                new MailPublicCallable() {
                    @Override
                    public void call() throws IOException {
                        MailInfoUpdatedPasswordOrBlockedBean mailInfoUpdatedPasswordOrBlockedBean = (MailInfoUpdatedPasswordOrBlockedBean)
                                request.getAttribute(RequestAttribute.MAIL_INFO_UPDATED_PASSWORD_OR_BLOCKED_BEAN);
                        String firstName = mailInfoUpdatedPasswordOrBlockedBean.getFirstName();
                        String lastName = mailInfoUpdatedPasswordOrBlockedBean.getLastName();
                        String patronymic = mailInfoUpdatedPasswordOrBlockedBean.getPatronymic();
                        String accountName = mailInfoUpdatedPasswordOrBlockedBean.getAccountName();
                        String subjectMail = MailMessagesUtilities.createSubjectUpdatedPassword();
                        String messageMail = MailMessagesUtilities.createMessageUpdatedPassword(firstName, lastName, patronymic);
                        try {
                            MailHelperPublic.sendMail(accountName, subjectMail, messageMail);
                            session.setAttribute(SessionAttribute.RESET_PASSWORD_IS_UPDATED_PASSWORD, true);
                            response.sendRedirect(Pages.RESET_PASSWORD_SUCCESS_HTML);
                        } catch (MessagingException e) {
                            response.sendRedirect(Pages.RESET_PASSWORD_SUCCESS_HTML);
                        }
                    }
                }
        );
        mailCallableMap.put(
                ADMIN_COMMAND_BAN_USER,
                new MailPublicCallable() {
                    @Override
                    public void call() throws IOException {
                        MailInfoUpdatedPasswordOrBlockedBean mailInfoUpdatedPasswordOrBlockedBean = (MailInfoUpdatedPasswordOrBlockedBean)
                                request.getAttribute(RequestAttribute.MAIL_INFO_UPDATED_PASSWORD_OR_BLOCKED_BEAN);
                        int currentPage = (int) session.getAttribute(SessionAttribute.CURRENT_PAGE);
                        String firstName = mailInfoUpdatedPasswordOrBlockedBean.getFirstName();
                        String lastName = mailInfoUpdatedPasswordOrBlockedBean.getLastName();
                        String patronymic = mailInfoUpdatedPasswordOrBlockedBean.getPatronymic();
                        String accountName = mailInfoUpdatedPasswordOrBlockedBean.getAccountName();
                        String subjectMail = MailMessagesUtilities.createSubjectBannedAccount();
                        String messageMail = MailMessagesUtilities.createMessageBannedAccount(firstName, lastName, patronymic);
                        try {
                            MailHelperPublic.sendMail(accountName, subjectMail, messageMail);
                            response.sendRedirect(Pages.ENTRANTS_ADMIN_SERVLET + "?page=" + currentPage);
                        } catch (MessagingException e) {
                            response.sendRedirect(Pages.ENTRANTS_ADMIN_SERVLET + "?page=" + currentPage);
                        }
                    }
                }
        );
        mailCallableMap.put(
                ADMIN_COMMAND_UN_BAN_USER,
                new MailPublicCallable() {
                    @Override
                    public void call() throws IOException {
                        MailInfoUpdatedPasswordOrBlockedBean mailInfoUpdatedPasswordOrBlockedBean = (MailInfoUpdatedPasswordOrBlockedBean)
                                request.getAttribute(RequestAttribute.MAIL_INFO_UPDATED_PASSWORD_OR_BLOCKED_BEAN);
                        int currentPage = (int) session.getAttribute(SessionAttribute.CURRENT_PAGE);
                        String firstName = mailInfoUpdatedPasswordOrBlockedBean.getFirstName();
                        String lastName = mailInfoUpdatedPasswordOrBlockedBean.getLastName();
                        String patronymic = mailInfoUpdatedPasswordOrBlockedBean.getPatronymic();
                        String accountName = mailInfoUpdatedPasswordOrBlockedBean.getAccountName();
                        String subjectMail = MailMessagesUtilities.createSubjectUnBannedAccount();
                        String messageMail = MailMessagesUtilities.createMessageUnBannedAccount(firstName, lastName, patronymic);
                        try {
                            MailHelperPublic.sendMail(accountName, subjectMail, messageMail);
                            response.sendRedirect(Pages.ENTRANTS_ADMIN_SERVLET + "?page=" + currentPage);
                        } catch (MessagingException e) {
                            response.sendRedirect(Pages.ENTRANTS_ADMIN_SERVLET + "?page=" + currentPage);
                        }
                    }
                }
        );
        mailCallableMap.put(
                ADMIN_COMMAND_NOTIFY_ENTRANTS_WHO_PASSED,
                new MailPublicCallable() {
                    @Override
                    public void call() throws IOException {
                        UniversityFinalSheetBean universityFinalSheetBean =
                                (UniversityFinalSheetBean) request.getAttribute(RequestAttribute.UNIVERSITY_FINAL_SHEET_BEAN);
                        List<FacultyFinalSheetBean> allFacultiesFinalSheetBeanList = universityFinalSheetBean.getFacultiesFinalSheetBean();
                        for (FacultyFinalSheetBean facultyFinalSheetBean : allFacultiesFinalSheetBeanList) {
                            List<EntrantFinalSheetBean> budgetEntrantFinalSheetBeanList = facultyFinalSheetBean.getBudgetEntrants();
                            for (EntrantFinalSheetBean entrantFinalSheetBean : budgetEntrantFinalSheetBeanList) {
                                String facultyName = facultyFinalSheetBean.getFacultyName();
                                String firstName = entrantFinalSheetBean.getFirstName();
                                String lastName = entrantFinalSheetBean.getLastName();
                                String patronymic = entrantFinalSheetBean.getPatronymic();
                                String accountName = entrantFinalSheetBean.getAccountName();
                                String subjectMail = MailMessagesUtilities.createSubjectEnterUniversity();
                                String messageMail = MailMessagesUtilities.createMessageCongratulationsBudgetEntrant(firstName, lastName, patronymic, facultyName);
                                try {
                                    MailHelperPublic.sendMail(accountName, subjectMail, messageMail);
                                } catch (MessagingException e) {
                                    e.printStackTrace();
                                    //TODO: LOG4J
                                }
                            }
                            List<EntrantFinalSheetBean> contractEntrantFinalSheetBeanList = facultyFinalSheetBean.getContractEntrants();
                            for (EntrantFinalSheetBean entrantFinalSheetBean : contractEntrantFinalSheetBeanList) {
                                String facultyName = facultyFinalSheetBean.getFacultyName();
                                String firstName = entrantFinalSheetBean.getFirstName();
                                String lastName = entrantFinalSheetBean.getLastName();
                                String patronymic = entrantFinalSheetBean.getPatronymic();
                                String accountName = entrantFinalSheetBean.getAccountName();
                                String subjectMail = MailMessagesUtilities.createSubjectEnterUniversity();
                                String messageMail = MailMessagesUtilities.createMessageCongratulationsContractEntrant(firstName, lastName, patronymic, facultyName);
                                try {
                                    MailHelperPublic.sendMail(accountName, subjectMail, messageMail);
                                } catch (MessagingException e) {
                                    //TODO: LOG4J
                                }
                            }
                        }
                        List<EntrantFinalSheetBean> notPassedEntrants = universityFinalSheetBean.getNotPassedEntrants();
                        for (EntrantFinalSheetBean notPassedEntrant : notPassedEntrants) {
                            String firstName = notPassedEntrant.getFirstName();
                            String lastName = notPassedEntrant.getLastName();
                            String patronymic = notPassedEntrant.getPatronymic();
                            String accountName = notPassedEntrant.getAccountName();
                            String subjectMail = MailMessagesUtilities.createSubjectEnterUniversity();
                            String messageMail = MailMessagesUtilities.createMessageNotPassedEntrant(firstName, lastName, patronymic);
                            try {
                                MailHelperPublic.sendMail(accountName, subjectMail, messageMail);
                            } catch (MessagingException e) {
                                //TODO: LOG4J
                            }
                        }
                        response.sendRedirect(Pages.PUBLIC_FINAL_SHEET_SERVLET + "?page=1");
                    }
                }
        );
    }
}
