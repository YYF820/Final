package ua.nure.hanzha.SummaryTask4.servlet;

import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.RequestAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Validations;
import ua.nure.hanzha.SummaryTask4.db.util.PasswordHash;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.enums.EntrantStatus;
import ua.nure.hanzha.SummaryTask4.enums.Role;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.entrant.EntrantService;
import ua.nure.hanzha.SummaryTask4.service.user.UserService;
import ua.nure.hanzha.SummaryTask4.validation.Validation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    private static final String REQUEST_ATTRIBUTE_IS_CORRECT_ACCOUNT_NAME_OR_PASSWORD = "isCorrectAccountNameOrPassword";
    private static final String REQUEST_ATTRIBUTE_IS_VERIFIED_ACCOUNT = "isVerifiedAccount";
    private static final String REQUEST_ATTRIBUTE_IS_BLOCKED = "isBlocked";


    private UserService userService;
    private EntrantService entrantService;

    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute(AppAttribute.USER_SERVICE);
        entrantService = (EntrantService) getServletContext().getAttribute(AppAttribute.ENTRANT_SERVICE);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter(PARAM_ACCOUNT_NAME);
        String password = request.getParameter(PARAM_PASSWORD);
        request.setAttribute(RequestAttribute.ACCOUNT_NAME, email);
        request.setAttribute(RequestAttribute.PASSWORD, password);
        int numberOfNulls = checkNull(email, password, request);
        Map<String, Boolean> validationsEmailPassword = Validation.validateLoginAction(email, password);
        int numberOfBadValidations = checkValidations(validationsEmailPassword, request);
        if (numberOfNulls != 0 || numberOfBadValidations != 0) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.LOGIN_HTML);
            requestDispatcher.forward(request, response);
        } else {
            try {
                User user = userService.selectByEmail(email);
                if (PasswordHash.validatePassword(password, user.getPassword())) {
                    String role = Role.getRole(user).getName();
                    if (role.equals(ROLE_ADMIN)) {
                        response.sendRedirect(Pages.INDEX_HTML);
                    } else {
                        int userId = user.getId();
                        int statusId = entrantService.selectStatusIdByUserId(userId);
                        String status = EntrantStatus.getEntrantStatusById(statusId).getName();
                        switch (status) {
                            case ENTRANT_STATUS_ACTIVE:
                                response.sendRedirect(Pages.INDEX_HTML);
                                break;
                            case ENTRANT_STATUS_NOT_VERIFIED:
                                request.setAttribute(REQUEST_ATTRIBUTE_IS_VERIFIED_ACCOUNT, false);
                                RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.LOGIN_HTML);
                                requestDispatcher.forward(request, response);
                                break;
                            case ENTRANT_STATUS_BLOCKED:
                                request.setAttribute(REQUEST_ATTRIBUTE_IS_BLOCKED, true);
                                requestDispatcher = request.getRequestDispatcher(Pages.LOGIN_HTML);
                                requestDispatcher.forward(request, response);
                                break;
                            default:
                                throw new DaoSystemException("BAD ROLES");
                        }
                    }
                } else {
                    request.setAttribute(REQUEST_ATTRIBUTE_IS_CORRECT_ACCOUNT_NAME_OR_PASSWORD, false);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.LOGIN_HTML);
                    requestDispatcher.forward(request, response);
                }
            } catch (DaoSystemException e) {
                request.setAttribute(REQUEST_ATTRIBUTE_IS_CORRECT_ACCOUNT_NAME_OR_PASSWORD, false);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.LOGIN_HTML);
                requestDispatcher.forward(request, response);
            }
        }
    }


    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(Pages.LOGIN_HTML);
    }

    private int checkValidations(Map<String, Boolean> validationsEmailPassword, HttpServletRequest request) {
        int k = 0;
        if (!validationsEmailPassword.get(Validations.MAP_KEY_IS_ACCOUNT_NAME_VALID)) {
            k++;
            request.setAttribute(RequestAttribute.IS_ACCOUNT_NAME_VALID, false);
        } else {
            request.setAttribute(RequestAttribute.IS_ACCOUNT_NAME_VALID, true);
        }
        if (!validationsEmailPassword.get(Validations.MAP_KEY_IS_PASSWORD_VALID)) {
            k++;
            request.setAttribute(RequestAttribute.IS_PASSWORD_VALID, false);
        } else {
            request.setAttribute(RequestAttribute.IS_PASSWORD_VALID, true);
        }
        return k;
    }

    private int checkNull(String accountName, String password, HttpServletRequest request) {
        int k = 0;
        if (accountName.equals(EMPTY_PARAM)) {
            request.setAttribute(RequestAttribute.IS_ACCOUNT_NAME_NULL, true);
            k++;
        } else {
            request.setAttribute(RequestAttribute.IS_ACCOUNT_NAME_NULL, false);
        }
        if (password.equals(EMPTY_PARAM)) {
            request.setAttribute(RequestAttribute.IS_PASSWORD_NULL, true);
            k++;
        } else {
            request.setAttribute(RequestAttribute.IS_PASSWORD_NULL, false);
        }
        return k;
    }


}
