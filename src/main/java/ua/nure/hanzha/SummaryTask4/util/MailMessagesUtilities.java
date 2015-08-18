package ua.nure.hanzha.SummaryTask4.util;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 16/08/15.
 */
public final class MailMessagesUtilities {

    private MailMessagesUtilities() {

    }

    public static String createSubjectVerifyAccount() {
        return "Подтверждение E-mail учетной записи UniversityAlpha.com";
    }

    public static String createSubjectRecoverPassword() {
        return "Восстановление пароля учетной записи UniversityAlpha.com";
    }

    public static String createSubjectUpdatedPassword() {
        return "Восстановление пароля прошло успешно.";
    }

    public static String createSubjectBannedAccount() {
        return "Ваша запись была заблокирована.";
    }

    public static String createSubjectUnBannedAccount() {
        return "Ваша запись была разблокирована.";
    }

    public static String createSubjectEnterUniversity() {
        return "Информация о поступлении";
    }

    public static String createMessageVerifyAccount(String firstName, String lastName, String patronymic, String confirmLink) {
        return "Здравствуйте, " + lastName + " " + firstName + " " + patronymic + ".\n\n"
                + "Добро пожаловать на UniversityAlpha.com\n"
                + "Если Вы еще не подтвердили действительность своего E-mail, настоятельно рекомендуем Вам сделать это сейчас.\n\n"
                + " " + confirmLink;
    }

    public static String createMessageRecoverPassword(String firstName, String lastName, String patronymic, String ticketRecoverPassword) {
        return "Здравствуйте, " + lastName + " " + firstName + " " + patronymic + ".\n\n"
                + "Добро пожаловать на UniversityAlpha.com\n"
                + "Мы вам выслали код который вы должны ввести в форму на которую вы перешли\n\n"
                + " " + ticketRecoverPassword;
    }

    public static String createMessageUpdatedPassword(String firstName, String lastName, String patronymic) {
        return "Здравствуйте, " + lastName + " " + firstName + " " + patronymic + ".\n\n"
                + "Вашей записи назначен новый пароль.\n\n"
                + "Спасибо что пользуетесь нашим сервисом.\n\n";
    }

    public static String createMessageBannedAccount(String firstName, String lastName, String patronymic) {
        return "Здравствуйте, " + lastName + " " + firstName + " " + patronymic + ".\n\n"
                + "Ваша запись была заблокирована, администрацией.\n\n"
                + "Свяжитесь с поддержкой для выявления причины.\n\n";
    }

    public static String createMessageUnBannedAccount(String firstName, String lastName, String patronymic) {
        return "Здравствуйте, " + lastName + " " + firstName + " " + patronymic + ".\n\n"
                + "Ваша запись была разблокирована.\n\n"
                + "Спасибо что пользуетесь нашей системой.\n\n";
    }

    public static String createMessageCongratulationsBudgetEntrant(String firstName, String lastName, String patronymic, String facultyName) {
        return "Уважаемый " + lastName + " " + firstName + " " + patronymic + ".\n" +
                "Поздравляем Вас с зачислением на бюджетное место дневной формы обучения на факультете" + facultyName + "!\n" +
                "Желаем успехов в учебе!\n\n" +
                "С уважение University.";
    }

    public static String createMessageCongratulationsContractEntrant(String firstName, String lastName, String patronymic, String facultyName) {
        return "Уважаемый " + lastName + " " + firstName + " " + patronymic + ".\n" +
                "Cообщаем Вам что в этом году Вы набрали недостаточное количество баллов для зачисления на бюджетное место дневной формы обучения.\n" +
                "Предлагаем Вам воспользоваться контрактной формой обучения на факультете" + facultyName + ". " +
                "По всем вопросам просим обращаться в приемную комиссию.\n\n" +
                "С уважение University.";
    }

    public static String createMessageNotPassedEntrant(String firstName, String lastName, String patronymic) {
        return "Уважаемый " + lastName + " " + firstName + " " + patronymic + ".\n" +
                "Cообщаем Вам, что в этом году Вы набрали недостаточное количество баллов для поступления в University.\n" +
                "По всем вопросам просим обращаться в приемную комиссию.\n\n" +
                "С уважение University.";
    }
}
