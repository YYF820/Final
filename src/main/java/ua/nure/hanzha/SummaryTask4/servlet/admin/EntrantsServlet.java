package ua.nure.hanzha.SummaryTask4.servlet.admin;

import ua.nure.hanzha.SummaryTask4.bean.EntrantInfoAdminBean;
import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.entrantInfoAdmin.EntrantInfoAdminService;

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
 *         Created by faffi-ubuntu on 06/08/15.
 */
public class EntrantsServlet extends HttpServlet {

    private static final String PARAM_PAGE = "page";
    private static final int RECORDS_PER_PAGE = 20;
    private static int page = 1;

    private EntrantInfoAdminService entrantInfoAdminService;

    @Override
    public void init() throws ServletException {
        entrantInfoAdminService = (EntrantInfoAdminService) getServletContext().getAttribute(AppAttribute.ENTRANT_INFO_ADMIN_SERVICE);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<EntrantInfoAdminBean> entrants = entrantInfoAdminService.getEntrantsForAdmin();
            if (request.getParameter(PARAM_PAGE) != null)
                page = Integer.parseInt(request.getParameter(PARAM_PAGE));
            int numberOfRecords = entrants.size();
            int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);
            List<EntrantInfoAdminBean> entrantsFixed = new ArrayList<>(20);
            copyList(entrants, entrantsFixed, page, RECORDS_PER_PAGE);
            setUpSessionAttr(request.getSession(false), entrantsFixed, numberOfPages, page);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.ENTRANTS_ADMIN_HTML);
            requestDispatcher.forward(request, response);
        } catch (DaoSystemException e) {
            e.printStackTrace();
        }
    }

    private void copyList(List<EntrantInfoAdminBean> entrants, List<EntrantInfoAdminBean> entrantsPagination, int page, int recordsPerPage) {
        entrantsPagination.clear();
        int lastElementOnPage = (page - 1) * recordsPerPage + recordsPerPage;
        if (lastElementOnPage > entrants.size()) {
            lastElementOnPage = entrants.size();
        }
        System.out.println(page);
        for (int i = (page - 1) * recordsPerPage; i < lastElementOnPage; i++) {
            System.out.println(i);
            entrantsPagination.add(entrants.get(i));
        }
    }

    private void setUpSessionAttr(HttpSession session, List<EntrantInfoAdminBean> entrantsFixed,
                                  int numberOfPages, int page) {
        session.setAttribute(SessionAttribute.ENTRANTS_ADMIN, entrantsFixed);
        session.setAttribute(SessionAttribute.NUMBER_OF_PAGES, numberOfPages);
        session.setAttribute(SessionAttribute.CURRENT_PAGE, page);
    }
}
