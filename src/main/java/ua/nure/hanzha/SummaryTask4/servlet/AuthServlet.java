package ua.nure.hanzha.SummaryTask4.servlet;

import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Validations;
import ua.nure.hanzha.SummaryTask4.db.util.PasswordHash;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.enums.EntrantStatus;
import ua.nure.hanzha.SummaryTask4.enums.Role;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.entrant.EntrantService;
import ua.nure.hanzha.SummaryTask4.service.user.UserService;
import ua.nure.hanzha.SummaryTask4.servlet.callable.auth.AuthOperationsMap;
import ua.nure.hanzha.SummaryTask4.validation.Validation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 30/07/15.
 */
public class AuthServlet extends HttpServlet {

    private static final String EMPTY_PARAM = "";


    private static final String ROLE_ADMIN = "admin";

    private static final String PARAM_ACCOUNT_NAME = "accountName";
    private static final String PARAM_PASSWORD = "password";

    private UserService userService;
    private EntrantService entrantService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute(AppAttribute.USER_SERVICE);
        entrantService = (EntrantService) getServletContext().getAttribute(AppAttribute.ENTRANT_SERVICE);
        AuthOperationsMap.getInstance();

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        cleanBadAttributes(session);
        String email = request.getParameter(PARAM_ACCOUNT_NAME);
        String password = request.getParameter(PARAM_PASSWORD);
        session.setAttribute(SessionAttribute.LOGIN_ACCOUNT_NAME, email);
        session.setAttribute(SessionAttribute.LOGIN_PASSWORD, password);
        boolean isEmptyAnyField = checkEmpty(email, password, session);
        Map<String, Boolean> validationsEmailPassword = Validation.validateLoginAction(email, password);
        boolean isValidFields = checkValidations(validationsEmailPassword, session);
        if (isEmptyAnyField || !isValidFields) {
            response.sendRedirect(Pages.LOGIN_HTML);
        } else {
            try {
                User user = userService.getByEmail(email);
                if (PasswordHash.validatePassword(password, user.getPassword())) {
                    String role = Role.getRole(user).getName();
                    if (role.equals(ROLE_ADMIN)) {
                        session.setAttribute(SessionAttribute.ACCOUNT, user);
                        response.sendRedirect(Pages.INDEX_HTML);
                    } else {
                        int statusId = entrantService.getStatusIdByUserId(user.getId());
                        String status = EntrantStatus.getEntrantStatusById(statusId).getName();
                        AuthOperationsMap.initAuthCallableMap(session, response, user);
                        AuthOperationsMap.getAuthCallable(status).call();
                    }
                } else {
                    session.setAttribute(SessionAttribute.LOGIN_IS_CORRECT_ACCOUNT_NAME_OR_PASSWORD, false);
                    response.sendRedirect(Pages.LOGIN_HTML);
                }
            } catch (DaoSystemException e) {
                session.setAttribute(SessionAttribute.LOGIN_IS_CORRECT_ACCOUNT_NAME_OR_PASSWORD, false);
                response.sendRedirect(Pages.LOGIN_HTML);
            }
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.LOGIN_HTML);
        requestDispatcher.forward(request, response);
    }

    private boolean checkValidations(Map<String, Boolean> validationsEmailPassword, HttpSession session) {
        boolean isValid = true;
        if (!validationsEmailPassword.get(Validations.MAP_KEY_IS_ACCOUNT_NAME_VALID)) {
            isValid = false;
            session.setAttribute(SessionAttribute.LOGIN_IS_ACCOUNT_NAME_VALID, false);
        } else {
            session.setAttribute(SessionAttribute.LOGIN_IS_ACCOUNT_NAME_VALID, true);
        }
        if (!validationsEmailPassword.get(Validations.MAP_KEY_IS_PASSWORD_VALID)) {
            isValid = false;
            session.setAttribute(SessionAttribute.LOGIN_IS_PASSWORD_VALID, false);
        } else {
            session.setAttribute(SessionAttribute.LOGIN_IS_PASSWORD_VALID, true);
        }
        return isValid;
    }

    private boolean checkEmpty(String accountName, String password, HttpSession session) {
        boolean isEmpty = false;
        if (accountName.equals(EMPTY_PARAM)) {
            isEmpty = true;
            session.setAttribute(SessionAttribute.LOGIN_IS_ACCOUNT_NAME_EMPTY, true);
        } else {
            session.setAttribute(SessionAttribute.LOGIN_IS_ACCOUNT_NAME_EMPTY, false);
        }
        if (password.equals(EMPTY_PARAM)) {
            isEmpty = true;
            session.setAttribute(SessionAttribute.LOGIN_IS_PASSWORD_EMPTY, true);
        } else {
            session.setAttribute(SessionAttribute.LOGIN_IS_PASSWORD_EMPTY, false);
        }
        return isEmpty;
    }

    private void cleanBadAttributes(HttpSession session) {
        session.removeAttribute(SessionAttribute.LOGIN_IS_CORRECT_ACCOUNT_NAME_OR_PASSWORD);
    }

}
