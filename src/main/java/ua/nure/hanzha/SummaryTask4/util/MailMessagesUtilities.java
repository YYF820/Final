package ua.nure.hanzha.SummaryTask4.util;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 16/08/15.
 */
public final class MailMessagesUtilities {

    private MailMessagesUtilities() {

    }

    public static String createSubjectVerifyAccount() {
        return "Подтверждение учетной записи University";
    }

    public static String createSubjectRecoverPassword() {
        return "Восстановление пароля учетной записи University";
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
        return "Уважаемый, " + lastName + " " + firstName + " " + patronymic + ".\n\n"
                + "Добро пожаловать на University.com\n"
                + "Подтвердите свой электронный почтовый ящик переходом по. \n" + confirmLink
                + "\nдля завершения настройки учетной записи.\n\n"
                + "С уважение University.";
    }

    public static String createMessageRecoverPassword(String firstName, String lastName, String patronymic, String ticketRecoverPassword) {
        return "Здравствуйте, " + lastName + " " + firstName + " " + patronymic + ".\n\n"
                + "Добро пожаловать на University.com\n"
                + "Вам выслан код на восстановление пароля:\n\n"
                + " " + ticketRecoverPassword + "\n\n"
                + "Код действителен 10 минут\n\n"
                + "С уважение University.";
    }

    public static String createMessageUpdatedPassword(String firstName, String lastName, String patronymic) {
        return "Здравствуйте, " + lastName + " " + firstName + " " + patronymic + ".\n\n"
                + "Пароль изменен успешно! Желаем удачи!\n\n"
                + "С уважение University.";
    }

    public static String createMessageBannedAccount(String firstName, String lastName, String patronymic) {
        return "Здравствуйте, " + lastName + " " + firstName + " " + patronymic + ".\n\n"
                + "Ваша учетная запись заблокирована.\n\n"
                + "Свяжитесь с поддержкой для выявления причины.\n\n"
                + "С уважение University.";
    }

    public static String createMessageUnBannedAccount(String firstName, String lastName, String patronymic) {
        return "Здравствуйте, " + lastName + " " + firstName + " " + patronymic + ".\n\n"
                + "Ваш учетная запись успешно разблокирована!\n\n"
                + "С уважение University.";
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
