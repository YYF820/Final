package ua.nure.hanzha.SummaryTask4.constants;

/**
 * Created by faffi-ubuntu on 02/08/15.
 */
public class SessionAttribute {

    public static final String ACCOUNT = "account";

    public static final String COMMAND = "command";

    public static final String ENTRANTS_ADMIN = "entrantsAdmin";
    public static final String NUMBER_OF_PAGES = "numberOfPages";
    public static final String CURRENT_PAGE = "currentPage";

    public static final String ENTRANT_FOR_VERIFY_ACCOUNT_RESET_PASSWORD = "entrantForVerifyAccountResetPassword";
    public static final String USER_FOR_VERIFY_ACCOUNT_RESET_PASSWORD = "userForVerifyAccountResetPassword";

    //================LOGIN FORM=========================//
    public static final String LOGIN_IS_ACCOUNT_NAME_EXISTS = "loginIsAccountNameExists";
    public static final String LOGIN_IS_ACCOUNT_NAME_EMPTY = "loginIsAccountNameEmpty";
    public static final String LOGIN_IS_ACCOUNT_NAME_VALID = "loginIsAccountNameValid";
    public static final String LOGIN_IS_PASSWORD_EMPTY = "loginIsPasswordEmpty";
    public static final String LOGIN_IS_PASSWORD_VALID = "loginIsPasswordValid";

    public static final String LOGIN_ACCOUNT_NAME = "loginAccountName";
    public static final String LOGIN_PASSWORD = "loginPassword";

    //================REGISTRATION FORM==================//
    public static final String REGISTRATION_IS_ACCOUNT_NAME_EXISTS = "registrationIsAccountNameExists";
    public static final String REGISTRATION_IS_ACCOUNT_NAME_EMPTY = "registrationIsAccountNameEmpty";
    public static final String REGISTRATION_IS_PASSWORD_EMPTY = "registrationIsPasswordEmpty";
    public static final String REGISTRATION_IS_FIRST_NAME_EMPTY = "registrationIsFirstNameEmpty";
    public static final String REGISTRATION_IS_LAST_NAME_EMPTY = "registrationIsLastNameEmpty";
    public static final String REGISTRATION_IS_PATRONYMIC_EMPTY = "registrationIsPatronymicEmpty";
    public static final String REGISTRATION_IS_CITY_EMPTY = "registrationIsCityEmpty";
    public static final String REGISTRATION_IS_REGION_EMPTY = "registrationIsRegionEmpty";
    public static final String REGISTRATION_IS_SCHOOL_EMPTY = "registrationIsSchoolEmpty";


    public static final String REGISTRATION_IS_ACCOUNT_NAME_VALID = "registrationIsAccountNameValid";
    public static final String REGISTRATION_IS_PASSWORD_VALID = "registrationIsPasswordValid";
    public static final String REGISTRATION_IS_FIRST_NAME_VALID = "registrationIsFirstNameValid";
    public static final String REGISTRATION_IS_LAST_NAME_VALID = "registrationIsLastNameValid";
    public static final String REGISTRATION_IS_PATRONYMIC_VALID = "registrationIsPatronymicValid";
    public static final String REGISTRATION_IS_CITY_VALID = "registrationIsCityValid";
    public static final String REGISTRATION_IS_REGION_VALID = "registrationIsRegionValid";
    public static final String REGISTRATION_IS_SCHOOL_VALID = "registrationIsSchoolValid";

    public static final String REGISTRATION_ACCOUNT_NAME = "registrationAccountName";
    public static final String REGISTRATION_PASSWORD = "registrationPassword";
    public static final String REGISTRATION_FIRST_NAME = "registrationFirstName";
    public static final String REGISTRATION_LAST_NAME = "registrationLastName";
    public static final String REGISTRATION_PATRONYMIC = "registrationPatronymic";
    public static final String REGISTRATION_CITY = "registrationCity";
    public static final String REGISTRATION_REGION = "registrationRegion";
    public static final String REGISTRATION_SCHOOL = "registrationSchool";

