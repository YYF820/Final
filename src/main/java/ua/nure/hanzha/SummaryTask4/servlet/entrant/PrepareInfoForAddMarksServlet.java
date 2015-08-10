package ua.nure.hanzha.SummaryTask4.servlet.entrant;

import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.Subject;
import ua.nure.hanzha.SummaryTask4.util.StringToDecimalArray;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmytro Hanzhas
 *         Created by faffi-ubuntu on 10/08/15.
 */
public class PrepareInfoForAddMarksServlet extends HttpServlet {

    private static final String PARAM_SUBJECTS_ID_TO_ADD = "subjectsIdToAdd";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String[] subjectsIdToAdd = request.getParameterValues(PARAM_SUBJECTS_ID_TO_ADD);
        boolean isFormEmpty = checkIsFormEmpty(session, subjectsIdToAdd);
        if (isFormEmpty) {
            response.sendRedirect(Pages.ENTRANT_ACCOUNT_SETTINGS_ADD_SUBJECTS_HTML);
        } else {
            boolean isCorrectNumberOfSubjects = checkIsCorrectNumberOfSubjects(session, subjectsIdToAdd);
            if (!isCorrectNumberOfSubjects) {
                response.sendRedirect(Pages.ENTRANT_ACCOUNT_SETTINGS_ADD_SUBJECTS_HTML);
            } else {
                Integer[] subjectsIdToAddInt = StringToDecimalArray.convertToInteger(subjectsIdToAdd);
                List<Subject> subjectsToAdd = prepareSubjects(session, subjectsIdToAddInt);
                session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_SUBJECTS_TO_ADD, subjectsToAdd);
                response.sendRedirect(Pages.ENTRANT_ACCOUNT_SETTINGS_ADD_MARKS_HTML);
            }
        }
    }

    private boolean checkIsFormEmpty(HttpSession session, String[] subjectsIdToAdd) {
        if (subjectsIdToAdd == null) {
            session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_IS_EMPTY_FORM, true);
            return true;
        } else {
            session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_IS_EMPTY_FORM, true);
            return false;
        }
    }

    private boolean checkIsCorrectNumberOfSubjects(HttpSession session, String[] subjectsIdToAdd) {
        if (subjectsIdToAdd.length != 3) {
            session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_IS_CORRECT_NUMBER_OF_SUBJECTS, false);
            return false;
        } else {
            session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_IS_CORRECT_NUMBER_OF_SUBJECTS, false);
            return true;
        }
    }

    private List<Subject> prepareSubjects(HttpSession session, Integer[] subjectsIdToAddInt) {
        List<Subject> subjectsToAdd = new ArrayList<>();
        List<Subject> allSubjects =
                (List<Subject>) session.getAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_ALL_SUBJECTS);
        for (Subject allSubject : allSubjects) {
            for (Integer aSubjectsIdToAddInt : subjectsIdToAddInt) {
                if (allSubject.getId() == aSubjectsIdToAddInt) {
                    subjectsToAdd.add(allSubject);
                }
            }
        }
        return subjectsToAdd;
    }

}
