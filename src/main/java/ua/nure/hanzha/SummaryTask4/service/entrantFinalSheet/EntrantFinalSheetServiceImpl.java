package ua.nure.hanzha.SummaryTask4.service.entrantFinalSheet;

import ua.nure.hanzha.SummaryTask4.bean.ReadyFinalEntrantSheetBean;
import ua.nure.hanzha.SummaryTask4.db.dao.entrantfinalsheet.EntrantFinalSheetDao;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.SqlCallable;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.TransactionManager;
import ua.nure.hanzha.SummaryTask4.entity.EntrantFinalSheet;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 14/08/15.
 */
public class EntrantFinalSheetServiceImpl implements EntrantFinalSheetService {

    private TransactionManager txManager;
    private EntrantFinalSheetDao entrantFinalSheetDao;

    public EntrantFinalSheetServiceImpl(TransactionManager txManager, EntrantFinalSheetDao entrantFinalSheetDao) {
        this.txManager = txManager;
        this.entrantFinalSheetDao = entrantFinalSheetDao;
    }

    @Override
    public Integer getMaxNumberOfSheet() throws DaoSystemException {
        return txManager.doInTransaction(new SqlCallable<Integer>() {
            @Override
            public Integer call(Connection connection) throws SQLException, CrudException {
                return entrantFinalSheetDao.selectMaxNumberOfPage(connection);
            }
        });
    }

    @Override
    public Integer getMaxIncrementedNumberOfSheet() throws DaoSystemException {
        return txManager.doInTransaction(new SqlCallable<Integer>() {
            @Override
            public Integer call(Connection connection) throws SQLException, CrudException {
                return entrantFinalSheetDao.selectIncrementedNumberOfPage(connection);
            }
        });
    }

    @Override
    public void addEntrantToFinalSheet(final EntrantFinalSheet entrantFinalSheet) throws DaoSystemException {
        txManager.doInTransaction(new SqlCallable<Void>() {
            @Override
            public Void call(Connection connection) throws SQLException, CrudException {
                entrantFinalSheetDao.insert(entrantFinalSheet, connection);
                return null;
            }
        });
    }

    @Override
    public List<ReadyFinalEntrantSheetBean> getPassedEntrants() throws DaoSystemException {
        return txManager.doInTransaction(new SqlCallable<List<ReadyFinalEntrantSheetBean>>() {
            @Override
            public List<ReadyFinalEntrantSheetBean> call(Connection connection) throws SQLException, CrudException {
                return entrantFinalSheetDao.selectPassedEntrants(connection);
            }
        });
    }

    @Override
    public ReadyFinalEntrantSheetBean getPassedEntrantByUserId(final int userId) throws DaoSystemException {
        return txManager.doInTransaction(new SqlCallable<ReadyFinalEntrantSheetBean>() {
            @Override
            public ReadyFinalEntrantSheetBean call(Connection connection) throws SQLException, CrudException {
                return entrantFinalSheetDao.selectPassedEntrantByUserId(userId, connection);
            }
        });
    }
}