    //========================== RESEND VERIFY MESSAGE OR RESET PASSWORD ===================//
    public static final String RESEND_IS_ACCOUNT_NAME_VALID = "resendIsAccountNameValid";
    public static final String RESEND_IS_ACCOUNT_NAME_EMPTY = "resendIsAccountNameEmpty";
    public static final String RESEND_ACCOUNT_NAME = "resendAccountName";
    public static final String RESEND_IS_USER_EXISTS_BY_ACCOUNT_NAME = "resendIsUserExistsByAccountName";
    public static final String RESEND_IS_ADMIN_TRYING_VERIFY_ACCOUNT = "resendIsAdminTryingVerifyAccount";
    public static final String RESEND_IS_ACTIVE_ACCOUNT = "resendIsActiveAccount";
    public static final String RESEND_IS_BLOCKED_ACCOUNT = "resendIsBlockedAccount";

    //========================== CHECK QUESTION =================================//

    public static final String CHECK_QUESTION_IS_SCHOOL_VALID = "checkQuestionIsSchoolValid";
    public static final String CHECK_QUESTION_IS_SCHOOL_EMPTY = "checkQuestionIsSchoolEmpty";
    public static final String CHECK_QUESTION_IS_SCHOOL_CORRECT = "checkQuestionIsSchoolCorrect";
    public static final String CHECK_QUESTION_SCHOOL = "checkQuestionSchool";

    //========================= CHECK TICKET ====================================//

    public static final String CHECK_TICKET_IS_MESSAGE_SENT = "checkTicketIsMessageSent";
    public static final String CHECK_TICKET_IS_EMPTY = "checkTicketIsEmpty";
    public static final String CHECK_TICKET_TICKET_RESET_PASSWORD = "checkTicketTicketResetPassword";
    public static final String CHECK_TICKET_HASH_TICKET_RESET_PASSWORD = "checkTicketHashTicketResetPassword";
    public static final String CHECK_TICKET_COUNTER_BAD_TICKET_INSERTS = "checkTicketCounterBadTicketInserts";
    public static final String CHECK_TICKET_IS_TICKET_RESET_PASSWORD_CORRECT = "checkTicketIsResetPasswordCorrect";
    public static final String CHECK_TICKET_IS_BLOCKED_ACCOUNT = "checkQuestionIsBlockedAccount";

    //========================= VERIFY ACCOUNT ===================================//
    public static final String VERIFY_ACCOUNT_IS_MESSAGE_SENT = "verifyAccountIsMessageSent";
    public static final String VERIFY_ACCOUNT_ACCOUNT_NAME = "verifyAccountName";
    public static final String VERIFY_ACCOUNT_IS_VERIFIED_ACCOUNT = "isVerifiedAccount";

    //========================= RESET PASSWORD ==================================//
    public static final String RESET_PASSWORD_IS_PASSWORD_VALID = "resetPasswordIsPasswordValid";
    public static final String RESET_PASSWORD_IS_PASSWORD_EMPTY = "resetPasswordIsPasswordEmpty";
    public static final String RESET_PASSWORD_IS_CONFIRM_PASSWORD_EMPTY = "resetPasswordIsConfirmPasswordEmpty";
    public static final String RESET_PASSWORD_IS_SAME_PASSWORDS = "resetPasswordIsPasswordsSame";
    public static final String RESET_PASSWORD_IS_UPDATED_PASSWORD = "resetPasswordIsUpdatedPassword";


    //========================= ADMIN_BLOCK_ENTRANT ===============================//
    public static final String ADMIN_IS_ENTRANT_BLOCKED = "adminIsEntrantBlocked";
    public static final String ADMIN_COMMAND_BLOCK_USER = "adminCommandBlockUser";
}
