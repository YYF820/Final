package ua.nure.hanzha.SummaryTask4.bean;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 18/08/15.
 */
public class MailInfoToSupportBean {
    private String accountName;
    private String subject;
    private String message;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "accountName : " + accountName +
                "\n\tsubject : " + subject +
                "\n\tmessage : " + message;
    }
}
