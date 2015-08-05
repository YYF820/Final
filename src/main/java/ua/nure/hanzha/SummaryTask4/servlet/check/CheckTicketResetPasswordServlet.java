package ua.nure.hanzha.SummaryTask4.servlet.check;

import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.db.util.PasswordHash;
import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.entrant.EntrantService;

import javax.servlet.RequestDispatcher;
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

    private static final String REQUEST_ATTRIBUTE_IS_TICKET_RESET_PASSWORD_EMPTY = "isTicketResetPasswordEmpty";
    private static final String REQUEST_ATTRIBUTE_IS_BLOCKED_ACCOUNT = "isBlockedAccount";
    private static final String REQUEST_ATTRIBUTE_IS_TICKET_RESET_PASSWORD_CORRECT = "isTicketResetPasswordCorrect";
    private static final String REQUEST_ATTRIBUTE_IS_FROM_SERVLET = "isFromServlet";
    private static final String REQUEST_ATTRIBUTE_TICKET_RESET_PASSWORD = "ticketResetPassword";

    private static final String SESSION_ATTRIBUTE_HASH_TICKET_RESET_PASSWORD = "hashTicketRecoverPassword";
    private static final String SESSION_ATTRIBUTE_COUNTER_BAD_TICKET_INSERTS = "counterBadTicketInserts";
    private static final String SESSION_ATTRIBUTE_ENTRANT_FOR_VERIFY_ACCOUNT_RESET_PASSWORD = "entrantForVerifyAccountResetPassword";

    private static final int STATUS_BLOCKED_ID = 1;


    private EntrantService entrantService;

    @Override
    public void init() throws ServletException {
        entrantService = (EntrantService) getServletContext().getAttribute(AppAttribute.ENTRANT_SERVICE);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        Long counterBadTicketInserts = (Long) session.getAttribute(SESSION_ATTRIBUTE_COUNTER_BAD_TICKET_INSERTS);
        if (counterBadTicketInserts == 3) {
            String ticketResetPassword = request.getParameter(PARAM_TICKET_RESET_PASSWORD);
            request.setAttribute(REQUEST_ATTRIBUTE_TICKET_RESET_PASSWORD, ticketResetPassword);
            boolean checkEmpty = checkEmpty(request, ticketResetPassword);
            if (checkEmpty) {
                Entrant entrantForBlock = (Entrant) session.getAttribute(SESSION_ATTRIBUTE_ENTRANT_FOR_VERIFY_ACCOUNT_RESET_PASSWORD);
                int entrantId = entrantForBlock.getId();
                try {
                    entrantService.updateEntrantStatus(STATUS_BLOCKED_ID, entrantId);
                    request.setAttribute(REQUEST_ATTRIBUTE_IS_BLOCKED_ACCOUNT, true);
                    session.invalidate();
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.RESET_PASSWORD_MESSAGE_SENT_HTML);
                    requestDispatcher.forward(request, response);
                } catch (DaoSystemException e) {
                    e.printStackTrace();
                }
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.RESET_PASSWORD_MESSAGE_SENT_HTML);
                requestDispatcher.forward(request, response);
            } else {
                Entrant entrantForBlock = (Entrant) session.getAttribute(SESSION_ATTRIBUTE_ENTRANT_FOR_VERIFY_ACCOUNT_RESET_PASSWORD);
                int entrantId = entrantForBlock.getId();
                String hashTicketResetPassword = (String) session.getAttribute(SESSION_ATTRIBUTE_HASH_TICKET_RESET_PASSWORD);
                if (!PasswordHash.validatePassword(ticketResetPassword, hashTicketResetPassword)) {
                    try {
                        entrantService.updateEntrantStatus(STATUS_BLOCKED_ID, entrantId);
                        request.setAttribute(REQUEST_ATTRIBUTE_IS_BLOCKED_ACCOUNT, true);
                        session.invalidate();
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.RESET_PASSWORD_MESSAGE_SENT_HTML);
                        requestDispatcher.forward(request, response);
                    } catch (DaoSystemException e) {
                        //TODO: no entrant by id, add 500 page maybe your account was deleted.
                        e.printStackTrace();
                    }
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.RESET_PASSWORD_MESSAGE_SENT_HTML);
                    requestDispatcher.forward(request, response);
                } else {
                    request.setAttribute(REQUEST_ATTRIBUTE_IS_TICKET_RESET_PASSWORD_CORRECT, true);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.RESET_PASSWORD_HTML);
                    requestDispatcher.forward(request, response);
                }
            }
        } else {
            String ticketResetPassword = request.getParameter(PARAM_TICKET_RESET_PASSWORD);
            request.setAttribute(REQUEST_ATTRIBUTE_TICKET_RESET_PASSWORD, ticketResetPassword);
            boolean checkEmpty = checkEmpty(request, ticketResetPassword);
            if (checkEmpty) {
                request.setAttribute(REQUEST_ATTRIBUTE_IS_FROM_SERVLET, true);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.RESET_PASSWORD_MESSAGE_SENT_HTML);
                requestDispatcher.forward(request, response);
            } else {
                String hashTicketResetPassword = (String) session.getAttribute(SESSION_ATTRIBUTE_HASH_TICKET_RESET_PASSWORD);
                if (!PasswordHash.validatePassword(ticketResetPassword, hashTicketResetPassword)) {
                    request.setAttribute(REQUEST_ATTRIBUTE_IS_FROM_SERVLET, true);
                    request.setAttribute(REQUEST_ATTRIBUTE_IS_TICKET_RESET_PASSWORD_CORRECT, false);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.RESET_PASSWORD_MESSAGE_SENT_HTML);
                    requestDispatcher.forward(request, response);
                } else {
                    request.setAttribute(REQUEST_ATTRIBUTE_IS_TICKET_RESET_PASSWORD_CORRECT, true);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.RESET_PASSWORD_HTML);
                    requestDispatcher.forward(request, response);
                }
            }
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(Pages.RESET_PASSWORD_MESSAGE_SENT_HTML);
    }

    private boolean checkEmpty(HttpServletRequest request, String ticketResetPassword) {
        if (ticketResetPassword.equals(EMPTY_PARAM)) {
            request.setAttribute(REQUEST_ATTRIBUTE_IS_TICKET_RESET_PASSWORD_EMPTY, true);
            return true;
        }
        return false;
    }
}
