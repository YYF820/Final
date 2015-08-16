package ua.nure.hanzha.SummaryTask4.servlet;

import ua.nure.hanzha.SummaryTask4.bean.MailInfoVerifyAccountBean;
import ua.nure.hanzha.SummaryTask4.bean.RegistrationBean;
import ua.nure.hanzha.SummaryTask4.constants.*;
import ua.nure.hanzha.SummaryTask4.db.util.PasswordHash;
import ua.nure.hanzha.SummaryTask4.entity.Entity;
import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.exception.PropertiesDuplicateException;
import ua.nure.hanzha.SummaryTask4.service.registration.RegistrationService;
import ua.nure.hanzha.SummaryTask4.service.user.UserService;
import ua.nure.hanzha.SummaryTask4.util.SessionCleaner;
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
 * Registration servlet, takes all information, doing some validation --> genarate ticket for confirm account
 * put ticket in properties file tickets.properties --> send request response to servlet which send email with ticket.
 * <p/>
 * doGet send to REGISTRATION html.
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 01/08/15.
 */
public class RegistrationServlet extends HttpServlet {

    private static final String EMPTY_PARAM = "";

    private static final String PARAM_FIRST_NAME = "firstName";
    private static final String PARAM_LAST_NAME = "lastName";
    private static final String PARAM_PATRONYMIC = "patronymic";
    private static final String PARAM_ACCOUNT_NAME = "accountName";
    private static final String PARAM_CITY = "city";
    private static final String PARAM_REGION = "region";
    private static final String PARAM_PASSWORD = "password";
    private static final String PARAM_SCHOOL = "school";

    private static final int ROLE_ENTRANT_ID = 2;
    private static final int STATUS_NOT_VERIFIED_ID = 3;
    private static final String MAP_KEY_USER = "user";
    private static final String MAP_KEY_ENTRANT = "entrant";

    private static final String SESSION_ATTRIBUTE_COMMAND = "command";

    private static final String COMMAND_VERIFY_ACCOUNT = "verifyAccount";

    private UserService userService;
    private RegistrationService registrationService;


    @Override
    public void init() throws ServletException {
        userService = (UserService) getServletContext().getAttribute(AppAttribute.USER_SERVICE);
        registrationService = (RegistrationService) getServletContext().getAttribute(AppAttribute.REGISTRATION_SERVICE);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        RegistrationBean registrationBean = getRegistrationBean(request);
        Map<String, Boolean> validationsRegisterForm = Validation.validateRegistrationForm(registrationBean);
        boolean isAnyEmptyField = checkFormEmptyFields(session, registrationBean);
        boolean isAnyBadValidation = checkFormBadValidations(session, validationsRegisterForm);
        if (isAnyEmptyField || isAnyBadValidation) {
            setUpAttrForFields(session, registrationBean);
            response.sendRedirect(Pages.REGISTRATION_HTML);
        } else {
            try {
                boolean isUserExists = userService.userExistsByAccountName(registrationBean.getAccountName());
                if (!isUserExists) {
                    Map<String, Entity> infoForRegistration = prepareEntities(registrationBean);
                    User userInfo = (User) infoForRegistration.get(MAP_KEY_USER);
                    Entrant entrantInfo = (Entrant) infoForRegistration.get(MAP_KEY_ENTRANT);
                    registrationService.registerNewEntrant(userInfo, entrantInfo);
                    prepareInfoVerifyEmail(request, registrationBean);
                    session.setAttribute(SESSION_ATTRIBUTE_COMMAND, COMMAND_VERIFY_ACCOUNT);
                    cleanSession(session);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.MAIL_SENDER_SERVLET);
                    requestDispatcher.forward(request, response);
                } else {
                    setUpAttrForFields(session, registrationBean);
                    session.setAttribute(SessionAttribute.REGISTRATION_IS_ACCOUNT_NAME_EXISTS, true);
                    response.sendRedirect(Pages.REGISTRATION_HTML);
                }
            } catch (DaoSystemException e) {
                //TODO: only SQLException can be here, send to 500 error page.
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.REGISTRATION_HTML);
        requestDispatcher.forward(request, response);
    }

