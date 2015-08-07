package ua.nure.hanzha.SummaryTask4.servlet;

import ua.nure.hanzha.SummaryTask4.constants.*;
import ua.nure.hanzha.SummaryTask4.db.util.PasswordHash;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.enums.EntrantStatus;
import ua.nure.hanzha.SummaryTask4.enums.Role;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.entrant.EntrantService;
import ua.nure.hanzha.SummaryTask4.service.user.UserService;
import ua.nure.hanzha.SummaryTask4.validation.Validation;

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

    private static final String ENTRANT_STATUS_NOT_VERIFIED = "notverified";
    private static final String ENTRANT_STATUS_BLOCKED = "blocked";
    private static final String ENTRANT_STATUS_ACTIVE = "active";

    private static final String PARAM_ACCOUNT_NAME = "accountName";
    private static final String PARAM_PASSWORD = "password";

    private static final String SESSION_ATTRIBUTE_LOGIN_IS_CORRECT_ACCOUNT_NAME_OR_PASSWORD = "loginIsCorrectAccountNameOrPassword";
    private static final String SESSION_ATTRIBUTE_LOGIN_IS_VERIFIED_ACCOUNT = "loginIsVerifiedAccount";
    private static final String SESSION_ATTRIBUTE_LOGIN_IS_BLOCKED = "loginIsBlocked";


    private UserService userService;
    private EntrantService entrantService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute(AppAttribute.USER_SERVICE);
        entrantService = (EntrantService) getServletContext().getAttribute(AppAttribute.ENTRANT_SERVICE);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String email = request.getParameter(PARAM_ACCOUNT_NAME);
        String password = request.getParameter(PARAM_PASSWORD);
        session.setAttribute(SessionAttribute.LOGIN_ACCOUNT_NAME, email);
        session.setAttribute(SessionAttribute.LOGIN_PASSWORD, password);
        int numberOfEmptyFields = checkEmpty(email, password, session);
        Map<String, Boolean> validationsEmailPassword = Validation.validateLoginAction(email, password);
        int numberOfBadValidations = checkValidations(validationsEmailPassword, session);
        if (numberOfEmptyFields != 0 || numberOfBadValidations != 0) {
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
                        int userId = user.getId();
                        int statusId = entrantService.getStatusIdByUserId(userId);
                        String status = EntrantStatus.getEntrantStatusById(statusId).getName();
                        switch (status) {
                            case ENTRANT_STATUS_ACTIVE:
                                session.setAttribute(SessionAttribute.ACCOUNT, user);
                                response.sendRedirect(Pages.INDEX_HTML);
                                break;
                            case ENTRANT_STATUS_NOT_VERIFIED:
                                session.setAttribute(SESSION_ATTRIBUTE_LOGIN_IS_VERIFIED_ACCOUNT, false);
                                response.sendRedirect(Pages.LOGIN_HTML);
                                break;
                            case ENTRANT_STATUS_BLOCKED:
                                session.setAttribute(SESSION_ATTRIBUTE_LOGIN_IS_BLOCKED, true);
                                response.sendRedirect(Pages.LOGIN_HTML);
                                break;
                            default:
                                throw new DaoSystemException("BAD ROLES");
                        }
                    }
                } else {
                    session.setAttribute(SESSION_ATTRIBUTE_LOGIN_IS_CORRECT_ACCOUNT_NAME_OR_PASSWORD, false);
                    response.sendRedirect(Pages.LOGIN_HTML);
                }
            } catch (DaoSystemException e) {
                session.setAttribute(SESSION_ATTRIBUTE_LOGIN_IS_CORRECT_ACCOUNT_NAME_OR_PASSWORD, false);
                response.sendRedirect(Pages.LOGIN_HTML);
            }
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(Pages.LOGIN_HTML);
    }

    private int checkValidations(Map<String, Boolean> validationsEmailPassword, HttpSession session) {
        int k = 0;
        if (!validationsEmailPassword.get(Validations.MAP_KEY_IS_ACCOUNT_NAME_VALID)) {
            k++;
            session.setAttribute(SessionAttribute.LOGIN_IS_ACCOUNT_NAME_VALID, false);
        } else {
            session.setAttribute(SessionAttribute.LOGIN_IS_ACCOUNT_NAME_VALID, true);
        }
        if (!validationsEmailPassword.get(Validations.MAP_KEY_IS_PASSWORD_VALID)) {
            k++;
            session.setAttribute(SessionAttribute.LOGIN_IS_PASSWORD_VALID, false);
        } else {
            session.setAttribute(SessionAttribute.LOGIN_IS_PASSWORD_VALID, true);
        }
        return k;
    }

    private int checkEmpty(String accountName, String password, HttpSession session) {
        int k = 0;
        if (accountName.equals(EMPTY_PARAM)) {
            session.setAttribute(SessionAttribute.LOGIN_IS_ACCOUNT_NAME_EMPTY, true);
            k++;
        } else {
            session.setAttribute(SessionAttribute.LOGIN_IS_ACCOUNT_NAME_EMPTY, false);
        }
        if (password.equals(EMPTY_PARAM)) {
            session.setAttribute(SessionAttribute.LOGIN_IS_PASSWORD_EMPTY, true);
            k++;
        } else {
            session.setAttribute(SessionAttribute.LOGIN_IS_PASSWORD_EMPTY, false);
        }
        return k;
    }


}
