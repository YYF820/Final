package ua.nure.hanzha.SummaryTask4.tag;

import ua.nure.hanzha.SummaryTask4.constants.Roles;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.enums.Role;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.jstl.core.ConditionalTagSupport;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 06/08/15.
 */
public class IfAuthAs extends ConditionalTagSupport {

    private String role;

    public IfAuthAs() {
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
        Role roleFromTag = getRoleFromTag(this.role);
        return role == roleFromTag;
    }

    private void init() {
    }

    private Role getRoleFromTag(String role) {
        Role roleFromTag;
        switch (role) {
            case Roles.ADMIN:
                roleFromTag = Role.ADMIN;
                break;
            case Roles.ENTRANT:
                roleFromTag = Role.ENTRANT;
                break;
            case Roles.GUEST:
                roleFromTag = Role.GUEST;
                break;
            default:
                roleFromTag = Role.GUEST;
                break;
        }
        return roleFromTag;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
