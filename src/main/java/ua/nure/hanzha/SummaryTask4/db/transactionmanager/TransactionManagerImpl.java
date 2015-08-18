package ua.nure.hanzha.SummaryTask4.db.transactionmanager;

import org.apache.log4j.Logger;
import ua.nure.hanzha.SummaryTask4.constants.ExceptionMessages;
import ua.nure.hanzha.SummaryTask4.db.datasource.ConnectionDataSource;
import ua.nure.hanzha.SummaryTask4.db.util.JdbcUtilities;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.util.ClassNameUtilities;

import java.sql.Connection;
import java.sql.SQLException;

public class TransactionManagerImpl implements TransactionManager {


    private static final Logger LOGGER = Logger.getLogger(ClassNameUtilities.getCurrentClassName());
    private static ConnectionDataSource connectionSource;

    static {
        connectionSource = ConnectionDataSource.getInstance();
        try {
            connectionSource.initDataSource();
        } catch (SQLException e) {
            LOGGER.error("Can't init DataSource", e);
        }
    }

    @Override
    public <T> T doInTransaction(SqlCallable<T> unitOfWork) throws DaoSystemException {
        Connection conn = null;
        try {
            conn = connectionSource.getConnection();
            T result = unitOfWork.call(conn);
            conn.commit();
            return result;
        } catch (SQLException e) {
            LOGGER.warn("Roll back, exception :", e);
            JdbcUtilities.rollBackQuietly(conn);
            throw new DaoSystemException(ExceptionMessages.SQL_EXCEPTION, e);
        } catch (CrudException e) {
            LOGGER.warn("Roll back, exception :", e);
            JdbcUtilities.rollBackQuietly(conn);
            throw new DaoSystemException(e.getMessage(), e);
        } finally {
            LOGGER.debug("Close connection.");
            JdbcUtilities.closeQuietly(conn);
        }
    }

}
