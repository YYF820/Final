package ua.nure.hanzha.SummaryTask4.validation;

import ua.nure.hanzha.SummaryTask4.bean.RegistrationBean;
import ua.nure.hanzha.SummaryTask4.constants.Validations;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 18/07/15.
 */
public class Validation {


    public static Map<String, Boolean> validateLoginAction(String email, String password) {
        Map<String, Boolean> validations = new HashMap<>();

        boolean isEmailValid = validateEmail(email);
        boolean isPasswordValid = validatePassword(password);

        validations.put(Validations.MAP_KEY_IS_ACCOUNT_NAME_VALID, isEmailValid);
        validations.put(Validations.MAP_KEY_IS_PASSWORD_VALID, isPasswordValid);
        return validations;
    }

    public static boolean[] validatePasswordFullNameEmail(String password, String fullName, String email) {
        boolean isPasswordValid = validatePassword(password);
        boolean isFullNameValid = validateFullName(fullName);
        boolean isEmailValid = validateEmail(email);
        return new boolean[]{isPasswordValid, isFullNameValid, isEmailValid};
    }

    public static Map<String, Boolean> validateAddForm(String login, String password, String fullName, String email) {
        Map<String, Boolean> validations = new HashMap<>();

        Boolean isLoginValid = validateLogin(login);
        Boolean isPasswordValid = validatePassword(password);
        Boolean isFullNameValid = validateFullName(fullName);
        Boolean isEmailValid = validateEmail(email);

        validations.put(login, isLoginValid);
        validations.put(password, isPasswordValid);
        validations.put(fullName, isFullNameValid);
        validations.put(email, isEmailValid);

        return validations;
    }

    public static Map<String, Boolean> validateRegistrationForm(
            RegistrationBean registrationBean) {

        Map<String, Boolean> validations = new HashMap<>();

        Boolean isFirstNameValid = validateFirstName(registrationBean.getFirstName());
        Boolean isLastNameValid = validateLastName(registrationBean.getLastName());
        Boolean isPatronymicValid = validatePatronymic(registrationBean.getPatronymic());
        Boolean isAccountNameValid = validateEmail(registrationBean.getAccountName());
        Boolean isCityValid = validateCity(registrationBean.getCity());
        Boolean isRegionValid = validateRegion(registrationBean.getRegion());
        Boolean isPasswordValid = validatePassword(registrationBean.getPassword());
        Boolean isSchoolValid = validateSchool(registrationBean.getSchool());

        validations.put(Validations.MAP_KEY_IS_FIRST_NAME_VALID, isFirstNameValid);
        validations.put(Validations.MAP_KEY_IS_LAST_NAME_VALID, isLastNameValid);
        validations.put(Validations.MAP_KEY_IS_PATRONYMIC_VALID, isPatronymicValid);
        validations.put(Validations.MAP_KEY_IS_ACCOUNT_NAME_VALID, isAccountNameValid);
        validations.put(Validations.MAP_KEY_IS_CITY_VALID, isCityValid);
        validations.put(Validations.MAP_KEY_IS_REGION_VALID, isRegionValid);
        validations.put(Validations.MAP_KEY_IS_PASSWORD_VALID, isPasswordValid);
        validations.put(Validations.MAP_KEY_IS_SCHOOL_VALID, isSchoolValid);

        return validations;
    }


    private static boolean validateLogin(String login) {
        Pattern p = Pattern.compile("^[\\p{L}0-9_-]{6,15}$");
        Matcher m = p.matcher(login);
        return m.matches();
    }

    public static boolean validatePassword(String password) {
        Pattern p = Pattern.compile("^((?=.*\\d)(?=.*[\\p{L}]).{6,20})$");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    public static boolean validateEmail(String email) {
        Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    private static boolean validateFullName(String fullName) {
        Pattern p = Pattern.compile("^\\p{L}+ \\p{L}+$");
        Matcher m = p.matcher(fullName);
        return m.matches();
    }

    private static boolean validateCity(String city) {
        Pattern p = Pattern.compile("^[\\p{Lu}][\\p{L}&&[^\\p{Lu}]]{2,14}+([' -][\\p{Lu}][\\p{L}&&[^\\p{Lu}]]*+){0,2}$");
        Matcher m = p.matcher(city);
        return m.matches();
    }

    private static boolean validateRegion(String region) {
        Pattern p = Pattern.compile("^[\\p{Lu}][\\p{L}&&[^\\p{Lu}]]{2,14}(?:[' -][\\p{L}&&[^\\p{Lu}]]*+)?$");
        Matcher m = p.matcher(region);
        return m.matches();
    }

    public static boolean validateSchool(String school) {
        Pattern p = Pattern.compile("^[1-9]|[1-9][0-9]|1[0-9][0-9]|2[0-5][0-5]$");
        Matcher m = p.matcher(school);
        return m.matches();
    }

    private static boolean validateFirstName(String firstName) {
        Pattern p = Pattern.compile("^[\\p{Lu}][['-]?\\p{L}&&[^\\p{Lu}]]{2,15}$");
        Matcher m = p.matcher(firstName);
        return m.matches();
    }

    public static boolean validateLastName(String lastName) {
        Pattern p = Pattern.compile("^[\\p{Lu}](['-]?[\\p{L}&&[^\\p{Lu}]]+){2,15}$");
        Matcher m = p.matcher(lastName);
        return m.matches();
    }

    private static boolean validatePatronymic(String patronymic) {
        Pattern p = Pattern.compile("^[\\p{Lu}]['-]?[\\p{L}&&[^\\p{Lu}]]{2,15}$");
        Matcher m = p.matcher(patronymic);
        return m.matches();
    }

    public static boolean validateTicketResetPassword(String ticketResetPassword) {
        Pattern p = Pattern.compile("^[A-Z0-9]{6}$");
        Matcher m = p.matcher(ticketResetPassword);
        return m.matches();
    }

    public static boolean validateFacultyName(String facultyName) {
        Pattern p = Pattern.compile("^[\\p{Lu}][\\p{L}&&[^\\p{Lu}]]{2,25}(\\s[\\p{Lu}]*[\\p{L}&&[^\\p{Lu}]]{2,25})*$");
        Matcher m = p.matcher(facultyName);
        return m.matches();
    }

    public static boolean validateParamFacultyId(String facultyId) {
        Pattern p = Pattern.compile("^[0-9]+$");
        Matcher m = p.matcher(facultyId);
        return m.matches();
    }

    public static boolean validateMark(String mark) {
        Pattern p = Pattern.compile("[1][0-9][0-9](\\.\\d{0,2}|)|200(\\.0{1,2}|)");
        Matcher m = p.matcher(mark);
        return m.matches();
    }

    public static boolean validateCertificatePoints(String certificatePoints) {
        Pattern p = Pattern.compile("^[3-5][0-9](\\.\\d{0,2}|)|60(\\.0{1,2}|)$");
        Matcher m = p.matcher(certificatePoints);
        return m.matches();
    }

    public static boolean validateExtraPoints(String extraPoints) {
        Pattern p = Pattern.compile("^[0-9](\\.\\d{0,2}|)|[1][0-9](\\.\\d{0,2}|)|20(\\.[0]{1,2}|)$");
        Matcher m = p.matcher(extraPoints);
        return m.matches();
    }
}
