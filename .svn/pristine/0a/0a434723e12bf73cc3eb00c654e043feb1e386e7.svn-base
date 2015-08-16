package ua.nure.hanzha.SummaryTask4.enums;

import ua.nure.hanzha.SummaryTask4.entity.Entrant;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 02/08/15.
 */
public enum EntrantStatus {

    BLOCKED, ACTIVE, NOT_VERIFIED;

    public static EntrantStatus getEntrantStatus(Entrant user) {
        int entrantStatusId = user.getEntrantStatus() - 1;
        return EntrantStatus.values()[entrantStatusId];
    }

    public static EntrantStatus getEntrantStatusById(int statusId) {
        return EntrantStatus.values()[statusId - 1];
    }

    public String getName() {
        return name().toLowerCase().replaceAll("_", "");
    }
}
