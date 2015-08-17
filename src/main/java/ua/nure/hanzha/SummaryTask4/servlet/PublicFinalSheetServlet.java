package ua.nure.hanzha.SummaryTask4.servlet;

import ua.nure.hanzha.SummaryTask4.bean.ReadyFinalEntrantSheetBean;
import ua.nure.hanzha.SummaryTask4.constants.*;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.entrantFinalSheet.EntrantFinalSheetService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 14/08/15.
 */
public class PublicFinalSheetServlet extends HttpServlet {

    private static final String COMMAND_FIND_ALL_ENTRANTS = "findAllEntrants";
    private static final String PARAM_PAGE = "page";
    private EntrantFinalSheetService entrantFinalSheetService;

    @Override
    public void init() throws ServletException {
        entrantFinalSheetService =
                (EntrantFinalSheetService) getServletContext().getAttribute(AppAttribute.ENTRANT_FINAL_SHEET_SERVICE);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        Integer page;
        if (request.getParameter(PARAM_PAGE) != null) {
            page = Integer.parseInt(request.getParameter(PARAM_PAGE));
            request.setAttribute(PARAM_PAGE, page);
        }
        try {
            List<ReadyFinalEntrantSheetBean> passedEntrants = entrantFinalSheetService.getPassedEntrants();
            session.setAttribute(SessionAttribute.PASSED_ENTRANTS, passedEntrants);
            if (request.getParameter(PARAM_PAGE) != null) {
                page = Integer.parseInt(request.getParameter(PARAM_PAGE));
                response.sendRedirect(Pages.PAGINATION_FINAL_SHEET + "?page=" + page + "&command=" + COMMAND_FIND_ALL_ENTRANTS);
            } else {
                response.sendRedirect(Pages.PAGINATION_FINAL_SHEET + "?command=" + COMMAND_FIND_ALL_ENTRANTS);

            }
        } catch (DaoSystemException e) {
            if (e.getMessage().equals(ExceptionMessages.SELECT_EXCEPTION_MESSAGE)) {
                request.setAttribute(RequestAttribute.IS_READY_FINAL_SHEET, false);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.PUBLIC_FINAL_SHEET_HTML);
                requestDispatcher.forward(request, response);
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
    }
}
