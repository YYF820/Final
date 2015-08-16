package ua.nure.hanzha.SummaryTask4.enums;

import ua.nure.hanzha.SummaryTask4.entity.User;

/**
 * Created by faffi-ubuntu on 28/07/15.
 */
public enum Role {

    ADMIN, ENTRANT, GUEST; //TODO: Add to database new role and trigger to prevent add users with role GUEST.

    public static Role getRole(User user) {
        int roleId = user.getRoleId() - 1;
        return Role.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}

