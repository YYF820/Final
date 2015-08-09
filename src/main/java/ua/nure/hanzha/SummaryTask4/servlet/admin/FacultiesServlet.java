package ua.nure.hanzha.SummaryTask4.servlet.admin;

import ua.nure.hanzha.SummaryTask4.bean.FacultiesInfoAdminBean;
import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.ExceptionMessages;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.Faculty;
import ua.nure.hanzha.SummaryTask4.entity.Subject;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.faculty.FacultyService;
import ua.nure.hanzha.SummaryTask4.service.subject.SubjectService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmytro Hanhza
 *         Created by faffi-ubuntu on 08/08/15.
 */

public class FacultiesServlet extends HttpServlet {


    private static final String PARAM_PAGE = "page";
    private static final int RECORDS_PER_PAGE = 5;
    private static final String SORT_TYPE_NO_SORT = "noSort";
    private static int page = 1;

    private FacultyService facultyService;
    private SubjectService subjectService;

    @Override
    public void init() throws ServletException {
        facultyService = (FacultyService) getServletContext().getAttribute(AppAttribute.FACULTY_SERVICE);
        subjectService = (SubjectService) getServletContext().getAttribute(AppAttribute.SUBJECT_SERVICE);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        List<FacultiesInfoAdminBean> facultiesInfoAdminBeans = new ArrayList<>();
        try {
            List<Faculty> faculties = facultyService.getAllFaculties();
            for (Faculty faculty : faculties) {
                int facultyId = faculty.getId();
                List<Subject> subjects = new ArrayList<>();
                try {
                    subjects = subjectService.getAllByFacultyId(facultyId);
                } catch (DaoSystemException e) {
                    if (e.getMessage().equals(ExceptionMessages.SQL_EXCEPTION)) {
                        response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    }
                }
                FacultiesInfoAdminBean facultiesInfoAdminBean = new FacultiesInfoAdminBean();
                facultiesInfoAdminBean.setFaculty(faculty);
                facultiesInfoAdminBean.setSubjects(subjects);
                facultiesInfoAdminBeans.add(facultiesInfoAdminBean);
            }
            if (request.getParameter(PARAM_PAGE) != null)
                page = Integer.parseInt(request.getParameter(PARAM_PAGE));
            int numberOfRecords = facultiesInfoAdminBeans.size();
            int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);
            if (page > numberOfPages) {
                page = 1;
                List<FacultiesInfoAdminBean> facultiesInfoAdminBeansPagination = new ArrayList<>();
                copyList(facultiesInfoAdminBeans, facultiesInfoAdminBeansPagination, page, RECORDS_PER_PAGE);
                setUpSessionAttributes(session, numberOfPages, facultiesInfoAdminBeans, facultiesInfoAdminBeansPagination);
                response.sendRedirect(Pages.FACULTIES_ADMIN_HTML);
            } else {
                List<FacultiesInfoAdminBean> facultiesInfoAdminBeansPagination = new ArrayList<>();
                copyList(facultiesInfoAdminBeans, facultiesInfoAdminBeansPagination, page, RECORDS_PER_PAGE);
                setUpSessionAttributes(session, numberOfPages, facultiesInfoAdminBeans, facultiesInfoAdminBeansPagination);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.FACULTIES_ADMIN_HTML);
                requestDispatcher.forward(request, response);
            }
        } catch (DaoSystemException e) {
            e.printStackTrace();
        }

    }

    private void copyList(
            List<FacultiesInfoAdminBean> facultiesInfoAdminBeans,
            List<FacultiesInfoAdminBean> facultiesInfoAdminBeansPagination,
            int page,
            int recordsPerPage) {
        facultiesInfoAdminBeansPagination.clear();
        int lastElementOnPage = (page - 1) * recordsPerPage + recordsPerPage;
        if (lastElementOnPage > facultiesInfoAdminBeans.size()) {
            lastElementOnPage = facultiesInfoAdminBeans.size();
        }
        for (int i = (page - 1) * recordsPerPage; i < lastElementOnPage; i++) {
            System.out.println(i);
            facultiesInfoAdminBeansPagination.add(facultiesInfoAdminBeans.get(i));
        }
    }

    private void setUpSessionAttributes(HttpSession session, int numberOfPages,
                                        List<FacultiesInfoAdminBean> facultiesInfoAdminBeans,
                                        List<FacultiesInfoAdminBean> facultiesInfoAdminBeansPagination) {
        session.setAttribute(SessionAttribute.NUMBER_OF_PAGES, numberOfPages);
        session.setAttribute(SessionAttribute.CURRENT_PAGE, page);
        session.setAttribute(SessionAttribute.ADMIN_FACULTIES_INFO_BEANS, facultiesInfoAdminBeans);
        session.setAttribute(SessionAttribute.ADMIN_FACULTIES_INFO_BEANS_PAGINATION, facultiesInfoAdminBeansPagination);
        session.setAttribute(SessionAttribute.ADMIN_SORT_TYPE, SORT_TYPE_NO_SORT);
        session.setAttribute(SessionAttribute.ADMIN_IS_SORTED_FACULTIES, true);
    }
}
