package ua.nure.hanzha.SummaryTask4.db.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * <p/>
 * Class is using patter Singleton, which loads property file with method SqlQueriesHolder#initSqlQueriesHolder.
 * Method SqlQueriesHolder#getSqlQuery returns sql query by key.
 * <p/>
 * Created by faffi-ubuntu on 28/07/15.
 *
 * @author Dmytro Hanzha
 */
public class SqlQueriesHolder {
    //===========================SINGLETON
    private static Properties properties;

    private SqlQueriesHolder() {

    }

    public static void initSqlQueriesHolder(String sqlQueriesPath) {
        loadSqlQueries(sqlQueriesPath);
    }

    private static void loadSqlQueries(String sqlQueries) {
        try (InputStream in = new FileInputStream(sqlQueries)) {
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getSqlQuery(String key) {
        return String.valueOf(properties.getProperty(key));
    }

    public static void main(String[] args) {
        SqlQueriesHolder.initSqlQueriesHolder("src/main/resources/sqlQueries.properties");
        System.out.println(SqlQueriesHolder.getSqlQuery("users.select.all"));
    }
}