    private boolean checkFormBadValidations(HttpSession session, Map<String, Boolean> validationsRegisterForm) {
        boolean isAnyBadValidation = false;
        if (!validationsRegisterForm.get(Validations.MAP_KEY_IS_FIRST_NAME_VALID)) {
            isAnyBadValidation = true;
            session.setAttribute(SessionAttribute.REGISTRATION_IS_FIRST_NAME_VALID, false);
        } else {
            session.setAttribute(SessionAttribute.REGISTRATION_IS_FIRST_NAME_VALID, true);
        }

        if (!validationsRegisterForm.get(Validations.MAP_KEY_IS_LAST_NAME_VALID)) {
            isAnyBadValidation = true;
            session.setAttribute(SessionAttribute.REGISTRATION_IS_LAST_NAME_VALID, false);
        } else {
            session.setAttribute(SessionAttribute.REGISTRATION_IS_LAST_NAME_VALID, true);
        }

        if (!validationsRegisterForm.get(Validations.MAP_KEY_IS_PATRONYMIC_VALID)) {
            isAnyBadValidation = true;
            session.setAttribute(SessionAttribute.REGISTRATION_IS_PATRONYMIC_VALID, false);
        } else {
            session.setAttribute(SessionAttribute.REGISTRATION_IS_PATRONYMIC_VALID, true);
        }

        if (!validationsRegisterForm.get(Validations.MAP_KEY_IS_ACCOUNT_NAME_VALID)) {
            isAnyBadValidation = true;
            session.setAttribute(SessionAttribute.REGISTRATION_IS_ACCOUNT_NAME_VALID, false);
        } else {
            session.setAttribute(SessionAttribute.REGISTRATION_IS_ACCOUNT_NAME_VALID, true);
        }

        if (!validationsRegisterForm.get(Validations.MAP_KEY_IS_CITY_VALID)) {
            isAnyBadValidation = true;
            session.setAttribute(SessionAttribute.REGISTRATION_IS_CITY_VALID, false);
        } else {
            session.setAttribute(SessionAttribute.REGISTRATION_IS_CITY_VALID, true);
        }

        if (!validationsRegisterForm.get(Validations.MAP_KEY_IS_REGION_VALID)) {
            isAnyBadValidation = true;
            session.setAttribute(SessionAttribute.REGISTRATION_IS_REGION_VALID, false);
        } else {
            session.setAttribute(SessionAttribute.REGISTRATION_IS_REGION_VALID, true);
        }

        if (!validationsRegisterForm.get(Validations.MAP_KEY_IS_PASSWORD_VALID)) {
            isAnyBadValidation = true;
            session.setAttribute(SessionAttribute.REGISTRATION_IS_PASSWORD_VALID, false);
        } else {
            session.setAttribute(SessionAttribute.REGISTRATION_IS_PASSWORD_VALID, true);
        }

        if (!validationsRegisterForm.get(Validations.MAP_KEY_IS_SCHOOL_VALID)) {
            isAnyBadValidation = true;
            session.setAttribute(SessionAttribute.REGISTRATION_IS_SCHOOL_VALID, false);
        } else {
            session.setAttribute(SessionAttribute.REGISTRATION_IS_SCHOOL_VALID, true);
        }
        return isAnyBadValidation;
    }

    private boolean checkFormEmptyFields(HttpSession session, RegistrationBean registrationBean) {
        boolean isAnyEmptyField = false;
        if (registrationBean.getFirstName().equals(EMPTY_PARAM)) {
            isAnyEmptyField = true;
            session.setAttribute(SessionAttribute.REGISTRATION_IS_FIRST_NAME_EMPTY, true);
        } else {
            session.setAttribute(SessionAttribute.REGISTRATION_IS_FIRST_NAME_EMPTY, false);
        }

        if (registrationBean.getLastName().equals(EMPTY_PARAM)) {
            isAnyEmptyField = true;
            session.setAttribute(SessionAttribute.REGISTRATION_IS_LAST_NAME_EMPTY, true);
        } else {
            session.setAttribute(SessionAttribute.REGISTRATION_IS_LAST_NAME_EMPTY, false);
        }

        if (registrationBean.getPatronymic().equals(EMPTY_PARAM)) {
            isAnyEmptyField = true;
            session.setAttribute(SessionAttribute.REGISTRATION_IS_PATRONYMIC_EMPTY, true);
        } else {
            session.setAttribute(SessionAttribute.REGISTRATION_IS_PATRONYMIC_EMPTY, false);
        }

        if (registrationBean.getAccountName().equals(EMPTY_PARAM)) {
            isAnyEmptyField = true;
            session.setAttribute(SessionAttribute.REGISTRATION_IS_ACCOUNT_NAME_EMPTY, true);
        } else {
            session.setAttribute(SessionAttribute.REGISTRATION_IS_ACCOUNT_NAME_EMPTY, false);
        }

        if (registrationBean.getCity().equals(EMPTY_PARAM)) {
            isAnyEmptyField = true;
            session.setAttribute(SessionAttribute.REGISTRATION_IS_CITY_EMPTY, true);
        } else {
            session.setAttribute(SessionAttribute.REGISTRATION_IS_CITY_EMPTY, false);
        }

        if (registrationBean.getRegion().equals(EMPTY_PARAM)) {
            isAnyEmptyField = true;
            session.setAttribute(SessionAttribute.REGISTRATION_IS_REGION_EMPTY, true);
        } else {
            session.setAttribute(SessionAttribute.REGISTRATION_IS_REGION_EMPTY, false);
        }

        if (registrationBean.getPassword().equals(EMPTY_PARAM)) {
            isAnyEmptyField = true;
            session.setAttribute(SessionAttribute.REGISTRATION_IS_PASSWORD_EMPTY, true);
        } else {
            session.setAttribute(SessionAttribute.REGISTRATION_IS_PASSWORD_EMPTY, false);
        }

        if (registrationBean.getSchool().equals(EMPTY_PARAM)) {
            isAnyEmptyField = true;
            session.setAttribute(SessionAttribute.REGISTRATION_IS_SCHOOL_EMPTY, true);
        } else {
            session.setAttribute(SessionAttribute.REGISTRATION_IS_SCHOOL_EMPTY, false);
        }
        return isAnyEmptyField;
    }

