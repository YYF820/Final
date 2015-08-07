package ua.nure.hanzha.SummaryTask4.filter;

import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.util.SessionCleaner;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 07/08/15.
 */
public class CleanSessionAttributesFilter extends BaseFilter {
    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        HttpSession session = request.getSession(true);

        User user = (User) session.getAttribute(SessionAttribute.ACCOUNT);
        if (user == null && !session.isNew()) {
            session.invalidate();
        }
        filterChain.doFilter(request, response);
    }
}



 /*SessionCleaner.cleanAttributes(
                session,
                //========== CLEAN LOGIN FORM ===========//
                SessionAttribute.LOGIN_IS_ACCOUNT_NAME_VALID,
                SessionAttribute.LOGIN_IS_ACCOUNT_NAME_EMPTY,
                SessionAttribute.LOGIN_IS_ACCOUNT_NAME_EXISTS,
                SessionAttribute.LOGIN_IS_PASSWORD_EMPTY,
                SessionAttribute.LOGIN_IS_PASSWORD_VALID,
                SessionAttribute.LOGIN_ACCOUNT_NAME,
                SessionAttribute.LOGIN_PASSWORD,

                //=========== CLEAN REGISTRATION FORM ============//
                SessionAttribute.REGISTRATION_IS_ACCOUNT_NAME_VALID,
                SessionAttribute.REGISTRATION_IS_ACCOUNT_NAME_EMPTY,
                SessionAttribute.REGISTRATION_IS_ACCOUNT_NAME_EXISTS,
                SessionAttribute.REGISTRATION_IS_FIRST_NAME_EMPTY,
                SessionAttribute.REGISTRATION_IS_FIRST_NAME_VALID,
                SessionAttribute.REGISTRATION_IS_LAST_NAME_EMPTY,
                SessionAttribute.REGISTRATION_IS_LAST_NAME_VALID,
                SessionAttribute.REGISTRATION_IS_PATRONYMIC_EMPTY,
                SessionAttribute.REGISTRATION_IS_PATRONYMIC_VALID,
                SessionAttribute.REGISTRATION_IS_CITY_EMPTY,
                SessionAttribute.REGISTRATION_IS_CITY_VALID,
                SessionAttribute.REGISTRATION_IS_REGION_EMPTY,
                SessionAttribute.REGISTRATION_IS_REGION_VALID,
                SessionAttribute.REGISTRATION_IS_PASSWORD_EMPTY,
                SessionAttribute.REGISTRATION_IS_PASSWORD_VALID,
                SessionAttribute.REGISTRATION_IS_SCHOOL_EMPTY,
                SessionAttribute.REGISTRATION_IS_SCHOOL_VALID,
                SessionAttribute.REGISTRATION_FIRST_NAME,
                SessionAttribute.REGISTRATION_LAST_NAME,
                SessionAttribute.REGISTRATION_PATRONYMIC,
                SessionAttribute.REGISTRATION_CITY,
                SessionAttribute.REGISTRATION_REGION,
                SessionAttribute.REGISTRATION_ACCOUNT_NAME,
                SessionAttribute.REGISTRATION_PASSWORD,
                SessionAttribute.REGISTRATION_SCHOOL,

                //=============== RESEND VERIFY OR RESET PASSWORD ===============//
                SessionAttribute.RESEND_ACCOUNT_NAME,
                SessionAttribute.RESEND_IS_ACCOUNT_NAME_EMPTY,
                SessionAttribute.RESEND_IS_ACCOUNT_NAME_VALID,
                SessionAttribute.RESEND_IS_ACTIVE_ACCOUNT,
                SessionAttribute.RESEND_IS_BLOCKED_ACCOUNT,
                SessionAttribute.RESEND_IS_ADMIN_TRYING_VERIFY_ACCOUNT,
                SessionAttribute.RESEND_IS_USER_EXISTS_BY_ACCOUNT_NAME,


                //============= CHECK QUESTION =============//
                SessionAttribute.CHECK_QUESTION_IS_SCHOOL_CORRECT,
                SessionAttribute.CHECK_QUESTION_IS_SCHOOL_VALID,
                SessionAttribute.CHECK_QUESTION_IS_SCHOOL_EMPTY,
                SessionAttribute.CHECK_QUESTION_SCHOOL,

                //============= CHECK TICKET RESET PASSWORD ===============//
                SessionAttribute.CHECK_TICKET_COUNTER_BAD_TICKET_INSERTS,
                SessionAttribute.CHECK_TICKET_HASH_TICKET_RESET_PASSWORD,
                SessionAttribute.CHECK_TICKET_IS_BLOCKED_ACCOUNT,
                SessionAttribute.CHECK_TICKET_IS_EMPTY,
                SessionAttribute.CHECK_TICKET_IS_MESSAGE_SENT,
                SessionAttribute.CHECK_TICKET_IS_TICKET_RESET_PASSWORD_CORRECT,
                SessionAttribute.CHECK_TICKET_TICKET_RESET_PASSWORD,

                //============= VERIFY ACCOUNT ============================//
                SessionAttribute.VERIFY_ACCOUNT_ACCOUNT_NAME,
                SessionAttribute.VERIFY_ACCOUNT_IS_MESSAGE_SENT,
                SessionAttribute.VERIFY_ACCOUNT_IS_VERIFIED_ACCOUNT
        );*/
