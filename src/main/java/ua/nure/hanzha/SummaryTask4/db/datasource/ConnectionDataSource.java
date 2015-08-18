package ua.nure.hanzha.SummaryTask4.db.datasource;

import org.apache.log4j.Logger;
import ua.nure.hanzha.SummaryTask4.util.ClassNameUtilities;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Dmytro Hanhza
 *         Created by faffi-ubuntu on 28/07/15.
 */
public class ConnectionDataSource {

    private static final Logger LOGGER = Logger.getLogger(ClassNameUtilities.getCurrentClassName());

    private static ConnectionDataSource instance;
    private static DataSource dataSource;

    private ConnectionDataSource() {

    }

    public static ConnectionDataSource getInstance() {
        if (instance == null) {
            instance = new ConnectionDataSource();
        }
        return instance;
    }

    public void initDataSource() throws SQLException {
        if (dataSource == null) {
            try {
                Context initContext = new InitialContext();
                Context envContext = (Context) initContext.lookup("java:comp/env");
                dataSource = (DataSource) envContext.lookup("jdbc/summaryTask4");
            } catch (NamingException e) {
                LOGGER.warn("initDataSource, exception :", e);
                e.printStackTrace();
            }
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
