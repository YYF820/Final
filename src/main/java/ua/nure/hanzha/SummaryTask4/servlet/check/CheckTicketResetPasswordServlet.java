package ua.nure.hanzha.SummaryTask4.servlet.check;

import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.db.util.PasswordHash;
import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.entrant.EntrantService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 04/08/15.
 */

public class CheckTicketResetPasswordServlet extends HttpServlet {

    private static final String EMPTY_PARAM = "";

    private static final String PARAM_TICKET_RESET_PASSWORD = "ticketResetPassword";

    private static final int STATUS_BLOCKED_ID = 1;


    private EntrantService entrantService;

    @Override
    public void init() throws ServletException {
        entrantService = (EntrantService) getServletContext().getAttribute(AppAttribute.ENTRANT_SERVICE);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Long counterBadTicketInserts = (Long) session.getAttribute(SessionAttribute.CHECK_TICKET_COUNTER_BAD_TICKET_INSERTS);
        session.setAttribute(SessionAttribute.CHECK_TICKET_COUNTER_BAD_TICKET_INSERTS, ++counterBadTicketInserts);
        if (counterBadTicketInserts == 3) {
            String ticketResetPassword = request.getParameter(PARAM_TICKET_RESET_PASSWORD);
            boolean checkEmpty = checkEmpty(session, ticketResetPassword);
            String hashTicketResetPassword = (String) session.getAttribute(SessionAttribute.CHECK_TICKET_HASH_TICKET_RESET_PASSWORD);
            if (checkEmpty || !PasswordHash.validatePassword(ticketResetPassword, hashTicketResetPassword)) {
                Entrant entrantForBlock = (Entrant) session.getAttribute(SessionAttribute.ENTRANT_FOR_VERIFY_ACCOUNT_RESET_PASSWORD);
                int entrantId = entrantForBlock.getId();
                blockAccount(session, request, response, entrantId);
            } else {
                session.setAttribute(SessionAttribute.CHECK_TICKET_IS_TICKET_RESET_PASSWORD_CORRECT, true);
                response.sendRedirect(Pages.RESET_PASSWORD_HTML);
            }
        } else {
            String ticketResetPassword = request.getParameter(PARAM_TICKET_RESET_PASSWORD);
            session.setAttribute(SessionAttribute.CHECK_TICKET_TICKET_RESET_PASSWORD, ticketResetPassword);
            boolean checkEmpty = checkEmpty(session, ticketResetPassword);
            if (checkEmpty) {
                response.sendRedirect(Pages.RESET_PASSWORD_MESSAGE_SENT_HTML + "?" + PARAM_TICKET_RESET_PASSWORD + "=" + ticketResetPassword);
            } else {
                String hashTicketResetPassword = (String) session.getAttribute(SessionAttribute.CHECK_TICKET_HASH_TICKET_RESET_PASSWORD);
                if (!PasswordHash.validatePassword(ticketResetPassword, hashTicketResetPassword)) {
                    session.setAttribute(SessionAttribute.CHECK_TICKET_IS_TICKET_RESET_PASSWORD_CORRECT, false);
                    response.sendRedirect(Pages.RESET_PASSWORD_MESSAGE_SENT_HTML + "?" + PARAM_TICKET_RESET_PASSWORD + "=" + ticketResetPassword);
                } else {
                    session.setAttribute(SessionAttribute.CHECK_TICKET_IS_TICKET_RESET_PASSWORD_CORRECT, true);
                    response.sendRedirect(Pages.RESET_PASSWORD_HTML);
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(Pages.RESET_PASSWORD_MESSAGE_SENT_HTML);
    }

    private boolean checkEmpty(HttpSession session, String ticketResetPassword) {
        if (ticketResetPassword.equals(EMPTY_PARAM)) {
            session.setAttribute(SessionAttribute.CHECK_TICKET_IS_EMPTY, true);
            return true;
        }
        return false;
    }

    private void blockAccount(HttpSession session, HttpServletRequest request,
                              HttpServletResponse response, int entrantId) throws IOException {
        try {
            entrantService.updateEntrantStatus(STATUS_BLOCKED_ID, entrantId);
            session.invalidate();
            request.getSession(true).setAttribute(SessionAttribute.CHECK_TICKET_IS_BLOCKED_ACCOUNT, true);
            response.sendRedirect(Pages.RESET_PASSWORD_BLOCK_ACCOUNT);
        } catch (DaoSystemException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
