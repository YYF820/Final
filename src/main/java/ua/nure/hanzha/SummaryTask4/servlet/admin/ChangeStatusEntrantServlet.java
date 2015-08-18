package ua.nure.hanzha.SummaryTask4.servlet.admin;

import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.entrant.EntrantService;
import ua.nure.hanzha.SummaryTask4.service.user.UserService;
import ua.nure.hanzha.SummaryTask4.servlet.callable.adminChangeStatus.AdminChangeStatusCallable;
import ua.nure.hanzha.SummaryTask4.servlet.callable.adminChangeStatus.AdminChangeStatusOperationsMap;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 06/08/15.
 */
public class ChangeStatusEntrantServlet extends HttpServlet {

    private static final String PARAM_ENTRANT_ID = "entrantId";
    private static final String PARAM_ACTION = "action";

    private EntrantService entrantService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        entrantService = (EntrantService) getServletContext().getAttribute(AppAttribute.ENTRANT_SERVICE);
        userService = (UserService) getServletContext().getAttribute(AppAttribute.USER_SERVICE);
        AdminChangeStatusOperationsMap.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String action = request.getParameter(PARAM_ACTION);
        int entrantId = Integer.parseInt(request.getParameter(PARAM_ENTRANT_ID));
        AdminChangeStatusOperationsMap
                .initAdminChangeStatusCallableMap
                        (session, request, response, entrantService, userService, entrantId);
        AdminChangeStatusCallable adminChangeStatusCallable =
                AdminChangeStatusOperationsMap.getAdminChangeStatusCallable(action);
        if (adminChangeStatusCallable != null) {
            try {
                adminChangeStatusCallable.call();
            } catch (DaoSystemException e) {
                //User deleted by another admin or SQLException
                session.setAttribute(SessionAttribute.ADMIN_IS_ENTRANT_BLOCKED, false);
                int currentPage = (int) session.getAttribute(SessionAttribute.CURRENT_PAGE);
                response.sendRedirect(Pages.ENTRANTS_ADMIN_SERVLET + "?page=" + currentPage);
            }
        } else {
            //UNSUPPORTED ACTION
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
