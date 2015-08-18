package ua.nure.hanzha.SummaryTask4.servlet.entrant;

import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.entity.Mark;
import ua.nure.hanzha.SummaryTask4.entity.Subject;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.mark.MarkService;
import ua.nure.hanzha.SummaryTask4.util.StringToDecimalArray;
import ua.nure.hanzha.SummaryTask4.validation.Validation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 10/08/15.
 */
public class AddSubjectsMarksServlet extends HttpServlet {
    private static final String EMPTY_PARAM = "";

    private MarkService markService;

    @Override
    public void init() throws ServletException {
        markService = (MarkService) getServletContext().getAttribute(AppAttribute.MARK_SERVICE);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        List<Subject> subjectsToAdd =
                (List<Subject>) session.getAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_SUBJECTS_TO_ADD);
        String[] params = getParameters(request, session, subjectsToAdd);
        boolean isAnyEmptyField = checkEmptyFields(session, params);
        if (isAnyEmptyField) {
            setUpFields(session, params);
            response.sendRedirect(Pages.ENTRANT_ACCOUNT_SETTINGS_ADD_MARKS_HTML);
        } else {
            boolean isValidMarks = checkIsValidMarks(session, params);
            if (!isValidMarks) {
                setUpFields(session, params);
                response.sendRedirect(Pages.ENTRANT_ACCOUNT_SETTINGS_ADD_MARKS_HTML);
            } else {
                Entrant entrant = (Entrant) session.getAttribute(
                        SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_ENTRANT_TO_ADD_SUBJECTS_MARKS);
                Double[] marks = StringToDecimalArray.convertToDouble(params);
                for (int i = 0; i < subjectsToAdd.size(); i++) {
                    Mark mark = new Mark();
                    mark.setEntrantId(entrant.getId());
                    mark.setSubjectId(subjectsToAdd.get(i).getId());
                    mark.setMarkValue(marks[i]);
                    try {
                        markService.addMark(mark);
                    } catch (DaoSystemException e) {
                        session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_SOMETHING_BAD, true);
                    }
                }
                response.sendRedirect(Pages.ACCOUNT_SETTINGS_HTML);
            }
        }
    }

    //check for any one empty field

    private boolean checkEmptyFields(HttpSession session, String[] params) {
        boolean flag = false;
        for (String param : params) {
            System.out.println(param);
            if (param.equals(EMPTY_PARAM)) {
                flag = true;
            }
        }
        if (flag) {
            session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_IS_EMPTY_MARKS, true);
        } else {
            session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_IS_EMPTY_MARKS, false);
        }
        return flag;
    }

    //validate marks --> 100 - 200 double with 2 digits after dot
    private boolean checkIsValidMarks(HttpSession session, String[] params) {
        boolean isValidMarks = true;
        for (String param : params) {
            boolean temp = Validation.validateMark(param);
            if (!temp) {
                isValidMarks = false;
            }
        }
        if (!isValidMarks) {
            session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_IS_VALID_MARKS, false);
        } else {
            session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_IS_VALID_MARKS, true);
        }
        return isValidMarks;

    }

    //set value for fields after bad access
    private void setUpFields(HttpSession session, String[] params) {
        if (params[0].equals(EMPTY_PARAM)) {
            session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_MARK_FIRST, null);
        } else {
            session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_MARK_FIRST, params[0]);
        }
        if (params[1].equals(EMPTY_PARAM)) {
            session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_MARK_SECOND, null);
        } else {
            session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_MARK_SECOND, params[1]);
        }
        if (params[2].equals(EMPTY_PARAM)) {
            session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_MARK_THIRD, null);
        } else {
            session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_MARK_THIRD, params[2]);
        }
    }

    //extract info from form to params[]
    private String[] getParameters(HttpServletRequest request, HttpSession session, List<Subject> subjectsToAdd) {
        String[] params = new String[subjectsToAdd.size()];
        for (int i = 0; i < params.length; i++) {
            params[i] = request.getParameter(subjectsToAdd.get(i).getName());
        }
        return params;
    }

}
