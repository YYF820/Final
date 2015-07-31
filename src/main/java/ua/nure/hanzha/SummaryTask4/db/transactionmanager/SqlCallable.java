package ua.nure.hanzha.SummaryTask4.db.transactionmanager;


import ua.nure.hanzha.SummaryTask4.exception.CrudException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.Callable;

/**
 * Created by faffi-ubuntu on 18/07/15.
 */
public interface SqlCallable<T> {

    T call(Connection connection) throws SQLException, CrudException;
}
