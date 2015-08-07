package ua.nure.hanzha.SummaryTask4.servlet.admin;

import ua.nure.hanzha.SummaryTask4.bean.MailInfoUpdatedPasswordOrBlockedBean;
import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.RequestAttribute;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.exception.ServletSystemException;
import ua.nure.hanzha.SummaryTask4.service.entrant.EntrantService;
import ua.nure.hanzha.SummaryTask4.service.user.UserService;

import javax.servlet.RequestDispatcher;
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
    private static final String ADMIN_COMMAND_BAN_USER = "adminCommandBanUser";
    private static final String ADMIN_COMMAND_UN_BAN_USER = "adminCommandUnBanUser";

    private static final String ACTION_BAN = "ban";
    private static final String ACTION_UN_BAN = "unBan";


    private static final int STATUS_BLOCK = 1;
    private static final int STATUS_ACTIVE = 2;

    private EntrantService entrantService;
    private UserService userService;

    @Override
    public void init() throws ServletException {
        entrantService = (EntrantService) getServletContext().getAttribute(AppAttribute.ENTRANT_SERVICE);
        userService = (UserService) getServletContext().getAttribute(AppAttribute.USER_SERVICE);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        String action = request.getParameter(PARAM_ACTION);
        int entrantId = Integer.parseInt(request.getParameter(PARAM_ENTRANT_ID));
        switch (action) {
            case ACTION_BAN:
                try {
                    Entrant entrantToBlock = entrantService.getById(entrantId);
                    int userId = entrantToBlock.getUserId();
                    User userToNotifyByEmail = userService.getById(userId);
                    entrantService.updateEntrantStatus(STATUS_BLOCK, entrantId);
                    prepareMessageBlockAccount(request, userToNotifyByEmail);
                    session.setAttribute(SessionAttribute.COMMAND, ADMIN_COMMAND_BAN_USER);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.MAIL_SENDER_SERVLET);
                    requestDispatcher.forward(request, response);
                } catch (DaoSystemException e) {
                    session.setAttribute(SessionAttribute.ADMIN_IS_ENTRANT_BLOCKED, false);
                    e.printStackTrace(); //TODO: NO USER BY THIS ID.
                }
                break;
            case ACTION_UN_BAN:
                try {
                    Entrant entrantToBlock = entrantService.getById(entrantId);
                    int userId = entrantToBlock.getUserId();
                    User userToNotifyByEmail = userService.getById(userId);
                    entrantService.updateEntrantStatus(STATUS_ACTIVE, entrantId);
                    prepareMessageBlockAccount(request, userToNotifyByEmail);
                    session.setAttribute(SessionAttribute.COMMAND, ADMIN_COMMAND_UN_BAN_USER);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.MAIL_SENDER_SERVLET);
                    requestDispatcher.forward(request, response);
                } catch (DaoSystemException e) {
                    session.setAttribute(SessionAttribute.ADMIN_IS_ENTRANT_BLOCKED, false);
                    e.printStackTrace(); //TODO: NO USER BY THIS ID.
                }
                break;
            default:
                response.sendRedirect(Pages.ENTRANTS_ADMIN_HTML);
                break;
        }
    }

    private void prepareMessageBlockAccount(HttpServletRequest request, User user) {
        MailInfoUpdatedPasswordOrBlockedBean mailInfoUpdatedPasswordOrBlockedBean = new MailInfoUpdatedPasswordOrBlockedBean();

        mailInfoUpdatedPasswordOrBlockedBean.setFirstName(user.getFirstName());
        mailInfoUpdatedPasswordOrBlockedBean.setLastName(user.getLastName());
        mailInfoUpdatedPasswordOrBlockedBean.setPatronymic(user.getPatronymic());
        mailInfoUpdatedPasswordOrBlockedBean.setAccountName(user.getEmail());

        request.setAttribute(RequestAttribute.MAIL_INFO_UPDATED_PASSWORD_OR_BLOCKED_BEAN, mailInfoUpdatedPasswordOrBlockedBean);
    }
}
