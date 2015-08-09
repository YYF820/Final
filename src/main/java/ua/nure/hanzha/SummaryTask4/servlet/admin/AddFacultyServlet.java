package ua.nure.hanzha.SummaryTask4.servlet.admin;

import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.ExceptionMessages;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.Faculty;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.facultyAdmin.FacultyAdminService;
import ua.nure.hanzha.SummaryTask4.util.StringToIntegerArray;
import ua.nure.hanzha.SummaryTask4.validation.Validation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 09/08/15.
 */
public class AddFacultyServlet extends HttpServlet {

    private static final String EMPTY_PARAM = "";
    private static final String PARAM_SUBJECTS_ID_TO_ADD = "subjectsIdToAdd";
    private static final String PARAM_FACULTY_NAME = "facultyName";
    private static final String PARAM_TOTAL_SPOTS = "totalSpots";
    private static final String PARAM_BUDGET_SPOTS = "budgetSpots";

    private FacultyAdminService facultyAdminService;

    @Override
    public void init() throws ServletException {
        facultyAdminService = (FacultyAdminService)
                getServletContext().getAttribute(AppAttribute.FACULTY_ADMIN_SERVICE);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        cleanSessionPrg(session);
        String[] subjectsIdToAdd = request.getParameterValues(PARAM_SUBJECTS_ID_TO_ADD);
        String facultyName = request.getParameter(PARAM_FACULTY_NAME);
        String totalSpots = request.getParameter(PARAM_TOTAL_SPOTS);
        String budgetSpots = request.getParameter(PARAM_BUDGET_SPOTS);
        boolean isEmptyAnyField = checkEmpty(session, subjectsIdToAdd, facultyName, totalSpots, budgetSpots);
        boolean isFacultyNameValid = isFacultyNameValid(session, facultyName);
        if (isEmptyAnyField || !isFacultyNameValid) {
            response.sendRedirect(Pages.FACULTY_ADD_ADMIN_HTML);
        } else {
            int totalSpotsInt = Integer.valueOf(totalSpots);
            int budgetSpotsInt = Integer.valueOf(budgetSpots);
            if (totalSpotsInt < budgetSpotsInt) {
                session.setAttribute(SessionAttribute.ADMIN_ADD_TOTAL_LOWER_BUDGET, true);
                response.sendRedirect(Pages.FACULTY_ADD_ADMIN_HTML);
            } else {
                Faculty faculty = prepareForCreate(facultyName, totalSpotsInt, budgetSpotsInt);
                Integer[] subjectsIdToAddForService = StringToIntegerArray.convert(subjectsIdToAdd);
                try {
                    facultyAdminService.addFaculty(faculty, subjectsIdToAddForService);
                    response.sendRedirect(Pages.FACULTIES_ADMIN_SERVLET);
                } catch (DaoSystemException e) {
                    if (e.getCause().getMessage().contains(ExceptionMessages.INSERT_FACULTY_SAME_NAME)) {
                        session.setAttribute(SessionAttribute.ADMIN_ADD_IS_DUPLICATE_FACULTY_NAME, true);
                        response.sendRedirect(Pages.FACULTY_ADD_ADMIN_HTML);
                    }
                }
            }
        }


    }

    private boolean checkEmpty(HttpSession session, String[] subjectsIdToAdd, String... fields) {
        boolean isEmptyAnyField = false;
        if (subjectsIdToAdd == null) {
            isEmptyAnyField = true;
        }
        for (String field : fields) {
            if (field.equals(EMPTY_PARAM)) {
                isEmptyAnyField = true;
            }
        }
        if (isEmptyAnyField) {
            session.setAttribute(SessionAttribute.ADMIN_ADD_IS_ANY_EMPTY_FIELDS, true);
        } else {
            session.setAttribute(SessionAttribute.ADMIN_ADD_IS_ANY_EMPTY_FIELDS, false);
        }
        return isEmptyAnyField;

    }

    private boolean isFacultyNameValid(HttpSession session, String facultyName) {
        if (facultyName.equals(EMPTY_PARAM)) {
            session.setAttribute(SessionAttribute.ADMIN_ADD_IS_FACULTY_NAME_VALID, true);
            return true;
        }
        boolean isFacultyNameValid = Validation.validateFacultyName(facultyName);
        if (!isFacultyNameValid) {
            session.setAttribute(SessionAttribute.ADMIN_ADD_IS_FACULTY_NAME_VALID, false);
            return false;
        } else {
            session.setAttribute(SessionAttribute.ADMIN_ADD_IS_FACULTY_NAME_VALID, true);
        }
        return true;
    }

    private void cleanSessionPrg(HttpSession session) {
        session.removeAttribute(SessionAttribute.ADMIN_ADD_TOTAL_LOWER_BUDGET);
        session.removeAttribute(SessionAttribute.ADMIN_ADD_IS_DUPLICATE_FACULTY_NAME);
    }

    private Faculty prepareForCreate(String facultyName, int totalSpots, int budgetSpots) {
        Faculty faculty = new Faculty();
        faculty.setName(facultyName);
        faculty.setTotalSpots(totalSpots);
        faculty.setBudgetSpots(budgetSpots);
        return faculty;
    }

}
