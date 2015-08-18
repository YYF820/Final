package ua.nure.hanzha.SummaryTask4.servlet.admin;

import ua.nure.hanzha.SummaryTask4.bean.FacultiesInfoBean;
import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.Subject;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.subject.SubjectService;
import ua.nure.hanzha.SummaryTask4.util.SessionCleanerUtilities;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 09/08/15.
 */
public class PrepareInfoEditFacultyServlet extends HttpServlet {

    private static final String PARAM_INDEX = "index";
    private static final String SORT_TYPE_NO_SORT = "noSort";

    private SubjectService subjectService;

    @Override
    public void init() throws ServletException {
        subjectService = (SubjectService) getServletContext().getAttribute(AppAttribute.SUBJECT_SERVICE);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        int index = Integer.parseInt(request.getParameter(PARAM_INDEX));
        String sortType = (String) session.getAttribute(SessionAttribute.ADMIN_SORT_TYPE);
        if (sortType.equals(SORT_TYPE_NO_SORT)) {
            prepareInfoForEdit(SessionAttribute.ADMIN_FACULTIES_INFO_BEANS_PAGINATION, index, response, session);
        } else {
            prepareInfoForEdit(SessionAttribute.ADMIN_FACULTIES_INFO_BEANS_SORTED_PAGINATION, index, response, session);
        }
    }

    private List<Subject> subjectsToAdd(List<Subject> subjectsInFaculty, List<Subject> allSubjects) {
        List<Subject> subjectsToAdd = new ArrayList<>();
        for (Subject subject : allSubjects) {
            boolean flag = false;
            for (Subject subjectInFaculty : subjectsInFaculty) {
                if (subject.getName().equals(subjectInFaculty.getName())) {
                    flag = true;
                }
            }
            if (!flag) {
                subjectsToAdd.add(subject);
            }
        }
        return subjectsToAdd;
    }

    private void prepareInfoForEdit(String nameOfListForExtractBean, int index, HttpServletResponse response, HttpSession session) throws IOException {
        List<FacultiesInfoBean> facultiesInfoBeansPagination =
                (List<FacultiesInfoBean>) session.getAttribute(nameOfListForExtractBean);
        FacultiesInfoBean facultiesInfoBean = facultiesInfoBeansPagination.get(index);

        List<Subject> subjectsInFaculty = facultiesInfoBean.getSubjects();
        try {
            List<Subject> allSubjects = subjectService.getAll();
            List<Subject> subjectToAdd = subjectsToAdd(subjectsInFaculty, allSubjects);
            session.setAttribute(SessionAttribute.ADMIN_SUBJECTS_TO_ADD, subjectToAdd);
            session.setAttribute(SessionAttribute.ADMIN_FACULTY_FOR_EDIT, facultiesInfoBean);
            cleanSession(session);
            response.sendRedirect(Pages.FACULTY_EDIT_ADMIN_HTML);
        } catch (DaoSystemException e) {
            //NO SUBJECTS IN DATABASE CrudException | SQLException problems connection etc.
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private void cleanSession(HttpSession session) {
        SessionCleanerUtilities.cleanAttributes(
                session,
                SessionAttribute.ADMIN_EDIT_IS_ALL_FIELDS_EMPTY,
                SessionAttribute.ADMIN_EDIT_IS_FACULTY_NAME_VALID,
                SessionAttribute.ADMIN_EDIT_FACULTY_NAME,
                SessionAttribute.ADMIN_EDIT_TOTAL_SPOTS,
                SessionAttribute.ADMIN_EDIT_BUDGET_SPOTS,
                SessionAttribute.ADMIN_EDIT_TOTAL_LOWER_BUDGET,
                SessionAttribute.ADMIN_ADD_IS_ENOUGH_SUBJECTS
        );
    }
}
