package ua.nure.hanzha.SummaryTask4.servlet.check;

import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.servlet.callable.checkQuestion.CheckQuestionCallable;
import ua.nure.hanzha.SummaryTask4.servlet.callable.checkQuestion.CheckQuestionOperationsMap;
import ua.nure.hanzha.SummaryTask4.util.SessionCleaner;
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
public class CheckQuestionServlet extends HttpServlet {

    private static final String EMPTY_PARAM = "";
    private static final String PARAM_SCHOOL_NUMBER = "school";
    private static final String SESSION_ATTRIBUTE_COMMAND = "command";

    @Override
    public void init() throws ServletException {
        CheckQuestionOperationsMap.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String schoolNumberFromQuestion = request.getParameter(PARAM_SCHOOL_NUMBER);
        session.setAttribute(SessionAttribute.CHECK_QUESTION_SCHOOL, schoolNumberFromQuestion);
        boolean isSchoolNumberFromQuestionValid = checkValidationSchoolNumber(session, schoolNumberFromQuestion);
        boolean isSchoolNumberEmpty = checkEmptyFields(session, schoolNumberFromQuestion);
        if (!isSchoolNumberFromQuestionValid || isSchoolNumberEmpty) {
            response.sendRedirect(Pages.CHECK_QUESTION_HTML);
        } else {
            Entrant entrant = (Entrant) session.getAttribute(SessionAttribute.ENTRANT_FOR_VERIFY_ACCOUNT_RESET_PASSWORD);
            int schoolNumberFromEntrant = entrant.getSchool();
            if (!(Integer.parseInt(schoolNumberFromQuestion) == schoolNumberFromEntrant)) {
                session.setAttribute(SessionAttribute.CHECK_QUESTION_IS_SCHOOL_CORRECT, false);
                response.sendRedirect(Pages.CHECK_QUESTION_HTML);
            } else {
                String command = (String) session.getAttribute(SESSION_ATTRIBUTE_COMMAND);
                CheckQuestionOperationsMap.initCheckQuestionCallableMap(session, request, response);
                CheckQuestionCallable checkQuestionCallable = CheckQuestionOperationsMap.getCheckQuestionCallable(command);
                if (checkQuestionCallable != null) {
                    cleanSession(session);
                    checkQuestionCallable.call();
                } else {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.CHECK_QUESTION_HTML);
        requestDispatcher.forward(request, response);
    }

    private boolean checkValidationSchoolNumber(HttpSession session, String schoolNumber) {
        boolean isAccountNameValid = Validation.validateSchool(schoolNumber);
        if (!isAccountNameValid) {
            session.setAttribute(SessionAttribute.CHECK_QUESTION_IS_SCHOOL_VALID, false);
        } else {
            session.setAttribute(SessionAttribute.CHECK_QUESTION_IS_SCHOOL_VALID, true);
        }
        return isAccountNameValid;
    }

    private boolean checkEmptyFields(HttpSession session, String schoolNumber) {
        boolean isSchoolNumberEmpty = false;
        if (schoolNumber.equals(EMPTY_PARAM)) {
            isSchoolNumberEmpty = true;
            session.setAttribute(SessionAttribute.CHECK_QUESTION_IS_SCHOOL_EMPTY, true);
        } else {
            session.setAttribute(SessionAttribute.CHECK_QUESTION_IS_SCHOOL_EMPTY, false);
        }
        return isSchoolNumberEmpty;
    }

    private void cleanSession(HttpSession session) {
        SessionCleaner.cleanAttributes(
                session,
                SessionAttribute.CHECK_QUESTION_IS_SCHOOL_EMPTY,
                SessionAttribute.CHECK_QUESTION_IS_SCHOOL_VALID,
                SessionAttribute.CHECK_QUESTION_SCHOOL);
    }

}
