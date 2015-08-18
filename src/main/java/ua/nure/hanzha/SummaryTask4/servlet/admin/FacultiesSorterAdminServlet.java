package ua.nure.hanzha.SummaryTask4.servlet.admin;

import ua.nure.hanzha.SummaryTask4.bean.FacultiesInfoBean;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.servlet.callable.facultiesSorter.FacultiesSorterCallable;
import ua.nure.hanzha.SummaryTask4.servlet.callable.facultiesSorter.FacultiesSorterOperationsMap;

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
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 08/08/15.
 */
public class FacultiesSorterAdminServlet extends HttpServlet {
    private static final String PARAM_SORT = "sort";
    private static final String PARAM_PAGE = "page";
    private static final String SORT_TYPE_NO_SORT = "noSort";
    private static final int RECORDS_PER_PAGE = 5;
    private static int page = 1;

    @Override
    public void init() throws ServletException {
        FacultiesSorterOperationsMap.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        List<FacultiesInfoBean> facultiesInfoBeans =
                (List<FacultiesInfoBean>) session.getAttribute(SessionAttribute.ADMIN_FACULTIES_INFO_BEANS);

        String sortType = request.getParameter(PARAM_SORT);
        if (sortType == null) {
            session.setAttribute(SessionAttribute.ADMIN_IS_SORTED_FACULTIES, false);
            response.sendRedirect(Pages.FACULTIES_ADMIN_HTML);
        } else {
            FacultiesSorterOperationsMap.initFacultiesSorterCallableMap(session, facultiesInfoBeans);
            FacultiesSorterCallable facultiesSorterCallable =
                    FacultiesSorterOperationsMap.getFacultiesSorterCallable(sortType);
            if (facultiesSorterCallable != null) {
                facultiesSorterCallable.call(SessionAttribute.ADMIN_SORT_TYPE);
                page = (int) session.getAttribute(SessionAttribute.CURRENT_PAGE);
                int numberOfRecords = facultiesInfoBeans.size();
                int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);
                List<FacultiesInfoBean> facultiesInfoBeansSortedPagination = new ArrayList<>();
                copyList(facultiesInfoBeans, facultiesInfoBeansSortedPagination, page, RECORDS_PER_PAGE);
                setUpAttributes(session, numberOfPages, facultiesInfoBeans, facultiesInfoBeansSortedPagination);
                response.sendRedirect(Pages.FACULTIES_ADMIN_HTML);
            } else {
                //UNSUPPORTED SORT TYPE
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        session.removeAttribute(SessionAttribute.ADMIN_FACULTIES_IS_FROM_POST);
        String sortType = String.valueOf(session.getAttribute(SessionAttribute.ADMIN_SORT_TYPE));
        if (sortType == null || sortType.equals(SORT_TYPE_NO_SORT)) {
            response.sendRedirect(Pages.FACULTIES_ADMIN_SERVLET);
        } else {
            List<FacultiesInfoBean> facultiesInfoBeans =
                    (List<FacultiesInfoBean>) session.getAttribute(SessionAttribute.ADMIN_FACULTIES_INFO_BEANS_SORTED);
            if (request.getParameter(PARAM_PAGE) != null)
                page = Integer.parseInt(request.getParameter(PARAM_PAGE));
            int numberOfRecords = facultiesInfoBeans.size();
            int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);
            List<FacultiesInfoBean> facultiesInfoBeansSortedPagination = new ArrayList<>();
            copyList(facultiesInfoBeans, facultiesInfoBeansSortedPagination, page, RECORDS_PER_PAGE);
            session.setAttribute(SessionAttribute.NUMBER_OF_PAGES, numberOfPages);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, page);
            session.setAttribute(SessionAttribute.ADMIN_FACULTIES_INFO_BEANS_SORTED, facultiesInfoBeans);
            session.setAttribute(SessionAttribute.ADMIN_FACULTIES_INFO_BEANS_SORTED_PAGINATION, facultiesInfoBeansSortedPagination);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.FACULTIES_ADMIN_HTML);
            requestDispatcher.forward(request, response);
        }
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
            System.out.println(i);
            facultiesInfoBeansPagination.add(facultiesInfoBeans.get(i));
        }
    }

    private void setUpAttributes(HttpSession session, int numberOfPages,
                                 List<FacultiesInfoBean> facultiesInfoBeans,
                                 List<FacultiesInfoBean> facultiesInfoBeansSortedPagination) {
        session.setAttribute(SessionAttribute.NUMBER_OF_PAGES, numberOfPages);
        session.setAttribute(SessionAttribute.CURRENT_PAGE, page);
        session.setAttribute(SessionAttribute.ADMIN_FACULTIES_INFO_BEANS_SORTED, facultiesInfoBeans);
        session.setAttribute(SessionAttribute.ADMIN_FACULTIES_INFO_BEANS_SORTED_PAGINATION, facultiesInfoBeansSortedPagination);
        session.setAttribute(SessionAttribute.ADMIN_IS_SORTED_FACULTIES, true);
        session.setAttribute(SessionAttribute.ADMIN_FACULTIES_IS_FROM_POST, true);
    }


}

