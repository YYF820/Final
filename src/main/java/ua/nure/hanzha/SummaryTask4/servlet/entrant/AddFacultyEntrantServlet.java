package ua.nure.hanzha.SummaryTask4.servlet.entrant;

import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.FacultyEntrant;
import ua.nure.hanzha.SummaryTask4.entity.FacultySubject;
import ua.nure.hanzha.SummaryTask4.entity.Mark;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.facultyEntrant.FacultyEntrantService;
import ua.nure.hanzha.SummaryTask4.service.facultySubject.FacultySubjectService;
import ua.nure.hanzha.SummaryTask4.service.mark.MarkService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private MarkService markService;
    private FacultySubjectService facultySubjectService;

    @Override
    public void init() throws ServletException {
        markService = (MarkService) getServletContext().getAttribute(AppAttribute.MARK_SERVICE);
        facultyEntrantService =
                (FacultyEntrantService) getServletContext().getAttribute(AppAttribute.FACULTY_ENTRANT_SERVICE);
        facultySubjectService =
                (FacultySubjectService) getServletContext().getAttribute(AppAttribute.FACULTY_SUBJECT_SERVICE);

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        int facultyId = Integer.parseInt(request.getParameter(PARAM_FACULTY_ID));
        int entrantId = Integer.parseInt(request.getParameter(PARAM_ENTRANT_ID));
        try {
            List<Mark> marks = markService.getAllMarksByEntrantId(entrantId);
            List<FacultySubject> facultySubjects = facultySubjectService.getAllByFacultyId(facultyId);
            if (!checkCorrectSubjects(session, marks, facultySubjects)) {
                response.sendRedirect(Pages.FACULTIES_HTML);
            } else {
                int priority = Integer.parseInt(request.getParameter(PARAM_PRIORITY));
                FacultyEntrant facultyEntrant = setUpBeforeInsert(facultyId, entrantId, priority);
                facultyEntrantService.addFaculty(facultyEntrant);
                response.sendRedirect(Pages.FACULTIES_HTML);
            }
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

    private boolean checkCorrectSubjects(HttpSession session,
                                         List<Mark> markList,
                                         List<FacultySubject> facultySubjectList) {
        List<Integer> entrantSubjects = new ArrayList<>(markList.size());
        List<Integer> facultySubjects = new ArrayList<>(facultySubjectList.size());
        for (Mark mark : markList) {
            entrantSubjects.add(mark.getSubjectId());
        }
        for (FacultySubject facultySubject : facultySubjectList) {
            facultySubjects.add(facultySubject.getSubjectId());
        }
        int counterOfSameSubjects = 0;
        for (Integer entrantSubject : entrantSubjects) {
            for (Integer facultySubject : facultySubjects) {
                if (Objects.equals(entrantSubject, facultySubject)) {
                    counterOfSameSubjects++;
                }
            }
        }
        if (!(counterOfSameSubjects == entrantSubjects.size())) {
            session.setAttribute(SessionAttribute.FACULTIES_IS_SAME_SUBJECTS, false);
            return false;
        }
        return true;
    }

}
