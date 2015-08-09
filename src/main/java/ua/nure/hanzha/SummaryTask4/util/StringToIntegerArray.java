package ua.nure.hanzha.SummaryTask4.util;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 09/08/15.
 */
public class StringToIntegerArray {

    public static Integer[] convert (String[] strings) {
        if (strings == null) {
            return null;
        } else {
            Integer[] integers = new Integer[strings.length];
            for (int i = 0; i < strings.length; i++) {
                integers[i] = Integer.valueOf(strings[i]);
            }
            strings = null;
            return integers;
        }
    }
}
