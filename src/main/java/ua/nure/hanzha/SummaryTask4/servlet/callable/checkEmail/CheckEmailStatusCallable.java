package ua.nure.hanzha.SummaryTask4.servlet.callable.checkEmail;

import java.io.IOException;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 18/08/15.
 */
public interface CheckEmailStatusCallable {

    void call(String command) throws IOException;
}
