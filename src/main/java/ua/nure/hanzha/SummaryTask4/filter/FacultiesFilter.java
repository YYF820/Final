package ua.nure.hanzha.SummaryTask4.filter;

import ua.nure.hanzha.SummaryTask4.bean.FacultiesInfoBean;
import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.ExceptionMessages;
import ua.nure.hanzha.SummaryTask4.constants.Roles;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.entity.Faculty;
import ua.nure.hanzha.SummaryTask4.entity.Subject;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.enums.Role;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.entrant.EntrantService;
import ua.nure.hanzha.SummaryTask4.service.faculty.FacultyService;
import ua.nure.hanzha.SummaryTask4.service.facultyEntrant.FacultyEntrantService;
import ua.nure.hanzha.SummaryTask4.service.subject.SubjectService;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 12/08/15.
 */
public class FacultiesFilter extends BaseFilter {


    private static final String PARAM_PAGE = "page";
    private static final int RECORDS_PER_PAGE = 2;
    private static final String SORT_TYPE_NO_SORT = "noSort";
    private static final String COMMAND = "command";
    private static int page = 1;


    private FacultyService facultyService;
    private EntrantService entrantService;
    private SubjectService subjectService;
    private FacultyEntrantService facultyEntrantService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        facultyService = (FacultyService) filterConfig.getServletContext().getAttribute(AppAttribute.FACULTY_SERVICE);
        entrantService = (EntrantService) filterConfig.getServletContext().getAttribute(AppAttribute.ENTRANT_SERVICE);
        subjectService = (SubjectService) filterConfig.getServletContext().getAttribute(AppAttribute.SUBJECT_SERVICE);
        facultyEntrantService =
                (FacultyEntrantService) filterConfig.getServletContext().getAttribute(AppAttribute.FACULTY_ENTRANT_SERVICE);
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        User user = (User) session.getAttribute(SessionAttribute.ACCOUNT);
        if (user == null) {
            preparePage(response, request, session, filterChain);
        } else if (Role.getRole(user).getName().equals(Roles.ADMIN)) {
            preparePage(response, request, session, filterChain);
        } else {
            int userId = user.getId();
            Entrant entrant;
            try {
                entrant = entrantService.getByUserId(userId);
                session.setAttribute(SessionAttribute.FACULTIES_ENTRANT_ENTITY, entrant);
            } catch (DaoSystemException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
            int entrantId = entrant.getId();
            try {
                Map<Integer, Integer> usedFacultiesIdPriority = facultyEntrantService.getFacultyIdPriorityByEntrantId(entrantId);
                Collection<Integer> usedPrioritiesList = usedFacultiesIdPriority.values();
                Collection<Integer> enrolledFacultiesIdList = usedFacultiesIdPriority.keySet();
                List<Integer> availablePrioritiesList = getAvailablePriorityList(usedPrioritiesList);
                List<Faculty> enrolledFaculties = getEnrolledFaculties(enrolledFacultiesIdList);
                session.setAttribute(SessionAttribute.FACULTIES_ENROLLED_FACULTIES, enrolledFaculties);
                session.setAttribute(SessionAttribute.FACULTIES_USED_FACULTIES_ID_PRIORITY, usedFacultiesIdPriority);
                session.setAttribute(SessionAttribute.FACULTIES_AVAILABLE_PROPERTIES_LIST, availablePrioritiesList);
                preparePage(response, request, session, filterChain);
            } catch (DaoSystemException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    private List<Integer> getAvailablePriorityList(Collection<Integer> usedPriorities) {
        List<Integer> allPriorities = new ArrayList<>(Arrays.asList(1, 2, 3));
        allPriorities.removeAll(usedPriorities);
        return allPriorities;
    }

    private List<Faculty> getEnrolledFaculties(Collection<Integer> facultiesId) throws DaoSystemException {
        List<Faculty> enrolledFaculties = new ArrayList<>();
        for (Integer facultyId : facultiesId) {
            enrolledFaculties.add(facultyService.getByFacultyId(facultyId));
        }
        return enrolledFaculties;
    }

    private void copyList(
            List<FacultiesInfoBean> facultiesInfoBeans,
            List<FacultiesInfoBean> facultiesInfoBeansPagination,
            int page,
            int recordsPerPage) {
        facultiesInfoBeansPagination.clear();
        int lastElementOnPage = (page - 1) * recordsPerPage + recordsPerPage;
        if (lastElementOnPage > facultiesInfoBeans.size()) {
            lastElementOnPage = facultiesInfoBeans.size();
        }
        for (int i = (page - 1) * recordsPerPage; i < lastElementOnPage; i++) {
            facultiesInfoBeansPagination.add(facultiesInfoBeans.get(i));
        }
    }

    private void setUpSessionAttributes(HttpSession session, int numberOfPages,
                                        List<FacultiesInfoBean> facultiesInfoBeans,
                                        List<FacultiesInfoBean> facultiesInfoBeansPagination) {
        session.setAttribute(SessionAttribute.NUMBER_OF_PAGES, numberOfPages);
        session.setAttribute(SessionAttribute.CURRENT_PAGE, page);
        session.setAttribute(SessionAttribute.FACULTIES_INFO_BEANS, facultiesInfoBeans);
        session.setAttribute(SessionAttribute.FACULTIES_INFO_BEANS_PAGINATION, facultiesInfoBeansPagination);
    }


    private void preparePage(HttpServletResponse response, HttpServletRequest request,
                             HttpSession session, FilterChain filterChain) throws IOException, ServletException {
        Boolean facultiesIsSorted = (Boolean) session.getAttribute(SessionAttribute.FACULTIES_IS_SORTED);
        String sortType = (String) session.getAttribute(SessionAttribute.FACULTIES_PUBLIC_SORT_TYPE);
        String reset = (String) request.getAttribute(COMMAND);
        if (sortType == null || sortType.equals(SORT_TYPE_NO_SORT) ||
                (reset != null && reset.equals(SORT_TYPE_NO_SORT))) {
            List<FacultiesInfoBean> facultiesInfoBeans = new ArrayList<>();
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
                    FacultiesInfoBean facultiesInfoBean = new FacultiesInfoBean();
                    facultiesInfoBean.setFaculty(faculty);
                    facultiesInfoBean.setSubjects(subjects);
                    facultiesInfoBeans.add(facultiesInfoBean);
                }
                if (request.getParameter(PARAM_PAGE) != null)
                    page = Integer.parseInt(request.getParameter(PARAM_PAGE));
                int numberOfRecords = facultiesInfoBeans.size();
                int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);
                if (page > numberOfPages) {
                    page = 1;
                    List<FacultiesInfoBean> facultiesInfoBeansPagination = new ArrayList<>();
                    copyList(facultiesInfoBeans, facultiesInfoBeansPagination, page, RECORDS_PER_PAGE);
                    if (facultiesIsSorted != null && !facultiesIsSorted) {
                        session.setAttribute(SessionAttribute.FACULTIES_IS_SORTED, false);
                    } else {
                        session.setAttribute(SessionAttribute.FACULTIES_IS_SORTED, true);
                    }
                    setUpSessionAttributes(session, numberOfPages, facultiesInfoBeans, facultiesInfoBeansPagination);
                } else {
                    List<FacultiesInfoBean> facultiesInfoBeansPagination = new ArrayList<>();
                    copyList(facultiesInfoBeans, facultiesInfoBeansPagination, page, RECORDS_PER_PAGE);
                    if (facultiesIsSorted != null && !facultiesIsSorted) {
                        session.setAttribute(SessionAttribute.FACULTIES_IS_SORTED, false);
                    } else {
                        session.setAttribute(SessionAttribute.FACULTIES_IS_SORTED, true);
                    }
                    setUpSessionAttributes(session, numberOfPages, facultiesInfoBeans, facultiesInfoBeansPagination);
                }
                session.setAttribute(SessionAttribute.FACULTIES_PUBLIC_SORT_TYPE, SORT_TYPE_NO_SORT);
            } catch (DaoSystemException e) {
                if (e.getMessage().equals(ExceptionMessages.SQL_EXCEPTION)) {
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }
            filterChain.doFilter(request, response);
        } else {
            System.out.println(2);
            List<FacultiesInfoBean> facultiesInfoBeans =
                    (List<FacultiesInfoBean>) session.getAttribute(SessionAttribute.FACULTIES_INFO_BEANS);
            if (request.getParameter(PARAM_PAGE) != null)
                page = Integer.parseInt(request.getParameter(PARAM_PAGE));
            int numberOfRecords = facultiesInfoBeans.size();
            int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);
            if (page > numberOfPages) {
                page = 1;
                List<FacultiesInfoBean> facultiesInfoBeansPagination = new ArrayList<>();
                copyList(facultiesInfoBeans, facultiesInfoBeansPagination, page, RECORDS_PER_PAGE);
                if (facultiesIsSorted != null && !facultiesIsSorted) {
                    session.setAttribute(SessionAttribute.FACULTIES_IS_SORTED, false);
                } else {
                    session.setAttribute(SessionAttribute.FACULTIES_IS_SORTED, true);
                }
                setUpSessionAttributes(session, numberOfPages, facultiesInfoBeans, facultiesInfoBeansPagination);
            } else {
                List<FacultiesInfoBean> facultiesInfoBeansPagination = new ArrayList<>();
                copyList(facultiesInfoBeans, facultiesInfoBeansPagination, page, RECORDS_PER_PAGE);
                if (facultiesIsSorted != null && !facultiesIsSorted) {
                    session.setAttribute(SessionAttribute.FACULTIES_IS_SORTED, false);
                } else {
                    session.setAttribute(SessionAttribute.FACULTIES_IS_SORTED, true);
                }
                setUpSessionAttributes(session, numberOfPages, facultiesInfoBeans, facultiesInfoBeansPagination);
            }
            filterChain.doFilter(request, response);
        }
    }
}
