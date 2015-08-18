package ua.nure.hanzha.SummaryTask4.servlet;

import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.enums.EntrantStatus;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.entrant.EntrantService;
import ua.nure.hanzha.SummaryTask4.service.user.UserService;
import ua.nure.hanzha.SummaryTask4.util.TicketsWriterReader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 02/08/15.
 */
public class VerifyAccountServlet extends HttpServlet {

    private static final String TICKET = "ticket";


    private UserService userService;
    private EntrantService entrantService;


    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute(AppAttribute.USER_SERVICE);
        entrantService = (EntrantService) getServletContext().getAttribute(AppAttribute.ENTRANT_SERVICE);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        String ticket = request.getParameter(TICKET);
        String accountName = TicketsWriterReader.getValue(ticket);
        if (accountName == null) {
            session.setAttribute(SessionAttribute.VERIFY_ACCOUNT_IS_VERIFIED_ACCOUNT, false);
            response.sendRedirect(Pages.VERIFY_ACCOUNT_HTML);
        } else {
            try {
                User userForConfirm = userService.getByEmail(accountName);
                Entrant entrant = entrantService.getByUserId(userForConfirm.getId());
                int activeStatusId = EntrantStatus.ACTIVE.ordinal() + 1;
                entrantService.updateEntrantStatus(activeStatusId, entrant.getId());
                TicketsWriterReader.removePair(ticket);
                request.setAttribute(SessionAttribute.VERIFY_ACCOUNT_ACCOUNT_NAME, accountName);
                session.setAttribute(SessionAttribute.VERIFY_ACCOUNT_IS_VERIFIED_ACCOUNT, true);
                response.sendRedirect(Pages.VERIFY_ACCOUNT_HTML);
            } catch (DaoSystemException e) {
                session.setAttribute(SessionAttribute.VERIFY_ACCOUNT_IS_VERIFIED_ACCOUNT, false);
                response.sendRedirect(Pages.VERIFY_ACCOUNT_HTML);
            }

        }
    }
}
