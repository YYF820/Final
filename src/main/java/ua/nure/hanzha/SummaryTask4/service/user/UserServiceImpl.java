package ua.nure.hanzha.SummaryTask4.service.user;

import ua.nure.hanzha.SummaryTask4.db.dao.user.UserDao;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.SqlCallable;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.TransactionManager;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by faffi-ubuntu on 30/07/15.
 */
public class UserServiceImpl implements UserService {

    private TransactionManager txManager;

    private UserDao userDao;

    public UserServiceImpl(TransactionManager txManager, UserDao userDao) {
        this.txManager = txManager;
        this.userDao = userDao;
    }

    @Override
    public User selectByEmail(final String accountName) throws DaoSystemException {
        return txManager.doInTransaction(new SqlCallable<User>() {
            @Override
            public User call(Connection connection) throws SQLException, CrudException {
                return userDao.selectByEmail(accountName, connection);
            }
        });
    }

    @Override
    public boolean userExistsByAccountName(final String accountName) throws DaoSystemException {
        return txManager.doInTransaction(new SqlCallable<Boolean>() {
            @Override
            public Boolean call(Connection connection) throws SQLException, CrudException {
                return userDao.userExistsByEmail(accountName, connection);
            }
        });
    }
}
