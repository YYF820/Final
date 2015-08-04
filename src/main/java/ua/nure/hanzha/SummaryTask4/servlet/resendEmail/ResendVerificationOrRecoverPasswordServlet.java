package ua.nure.hanzha.SummaryTask4.servlet.resendEmail;

import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.ExceptionMessages;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.RequestAttribute;
import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.enums.EntrantStatus;
import ua.nure.hanzha.SummaryTask4.enums.Role;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.exception.ServletSystemException;
import ua.nure.hanzha.SummaryTask4.service.entrant.EntrantService;
import ua.nure.hanzha.SummaryTask4.service.user.UserService;
import ua.nure.hanzha.SummaryTask4.validation.Validation;

import javax.servlet.RequestDispatcher;
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
public class ResendVerificationOrRecoverPasswordServlet extends HttpServlet {

    private static final String EMPTY_PARAM = "";

    private static final String ROLE_ADMIN = "admin";
    private static final String STATUS_ACTIVE = "active";
    private static final String STATUS_BLOCKED = "blocked";
    private static final String STATUS_NOT_VERIFIED = "notverified";

    private static final String PARAM_ACCOUNT_NAME = "accountName";
    private static final String PARAM_COMMAND = "command";
    private static final String REQUEST_ATTRIBUTE_IS_USER_EXISTS_BY_ACCOUNT_NAME = "isUserExistsByAccountName";
    private static final String REQUEST_ATTRIBUTE_IS_ADMIN_TRYING_VERIFY_ACCOUNT = "isAdminTryingVerifyAccount";
    private static final String REQUEST_ATTRIBUTE_IS_ACTIVE_ACCOUNT = "isActiveAccount";
    private static final String REQUEST_ATTRIBUTE_IS_BLOCKED_ACCOUNT = "isBlockedAccount";
    private static final String SESSION_ATTRIBUTE_ENTRANT_FOR_VERIFY_ACCOUNT_RESET_PASSWORD = "entrantForVerifyAccountResetPassword";
    private static final String SESSION_ATTRIBUTE_USER_FOR_VERIFY_ACCOUNT_RESET_PASSWORD = "userForVerifyAccountResetPassword";
    private static final String SESSION_ATTRIBUTE_COMMAND = "command";

    private static final String COMMAND_VERIFY_ACCOUNT = "verifyAccount";
    private static final String COMMAND_RECOVER_PASSWORD = "recoverPassword";


