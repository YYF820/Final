package ua.nure.hanzha.SummaryTask4.servlet.callable.auth;

import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.User;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 17/08/15.
 */
public class AuthOperationsMap {

    private static final String ENTRANT_STATUS_NOT_VERIFIED = "notverified";
    private static final String ENTRANT_STATUS_BLOCKED = "blocked";
    private static final String ENTRANT_STATUS_ACTIVE = "active";

    private static AuthOperationsMap authOperationsMap;
    private static Map<String, AuthCallable> authCallableMap;

    private AuthOperationsMap() {

    }

    public static AuthCallable getAuthCallable(String command) {
        return authCallableMap.get(command);
    }

    public static AuthOperationsMap getInstance() {
        if (authOperationsMap == null) {
            authOperationsMap = new AuthOperationsMap();
        }
        return authOperationsMap;
    }

    public static void initAuthCallableMap(final HttpSession session, final HttpServletResponse response, final User user) {
        authCallableMap = new HashMap<>();
        authCallableMap.put(
                ENTRANT_STATUS_NOT_VERIFIED,
                new AuthCallable() {
                    @Override
                    public void call() throws IOException {
                        session.setAttribute(SessionAttribute.LOGIN_IS_VERIFIED_ACCOUNT, false);
                        response.sendRedirect(Pages.LOGIN_HTML);
                    }
                }
        );
        authCallableMap.put(
                ENTRANT_STATUS_BLOCKED,
                new AuthCallable() {
                    @Override
                    public void call() throws IOException {
                        session.setAttribute(SessionAttribute.LOGIN_IS_BLOCKED, true);
                        response.sendRedirect(Pages.LOGIN_HTML);
                    }
                }
        );
        authCallableMap.put(
                ENTRANT_STATUS_ACTIVE,
                new AuthCallable() {
                    @Override
                    public void call() throws IOException {
                        session.setAttribute(SessionAttribute.ACCOUNT, user);
                        response.sendRedirect(Pages.INDEX_HTML);
                    }
                }
        );
    }
}
