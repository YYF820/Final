package ua.nure.hanzha.SummaryTask4.servlet;

import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.RequestAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Validations;
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
 *         Created by faffi-ubuntu on 01/08/15.
 */
public class RegistrationServlet extends HttpServlet {

    private static final String EMPTY_PARAM = "";

    private static final String PARAM_FIRST_NAME = "firstName";
    private static final String PARAM_LAST_NAME = "lastName";
    private static final String PARAM_PATRONYMIC = "patronymic";
    private static final String PARAM_ACCOUNT_NAME = "accountName";
    private static final String PARAM_CITY = "city";
    private static final String PARAM_REGION = "region";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_SCHOOL = "school";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter(PARAM_FIRST_NAME);
        String lastName = request.getParameter(PARAM_LAST_NAME);
        String patronymic = request.getParameter(PARAM_PATRONYMIC);
        String accountName = request.getParameter(PARAM_ACCOUNT_NAME);
        String city = request.getParameter(PARAM_CITY);
        String region = request.getParameter(PARAM_REGION);
        String password = request.getParameter(PARAM_PASSWORD);
        String school = request.getParameter(PARAM_SCHOOL);

        Map<String, Boolean> validationsRegisterForm = Validation.validateRegistrationForm(
                firstName, lastName, patronymic, accountName, city, region, password, school
        );
        int counterOfEmptyFields = checkFormEmptyFields(request, firstName, lastName, patronymic, accountName, city, region, password, school);
        int counterOfBadValidations = checkFormBadValidations(request, validationsRegisterForm);
        if (counterOfBadValidations != 0 || counterOfEmptyFields != 0) {
            setUpAttrForFields(request, firstName, lastName, password, accountName, city, region, school);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.REGISTRATION_HTML);
            requestDispatcher.forward(request, response);
        } else {

        }


    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(Pages.REGISTRATION_HTML);
    }

    private int checkFormBadValidations(HttpServletRequest request, Map<String, Boolean> validationsRegisterForm) {
        int counterOfBadValidations = 0;
        if (!validationsRegisterForm.get(Validations.MAP_KEY_IS_FIRST_NAME_VALID)) {
            counterOfBadValidations++;
            request.setAttribute(RequestAttribute.IS_FIRST_NAME_VALID, false);
        } else {
            request.setAttribute(RequestAttribute.IS_FIRST_NAME_VALID, true);
        }

        if (!validationsRegisterForm.get(Validations.MAP_KEY_IS_LAST_NAME_VALID)) {
            counterOfBadValidations++;
            request.setAttribute(RequestAttribute.IS_LAST_NAME_VALID, false);
        } else {
            request.setAttribute(RequestAttribute.IS_LAST_NAME_VALID, true);
        }

        if (!validationsRegisterForm.get(Validations.MAP_KEY_IS_PATRONYMIC_VALID)) {
            counterOfBadValidations++;
            request.setAttribute(RequestAttribute.IS_PATRONYMIC_VALID, false);
        } else {
            request.setAttribute(RequestAttribute.IS_PATRONYMIC_VALID, true);
        }

        if (!validationsRegisterForm.get(Validations.MAP_KEY_IS_ACCOUNT_NAME_VALID)) {
            counterOfBadValidations++;
            request.setAttribute(RequestAttribute.IS_ACCOUNT_NAME_VALID, false);
        } else {
            request.setAttribute(RequestAttribute.IS_ACCOUNT_NAME_VALID, true);
        }

        if (!validationsRegisterForm.get(Validations.MAP_KEY_IS_CITY_VALID)) {
            counterOfBadValidations++;
            request.setAttribute(RequestAttribute.IS_CITY_VALID, false);
        } else {
            request.setAttribute(RequestAttribute.IS_CITY_VALID, true);
        }

        if (!validationsRegisterForm.get(Validations.MAP_KEY_IS_REGION_VALID)) {
            counterOfBadValidations++;
            request.setAttribute(RequestAttribute.IS_REGION_VALID, false);
        } else {
            request.setAttribute(RequestAttribute.IS_REGION_VALID, true);
        }

        if (!validationsRegisterForm.get(Validations.MAP_KEY_IS_PASSWORD_VALID)) {
            counterOfBadValidations++;
            request.setAttribute(RequestAttribute.IS_PASSWORD_VALID, false);
        } else {
            request.setAttribute(RequestAttribute.IS_PASSWORD_VALID, true);
        }

        if (!validationsRegisterForm.get(Validations.MAP_KEY_IS_SCHOOL_VALID)) {
            counterOfBadValidations++;
            request.setAttribute(RequestAttribute.IS_SCHOOL_VALID, false);
        } else {
            request.setAttribute(RequestAttribute.IS_SCHOOL_VALID, true);
        }
        return counterOfBadValidations;
    }

    private int checkFormEmptyFields(HttpServletRequest request, String... params) {
        int counterOfEmptyFields = 0;
        if (params[0].equals(EMPTY_PARAM)) {
            counterOfEmptyFields++;
            request.setAttribute(RequestAttribute.IS_FIRST_NAME_NULL, true);
        } else {
            request.setAttribute(RequestAttribute.IS_FIRST_NAME_NULL, false);
        }

        if (params[1].equals(EMPTY_PARAM)) {
            counterOfEmptyFields++;
            request.setAttribute(RequestAttribute.IS_LAST_NAME_NULL, true);
        } else {
            request.setAttribute(RequestAttribute.IS_LAST_NAME_NULL, false);
        }

        if (params[2].equals(EMPTY_PARAM)) {
            counterOfEmptyFields++;
            request.setAttribute(RequestAttribute.IS_PATRONYMIC_NULL, true);
        } else {
            request.setAttribute(RequestAttribute.IS_PATRONYMIC_NULL, false);
        }

        if (params[3].equals(EMPTY_PARAM)) {
            counterOfEmptyFields++;
            request.setAttribute(RequestAttribute.IS_ACCOUNT_NAME_NULL, true);
        } else {
            request.setAttribute(RequestAttribute.IS_ACCOUNT_NAME_NULL, false);
        }

        if (params[4].equals(EMPTY_PARAM)) {
            counterOfEmptyFields++;
            request.setAttribute(RequestAttribute.IS_CITY_NULL, true);
        } else {
            request.setAttribute(RequestAttribute.IS_CITY_NULL, false);
        }

        if (params[5].equals(EMPTY_PARAM)) {
            counterOfEmptyFields++;
            request.setAttribute(RequestAttribute.IS_REGION_NULL, true);
        } else {
            request.setAttribute(RequestAttribute.IS_REGION_NULL, false);
        }

        if (params[6].equals(EMPTY_PARAM)) {
            counterOfEmptyFields++;
            request.setAttribute(RequestAttribute.IS_PASSWORD_NULL, true);
        } else {
            request.setAttribute(RequestAttribute.IS_PASSWORD_NULL, false);
        }

        if (params[7].equals(EMPTY_PARAM)) {
            counterOfEmptyFields++;
            request.setAttribute(RequestAttribute.IS_SCHOOL_NULL, true);
        } else {
            request.setAttribute(RequestAttribute.IS_SCHOOL_NULL, false);
        }
        return counterOfEmptyFields;
    }

    private void setUpAttrForFields(HttpServletRequest request, String... attributes) {
        request.setAttribute(RequestAttribute.FIRST_NAME, attributes[0]);
        request.setAttribute(RequestAttribute.LAST_NAME, attributes[1]);
        request.setAttribute(RequestAttribute.PATRONYMIC, attributes[2]);
        request.setAttribute(RequestAttribute.ACCOUNT_NAME, attributes[3]);
        request.setAttribute(RequestAttribute.CITY, attributes[4]);
        request.setAttribute(RequestAttribute.REGION, attributes[5]);
        request.setAttribute(RequestAttribute.SCHOOL, attributes[6]);
    }
}
