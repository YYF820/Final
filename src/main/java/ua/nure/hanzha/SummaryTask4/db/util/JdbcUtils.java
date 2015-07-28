package ua.nure.hanzha.SummaryTask4.db.util;

import java.sql.Connection;
import java.sql.SQLException;

public class JdbcUtils {

    public static void closeQuietly(AutoCloseable autoCloseable) {
        if (autoCloseable != null) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
                /* Log4j */
            }
        }
    }

    public static void closeAllQuietly(AutoCloseable... autoCloseables) {
        for (AutoCloseable autoCloseable : autoCloseables) {
            try {
                autoCloseable.close();
            } catch (Exception e) {
                /* Log4j */
            }
        }
    }

    public static void rollBackQuietly(Connection conn) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException e) {
                /* Log4j */
            }
        }
    }

}
