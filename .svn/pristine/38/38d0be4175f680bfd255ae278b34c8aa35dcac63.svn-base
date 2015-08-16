package ua.nure.hanzha.SummaryTask4.servlet.admin;

import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.ExceptionMessages;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.Subject;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.subject.SubjectService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 09/08/15.
 */
public class PrepareInfoAddFacultyServlet extends HttpServlet {

    private SubjectService subjectService;

    @Override
    public void init() throws ServletException {
        subjectService = (SubjectService) getServletContext().getAttribute(AppAttribute.SUBJECT_SERVICE);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        try {
            List<Subject> allSubjects = subjectService.getAll();
            session.setAttribute(SessionAttribute.ADMIN_ADD_ALL_SUBJECTS, allSubjects);
            cleanSession(session);
            response.sendRedirect(Pages.FACULTY_ADD_ADMIN_HTML);
        } catch (DaoSystemException e) {
            e.printStackTrace();
            if (e.getMessage().equals(ExceptionMessages.SQL_EXCEPTION)) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    private void cleanSession(HttpSession session) {
        session.removeAttribute(SessionAttribute.ADMIN_ADD_IS_ANY_EMPTY_FIELDS);
        session.removeAttribute(SessionAttribute.ADMIN_ADD_IS_FACULTY_NAME_VALID);
        session.removeAttribute(SessionAttribute.ADMIN_ADD_FACULTY_NAME);
        session.removeAttribute(SessionAttribute.ADMIN_ADD_TOTAL_SPOTS);
        session.removeAttribute(SessionAttribute.ADMIN_ADD_BUDGET_SPOTS);
        session.removeAttribute(SessionAttribute.ADMIN_ADD_IS_ENOUGH_SUBJECTS);
        session.removeAttribute(SessionAttribute.ADMIN_ADD_TOTAL_LOWER_BUDGET);
        session.removeAttribute(SessionAttribute.ADMIN_ADD_IS_DUPLICATE_FACULTY_NAME);
    }

}
