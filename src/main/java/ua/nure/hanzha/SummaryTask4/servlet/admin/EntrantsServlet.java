package ua.nure.hanzha.SummaryTask4.servlet.admin;

import ua.nure.hanzha.SummaryTask4.bean.EntrantInfoAdminBean;
import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.entrantInfoAdmin.EntrantInfoAdminService;

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

    private EntrantInfoAdminService entrantInfoAdminService;

    @Override
    public void init() throws ServletException {
        entrantInfoAdminService = (EntrantInfoAdminService) getServletContext().getAttribute(AppAttribute.ENTRANT_INFO_ADMIN_SERVICE);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<EntrantInfoAdminBean> entrants = entrantInfoAdminService.getEntrantsForAdmin();
            int page = 1;
            int recordsPerPage = 20;
            if (request.getParameter(PARAM_PAGE) != null)
                page = Integer.parseInt(request.getParameter(PARAM_PAGE));
            int numberOfRecords = entrants.size();
            int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / recordsPerPage);
            List<EntrantInfoAdminBean> entrantsFixed = new ArrayList<>(20);
            copyList(entrants, entrantsFixed, page, recordsPerPage);
            HttpSession session = request.getSession(false);
            session.setAttribute(SessionAttribute.ENTRANTS_ADMIN, entrantsFixed);
            session.setAttribute(SessionAttribute.NUMBER_OF_PAGES, numberOfPages);
            session.setAttribute(SessionAttribute.CURRENT_PAGE, page);
            response.sendRedirect(Pages.ENTRANTS_ADMIN_HTML);
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
}
