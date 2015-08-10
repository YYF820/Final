package ua.nure.hanzha.SummaryTask4.service.extraMark;

import ua.nure.hanzha.SummaryTask4.db.dao.extramark.ExtraMarkDao;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.SqlCallable;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.TransactionManager;
import ua.nure.hanzha.SummaryTask4.entity.ExtraMark;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 10/08/15.
 */
public class ExtraMarkServiceImpl implements ExtraMarkService {

    private TransactionManager txManager;
    private ExtraMarkDao extraMarkDao;

    public ExtraMarkServiceImpl(TransactionManager txManager, ExtraMarkDao extraMarkDao) {
        this.txManager = txManager;
        this.extraMarkDao = extraMarkDao;
    }

    @Override
    public ExtraMark getByEntrantId(final int entrantId) throws DaoSystemException {
        return txManager.doInTransaction(new SqlCallable<ExtraMark>() {
            @Override
            public ExtraMark call(Connection connection) throws SQLException, CrudException {
                return extraMarkDao.selectByEntrantId(entrantId, connection);
            }
        });
    }

    @Override
    public void addExtraMark(final ExtraMark extraMark) throws DaoSystemException {
        txManager.doInTransaction(new SqlCallable<Void>() {
            @Override
            public Void call(Connection connection) throws SQLException, CrudException {
                extraMarkDao.insert(extraMark, connection);
                return null;
            }
        });
    }
}
