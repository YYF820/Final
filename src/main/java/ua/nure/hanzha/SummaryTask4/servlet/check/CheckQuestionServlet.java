package ua.nure.hanzha.SummaryTask4.servlet.check;

import ua.nure.hanzha.SummaryTask4.bean.MailInfoBean;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.RequestAttribute;
import ua.nure.hanzha.SummaryTask4.db.util.PasswordHash;
import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.exception.PropertiesDuplicateException;
import ua.nure.hanzha.SummaryTask4.exception.ServletSystemException;
import ua.nure.hanzha.SummaryTask4.util.TicketsWriterReader;
import ua.nure.hanzha.SummaryTask4.validation.Validation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 03/08/15.
 */
public class CheckQuestionServlet extends HttpServlet {

    private static final String EMPTY_PARAM = "";

    private static final String PARAM_SCHOOL_NUMBER = "school";
    private static final String SESSION_ATTRIBUTE_COMMAND = "command";

    private static final String SESSION_ATTRIBUTE_ENTRANT_FOR_VERIFY_ACCOUNT = "entrantForVerifyAccount";
    private static final String SESSION_ATTRIBUTE_USER_FOR_VERIFY_ACCOUNT = "userForVerifyAccount";

    private static final String REQUEST_ATTRIBUTE_IS_SCHOOL_NUMBER_CORRECT = "isSchoolNumberCorrect";

    private static final String COMMAND_VERIFY_EMAIL = "verifyEmail";
    private static final String COMMAND_RECOVER_PASSWORD = "recoverPassword";

    private static final String FIRST_NAME = "firstName";
    private static final String LAST_NAME = "lastName";
    private static final String PATRONYMIC = "patronymic";
    private static final String ACCOUNT_NAME = "accountName";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String schoolNumberFromQuestion = request.getParameter(PARAM_SCHOOL_NUMBER);
        boolean isSchoolNumberFromQuestionValid = checkValidationSchoolNumber(request, schoolNumberFromQuestion);
        boolean isSchoolNumberEmpty = checkEmptyFields(request, schoolNumberFromQuestion);
        if (!isSchoolNumberFromQuestionValid || isSchoolNumberEmpty) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.CHECK_QUESTION_HTML);
            requestDispatcher.forward(request, response);
        } else {
            HttpSession session = request.getSession(false);
            Entrant entrant = (Entrant) session.getAttribute(SESSION_ATTRIBUTE_ENTRANT_FOR_VERIFY_ACCOUNT);
            int schoolNumberFromEntrant = entrant.getSchool();
            if (!(Integer.parseInt(schoolNumberFromQuestion) == schoolNumberFromEntrant)) {
                request.setAttribute(REQUEST_ATTRIBUTE_IS_SCHOOL_NUMBER_CORRECT, false);
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.CHECK_QUESTION_HTML);
                requestDispatcher.forward(request, response);
            } else {
                String command = (String) session.getAttribute(SESSION_ATTRIBUTE_COMMAND);
                try {
                    switch (command) {
                        case COMMAND_VERIFY_EMAIL:
                            User user = (User) session.getAttribute(SESSION_ATTRIBUTE_USER_FOR_VERIFY_ACCOUNT);
                            Map<String, String> extractedUserInfo = extractInfo(user);
                            prepareInfoForEmail(
                                    request,
                                    extractedUserInfo.get(FIRST_NAME),
                                    extractedUserInfo.get(LAST_NAME),
                                    extractedUserInfo.get(PATRONYMIC),
                                    extractedUserInfo.get(ACCOUNT_NAME)
                            );
                            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.CONFIRM_ACCOUNT_SEND_MAIL_SERVLET);
                            requestDispatcher.forward(request, response);
                            break;
                        case COMMAND_RECOVER_PASSWORD:
                            break;
                        default:
                            throw new ServletSystemException("unknown command...");
                    }
                } catch (ServletSystemException e) {
                    e.printStackTrace();
                    //TODO: add redirect to 500 page
                }
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(Pages.CHECK_QUESTION_HTML);
    }

    private boolean checkValidationSchoolNumber(HttpServletRequest request, String schoolNumber) {
        boolean isAccountNameValid = Validation.validateSchool(schoolNumber);
        if (!isAccountNameValid) {
            request.setAttribute(RequestAttribute.IS_SCHOOL_VALID, false);
        }
        return isAccountNameValid;
    }

    private boolean checkEmptyFields(HttpServletRequest request, String schoolNumber) {
        boolean isSchoolNumberEmpty = false;
        if (schoolNumber.equals(EMPTY_PARAM)) {
            isSchoolNumberEmpty = true;
            request.setAttribute(RequestAttribute.IS_ACCOUNT_NAME_NULL, true);
        }
        return isSchoolNumberEmpty;
    }

    private void prepareInfoForEmail(HttpServletRequest request, String firstName,
                                     String lastName, String patronymic, String accountName) {
        String ticket = generateTicket();
        String verifyLink = "localhost:8080/confirmAccount.do?ticket=" + ticket;
        boolean flagSuccessTicket = false;
        while (!flagSuccessTicket) {
            try {
                TicketsWriterReader.writePair(ticket, accountName);
                flagSuccessTicket = true;
            } catch (PropertiesDuplicateException e) {
                ticket = generateTicket();
            }
        }
        MailInfoBean mailInfoBean = new MailInfoBean();
        mailInfoBean.setFirstName(firstName);
        mailInfoBean.setLastName(lastName);
        mailInfoBean.setPatronymic(patronymic);
        mailInfoBean.setAccountName(accountName);
        mailInfoBean.setVerifyLink(verifyLink);
        request.setAttribute(RequestAttribute.MAIL_INFO_BEAN, mailInfoBean);
    }

    private Map<String, String> extractInfo(User user) {
        Map<String, String> userInfo = new HashMap<>();
        userInfo.put(FIRST_NAME, user.getFirstName());
        userInfo.put(LAST_NAME, user.getLastName());
        userInfo.put(PATRONYMIC, user.getPatronymic());
        userInfo.put(ACCOUNT_NAME, user.getEmail());
        return userInfo;
    }

    private String generateTicket() {
        return PasswordHash.randomPassword(40);
    }

}
