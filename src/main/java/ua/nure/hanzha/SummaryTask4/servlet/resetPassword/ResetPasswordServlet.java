package ua.nure.hanzha.SummaryTask4.servlet.resetPassword;

import ua.nure.hanzha.SummaryTask4.bean.MailInfoUpdatedPasswordOrBlockedBean;
import ua.nure.hanzha.SummaryTask4.constants.*;
import ua.nure.hanzha.SummaryTask4.db.util.HashUtilities;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.user.UserService;
import ua.nure.hanzha.SummaryTask4.util.SessionCleanerUtilities;
import ua.nure.hanzha.SummaryTask4.util.ValidationUtilities;

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
    private static final String PARAM_CONFIRM_PASSWORD = "confirmPassword";

    private static final String SESSION_ATTRIBUTE_COMMAND = "command";

    private static final String COMMAND_UPDATED_PASSWORD = "updatedPassword";


    private UserService userService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute(AppAttribute.USER_SERVICE);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String password = request.getParameter(PARAM_PASSWORD);
        String confirmPassword = request.getParameter(PARAM_CONFIRM_PASSWORD);
        boolean isEmpty = checkEmpty(session, password, confirmPassword);
        boolean isEquals = checkEquals(session, password, confirmPassword);
        boolean isValidPasswords = checkValidationPasswords(session, password, confirmPassword);
        if (isEmpty || !isEquals || !isValidPasswords) {
            response.sendRedirect(Pages.RESET_PASSWORD_HTML);
        } else {
            User userForUpdatePassword = (User) session.getAttribute(SessionAttribute.USER_FOR_VERIFY_ACCOUNT_RESET_PASSWORD);
            String hashPassword = HashUtilities.createHash(password);
            try {
                userService.updatePasswordById(userForUpdatePassword.getId(), hashPassword);
                session.setAttribute(SESSION_ATTRIBUTE_COMMAND, COMMAND_UPDATED_PASSWORD);
                prepareInfoUpdatedPasswordEmail(request, userForUpdatePassword);
                cleanSession(session);
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

    private boolean checkEmpty(HttpSession session, String password, String confirmPassword) {
        boolean isEmpty = false;
        if (password.equals(EMPTY_PARAM)) {
            isEmpty = true;
            session.setAttribute(SessionAttribute.RESET_PASSWORD_IS_PASSWORD_EMPTY, true);
        } else {
            session.setAttribute(SessionAttribute.RESET_PASSWORD_IS_PASSWORD_EMPTY, false);
        }
        if (confirmPassword.equals(EMPTY_PARAM)) {
            isEmpty = true;
            session.setAttribute(SessionAttribute.RESET_PASSWORD_IS_CONFIRM_PASSWORD_EMPTY, true);
        } else {
            session.setAttribute(SessionAttribute.RESET_PASSWORD_IS_CONFIRM_PASSWORD_EMPTY, false);
        }
        return isEmpty;
    }

    private boolean checkEquals(HttpSession session, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            session.setAttribute(SessionAttribute.RESET_PASSWORD_IS_SAME_PASSWORDS, false);
            return false;
        }
        session.setAttribute(SessionAttribute.RESET_PASSWORD_IS_SAME_PASSWORDS, true);
        return true;
    }

    private boolean checkValidationPasswords(HttpSession session, String password, String confirmPassword) {
        boolean isValidPassword = ValidationUtilities.validatePassword(password);
        boolean isValidConfirmPassword = ValidationUtilities.validatePassword(confirmPassword);
        if (!isValidPassword || !isValidConfirmPassword) {
            session.setAttribute(SessionAttribute.RESET_PASSWORD_IS_PASSWORD_VALID, false);
            return false;
        }
        session.setAttribute(SessionAttribute.RESET_PASSWORD_IS_PASSWORD_VALID, true);
        return true;
    }

    private void prepareInfoUpdatedPasswordEmail(HttpServletRequest request, User user) {

        MailInfoUpdatedPasswordOrBlockedBean mailInfoUpdatedPasswordOrBlockedBean = new MailInfoUpdatedPasswordOrBlockedBean();

        mailInfoUpdatedPasswordOrBlockedBean.setFirstName(user.getFirstName());
        mailInfoUpdatedPasswordOrBlockedBean.setLastName(user.getLastName());
        mailInfoUpdatedPasswordOrBlockedBean.setPatronymic(user.getPatronymic());
        mailInfoUpdatedPasswordOrBlockedBean.setAccountName(user.getEmail());

        request.setAttribute(RequestAttribute.MAIL_INFO_UPDATED_PASSWORD_OR_BLOCKED_BEAN, mailInfoUpdatedPasswordOrBlockedBean);
    }

    private void cleanSession(HttpSession session) {
        SessionCleanerUtilities.cleanAttributes(
                session,
                SessionAttribute.RESET_PASSWORD_IS_CONFIRM_PASSWORD_EMPTY,
                SessionAttribute.RESET_PASSWORD_IS_SAME_PASSWORDS,
                SessionAttribute.RESET_PASSWORD_IS_PASSWORD_VALID
        );
    }
}
