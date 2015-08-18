package ua.nure.hanzha.SummaryTask4.servlet.callable.adminChangeStatus;

import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;

import javax.servlet.ServletException;
import java.io.IOException;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 18/08/15.
 */
public interface AdminChangeStatusCallable {

    void call() throws ServletException, IOException, DaoSystemException;
}
