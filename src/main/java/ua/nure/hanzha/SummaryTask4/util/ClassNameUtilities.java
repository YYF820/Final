package ua.nure.hanzha.SummaryTask4.util;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 17/08/15.
 */
public final class ClassNameUtilities {

    private ClassNameUtilities() {

    }

    public static String getCurrentClassName() {
        try {
            throw new RuntimeException();
        } catch (RuntimeException e) {
            return e.getStackTrace()[1].getClassName();
        }
    }
}
