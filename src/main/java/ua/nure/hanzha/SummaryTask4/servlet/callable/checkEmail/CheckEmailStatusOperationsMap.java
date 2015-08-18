package ua.nure.hanzha.SummaryTask4.servlet.callable.checkEmail;

import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.entity.User;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 18/08/15.
 */
public class CheckEmailStatusOperationsMap {

    private static final String STATUS_ACTIVE = "active";
    private static final String STATUS_BLOCKED = "blocked";
    private static final String STATUS_NOT_VERIFIED = "notverified";
    private static final String PARAM_COMMAND = "command";

    private static final String SESSION_ATTRIBUTE_COMMAND = "command";

    private static CheckEmailStatusOperationsMap checkEmailStatusOperationsMap;
    private static Map<String, CheckEmailStatusCallable> checkEmailStatusCallableMap;

    private CheckEmailStatusOperationsMap() {
    }

    public static CheckEmailStatusOperationsMap getInstance() {
        if (checkEmailStatusOperationsMap == null) {
            checkEmailStatusOperationsMap = new CheckEmailStatusOperationsMap();
        }
        return checkEmailStatusOperationsMap;
    }

    public static CheckEmailStatusCallable getCheckEmailStatusCallable(String status) {
        return checkEmailStatusCallableMap.get(status);
    }

    public static void initCheckEmailCallableMap(final HttpSession session, final HttpServletResponse response,
                                                 final User userForResendVerifyMessage, final Entrant entrantForResendVerifyMessage) {
        checkEmailStatusCallableMap = new HashMap<>();
        checkEmailStatusCallableMap.put(
                STATUS_ACTIVE,
                new CheckEmailStatusCallable() {
                    @Override
                    public void call(String status) throws IOException {
                        session.setAttribute(SessionAttribute.RESEND_IS_ACTIVE_ACCOUNT, true);
                        response.sendRedirect(Pages.RESEND_VERIFICATION_OR_RESET_PASSWORD_HTML + "?" + PARAM_COMMAND + "=" + status);
                    }
                }
        );
        checkEmailStatusCallableMap.put(
                STATUS_BLOCKED,
                new CheckEmailStatusCallable() {
                    @Override
                    public void call(String status) throws IOException {
                        session.setAttribute(SessionAttribute.RESEND_IS_BLOCKED_ACCOUNT, true);
                        response.sendRedirect(Pages.RESEND_VERIFICATION_OR_RESET_PASSWORD_HTML + "?" + PARAM_COMMAND + "=" + status);
                    }
                }
        );
        checkEmailStatusCallableMap.put(
                STATUS_NOT_VERIFIED,
                new CheckEmailStatusCallable() {
                    @Override
                    public void call(String status) throws IOException {
                        session.setAttribute(SessionAttribute.ENTRANT_FOR_VERIFY_ACCOUNT_RESET_PASSWORD, entrantForResendVerifyMessage);
                        session.setAttribute(SessionAttribute.USER_FOR_VERIFY_ACCOUNT_RESET_PASSWORD, userForResendVerifyMessage);
                        session.setAttribute(SESSION_ATTRIBUTE_COMMAND, status);
                        response.sendRedirect(Pages.CHECK_QUESTION_HTML);
                    }
                }
        );
    }
}
