package ua.nure.hanzha.SummaryTask4.filter;

import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.enums.Role;
import ua.nure.hanzha.SummaryTask4.security.AuthValue;
import ua.nure.hanzha.SummaryTask4.security.AuthorizationMap;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Dmytro hanzha
 *         Created by faffi-ubuntu on 05/08/15.
 */
public class SecurityFilter extends BaseFilter {

    private AuthorizationMap authorizationMap;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        authorizationMap = (AuthorizationMap) filterConfig.getServletContext().getAttribute(AppAttribute.AUTHORIZATION_MAP);
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String path = request.getServletPath();
        Role role = null;
        User user = null;
        HttpSession session = request.getSession(false);
        if (session != null) {
            user = (User) session.getAttribute(SessionAttribute.ACCOUNT);
            role = (user == null ? null : Role.getRole(user));
        }
        String authValue = authorizationMap.isAuthorize(path, role);
        System.out.println(user);
        System.out.println(role);
        System.out.println(authValue);
        if (AuthValue.NOT_FOUND.equals(authValue)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        } else if (!AuthValue.ALLOWED.equals(authValue)) {
            if (user == null) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return;
            } else {
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }
        }
        filterChain.doFilter(request, response);

    }
}
