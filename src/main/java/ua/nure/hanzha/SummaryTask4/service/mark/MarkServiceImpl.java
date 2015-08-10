package ua.nure.hanzha.SummaryTask4.service.mark;

import ua.nure.hanzha.SummaryTask4.db.dao.mark.MarkDao;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.SqlCallable;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.TransactionManager;
import ua.nure.hanzha.SummaryTask4.entity.Mark;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 10/08/15.
 */
public class MarkServiceImpl implements MarkService {

    private TransactionManager txManager;
    private MarkDao markDao;

    public MarkServiceImpl(TransactionManager txManager, MarkDao markDao) {
        this.txManager = txManager;
        this.markDao = markDao;
    }

    @Override
    public List<Mark> getAllMarksByEntrantId(final int entrantId) throws DaoSystemException {
        return txManager.doInTransaction(new SqlCallable<List<Mark>>() {
            @Override
            public List<Mark> call(Connection connection) throws SQLException, CrudException {
                return markDao.selectByEntrantId(entrantId, connection);
            }
        });
    }

    @Override
    public void addMark(final Mark mark) throws DaoSystemException {
        txManager.doInTransaction(new SqlCallable<Void>() {
            @Override
            public Void call(Connection connection) throws SQLException, CrudException {
                markDao.insert(mark, connection);
                return null;
            }
        });
    }
}
