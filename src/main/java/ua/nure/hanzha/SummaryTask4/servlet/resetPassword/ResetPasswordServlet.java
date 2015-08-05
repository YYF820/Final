package ua.nure.hanzha.SummaryTask4.servlet.resetPassword;

import ua.nure.hanzha.SummaryTask4.bean.MailInfoUpdatedPasswordBean;
import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.ExceptionMessages;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.RequestAttribute;
import ua.nure.hanzha.SummaryTask4.db.util.PasswordHash;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
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
 *         Created by faffi-ubuntu on 04/08/15.
 */
public class ResetPasswordServlet extends HttpServlet {

    private static final String EMPTY_PARAM = "";
    private static final String PARAM_PASSWORD = "password";
    private static final String CONFIRM_PASSWORD = "confirmPassword";

    private static final String REQUEST_ATTRIBUTE_IS_CONFIRM_PASSWORD_EMPTY = "isConfirmPasswordEmpty";
    private static final String REQUEST_ATTRIBUTE_IS_SAME_PASSWORDS = "isPasswordsSame";
    private static final String REQUEST_ATTRIBUTE_IS_FROM_RESET_PASSWORD_SERVLET = "isFromResetPasswordServlet";
    private static final String REQUEST_ATTRIBUTE_IS_UPDATED_PASSWORD = "isUpdatedPassword";

    private static final String SESSION_ATTRIBUTE_USER_FOR_VERIFY_ACCOUNT_RESET_PASSWORD = "userForVerifyAccountResetPassword";
    private static final String SESSION_ATTRIBUTE_COMMAND = "command";

    private static final String COMMAND_UPDATED_PASSWORD = "updatedPassword";


    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute(AppAttribute.USER_SERVICE);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter(PARAM_PASSWORD);
        String confirmPassword = request.getParameter(CONFIRM_PASSWORD);
        request.setAttribute(REQUEST_ATTRIBUTE_IS_FROM_RESET_PASSWORD_SERVLET, true);
        boolean isEmpty = checkEmpty(request, password, confirmPassword);
        boolean isEquals = checkEquals(request, password, confirmPassword);
        boolean isValidPasswords = checkValidationPasswords(request, password, confirmPassword);
        if (isEmpty || !isEquals || !isValidPasswords) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.RESET_PASSWORD_HTML);
            requestDispatcher.forward(request, response);
        } else {
            HttpSession session = request.getSession(false);
            User userForUpdatePassword = (User) session.getAttribute(SESSION_ATTRIBUTE_USER_FOR_VERIFY_ACCOUNT_RESET_PASSWORD);
            int userId = userForUpdatePassword.getId();
            String hashPassword = PasswordHash.createHash(password);
            try {
                userService.updatePasswordById(userId, hashPassword);
                session.setAttribute(SESSION_ATTRIBUTE_COMMAND, COMMAND_UPDATED_PASSWORD);
                String firstName = userForUpdatePassword.getFirstName();
                String lastName = userForUpdatePassword.getLastName();
                String patronymic = userForUpdatePassword.getPatronymic();
                String accountName = userForUpdatePassword.getEmail();
                prepareInfoUpdatedPasswordEmail(request, firstName, lastName, patronymic, accountName);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.MAIL_SENDER_SERVLET);
                requestDispatcher.forward(request, response);
            } catch (DaoSystemException e) {
                e.printStackTrace();
                if (e.getMessage().equals(ExceptionMessages.UPDATE_EXCEPTION_MESSAGE)) {
                    //TODO: Add exception page like 500 or notification about account was deleted. 05.08.2015
                } else {
                    //TODO 500 exception SQLException 05.08.2015
                }
            }

        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(Pages.RESEND_VERIFICATION_OR_RESET_PASSWORD_HTML);
    }

    private boolean checkEmpty(HttpServletRequest request, String password, String confirmPassword) {
        boolean isEmpty = false;
        if (password.equals(EMPTY_PARAM)) {
            isEmpty = true;
            request.setAttribute(RequestAttribute.IS_PASSWORD_EMPTY, true);
        }
        if (confirmPassword.equals(EMPTY_PARAM)) {
            isEmpty = true;
            request.setAttribute(REQUEST_ATTRIBUTE_IS_CONFIRM_PASSWORD_EMPTY, true);
        }
        return isEmpty;
    }

    private boolean checkEquals(HttpServletRequest request, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            request.setAttribute(REQUEST_ATTRIBUTE_IS_SAME_PASSWORDS, false);
            return false;
        }
        return true;
    }

    private boolean checkValidationPasswords(HttpServletRequest request, String password, String confirmPassword) {
        boolean isValidPassword = Validation.validatePassword(password);
        boolean isValidConfirmPassword = Validation.validatePassword(confirmPassword);
        if (!isValidPassword || !isValidConfirmPassword) {
            request.setAttribute(RequestAttribute.IS_PASSWORD_VALID, false);
            return false;
        }
        return true;
    }

    private void prepareInfoUpdatedPasswordEmail(HttpServletRequest request, String firstName, String lastName,
                                                 String patronymic, String accountName) {

        MailInfoUpdatedPasswordBean mailInfoUpdatedPasswordBean = new MailInfoUpdatedPasswordBean();

        mailInfoUpdatedPasswordBean.setFirstName(firstName);
        mailInfoUpdatedPasswordBean.setLastName(lastName);
        mailInfoUpdatedPasswordBean.setPatronymic(patronymic);
        mailInfoUpdatedPasswordBean.setAccountName(accountName);

        request.setAttribute(RequestAttribute.MAIL_INFO_UPDATED_PASSWORD_BEAN, mailInfoUpdatedPasswordBean);
    }
}
