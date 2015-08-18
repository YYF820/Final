package ua.nure.hanzha.SummaryTask4.filter;

import ua.nure.hanzha.SummaryTask4.bean.EntrantAccountSettingsBean;
import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Roles;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.entity.Mark;
import ua.nure.hanzha.SummaryTask4.entity.Subject;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.enums.Role;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.entrant.EntrantService;
import ua.nure.hanzha.SummaryTask4.service.mark.MarkService;
import ua.nure.hanzha.SummaryTask4.service.subject.SubjectService;
import ua.nure.hanzha.SummaryTask4.util.SessionCleanerUtilities;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 10/08/15.
 */
public class EnoughSubjectsFilter extends BaseFilter {


    private static final int ENTRANT_NUMBER_OF_SUBJECTS = 3;
    private static final String SESSION_MESSAGE_NO_SUBJECTS_IN_DATABASE = "noSubjectsInDataBase";

    private EntrantService entrantService;
    private MarkService markService;
    private SubjectService subjectService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        entrantService = (EntrantService) filterConfig.getServletContext().getAttribute(AppAttribute.ENTRANT_SERVICE);
        markService = (MarkService) filterConfig.getServletContext().getAttribute(AppAttribute.MARK_SERVICE);
        subjectService = (SubjectService) filterConfig.getServletContext().getAttribute(AppAttribute.SUBJECT_SERVICE);
    }

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        cleanSession(session);
        User user = (User) session.getAttribute(SessionAttribute.ACCOUNT);
        if (user == null) {
            filterChain.doFilter(request, response);
        } else if (Role.getRole(user).getName().equals(Roles.ADMIN)) {
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
            session.setAttribute(SessionAttribute.FACULTIES_ENTRANT_ENTITY, entrant);
            int entrantId = entrant.getId();
            try {
                List<Mark> entrantMarks = markService.getAllMarksByEntrantId(entrantId);
                if (entrantMarks.size() < ENTRANT_NUMBER_OF_SUBJECTS) {
                    session.setAttribute(SessionAttribute.ENTRANT_HOW_MANY_MORE_SUBJECTS_NEED,
                            ENTRANT_NUMBER_OF_SUBJECTS - entrantMarks.size());
                    filterChain.doFilter(request, response);
                } else {
                    Map<String, Double> subjectMark = new HashMap<>();
                    for (Mark entrantMark : entrantMarks) {
                        int subjectId = entrantMark.getSubjectId();
                        Subject subject;
                        try {
                            subject = subjectService.getById(subjectId);
                        } catch (DaoSystemException e) {
                            e.printStackTrace();
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                            return;
                        }
                        subjectMark.put(subject.getName(), entrantMark.getMarkValue());
                    }
                    EntrantAccountSettingsBean entrantAccountSettingsBean = new EntrantAccountSettingsBean();
                    entrantAccountSettingsBean.setSubjectMark(subjectMark);
                    session.setAttribute(SessionAttribute.ENTRANT_HOW_MANY_MORE_SUBJECTS_NEED, 0);
                    session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_BEAN, entrantAccountSettingsBean);
                    filterChain.doFilter(request, response);
                }
            } catch (DaoSystemException e) {
                List<Subject> allSubjects;
                try {
                    allSubjects = subjectService.getAll();
                } catch (DaoSystemException e1) {
                    session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_ALL_SUBJECTS,
                            SESSION_MESSAGE_NO_SUBJECTS_IN_DATABASE);
                    filterChain.doFilter(request, response);
                    return;
                }
                session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_ENTRANT_TO_ADD_SUBJECTS_MARKS, entrant);
                session.setAttribute(SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_ALL_SUBJECTS, allSubjects);
                session.setAttribute(SessionAttribute.ENTRANT_NO_SUBJECTS_ADDED, true);
                filterChain.doFilter(request, response);
            }
        }
    }

    private void cleanSession(HttpSession session) {
        SessionCleanerUtilities.cleanAttributes(
                session,
                SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_ALL_SUBJECTS,
                SessionAttribute.ENTRANT_NO_SUBJECTS_ADDED,
                SessionAttribute.ENTRANT_HOW_MANY_MORE_SUBJECTS_NEED,
                SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_BEAN,
                SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_IS_EMPTY_FORM,
                SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_IS_EMPTY_MARKS,
                SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_IS_VALID_MARKS,
                SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_MARK_FIRST,
                SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_MARK_SECOND,
                SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_MARK_THIRD,
                SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_IS_CORRECT_NUMBER_OF_SUBJECTS,
                SessionAttribute.ENTRANT_ACCOUNT_SETTINGS_SUBJECTS_TO_ADD
        );
    }
}
