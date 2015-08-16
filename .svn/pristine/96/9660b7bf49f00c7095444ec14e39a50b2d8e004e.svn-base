package ua.nure.hanzha.SummaryTask4.service.user;

import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;

/**
 * Created by faffi-ubuntu on 30/07/15.
 */
public interface UserService {

    User getById(int userId) throws DaoSystemException;

    User getByEmail(String email) throws DaoSystemException;

    boolean userExistsByAccountName(String accountName) throws DaoSystemException;

    void updatePasswordById(int id, String password) throws DaoSystemException;
}
