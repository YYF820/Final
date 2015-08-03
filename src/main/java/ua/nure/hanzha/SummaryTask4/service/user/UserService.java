package ua.nure.hanzha.SummaryTask4.service.user;

import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;

import java.sql.Connection;

/**
 * Created by faffi-ubuntu on 30/07/15.
 */
public interface UserService {

    User selectByEmail(String email) throws DaoSystemException;

    boolean userExistsByAccountName(String accountName) throws DaoSystemException;
}