    private UserService userService;
    private EntrantService entrantService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute(AppAttribute.USER_SERVICE);
        entrantService = (EntrantService) getServletContext().getAttribute(AppAttribute.ENTRANT_SERVICE);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String accountName = request.getParameter(PARAM_ACCOUNT_NAME);
        String command = request.getParameter(PARAM_COMMAND);
        request.setAttribute(RequestAttribute.ACCOUNT_NAME, accountName);
        boolean isAccountNameValid = checkValidationAccountName(request, accountName);
        boolean isAccountNameEmpty = checkEmptyFields(request, accountName);
        if (isAccountNameEmpty || !isAccountNameValid) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.RESEND_VERIFICATION_OR_RECOVER_PASSWORD_HTML);
            requestDispatcher.forward(request, response);
        } else {
            try {
                User userForResendVerifyMessage = userService.selectByEmail(accountName);
                String userRole = Role.getRole(userForResendVerifyMessage).getName();
                if (userRole.equals(ROLE_ADMIN)) {
                    request.setAttribute(REQUEST_ATTRIBUTE_IS_ADMIN_TRYING_VERIFY_ACCOUNT, true);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.RESEND_VERIFICATION_OR_RECOVER_PASSWORD_HTML);
                    requestDispatcher.forward(request, response);
                } else {
                    int userId = userForResendVerifyMessage.getId();
                    Entrant entrantForResendVerifyMessage = entrantService.selectByUserId(userId);
                    String entrantStatus = EntrantStatus.getEntrantStatus(entrantForResendVerifyMessage).getName();
                    if (command.equals(COMMAND_VERIFY_ACCOUNT)) {
                        switch (entrantStatus) {
                            case STATUS_ACTIVE:
                                request.setAttribute(REQUEST_ATTRIBUTE_IS_ACTIVE_ACCOUNT, true);
                                RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.RESEND_VERIFICATION_OR_RECOVER_PASSWORD_HTML);
                                requestDispatcher.forward(request, response);
                                break;
                            case STATUS_BLOCKED:
                                request.setAttribute(REQUEST_ATTRIBUTE_IS_BLOCKED_ACCOUNT, true);
                                requestDispatcher = request.getRequestDispatcher(Pages.RESEND_VERIFICATION_OR_RECOVER_PASSWORD_HTML);
                                requestDispatcher.forward(request, response);
                                break;
                            case STATUS_NOT_VERIFIED:
                                HttpSession session = request.getSession(true);
                                session.setAttribute(SESSION_ATTRIBUTE_ENTRANT_FOR_VERIFY_ACCOUNT_RESET_PASSWORD, entrantForResendVerifyMessage);
                                session.setAttribute(SESSION_ATTRIBUTE_USER_FOR_VERIFY_ACCOUNT_RESET_PASSWORD, userForResendVerifyMessage);
                                session.setAttribute(SESSION_ATTRIBUTE_COMMAND, command);
                                requestDispatcher = request.getRequestDispatcher(Pages.CHECK_QUESTION_HTML);
                                requestDispatcher.forward(request, response);
                                break;
                            default:
                                throw new ServletSystemException("No statuses are match smth wrong...");
                        }
                    } else {
                        if (entrantStatus.equals(STATUS_BLOCKED)) {
                            request.setAttribute(REQUEST_ATTRIBUTE_IS_BLOCKED_ACCOUNT, true);
                            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.RESEND_VERIFICATION_OR_RECOVER_PASSWORD_HTML);
                            requestDispatcher.forward(request, response);
                        } else {
                            HttpSession session = request.getSession(true);
                            session.setAttribute(SESSION_ATTRIBUTE_ENTRANT_FOR_VERIFY_ACCOUNT_RESET_PASSWORD, entrantForResendVerifyMessage);
                            session.setAttribute(SESSION_ATTRIBUTE_USER_FOR_VERIFY_ACCOUNT_RESET_PASSWORD, userForResendVerifyMessage);
                            session.setAttribute(SESSION_ATTRIBUTE_COMMAND, command);
                            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.CHECK_QUESTION_HTML);
                            requestDispatcher.forward(request, response);
                        }
                    }
                }
            } catch (DaoSystemException e) {
                if (e.getMessage().equals(ExceptionMessages.SELECT_BY_SOME_VALUE_EXCEPTION_MESSAGE)) {
                    request.setAttribute(REQUEST_ATTRIBUTE_IS_USER_EXISTS_BY_ACCOUNT_NAME, false);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.RESEND_VERIFICATION_OR_RECOVER_PASSWORD_HTML);
                    requestDispatcher.forward(request, response);
                }
            } catch (ServletSystemException e) {
                e.printStackTrace();
            }
        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(Pages.RESEND_VERIFICATION_OR_RECOVER_PASSWORD_HTML);
    }

    private boolean checkValidationAccountName(HttpServletRequest request, String accountName) {
        boolean isAccountNameValid = Validation.validateEmail(accountName);
        if (!isAccountNameValid) {
            request.setAttribute(RequestAttribute.IS_ACCOUNT_NAME_VALID, false);
        }
        return isAccountNameValid;
    }

    private boolean checkEmptyFields(HttpServletRequest request, String accountName) {
        boolean isAccountNameEmpty = false;
        if (accountName.equals(EMPTY_PARAM)) {
            isAccountNameEmpty = true;
            request.setAttribute(RequestAttribute.IS_ACCOUNT_NAME_NULL, true);
        }
        return isAccountNameEmpty;
    }
}

