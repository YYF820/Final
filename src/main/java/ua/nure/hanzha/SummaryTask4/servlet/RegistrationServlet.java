package ua.nure.hanzha.SummaryTask4.servlet;

import ua.nure.hanzha.SummaryTask4.bean.MailInfoVerifyAccountBean;
import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.RequestAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Validations;
import ua.nure.hanzha.SummaryTask4.db.util.PasswordHash;
import ua.nure.hanzha.SummaryTask4.entity.Entity;
import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.exception.PropertiesDuplicateException;
import ua.nure.hanzha.SummaryTask4.service.registration.RegistrationService;
import ua.nure.hanzha.SummaryTask4.service.user.UserService;
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
        String firstName = request.getParameter(PARAM_FIRST_NAME);
        String lastName = request.getParameter(PARAM_LAST_NAME);
        String patronymic = request.getParameter(PARAM_PATRONYMIC);
        String accountName = request.getParameter(PARAM_ACCOUNT_NAME);
        String city = request.getParameter(PARAM_CITY);
        String region = request.getParameter(PARAM_REGION);
        String password = request.getParameter(PARAM_PASSWORD);
        String school = request.getParameter(PARAM_SCHOOL);

        Map<String, Boolean> validationsRegisterForm = Validation.validateRegistrationForm(
                firstName, lastName, patronymic, accountName, city, region, password, school
        );
        int counterOfEmptyFields = checkFormEmptyFields(request, firstName, lastName, patronymic, accountName, city, region, password, school);
        int counterOfBadValidations = checkFormBadValidations(request, validationsRegisterForm);
        if (counterOfBadValidations != 0 || counterOfEmptyFields != 0) {
            setUpAttrForFields(request, firstName, lastName, patronymic, accountName, city, region, password, school);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.REGISTRATION_HTML);
            requestDispatcher.forward(request, response);
        } else {
            try {
                boolean isUserExists = userService.userExistsByAccountName(accountName);
                if (!isUserExists) {
                    Map<String, Entity> infoForRegistration = prepareEntities(firstName, lastName, patronymic, accountName, city, region, password, school);
                    User userInfo = (User) infoForRegistration.get(MAP_KEY_USER);
                    Entrant entrantInfo = (Entrant) infoForRegistration.get(MAP_KEY_ENTRANT);
                    registrationService.registerNewEntrant(userInfo, entrantInfo);
                    HttpSession session = request.getSession(true);
                    prepareInfoVerifyEmail(request, firstName, lastName, patronymic, accountName);
                    session.setAttribute(SESSION_ATTRIBUTE_COMMAND, COMMAND_VERIFY_ACCOUNT);
                    session.setMaxInactiveInterval(2 * 60);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.MAIL_SENDER_SERVLET);
                    requestDispatcher.forward(request, response);
                } else {
                    setUpAttrForFields(request, firstName, lastName, patronymic, accountName, city, region, password, school);
                    request.setAttribute(RequestAttribute.IS_ACCOUNT_NAME_EXISTS, true);
                    RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.REGISTRATION_HTML);
                    requestDispatcher.forward(request, response);
                }
            } catch (DaoSystemException e) {
                //TODO: only SQLException can be here, send to 500 error page.
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.sendRedirect(Pages.REGISTRATION_HTML);
    }

    private int checkFormBadValidations(HttpServletRequest request, Map<String, Boolean> validationsRegisterForm) {
        int counterOfBadValidations = 0;
        if (!validationsRegisterForm.get(Validations.MAP_KEY_IS_FIRST_NAME_VALID)) {
            counterOfBadValidations++;
            request.setAttribute(RequestAttribute.IS_FIRST_NAME_VALID, false);
        } else {
            request.setAttribute(RequestAttribute.IS_FIRST_NAME_VALID, true);
        }

        if (!validationsRegisterForm.get(Validations.MAP_KEY_IS_LAST_NAME_VALID)) {
            counterOfBadValidations++;
            request.setAttribute(RequestAttribute.IS_LAST_NAME_VALID, false);
        } else {
            request.setAttribute(RequestAttribute.IS_LAST_NAME_VALID, true);
        }

        if (!validationsRegisterForm.get(Validations.MAP_KEY_IS_PATRONYMIC_VALID)) {
            counterOfBadValidations++;
            request.setAttribute(RequestAttribute.IS_PATRONYMIC_VALID, false);
        } else {
            request.setAttribute(RequestAttribute.IS_PATRONYMIC_VALID, true);
        }

        if (!validationsRegisterForm.get(Validations.MAP_KEY_IS_ACCOUNT_NAME_VALID)) {
            counterOfBadValidations++;
            request.setAttribute(RequestAttribute.IS_ACCOUNT_NAME_VALID, false);
        } else {
            request.setAttribute(RequestAttribute.IS_ACCOUNT_NAME_VALID, true);
        }

        if (!validationsRegisterForm.get(Validations.MAP_KEY_IS_CITY_VALID)) {
            counterOfBadValidations++;
            request.setAttribute(RequestAttribute.IS_CITY_VALID, false);
        } else {
            request.setAttribute(RequestAttribute.IS_CITY_VALID, true);
        }

        if (!validationsRegisterForm.get(Validations.MAP_KEY_IS_REGION_VALID)) {
            counterOfBadValidations++;
            request.setAttribute(RequestAttribute.IS_REGION_VALID, false);
        } else {
            request.setAttribute(RequestAttribute.IS_REGION_VALID, true);
        }

        if (!validationsRegisterForm.get(Validations.MAP_KEY_IS_PASSWORD_VALID)) {
            counterOfBadValidations++;
            request.setAttribute(RequestAttribute.IS_PASSWORD_VALID, false);
        } else {
            request.setAttribute(RequestAttribute.IS_PASSWORD_VALID, true);
        }

        if (!validationsRegisterForm.get(Validations.MAP_KEY_IS_SCHOOL_VALID)) {
            counterOfBadValidations++;
            request.setAttribute(RequestAttribute.IS_SCHOOL_VALID, false);
        } else {
            request.setAttribute(RequestAttribute.IS_SCHOOL_VALID, true);
        }
        return counterOfBadValidations;
    }

    private int checkFormEmptyFields(HttpServletRequest request, String... params) {
        int counterOfEmptyFields = 0;
        if (params[0].equals(EMPTY_PARAM)) {
            counterOfEmptyFields++;
            request.setAttribute(RequestAttribute.IS_FIRST_NAME_EMPTY, true);
        } else {
            request.setAttribute(RequestAttribute.IS_FIRST_NAME_EMPTY, false);
        }

        if (params[1].equals(EMPTY_PARAM)) {
            counterOfEmptyFields++;
            request.setAttribute(RequestAttribute.IS_LAST_NAME_EMPTY, true);
        } else {
            request.setAttribute(RequestAttribute.IS_LAST_NAME_EMPTY, false);
        }

        if (params[2].equals(EMPTY_PARAM)) {
            counterOfEmptyFields++;
            request.setAttribute(RequestAttribute.IS_PATRONYMIC_EMPTY, true);
        } else {
            request.setAttribute(RequestAttribute.IS_PATRONYMIC_EMPTY, false);
        }

        if (params[3].equals(EMPTY_PARAM)) {
            counterOfEmptyFields++;
            request.setAttribute(RequestAttribute.IS_ACCOUNT_NAME_EMPTY, true);
        } else {
            request.setAttribute(RequestAttribute.IS_ACCOUNT_NAME_EMPTY, false);
        }

        if (params[4].equals(EMPTY_PARAM)) {
            counterOfEmptyFields++;
            request.setAttribute(RequestAttribute.IS_CITY_EMPTY, true);
        } else {
            request.setAttribute(RequestAttribute.IS_CITY_EMPTY, false);
        }

        if (params[5].equals(EMPTY_PARAM)) {
            counterOfEmptyFields++;
            request.setAttribute(RequestAttribute.IS_REGION_EMPTY, true);
        } else {
            request.setAttribute(RequestAttribute.IS_REGION_EMPTY, false);
        }

        if (params[6].equals(EMPTY_PARAM)) {
            counterOfEmptyFields++;
            request.setAttribute(RequestAttribute.IS_PASSWORD_EMPTY, true);
        } else {
            request.setAttribute(RequestAttribute.IS_PASSWORD_EMPTY, false);
        }

        if (params[7].equals(EMPTY_PARAM)) {
            counterOfEmptyFields++;
            request.setAttribute(RequestAttribute.IS_SCHOOL_EMPTY, true);
        } else {
            request.setAttribute(RequestAttribute.IS_SCHOOL_EMPTY, false);
        }
        return counterOfEmptyFields;
    }

    private void setUpAttrForFields(HttpServletRequest request, String... attributes) {
        request.setAttribute(RequestAttribute.FIRST_NAME, attributes[0]);
        request.setAttribute(RequestAttribute.LAST_NAME, attributes[1]);
        request.setAttribute(RequestAttribute.PATRONYMIC, attributes[2]);
        request.setAttribute(RequestAttribute.ACCOUNT_NAME, attributes[3]);
        request.setAttribute(RequestAttribute.CITY, attributes[4]);
        request.setAttribute(RequestAttribute.REGION, attributes[5]);
        request.setAttribute(RequestAttribute.PASSWORD, attributes[6]);
        request.setAttribute(RequestAttribute.SCHOOL, attributes[7]);
    }

    private Map<String, Entity> prepareEntities(String firstName, String lastName,
                                                String patronymic, String accountName,
                                                String city, String region,
                                                String password, String school) {
        Map<String, Entity> entrantInfo = new HashMap<>();
        User user = new User();
        Entrant entrant = new Entrant();
        String hashPassword = PasswordHash.createHash(password);

        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPatronymic(patronymic);
        user.setEmail(accountName);
        user.setPassword(hashPassword);
        user.setRoleId(2);

        entrant.setCity(city);
        entrant.setRegion(region);
        entrant.setSchool(Integer.parseInt(school));
        entrant.setEntrantStatus(3);
        entrant.setWithoutCompetitiveEntry(false);

        entrantInfo.put(MAP_KEY_USER, user);
        entrantInfo.put(MAP_KEY_ENTRANT, entrant);

        return entrantInfo;
    }

    private void prepareInfoVerifyEmail(HttpServletRequest request, String firstName,
                                        String lastName, String patronymic, String accountName) {
        String ticket = generateTicket();
        String verifyLink = "http://localhost:8080/verifyAccount.do?ticket=" + ticket;
        boolean flagSuccessTicket = false;
        while (!flagSuccessTicket) {
            try {
                TicketsWriterReader.writePair(ticket, accountName);
                flagSuccessTicket = true;
            } catch (PropertiesDuplicateException e) {
                ticket = generateTicket();
            }
        }
        MailInfoVerifyAccountBean mailInfoVerifyAccountBean = new MailInfoVerifyAccountBean();
        mailInfoVerifyAccountBean.setFirstName(firstName);
        mailInfoVerifyAccountBean.setLastName(lastName);
        mailInfoVerifyAccountBean.setPatronymic(patronymic);
        mailInfoVerifyAccountBean.setAccountName(accountName);
        mailInfoVerifyAccountBean.setVerifyLink(verifyLink);
        request.setAttribute(RequestAttribute.MAIL_INFO_VERIFY_ACCOUNT_BEAN, mailInfoVerifyAccountBean);
    }

    private String generateTicket() {
        return PasswordHash.randomPassword(40);
    }

}
