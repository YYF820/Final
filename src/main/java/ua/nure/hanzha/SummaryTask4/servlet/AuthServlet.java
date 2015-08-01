package ua.nure.hanzha.SummaryTask4.servlet;

import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.Validations;
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
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Map;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 30/07/15.
 */
public class AuthServlet extends HttpServlet {

    private static final String EMPTY_PARAM = "";

    private static final String APP_CONTEXT_ATTRIBUTE_USER_SERVICE = "userService";

    private static final String PARAM_ACCOUNT_NAME = "accountName";
    private static final String PARAM_PASSWORD = "password";

    private static final String REQUEST_ATTRIBUTE_IS_ACCOUNT_NAME_NULL = "isAccountNameNull";
    private static final String REQUEST_ATTRIBUTE_IS_PASSWORD_NULL = "isPasswordNull";
    private static final String REQUEST_ATTRIBUTE_IS_ACCOUNT_NAME_VALID = "isAccountNameValid";
    private static final String REQUEST_ATTRIBUTE_IS_PASSWORD_VALID = "isPasswordValid";
    private static final String REQUEST_ATTRIBUTE_IS_CORRECT_ACCOUNT_NAME_OR_PASSWORD = "isCorrectAccountNameOrPassword";
    private static final String REQUEST_ATTRIBUTE_ACCOUNT_NAME = "accountName";
    private static final String REQUEST_ATTRIBUTE_PASSWORD = "password";


    private UserService userService;


    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute(APP_CONTEXT_ATTRIBUTE_USER_SERVICE);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter(PARAM_ACCOUNT_NAME);
        String password = request.getParameter(PARAM_PASSWORD);
        request.setAttribute(REQUEST_ATTRIBUTE_ACCOUNT_NAME, email);
        request.setAttribute(REQUEST_ATTRIBUTE_PASSWORD, password);
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
                    response.sendRedirect(Pages.INDEX_HTML);
                } else {
                    request.setAttribute(REQUEST_ATTRIBUTE_IS_CORRECT_ACCOUNT_NAME_OR_PASSWORD, false);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.LOGIN_HTML);
                    requestDispatcher.forward(request, response);
                }
            } catch (DaoSystemException | InvalidKeySpecException | NoSuchAlgorithmException e) {
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
            request.setAttribute(REQUEST_ATTRIBUTE_IS_ACCOUNT_NAME_VALID, false);
        } else {
            request.setAttribute(REQUEST_ATTRIBUTE_IS_ACCOUNT_NAME_VALID, true);
        }
        if (!validationsEmailPassword.get(Validations.MAP_KEY_IS_PASSWORD_VALID)) {
            k++;
            request.setAttribute(REQUEST_ATTRIBUTE_IS_PASSWORD_VALID, false);
        } else {
            request.setAttribute(REQUEST_ATTRIBUTE_IS_PASSWORD_VALID, true);
        }
        return k;
    }

    private int checkNull(String accountName, String password, HttpServletRequest request) {
        int k = 0;
        if (accountName.equals(EMPTY_PARAM)) {
            request.setAttribute(REQUEST_ATTRIBUTE_IS_ACCOUNT_NAME_NULL, true);
            k++;
        } else {
            request.setAttribute(REQUEST_ATTRIBUTE_IS_ACCOUNT_NAME_NULL, false);
        }
        if (password.equals(EMPTY_PARAM)) {
            request.setAttribute(REQUEST_ATTRIBUTE_IS_PASSWORD_NULL, true);
            k++;
        } else {
            request.setAttribute(REQUEST_ATTRIBUTE_IS_PASSWORD_NULL, false);
        }
        return k;
    }


}
