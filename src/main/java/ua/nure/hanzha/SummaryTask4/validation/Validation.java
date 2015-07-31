package ua.nure.hanzha.SummaryTask4.validation;

import ua.nure.hanzha.SummaryTask4.constants.Validations;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by faffi-ubuntu on 18/07/15.
 */
public class Validation {


    public static Map<String, Boolean> validateLoginAction(String email, String password) {
        boolean isEmailValid = validateEmail(email);
        boolean isPasswordValid = validatePassword(password);
        Map<String, Boolean> validations = new HashMap<>();
        validations.put(Validations.MAP_KEY_IS_EMAIL_VALID, isEmailValid);
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
        Boolean isLoginValid = validateLogin(login);
        Boolean isPasswordValid = validatePassword(password);
        Boolean isFullNameValid = validateFullName(fullName);
        Boolean isEmailValid = validateEmail(email);
        Map<String, Boolean> validations = new HashMap<>();
        validations.put(login, isLoginValid);
        validations.put(password, isPasswordValid);
        validations.put(fullName, isFullNameValid);
        validations.put(email, isEmailValid);
        return validations;
    }


    private static boolean validateLogin(String login) {
        Pattern p = Pattern.compile("^[\\p{L}0-9_-]{6,15}$");
        Matcher m = p.matcher(login);
        return m.matches();
    }

    private static boolean validatePassword(String password) {
        Pattern p = Pattern.compile("^((?=.*\\d)(?=.*[\\p{L}]).{6,20})$");
        Matcher m = p.matcher(password);
        return m.matches();
    }

    private static boolean validateEmail(String email) {
        Pattern p = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher m = p.matcher(email);
        return m.matches();
    }

    private static boolean validateFullName(String fullName) {
        Pattern p = Pattern.compile("^\\p{L}+ \\p{L}+$");
        Matcher m = p.matcher(fullName);
        return m.matches();
    }
}
