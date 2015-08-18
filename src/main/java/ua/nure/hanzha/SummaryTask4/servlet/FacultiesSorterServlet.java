package ua.nure.hanzha.SummaryTask4.servlet;

import ua.nure.hanzha.SummaryTask4.bean.FacultiesInfoBean;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.servlet.callable.facultiesSorter.FacultiesSorterCallable;
import ua.nure.hanzha.SummaryTask4.servlet.callable.facultiesSorter.FacultiesSorterOperationsMap;

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
public class FacultiesSorterServlet extends HttpServlet {

    private static final String PARAM_SORT = "sort";

    @Override
    public void init() throws ServletException {
        FacultiesSorterOperationsMap.getInstance();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        List<FacultiesInfoBean> facultiesInfoBeans =
                (List<FacultiesInfoBean>) session.getAttribute(SessionAttribute.FACULTIES_INFO_BEANS);
        String sortType = request.getParameter(PARAM_SORT);
        if (sortType == null) {
            session.setAttribute(SessionAttribute.FACULTIES_IS_SORTED, false);
            response.sendRedirect(Pages.FACULTIES_HTML);
        } else {
            FacultiesSorterOperationsMap.initFacultiesSorterCallableMap(session, facultiesInfoBeans);
            FacultiesSorterCallable facultiesSorterCallable =
                    FacultiesSorterOperationsMap.getFacultiesSorterCallable(sortType);
            if (facultiesSorterCallable != null) {
                facultiesSorterCallable.call(SessionAttribute.FACULTIES_PUBLIC_SORT_TYPE);
                session.setAttribute(SessionAttribute.FACULTIES_IS_SORTED, true);
                response.sendRedirect(Pages.FACULTIES_HTML + "?page=1");
            } else {
                //UNSUPPORTED SORT TYPE
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }

        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(Pages.FACULTIES_HTML);
    }
}
