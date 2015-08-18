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
import ua.nure.hanzha.SummaryTask4.service.entrant.EntrantService;
import ua.nure.hanzha.SummaryTask4.service.user.UserService;
import ua.nure.hanzha.SummaryTask4.servlet.callable.checkEmail.CheckEmailStatusCallable;
import ua.nure.hanzha.SummaryTask4.servlet.callable.checkEmail.CheckEmailStatusOperationsMap;
import ua.nure.hanzha.SummaryTask4.util.SessionCleanerUtilities;
import ua.nure.hanzha.SummaryTask4.util.ValidationUtilities;

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

    private static final String PARAM_ACCOUNT_NAME = "accountName";
    private static final String PARAM_COMMAND = "command";
    private static final String STATUS_BLOCKED = "blocked";
    private static final String SESSION_ATTRIBUTE_COMMAND = "command";

    private static final String COMMAND_VERIFY_ACCOUNT = "verifyAccount";
    private static final String COMMAND_RESET_PASSWORD = "resetPassword";


    private UserService userService;
    private EntrantService entrantService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute(AppAttribute.USER_SERVICE);
        entrantService = (EntrantService) getServletContext().getAttribute(AppAttribute.ENTRANT_SERVICE);
        CheckEmailStatusOperationsMap.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        cleanSession(session);
        String accountName = request.getParameter(PARAM_ACCOUNT_NAME);
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
                if (userRole.equals(ROLE_ADMIN) && command != null && !command.equals(COMMAND_RESET_PASSWORD)) {
                    session.setAttribute(SessionAttribute.RESEND_IS_ADMIN_TRYING_VERIFY_ACCOUNT, true);
                    response.sendRedirect(Pages.RESEND_VERIFICATION_OR_RESET_PASSWORD_HTML + "?" + PARAM_COMMAND + "=" + command);
                } else {
                    int userId = userForResendVerifyMessage.getId();
                    Entrant entrantForResendVerifyMessage = entrantService.getByUserId(userId);
                    String entrantStatus = EntrantStatus.getEntrantStatus(entrantForResendVerifyMessage).getName();
                    if (command != null && command.equals(COMMAND_VERIFY_ACCOUNT)) {
                        CheckEmailStatusOperationsMap.initCheckEmailCallableMap(session, response,
                                userForResendVerifyMessage, entrantForResendVerifyMessage);
                        CheckEmailStatusCallable checkEmailStatusCallable =
                                CheckEmailStatusOperationsMap.getCheckEmailStatusCallable(entrantStatus);
                        if (checkEmailStatusCallable != null) {
                            cleanSession(session);
                            checkEmailStatusCallable.call(entrantStatus);
                        } else {
                            //UNSUPPORTED STATUS
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        }
                    } else if (command != null && command.equals(COMMAND_RESET_PASSWORD)) {
                        if (entrantStatus.equals(STATUS_BLOCKED)) {
                            session.setAttribute(SessionAttribute.RESEND_IS_BLOCKED_ACCOUNT, true);
                            response.sendRedirect(Pages.RESEND_VERIFICATION_OR_RESET_PASSWORD_HTML + "?" + PARAM_COMMAND + "=" + command);
                        } else {
                            session.setAttribute(SessionAttribute.ENTRANT_FOR_VERIFY_ACCOUNT_RESET_PASSWORD, entrantForResendVerifyMessage);
                            session.setAttribute(SessionAttribute.USER_FOR_VERIFY_ACCOUNT_RESET_PASSWORD, userForResendVerifyMessage);
                            session.setAttribute(SESSION_ATTRIBUTE_COMMAND, command);
                            cleanSession(session);
                            response.sendRedirect(Pages.CHECK_QUESTION_HTML);
                        }
                    } else {
                        //UNSUPPORTED COMMAND
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    }
                }
            } catch (DaoSystemException e) {
                if (e.getMessage().equals(ExceptionMessages.SELECT_BY_SOME_VALUE_EXCEPTION_MESSAGE) ||
                        e.getMessage().equals(ExceptionMessages.SELECT_BY_ID_EXCEPTION_MESSAGE)) {
                    session.setAttribute(SessionAttribute.RESEND_IS_USER_EXISTS_BY_ACCOUNT_NAME, false);
                    response.sendRedirect(Pages.RESEND_VERIFICATION_OR_RESET_PASSWORD_HTML + "?" + PARAM_COMMAND + "=" + command);
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(Pages.RESEND_VERIFICATION_OR_RESET_PASSWORD_HTML);
    }

    private boolean checkValidationAccountName(HttpSession session, String accountName) {
        boolean isAccountNameValid = ValidationUtilities.validateEmail(accountName);
        if (!isAccountNameValid) {
            session.setAttribute(SessionAttribute.RESEND_IS_ACCOUNT_NAME_VALID, false);
        }
        return isAccountNameValid;
    }

    private boolean checkEmptyFields(HttpSession session, String accountName) {
        boolean isAccountNameEmpty = false;
        if (accountName.equals(EMPTY_PARAM)) {
            isAccountNameEmpty = true;
            session.setAttribute(SessionAttribute.RESEND_IS_ACCOUNT_NAME_EMPTY, true);
        }
        return isAccountNameEmpty;
    }

    private void cleanSession(HttpSession session) {
        SessionCleanerUtilities.cleanAttributes(
                session,
                SessionAttribute.RESEND_IS_ACCOUNT_NAME_EMPTY,
                SessionAttribute.RESEND_IS_ACCOUNT_NAME_VALID,
                SessionAttribute.RESEND_IS_ACTIVE_ACCOUNT,
                SessionAttribute.RESEND_IS_BLOCKED_ACCOUNT,
                SessionAttribute.RESEND_IS_ADMIN_TRYING_VERIFY_ACCOUNT,
                SessionAttribute.RESEND_IS_USER_EXISTS_BY_ACCOUNT_NAME,
                SessionAttribute.RESEND_ACCOUNT_NAME
        );
    }
}

