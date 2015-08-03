package ua.nure.hanzha.SummaryTask4.servlet;

import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.RequestAttribute;
import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.enums.EntrantStatus;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.entrant.EntrantService;
import ua.nure.hanzha.SummaryTask4.service.user.UserService;
import ua.nure.hanzha.SummaryTask4.util.TicketsWriterReader;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 02/08/15.
 */
public class ConfirmAccountServlet extends HttpServlet {

    private static final String TICKET = "ticket";
    private static final String REQUEST_ATTRIBUTE_IS_CONFIRMED_ACCOUNT = "isConfirmedAccount";

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
        String ticket = request.getParameter(TICKET);
        String accountName = TicketsWriterReader.getValue(ticket);
        System.out.println(accountName);
        if (accountName == null) {
            request.setAttribute(REQUEST_ATTRIBUTE_IS_CONFIRMED_ACCOUNT, false);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.CONFIRM_ACCOUNT_HTML);
            requestDispatcher.forward(request, response);
        } else {
            try {
                User userForConfirm = userService.selectByEmail(accountName);
                System.out.println(userForConfirm);
                int userId = userForConfirm.getId();
                Entrant entrant = entrantService.selectByUserId(userId);
                int entrantId = entrant.getId();
                int activeStatusId = EntrantStatus.ACTIVE.ordinal() + 1;
                entrantService.updateEntrantStatus(activeStatusId, entrantId);
                TicketsWriterReader.removePair(ticket);
                request.setAttribute(RequestAttribute.ACCOUNT_NAME, accountName);
                request.setAttribute(REQUEST_ATTRIBUTE_IS_CONFIRMED_ACCOUNT, true);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.CONFIRM_ACCOUNT_HTML);
                requestDispatcher.forward(request, response);
            } catch (DaoSystemException e) {
                request.setAttribute(REQUEST_ATTRIBUTE_IS_CONFIRMED_ACCOUNT, false);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.CONFIRM_ACCOUNT_HTML);
                requestDispatcher.forward(request, response);
            }

        }
    }
}
