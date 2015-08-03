package ua.nure.hanzha.SummaryTask4.service.entrant;

import ua.nure.hanzha.SummaryTask4.db.dao.entrant.EntrantDao;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.SqlCallable;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.TransactionManager;
import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by faffi-ubuntu on 02/08/15.
 */
public class EntrantServiceImpl implements EntrantService {

    private TransactionManager txManager;
    private EntrantDao entrantDao;

    public EntrantServiceImpl(TransactionManager txManager, EntrantDao entrantDao) {
        this.txManager = txManager;
        this.entrantDao = entrantDao;
    }

    @Override
    public int selectStatusIdByUserId(final int userId) throws DaoSystemException {
        return txManager.doInTransaction(new SqlCallable<Integer>() {
            @Override
            public Integer call(Connection connection) throws SQLException, CrudException {
                return entrantDao.selectStatusByUserId(userId, connection);
            }
        });
    }

    @Override
    public Entrant selectByUserId(final int userId) throws DaoSystemException {
        return txManager.doInTransaction(new SqlCallable<Entrant>() {
            @Override
            public Entrant call(Connection connection) throws SQLException, CrudException {
                return entrantDao.selectByUserId(userId, connection);
            }
        });
    }

    @Override
    public void updateEntrantStatus(final int statusId, final int entrantId) throws DaoSystemException {
        txManager.doInTransaction(new SqlCallable<Void>() {
            @Override
            public Void call(Connection connection) throws SQLException, CrudException {
                entrantDao.updateEntrantStatus(statusId, entrantId, connection);
                return null;
            }
        });
    }
}