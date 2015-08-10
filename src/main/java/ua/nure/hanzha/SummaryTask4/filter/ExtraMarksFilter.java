package ua.nure.hanzha.SummaryTask4.filter;

import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Roles;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.entity.ExtraMark;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.enums.Role;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.entrant.EntrantService;
import ua.nure.hanzha.SummaryTask4.service.extraMark.ExtraMarkService;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 10/08/15.
 */
public class ExtraMarksFilter extends BaseFilter {


    private EntrantService entrantService;
    private ExtraMarkService extraMarkService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        entrantService = (EntrantService) filterConfig.getServletContext().getAttribute(AppAttribute.ENTRANT_SERVICE);
        extraMarkService = (ExtraMarkService) filterConfig.getServletContext().getAttribute(AppAttribute.EXTRA_MARK_SERVICE);
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = request.getSession(false);
        cleanSession(session);
        User user = (User) session.getAttribute(SessionAttribute.ACCOUNT);
        if (Role.getRole(user).getName().equals(Roles.ADMIN)) {
            filterChain.doFilter(request, response);
        } else {
            int userId = user.getId();
            Entrant entrant;
            try {
                entrant = entrantService.getByUserId(userId);
            } catch (DaoSystemException e) {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                return;
            }
            int entrantId = entrant.getId();
            try {
                ExtraMark extraMarks = extraMarkService.getByEntrantId(entrantId);
                session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_EXTRA_MARKS, extraMarks);
                filterChain.doFilter(request, response);
            } catch (DaoSystemException e) {
                e.printStackTrace();
                session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_EXTRA_MARKS_ENTRANT_ID, entrantId);
                session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_NO_EXTRA_MARKS, true);
                filterChain.doFilter(request, response);
            }
        }
    }

    private void cleanSession(HttpSession session) {
        session.removeAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_EXTRA_MARKS_CERTIFICATE_POINTS);
        session.removeAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_EXTRA_MARKS_IS_VALID_CERTIFICATE_POINTS);
        session.removeAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_EXTRA_MARKS_EXTRA_POINTS);
        session.removeAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_EXTRA_MARKS_IS_VALID_EXTRA_POINTS);
        session.removeAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_EXTRA_MARKS_IS_EMPTY_FIELDS);
        session.removeAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_EXTRA_MARKS_ENTRANT_ID);
        session.removeAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_EXTRA_MARKS);
        session.removeAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_NO_EXTRA_MARKS);
    }
}
