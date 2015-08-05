package ua.nure.hanzha.SummaryTask4.security;

import ua.nure.hanzha.SummaryTask4.enums.Role;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 05/08/15.
 */
public interface AuthorizationMap {

    String isAuthorize(String path, Role role);
}