    private void setUpAttrForFields(HttpSession session, RegistrationBean registrationBean) {
        session.setAttribute(SessionAttribute.REGISTRATION_FIRST_NAME, registrationBean.getFirstName());
        session.setAttribute(SessionAttribute.REGISTRATION_LAST_NAME, registrationBean.getLastName());
        session.setAttribute(SessionAttribute.REGISTRATION_PATRONYMIC, registrationBean.getPatronymic());
        session.setAttribute(SessionAttribute.REGISTRATION_ACCOUNT_NAME, registrationBean.getAccountName());
        session.setAttribute(SessionAttribute.REGISTRATION_CITY, registrationBean.getCity());
        session.setAttribute(SessionAttribute.REGISTRATION_REGION, registrationBean.getRegion());
        session.setAttribute(SessionAttribute.REGISTRATION_PASSWORD, registrationBean.getPassword());
        session.setAttribute(SessionAttribute.REGISTRATION_SCHOOL, registrationBean.getSchool());
    }

    private Map<String, Entity> prepareEntities(RegistrationBean registrationBean) {
        Map<String, Entity> entrantInfo = new HashMap<>();
        User user = new User();
        Entrant entrant = new Entrant();
        String hashPassword = PasswordHash.createHash(registrationBean.getPassword());

        user.setFirstName(registrationBean.getFirstName());
        user.setLastName(registrationBean.getLastName());
        user.setPatronymic(registrationBean.getPatronymic());
        user.setEmail(registrationBean.getAccountName());
        user.setPassword(hashPassword);
        user.setRoleId(ROLE_ENTRANT_ID);

        entrant.setCity(registrationBean.getCity());
        entrant.setRegion(registrationBean.getRegion());
        entrant.setSchool(Integer.parseInt(registrationBean.getSchool()));
        entrant.setEntrantStatus(STATUS_NOT_VERIFIED_ID);

        entrantInfo.put(MAP_KEY_USER, user);
        entrantInfo.put(MAP_KEY_ENTRANT, entrant);

        return entrantInfo;
    }

    private void prepareInfoVerifyEmail(HttpServletRequest request, RegistrationBean registrationBean) {
        String ticket = generateTicket();
        String verifyLink = "http://localhost:8080/verifyAccount.do?ticket=" + ticket;
        boolean flagSuccessTicket = false;
        while (!flagSuccessTicket) {
            try {
                TicketsWriterReader.writePair(ticket, registrationBean.getAccountName());
                flagSuccessTicket = true;
            } catch (PropertiesDuplicateException e) {
                ticket = generateTicket();
            }
        }
        MailInfoVerifyAccountBean mailInfoVerifyAccountBean = new MailInfoVerifyAccountBean();
        mailInfoVerifyAccountBean.setFirstName(registrationBean.getFirstName());
        mailInfoVerifyAccountBean.setLastName(registrationBean.getLastName());
        mailInfoVerifyAccountBean.setPatronymic(registrationBean.getPatronymic());
        mailInfoVerifyAccountBean.setAccountName(registrationBean.getAccountName());
        mailInfoVerifyAccountBean.setVerifyLink(verifyLink);
        request.setAttribute(RequestAttribute.MAIL_INFO_VERIFY_ACCOUNT_BEAN, mailInfoVerifyAccountBean);
    }

    private String generateTicket() {
        return PasswordHash.randomPassword(40);
    }

    private void cleanSession(HttpSession session) {
        SessionCleaner.cleanAttributes(
                session,
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
                SessionAttribute.REGISTRATION_SCHOOL
        );
    }

    private RegistrationBean getRegistrationBean(HttpServletRequest request) {
        RegistrationBean registrationBean = new RegistrationBean();
        String firstName = request.getParameter(PARAM_FIRST_NAME);
        String lastName = request.getParameter(PARAM_LAST_NAME);
        String patronymic = request.getParameter(PARAM_PATRONYMIC);
        String accountName = request.getParameter(PARAM_ACCOUNT_NAME);
        String city = request.getParameter(PARAM_CITY);
        String region = request.getParameter(PARAM_REGION);
        String password = request.getParameter(PARAM_PASSWORD);
        String school = request.getParameter(PARAM_SCHOOL);
        registrationBean.setFirstName(firstName);
        registrationBean.setLastName(lastName);
        registrationBean.setPatronymic(patronymic);
        registrationBean.setAccountName(accountName);
        registrationBean.setCity(city);
        registrationBean.setRegion(region);
        registrationBean.setPassword(password);
        registrationBean.setSchool(school);
        return registrationBean;
    }
}
