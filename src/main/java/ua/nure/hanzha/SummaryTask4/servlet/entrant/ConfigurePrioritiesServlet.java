package ua.nure.hanzha.SummaryTask4.servlet.entrant;

import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.ExceptionMessages;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.entity.Faculty;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.facultyEntrant.FacultyEntrantService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 12/08/15.
 */
public class ConfigurePrioritiesServlet extends HttpServlet {

    private static final String PARAM_PRIORITY = " priority";


    private FacultyEntrantService facultyEntrantService;

    @Override
    public void init() throws ServletException {
        facultyEntrantService = (FacultyEntrantService) getServletContext().getAttribute(AppAttribute.FACULTY_ENTRANT_SERVICE);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        List<Faculty> enrolledFaculties =
                (List<Faculty>) session.getAttribute(SessionAttribute.FACULTIES_ENROLLED_FACULTIES);
        Integer[] priorities = getAllPrioritiesFromRequest(request, enrolledFaculties);
        boolean isSamePriorities = checkIsSamePriorities(session, priorities);
        if (isSamePriorities) {
            response.sendRedirect(Pages.FACULTIES_HTML);
        } else {
            Entrant entrant = (Entrant) session.getAttribute(SessionAttribute.FACULTIES_ENTRANT_ENTITY);
            int entrantId = entrant.getId();
            updatePriorities(session, response, enrolledFaculties, priorities, entrantId);
        }
    }

    private Integer[] getAllPrioritiesFromRequest(HttpServletRequest request, List<Faculty> enrolledFaculties) {
        Integer[] priorities = new Integer[enrolledFaculties.size()];
        for (int i = 0; i < enrolledFaculties.size(); i++) {
            priorities[i] = Integer.valueOf(request.getParameter(enrolledFaculties.get(i).getName() + PARAM_PRIORITY));
        }
        return priorities;
    }

    private void updatePriorities(HttpSession session, HttpServletResponse response, List<Faculty> enrolledFaculties,
                                  Integer[] priorities, int entrantId) throws IOException {
        try {
            for (int i = 0; i < enrolledFaculties.size(); i++) {
                facultyEntrantService.updatePriority(priorities[i], enrolledFaculties.get(i).getId(), entrantId);
            }
            session.setAttribute(SessionAttribute.FACULTIES_IS_ANY_PROBLEMS_UPDATE, false);
            response.sendRedirect(Pages.FACULTIES_HTML);
        } catch (DaoSystemException e) {
            if (e.getMessage().equals(ExceptionMessages.UPDATE_EXCEPTION_MESSAGE)) {
                session.setAttribute(SessionAttribute.FACULTIES_IS_ANY_PROBLEMS_UPDATE, true);
                response.sendRedirect(Pages.FACULTIES_HTML);
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    private boolean checkIsSamePriorities(HttpSession session, Integer[] priorities) {
        for (int i = 0; i < priorities.length - 1; i++) {
            for (int j = i + 1; j < priorities.length; j++) {
                if (priorities[i] == priorities[j]) {
                    session.setAttribute(SessionAttribute.FACULTIES_IS_SAME_PRIORITIES, true);
                    return true;
                }
            }
        }
        session.setAttribute(SessionAttribute.FACULTIES_IS_SAME_PRIORITIES, false);
        return false;
    }

}
