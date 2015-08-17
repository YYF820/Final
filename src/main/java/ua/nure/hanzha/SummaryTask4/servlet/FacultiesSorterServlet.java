package ua.nure.hanzha.SummaryTask4.servlet;

import ua.nure.hanzha.SummaryTask4.bean.FacultiesInfoBean;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 12/08/15.
 */
public class FacultiesSorterServlet extends HttpServlet {

    private static final String PARAM_SORT = "sort";
    private static final String SORT_TYPE_BY_NAME_ASC = "byNameAsc";
    private static final String SORT_TYPE_BY_NAME_DESC = "byNameDesc";
    private static final String SORT_TYPE_BY_BUDGET_SPOTS_ASC = "byBudgetSpotsAsc";
    private static final String SORT_TYPE_BY_BUDGET_SPOTS_DESC = "byBudgetSpotsDesc";
    private static final String SORT_TYPE_BY_ALL_SPOTS_ASC = "byAllSpotsAsc";
    private static final String SORT_TYPE_BY_ALL_SPOTS_DESC = "byAllSpotsDesc";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        List<FacultiesInfoBean> facultiesInfoBeans =
                (List<FacultiesInfoBean>) session.getAttribute(SessionAttribute.FACULTIES_INFO_BEANS);
        String sortType = request.getParameter(PARAM_SORT);
        if (sortType == null) {
            session.setAttribute(SessionAttribute.FACULTIES_IS_SORTED, false);
            response.sendRedirect(Pages.FACULTIES_HTML);
        } else {
            switch (sortType) {
                case SORT_TYPE_BY_NAME_ASC:
                    Collections.sort(facultiesInfoBeans, new Comparator<FacultiesInfoBean>() {
                        @Override
                        public int compare(FacultiesInfoBean o1, FacultiesInfoBean o2) {
                            return o1.getFaculty().getName().compareTo(o2.getFaculty().getName());
                        }
                    });
                    session.setAttribute(SessionAttribute.FACULTIES_PUBLIC_SORT_TYPE, SORT_TYPE_BY_NAME_ASC);
                    break;
                case SORT_TYPE_BY_NAME_DESC:
                    Collections.sort(facultiesInfoBeans, new Comparator<FacultiesInfoBean>() {
                        @Override
                        public int compare(FacultiesInfoBean o1, FacultiesInfoBean o2) {
                            return o2.getFaculty().getName().compareTo(o1.getFaculty().getName());
                        }
                    });
                    session.setAttribute(SessionAttribute.FACULTIES_PUBLIC_SORT_TYPE, SORT_TYPE_BY_NAME_DESC);
                    break;
                case SORT_TYPE_BY_BUDGET_SPOTS_DESC:
                    Collections.sort(facultiesInfoBeans, new Comparator<FacultiesInfoBean>() {
                        @Override
                        public int compare(FacultiesInfoBean o1, FacultiesInfoBean o2) {
                            if (o1.getFaculty().getBudgetSpots() < o2.getFaculty().getBudgetSpots()) {
                                return 1;
                            } else if (o1.getFaculty().getBudgetSpots() > o2.getFaculty().getBudgetSpots()) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }
                    });
                    session.setAttribute(SessionAttribute.FACULTIES_PUBLIC_SORT_TYPE, SORT_TYPE_BY_BUDGET_SPOTS_DESC);
                    break;
                case SORT_TYPE_BY_BUDGET_SPOTS_ASC:
                    Collections.sort(facultiesInfoBeans, new Comparator<FacultiesInfoBean>() {
                        @Override
                        public int compare(FacultiesInfoBean o1, FacultiesInfoBean o2) {
                            if (o2.getFaculty().getBudgetSpots() < o1.getFaculty().getBudgetSpots()) {
                                return 1;
                            } else if (o2.getFaculty().getBudgetSpots() > o1.getFaculty().getBudgetSpots()) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }
                    });
                    session.setAttribute(SessionAttribute.FACULTIES_PUBLIC_SORT_TYPE, SORT_TYPE_BY_BUDGET_SPOTS_ASC);
                    break;
                case SORT_TYPE_BY_ALL_SPOTS_DESC:
                    Collections.sort(facultiesInfoBeans, new Comparator<FacultiesInfoBean>() {
                        @Override
                        public int compare(FacultiesInfoBean o1, FacultiesInfoBean o2) {
                            if (o1.getFaculty().getTotalSpots() < o2.getFaculty().getTotalSpots()) {
                                return 1;
                            } else if (o1.getFaculty().getTotalSpots() > o2.getFaculty().getTotalSpots()) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }
                    });
                    session.setAttribute(SessionAttribute.FACULTIES_PUBLIC_SORT_TYPE, SORT_TYPE_BY_ALL_SPOTS_DESC);
                    break;
                case SORT_TYPE_BY_ALL_SPOTS_ASC:
                    Collections.sort(facultiesInfoBeans, new Comparator<FacultiesInfoBean>() {
                        @Override
                        public int compare(FacultiesInfoBean o1, FacultiesInfoBean o2) {
                            if (o2.getFaculty().getTotalSpots() < o1.getFaculty().getTotalSpots()) {
                                return 1;
                            } else if (o2.getFaculty().getTotalSpots() > o1.getFaculty().getTotalSpots()) {
                                return -1;
                            } else {
                                return 0;
                            }
                        }
                    });
                    session.setAttribute(SessionAttribute.FACULTIES_PUBLIC_SORT_TYPE, SORT_TYPE_BY_ALL_SPOTS_ASC);
                    break;
                default:
                    break;

            }
            session.setAttribute(SessionAttribute.FACULTIES_IS_SORTED, true);
            response.sendRedirect(Pages.FACULTIES_HTML + "?page=1");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(Pages.FACULTIES_HTML);
    }
}
