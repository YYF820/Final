package ua.nure.hanzha.SummaryTask4.enums;

import ua.nure.hanzha.SummaryTask4.entity.User;
/**
 * Created by faffi-ubuntu on 28/07/15.
 */
public enum Role {

    ADMIN, ENTRANT;

    public static Role getRole(User user) {
        int roleId = user.getRoleId() - 1;
        return Role.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}

