package ua.nure.hanzha.SummaryTask4.db.util;

import org.apache.log4j.Logger;
import ua.nure.hanzha.SummaryTask4.util.ClassNameUtilities;

import java.sql.Connection;
import java.sql.SQLException;

public final class JdbcUtilities {


    private static final Logger LOGGER = Logger.getLogger(ClassNameUtilities.getCurrentClassName());

    private JdbcUtilities() {

    }

    public static void closeQuietly(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
                LOGGER.error("FAIL close " + autoCloseable + ", exception :" + e);
            }
        }
    }

    public static void closeAllQuietly(AutoCloseable... autoCloseables) {
        for (AutoCloseable autoCloseable : autoCloseables) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
                LOGGER.error("FAIL close " + autoCloseable + ", exception :" + e);
            }
        }
    }

    public static void rollBackQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                LOGGER.error("FAIL rollback , exception :" + e);
            }
        }
    }

}
