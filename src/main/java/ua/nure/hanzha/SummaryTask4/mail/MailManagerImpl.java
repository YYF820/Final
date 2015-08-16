package ua.nure.hanzha.SummaryTask4.mail;

import java.io.IOException;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 16/08/15.
 */
public class MailManagerImpl implements MailManager {

    @Override
    public Void sendMail(MailCallable unitOfWork) throws IOException {
        unitOfWork.call();
        return null;
    }

}
