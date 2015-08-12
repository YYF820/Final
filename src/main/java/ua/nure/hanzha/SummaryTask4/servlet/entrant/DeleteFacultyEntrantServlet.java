package ua.nure.hanzha.SummaryTask4.servlet.entrant;

import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.facultyEntrant.FacultyEntrantService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 11/08/15.
 */
public class DeleteFacultyEntrantServlet extends HttpServlet {

    private static final String PARAM_FACULTY_ID = "facultyId";
    private static final String PARAM_ENTRANT_ID = "entrantId";

    private FacultyEntrantService facultyEntrantService;

    @Override
    public void init() throws ServletException {
        facultyEntrantService = (FacultyEntrantService)
                getServletContext().getAttribute(AppAttribute.FACULTY_ENTRANT_SERVICE);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int facultyId = Integer.parseInt(request.getParameter(PARAM_FACULTY_ID));
        int entrantId = Integer.parseInt(request.getParameter(PARAM_ENTRANT_ID));
        try {
            facultyEntrantService.removeFacultyByFacultyIdEntrantId(facultyId, entrantId);
            response.sendRedirect(Pages.FACULTIES_HTML);
        } catch (DaoSystemException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
