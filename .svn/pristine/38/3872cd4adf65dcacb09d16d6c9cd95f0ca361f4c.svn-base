package ua.nure.hanzha.SummaryTask4.util;

import org.joda.time.DateTime;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 13/08/15.
 */
public class TimeWorker {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss");

    private TimeWorker() {

    }

    public static DateTime getDateTime(String stringDateTime) {
        return new DateTime(SIMPLE_DATE_FORMAT.format(stringDateTime));
    }

    private static Timestamp getCurrentTimeStamp() {
        Date today = new java.util.Date();
        return new Timestamp(today.getTime());
    }
}
