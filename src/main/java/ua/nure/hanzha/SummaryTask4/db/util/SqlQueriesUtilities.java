package ua.nure.hanzha.SummaryTask4.db.util;

import org.apache.log4j.Logger;
import ua.nure.hanzha.SummaryTask4.util.ClassNameUtilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <p/>
 * Class is using pattern Singleton, which loads property file with method SqlQueriesUtilities#initSqlQueriesHolder.
 * Method SqlQueriesUtilities#getSqlQuery returns sql query by key.
 * <p/>
 * Created by faffi-ubuntu on 28/07/15.
 *
 * @author Dmytro Hanzha
 */
public final class SqlQueriesUtilities {

    private static final Logger LOGGER = Logger.getLogger(ClassNameUtilities.getCurrentClassName());

    //===========================SINGLETON
    private static Properties properties;

    private SqlQueriesUtilities() {

    }

    public static void initSqlQueriesHolder(String sqlQueriesPath) {
        loadSqlQueries(sqlQueriesPath);
    }

    private static void loadSqlQueries(String sqlQueries) {
        try (InputStream in = new FileInputStream(sqlQueries)) {
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            LOGGER.error("loadSqlQueries : ", e);
        }
    }

    public static String getSqlQuery(String key) {
        return String.valueOf(properties.getProperty(key));
    }

}
