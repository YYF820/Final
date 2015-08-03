package ua.nure.hanzha.SummaryTask4.servlet.check;

import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.RequestAttribute;
import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.validation.Validation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by faffi-ubuntu on 03/08/15.
 */
public class CheckQuestionServlet extends HttpServlet {

    private static final String EMPTY_PARAM = "";
    private static final String PARAM_SCHOOL_NUMBER = "school";
    private static final String REQUEST_ATTRIBUTE_ENTRANT_FOR_VERIFY_ACCOUNT = "entrantForVerifyAccount";
    private static final String REQUEST_ATTRIBUTE_IS_SCHOOL_NUMBER_CORRECT = "isSchoolNumberCorrect";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String schoolNumberFromQuestion = request.getParameter(PARAM_SCHOOL_NUMBER);
        Entrant entrant = (Entrant) request.getAttribute(REQUEST_ATTRIBUTE_ENTRANT_FOR_VERIFY_ACCOUNT);
        boolean isSchoolNumberFromQuestionValid = checkValidationSchoolNumber(request, schoolNumberFromQuestion);
        boolean isSchoolNumberEmpty = checkEmptyFields(request, schoolNumberFromQuestion);
        if (!isSchoolNumberFromQuestionValid || isSchoolNumberEmpty) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.CHECK_QUESTION_HTML);
            requestDispatcher.forward(request, response);
        } else {
            int schoolNumberFromEntrant = entrant.getSchool();
            if (!(Integer.parseInt(schoolNumberFromQuestion) == schoolNumberFromEntrant)) {
                request.setAttribute(REQUEST_ATTRIBUTE_IS_SCHOOL_NUMBER_CORRECT, false);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.CHECK_QUESTION_HTML);
                requestDispatcher.forward(request, response);
            } else {
                String accountName = (String) request.getAttribute(RequestAttribute.ACCOUNT_NAME);
                prepareMailInfo(request, accountName);


            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private boolean checkValidationSchoolNumber(HttpServletRequest request, String schoolNumber) {
        boolean isAccountNameValid = Validation.validateSchool(schoolNumber);
        if (!isAccountNameValid) {
            request.setAttribute(RequestAttribute.IS_SCHOOL_VALID, false);
        }
        return isAccountNameValid;
    }

    private boolean checkEmptyFields(HttpServletRequest request, String schoolNumber) {
        boolean isSchoolNumberEmpty = false;
        if (schoolNumber.equals(EMPTY_PARAM)) {
            isSchoolNumberEmpty = true;
            request.setAttribute(RequestAttribute.IS_ACCOUNT_NAME_NULL, true);
        }
        return isSchoolNumberEmpty;
    }

    private void prepareMailInfo(HttpServletRequest request, String userName) {

    }
}
