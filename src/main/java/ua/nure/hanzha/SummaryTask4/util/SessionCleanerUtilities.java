package ua.nure.hanzha.SummaryTask4.util;

import javax.servlet.http.HttpSession;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 07/08/15.
 */
public final class SessionCleanerUtilities {

    private SessionCleanerUtilities() {

    }

    public static void cleanAttributes(HttpSession session, String... attributes) {
        for (String attribute : attributes) {
            session.removeAttribute(attribute);
        }
    }
}
