package ua.nure.hanzha.SummaryTask4.util;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 09/08/15.
 */
public final class StringToDecimalArrayUtilities {

    private StringToDecimalArrayUtilities() {

    }

    public static Integer[] convertToInteger(String[] strings) {
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

    public static Double[] convertToDouble(String[] strings) {
        if (strings == null) {
            return null;
        } else {
            Double[] doubles = new Double[strings.length];
            for (int i = 0; i < strings.length; i++) {
                doubles[i] = Double.valueOf(strings[i]);
            }
            strings = null;
            return doubles;
        }
    }


}
