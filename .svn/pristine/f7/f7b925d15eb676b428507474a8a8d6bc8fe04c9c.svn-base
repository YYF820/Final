package ua.nure.hanzha.SummaryTask4.servlet.entrant;

import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.entity.FacultyEntrant;
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
public class AddFacultyEntrantServlet extends HttpServlet {

    private static final String PARAM_FACULTY_ID = "facultyId";
    private static final String PARAM_ENTRANT_ID = "entrantId";
    private static final String PARAM_PRIORITY = "priority";
    private static final double DEFAULT_SUM_MARKS = 0;

    private FacultyEntrantService facultyEntrantService;

    @Override
    public void init() throws ServletException {
        facultyEntrantService = (FacultyEntrantService)
                getServletContext().getAttribute(AppAttribute.FACULTY_ENTRANT_SERVICE);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int facultyId = Integer.parseInt(request.getParameter(PARAM_FACULTY_ID));
        int entrantId = Integer.parseInt(request.getParameter(PARAM_ENTRANT_ID));
        int priority = Integer.parseInt(request.getParameter(PARAM_PRIORITY));
        FacultyEntrant facultyEntrant = setUpBeforeInsert(facultyId, entrantId, priority);
        try {
            facultyEntrantService.addFaculty(facultyEntrant);
            response.sendRedirect(Pages.FACULTIES_HTML);
        } catch (DaoSystemException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private FacultyEntrant setUpBeforeInsert(int facultyId, int entrantId, int priority) {
        FacultyEntrant facultyEntrant = new FacultyEntrant();
        facultyEntrant.setFacultyId(facultyId);
        facultyEntrant.setEntrantId(entrantId);
        facultyEntrant.setPriority(priority);
        facultyEntrant.setSumMarks(DEFAULT_SUM_MARKS);
        return facultyEntrant;
    }

}
