package ua.nure.hanzha.SummaryTask4.exception;

/**
 * Created by faffi-ubuntu on 28/07/15.
 */
public class DaoSystemException extends Exception {

    public DaoSystemException(String message) {
        super(message);
    }

    public DaoSystemException(String message, Throwable cause) {
        super(message, cause);
    }

}