package ua.nure.hanzha.SummaryTask4.filters;

import ua.nure.hanzha.SummaryTask4.constants.Pages;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by faffi-ubuntu on 05/08/15.
 */
public class ResetPasswordCheckInvalidateFilter extends BaseFilter {

    private static final String SESSION_ATTRIBUTE_HASH_TICKET_RESET_PASSWORD = "hashTicketRecoverPassword";

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        String hashTicketResetPassword = (String) session.getAttribute(SESSION_ATTRIBUTE_HASH_TICKET_RESET_PASSWORD);
        if (hashTicketResetPassword == null) {
            session.invalidate();
            response.sendRedirect(Pages.RESET_PASSWORD_SESSION_INVALIDATED_HTML);
        } else {
            filterChain.doFilter(request, response);
        }
    }
}
