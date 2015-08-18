package ua.nure.hanzha.SummaryTask4.servlet.admin;

import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.faculty.FacultyService;
import ua.nure.hanzha.SummaryTask4.validation.Validation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 10/08/15.
 */
public class DeleteFacultyServlet extends HttpServlet {

    private static final String PARAM_FACULTY_ID = "facultyId";
    private FacultyService facultyService;

    @Override
    public void init() throws ServletException {
        facultyService = (FacultyService) getServletContext().getAttribute(AppAttribute.FACULTY_SERVICE);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        session.removeAttribute(SessionAttribute.ADMIN_DELETE_IS_VALID_PARAM_FACULTY_ID);
        String facultyId = request.getParameter(PARAM_FACULTY_ID);
        if (!checkFacultyIdIsValid(session, facultyId)) {
            response.sendRedirect(Pages.FACULTIES_ADMIN_HTML);
        } else {
            Integer facultyIdInt = Integer.valueOf(facultyId);
            try {
                facultyService.removeFacultyById(facultyIdInt);
                session.setAttribute(SessionAttribute.ADMIN_DELETE_IS_DELETED, true);
                response.sendRedirect(Pages.FACULTIES_ADMIN_SERVLET);
            } catch (DaoSystemException e) {
                session.setAttribute(SessionAttribute.ADMIN_DELETE_IS_DELETED, false);
                response.sendRedirect(Pages.FACULTIES_ADMIN_SERVLET);
            }
        }
    }

    private boolean checkFacultyIdIsValid(HttpSession session, String facultyId) {
        boolean isValidFacultyId = Validation.validateParamFacultyId(facultyId);
        if (!isValidFacultyId) {
            session.setAttribute(SessionAttribute.ADMIN_DELETE_IS_VALID_PARAM_FACULTY_ID, false);
            return false;
        } else {
            session.setAttribute(SessionAttribute.ADMIN_DELETE_IS_VALID_PARAM_FACULTY_ID, true);
            return true;
        }
    }
}
