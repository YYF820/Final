package ua.nure.hanzha.SummaryTask4.servlet.callable.checkQuestion;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 18/08/15.
 */
public interface CheckQuestionCallable {

    void call() throws ServletException, IOException;
}
