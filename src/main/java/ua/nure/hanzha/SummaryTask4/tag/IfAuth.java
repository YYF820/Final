package ua.nure.hanzha.SummaryTask4.tag;

import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.enums.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

/**
 * Created by faffi-ubuntu on 06/08/15.
 */
public class IfAuth extends ConditionalTagSupport {

    public IfAuth() {
        super();
        init();
    }

    public void release() {
        super.release();
        init();
    }

    @Override
    protected boolean condition() throws JspTagException {
        HttpServletRequest theRequest = (HttpServletRequest) pageContext.getRequest();
        HttpSession session = theRequest.getSession();
        User user = (User) (session != null ? session.getAttribute(SessionAttribute.ACCOUNT) : null);
        Role role = (user == null ? Role.GUEST : Role.getRole(user));
        return role == Role.ENTRANT || role == Role.ADMIN;
    }

    private void init() {
    }
}
