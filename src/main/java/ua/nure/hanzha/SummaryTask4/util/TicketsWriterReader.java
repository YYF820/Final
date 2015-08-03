package ua.nure.hanzha.SummaryTask4.util;

import ua.nure.hanzha.SummaryTask4.exception.PropertiesDuplicateException;

import java.io.*;
import java.util.Properties;

/**
 * Class for manipulating with properties file (confirm tickets).
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 02/08/15.
 */
public class TicketsWriterReader {

    //===========================SINGLETON
    private static Properties properties;
    private static File file;
    private static String ticketsConfirmAccountPath;

    private TicketsWriterReader() {

    }

    public static void initSqlQueriesHolder(String ticketsConfirmAccountPath) throws FileNotFoundException {
        file = new File(ticketsConfirmAccountPath);
        TicketsWriterReader.ticketsConfirmAccountPath = ticketsConfirmAccountPath;
        loadSqlQueries(ticketsConfirmAccountPath);
    }

    private static void loadSqlQueries(String ticketsConfirmAccountPath) {
        try (InputStream in = new FileInputStream(ticketsConfirmAccountPath)) {
            properties = new Properties();
            properties.load(in);
        } catch (IOException e) {
            e.printStackTrace();
            /*LOG4J*/
        }
    }

    public static String getValue(String key) {
        try (InputStream in = new FileInputStream(ticketsConfirmAccountPath)) {
            properties.load(in);
            return properties.getProperty(key);
        } catch (IOException e) {
            e.printStackTrace();
            /*LOG4J*/
        }
        return null;
    }

    public static void writePair(String key, String property) throws PropertiesDuplicateException {
        if (properties.containsKey(key)) {
            throw new PropertiesDuplicateException("duplicate key");
        } else {
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(file);
                properties.setProperty(key, property);
                properties.store(fos, null);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
                /*LOG4J*/
            }

        }
    }

    public static void removePair(String key) {
        try (InputStream in = new FileInputStream(ticketsConfirmAccountPath)) {
            FileOutputStream fos = new FileOutputStream(file, false);
            properties.load(in);
            properties.remove(key);
            properties.store(fos, "removed pair with key:" + key);
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
            /*LOG4J*/
        }
    }

    public static boolean containsParameter(String parameter) {
        return properties.containsValue(parameter);
    }
}
