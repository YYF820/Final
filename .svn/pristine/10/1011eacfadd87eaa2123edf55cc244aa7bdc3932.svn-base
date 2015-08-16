package ua.nure.hanzha.SummaryTask4.service.registration;

import ua.nure.hanzha.SummaryTask4.db.dao.entrant.EntrantDao;
import ua.nure.hanzha.SummaryTask4.db.dao.user.UserDao;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.SqlCallable;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.TransactionManager;
import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by faffi-ubuntu on 02/08/15.
 */
public class RegistrationServiceImpl implements RegistrationService {

    private TransactionManager txManager;
    private UserDao userDao;
    private EntrantDao entrantDao;

    public RegistrationServiceImpl(TransactionManager txManager, UserDao userDao, EntrantDao entrantDao) {
        this.txManager = txManager;
        this.userDao = userDao;
        this.entrantDao = entrantDao;
    }

    @Override
    public void registerNewEntrant(final User user, final Entrant entrant) throws DaoSystemException {
        txManager.doInTransaction(new SqlCallable<Void>() {
            @Override
            public Void call(Connection connection) throws SQLException, CrudException {
                userDao.insert(user, connection);
                entrant.setUserId(user.getId());
                entrantDao.insert(entrant, connection);
                return null;
            }
        });
    }
}
