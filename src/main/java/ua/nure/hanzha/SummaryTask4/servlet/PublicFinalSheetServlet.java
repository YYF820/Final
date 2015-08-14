package ua.nure.hanzha.SummaryTask4.servlet;

import ua.nure.hanzha.SummaryTask4.bean.ReadyFinalEntrantSheetBean;
import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.EntrantFinalSheet;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.entrantFinalSheet.EntrantFinalSheetService;

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

    private EntrantFinalSheetService entrantFinalSheetService;


    @Override
    public void init() throws ServletException {
        entrantFinalSheetService =
                (EntrantFinalSheetService) getServletContext().getAttribute(AppAttribute.ENTRANT_FINAL_SHEET_SERVICE);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        try {
            List<ReadyFinalEntrantSheetBean> passedEntrants = entrantFinalSheetService.getPassedEntrants();
            session.setAttribute(SessionAttribute.PASSED_ENTRANTS, passedEntrants);
            response.sendRedirect("/finalSheet.html");
        } catch (DaoSystemException e) {
            e.printStackTrace();
        }
    }
}
