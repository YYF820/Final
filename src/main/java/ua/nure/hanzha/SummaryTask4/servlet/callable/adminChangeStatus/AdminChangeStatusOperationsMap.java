package ua.nure.hanzha.SummaryTask4.servlet.callable.adminChangeStatus;

import ua.nure.hanzha.SummaryTask4.bean.MailInfoUpdatedPasswordOrBlockedBean;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.RequestAttribute;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.entrant.EntrantService;
import ua.nure.hanzha.SummaryTask4.service.user.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 18/08/15.
 */
public class AdminChangeStatusOperationsMap {

    private static final String ADMIN_COMMAND_BAN_USER = "adminCommandBanUser";
    private static final String ADMIN_COMMAND_UN_BAN_USER = "adminCommandUnBanUser";

    private static final String ACTION_BAN = "ban";
    private static final String ACTION_UN_BAN = "unBan";


    private static final int STATUS_BLOCK = 1;
    private static final int STATUS_ACTIVE = 2;

    private static AdminChangeStatusOperationsMap adminChangeStatusOperationsMap;
    private static Map<String, AdminChangeStatusCallable> adminChangeStatusCallableMap;

    private AdminChangeStatusOperationsMap() {

    }

    public static AdminChangeStatusOperationsMap getInstance() {
        if (adminChangeStatusOperationsMap == null) {
            adminChangeStatusOperationsMap = new AdminChangeStatusOperationsMap();
        }
        return adminChangeStatusOperationsMap;
    }

    public static AdminChangeStatusCallable getAdminChangeStatusCallable(String action) {
        return adminChangeStatusCallableMap.get(action);
    }

    public static void initAdminChangeStatusCallableMap(final HttpSession session,
                                                        final HttpServletRequest request,
                                                        final HttpServletResponse response,
                                                        final EntrantService entrantService,
                                                        final UserService userService,
                                                        final int entrantId) {
        adminChangeStatusCallableMap = new HashMap<>();
        adminChangeStatusCallableMap.put(
                ACTION_BAN,
                new AdminChangeStatusCallable() {
                    @Override
                    public void call() throws ServletException, IOException, DaoSystemException {
                        Entrant entrantToBlock = entrantService.getById(entrantId);
                        int userId = entrantToBlock.getUserId();
                        User userToNotifyByEmail = userService.getById(userId);
                        entrantService.updateEntrantStatus(STATUS_BLOCK, entrantId);
                        prepareMessageBlockAccount(request, userToNotifyByEmail);
                        session.setAttribute(SessionAttribute.COMMAND, ADMIN_COMMAND_BAN_USER);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.MAIL_SENDER_SERVLET);
                        requestDispatcher.forward(request, response);
                    }
                }
        );
        adminChangeStatusCallableMap.put(
                ACTION_UN_BAN,
                new AdminChangeStatusCallable() {
                    @Override
                    public void call() throws ServletException, IOException, DaoSystemException {
                        Entrant entrantToBlock = entrantService.getById(entrantId);
                        int userId = entrantToBlock.getUserId();
                        User userToNotifyByEmail = userService.getById(userId);
                        entrantService.updateEntrantStatus(STATUS_ACTIVE, entrantId);
                        prepareMessageBlockAccount(request, userToNotifyByEmail);
                        session.setAttribute(SessionAttribute.COMMAND, ADMIN_COMMAND_UN_BAN_USER);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.MAIL_SENDER_SERVLET);
                        requestDispatcher.forward(request, response);
                    }
                }
        );
    }

    private static void prepareMessageBlockAccount(HttpServletRequest request, User user) {
        MailInfoUpdatedPasswordOrBlockedBean mailInfoUpdatedPasswordOrBlockedBean = new MailInfoUpdatedPasswordOrBlockedBean();

        mailInfoUpdatedPasswordOrBlockedBean.setFirstName(user.getFirstName());
        mailInfoUpdatedPasswordOrBlockedBean.setLastName(user.getLastName());
        mailInfoUpdatedPasswordOrBlockedBean.setPatronymic(user.getPatronymic());
        mailInfoUpdatedPasswordOrBlockedBean.setAccountName(user.getEmail());

        request.setAttribute(RequestAttribute.MAIL_INFO_UPDATED_PASSWORD_OR_BLOCKED_BEAN, mailInfoUpdatedPasswordOrBlockedBean);
    }


}
