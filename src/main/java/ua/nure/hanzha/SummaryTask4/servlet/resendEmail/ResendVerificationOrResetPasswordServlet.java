package ua.nure.hanzha.SummaryTask4.servlet.resendEmail;

import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.ExceptionMessages;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.enums.EntrantStatus;
import ua.nure.hanzha.SummaryTask4.enums.Role;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.exception.ServletSystemException;
import ua.nure.hanzha.SummaryTask4.service.entrant.EntrantService;
import ua.nure.hanzha.SummaryTask4.service.user.UserService;
import ua.nure.hanzha.SummaryTask4.validation.Validation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 03/08/15.
 */
public class ResendVerificationOrResetPasswordServlet extends HttpServlet {

    private static final String EMPTY_PARAM = "";

    private static final String ROLE_ADMIN = "admin";
    private static final String STATUS_ACTIVE = "active";
    private static final String STATUS_BLOCKED = "blocked";
    private static final String STATUS_NOT_VERIFIED = "notverified";

    private static final String PARAM_ACCOUNT_NAME = "accountName";
    private static final String PARAM_COMMAND = "command";

    private static final String SESSION_ATTRIBUTE_COMMAND = "command";

    private static final String COMMAND_VERIFY_ACCOUNT = "verifyAccount";
    private static final String COMMAND_RESET_PASSWORD = "resetPassword";


    private UserService userService;
    private EntrantService entrantService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute(AppAttribute.USER_SERVICE);
        entrantService = (EntrantService) getServletContext().getAttribute(AppAttribute.ENTRANT_SERVICE);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String accountName = request.getParameter(PARAM_ACCOUNT_NAME);
        System.out.println(accountName);
        String command = request.getParameter(PARAM_COMMAND);
        session.setAttribute(SessionAttribute.RESEND_ACCOUNT_NAME, accountName);
        boolean isAccountNameValid = checkValidationAccountName(session, accountName);
        boolean isAccountNameEmpty = checkEmptyFields(session, accountName);
        if (isAccountNameEmpty || !isAccountNameValid) {
            response.sendRedirect(Pages.RESEND_VERIFICATION_OR_RESET_PASSWORD_HTML + "?" + PARAM_COMMAND + "=" + command);
        } else {
            try {
                User userForResendVerifyMessage = userService.getByEmail(accountName);
                String userRole = Role.getRole(userForResendVerifyMessage).getName();
                if (userRole.equals(ROLE_ADMIN) && !command.equals(COMMAND_RESET_PASSWORD)) {
                    session.setAttribute(SessionAttribute.RESEND_IS_ADMIN_TRYING_VERIFY_ACCOUNT, true);
                    response.sendRedirect(Pages.RESEND_VERIFICATION_OR_RESET_PASSWORD_HTML + "?" + PARAM_COMMAND + "=" + command);
                } else {
                    int userId = userForResendVerifyMessage.getId();
                    Entrant entrantForResendVerifyMessage = entrantService.getByUserId(userId);
                    String entrantStatus = EntrantStatus.getEntrantStatus(entrantForResendVerifyMessage).getName();
                    if (command.equals(COMMAND_VERIFY_ACCOUNT)) {
                        switch (entrantStatus) {
                            case STATUS_ACTIVE:
                                session.setAttribute(SessionAttribute.RESEND_IS_ACTIVE_ACCOUNT, true);
                                response.sendRedirect(Pages.RESEND_VERIFICATION_OR_RESET_PASSWORD_HTML + "?" + PARAM_COMMAND + "=" + command);
                                break;
                            case STATUS_BLOCKED:
                                session.setAttribute(SessionAttribute.RESEND_IS_BLOCKED_ACCOUNT, true);
                                response.sendRedirect(Pages.RESEND_VERIFICATION_OR_RESET_PASSWORD_HTML + "?" + PARAM_COMMAND + "=" + command);
                                break;
                            case STATUS_NOT_VERIFIED:
                                session.setAttribute(SessionAttribute.ENTRANT_FOR_VERIFY_ACCOUNT_RESET_PASSWORD, entrantForResendVerifyMessage);
                                session.setAttribute(SessionAttribute.USER_FOR_VERIFY_ACCOUNT_RESET_PASSWORD, userForResendVerifyMessage);
                                session.setAttribute(SESSION_ATTRIBUTE_COMMAND, command);
                                response.sendRedirect(Pages.CHECK_QUESTION_HTML);
                                break;
                            default:
                                throw new ServletSystemException("No statuses are match smth wrong...");
                        }
                    } else if (command.equals(COMMAND_RESET_PASSWORD)) {
                        if (entrantStatus.equals(STATUS_BLOCKED)) {
                            session.setAttribute(SessionAttribute.RESEND_IS_BLOCKED_ACCOUNT, true);
                            response.sendRedirect(Pages.RESEND_VERIFICATION_OR_RESET_PASSWORD_HTML + "?" + PARAM_COMMAND + "=" + command);
                        } else {
                            session.setAttribute(SessionAttribute.ENTRANT_FOR_VERIFY_ACCOUNT_RESET_PASSWORD, entrantForResendVerifyMessage);
                            session.setAttribute(SessionAttribute.USER_FOR_VERIFY_ACCOUNT_RESET_PASSWORD, userForResendVerifyMessage);
                            session.setAttribute(SESSION_ATTRIBUTE_COMMAND, command);
                            response.sendRedirect(Pages.CHECK_QUESTION_HTML);
                        }
                    }
                }
            } catch (DaoSystemException e) {
                if (e.getMessage().equals(ExceptionMessages.SELECT_BY_ID_EXCEPTION_MESSAGE)) {
                    session.setAttribute(SessionAttribute.RESEND_IS_USER_EXISTS_BY_ACCOUNT_NAME, false);
                    response.sendRedirect(Pages.RESEND_VERIFICATION_OR_RESET_PASSWORD_HTML + "?" + PARAM_COMMAND + "=" + command);
                }
            } catch (ServletSystemException e) {
                e.printStackTrace();
            }
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(Pages.RESEND_VERIFICATION_OR_RESET_PASSWORD_HTML);
    }

    private boolean checkValidationAccountName(HttpSession session, String accountName) {
        boolean isAccountNameValid = Validation.validateEmail(accountName);
        if (!isAccountNameValid) {
            session.setAttribute(SessionAttribute.RESEND_IS_ACCOUNT_NAME_VALID, false);
        } else {
            session.setAttribute(SessionAttribute.RESEND_IS_ACCOUNT_NAME_VALID, true);
        }
        return isAccountNameValid;
    }

    private boolean checkEmptyFields(HttpSession session, String accountName) {
        boolean isAccountNameEmpty = false;
        if (accountName.equals(EMPTY_PARAM)) {
            isAccountNameEmpty = true;
            session.setAttribute(SessionAttribute.RESEND_IS_ACCOUNT_NAME_EMPTY, true);
        } else {
            session.setAttribute(SessionAttribute.RESEND_IS_ACCOUNT_NAME_EMPTY, false);
        }
        return isAccountNameEmpty;
    }
}

