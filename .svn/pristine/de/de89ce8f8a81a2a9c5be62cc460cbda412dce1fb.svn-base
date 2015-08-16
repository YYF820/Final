package ua.nure.hanzha.SummaryTask4.service.entrantInfoAdmin;

import ua.nure.hanzha.SummaryTask4.bean.EntrantInfoAdminBean;
import ua.nure.hanzha.SummaryTask4.db.dao.entrantInfoAdmin.EntrantInfoAdminDao;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.SqlCallable;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.TransactionManager;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by faffi-ubuntu on 06/08/15.
 */
public class EntrantInfoAdminServiceImpl implements EntrantInfoAdminService {

    private TransactionManager txManager;
    private EntrantInfoAdminDao entrantInfoAdminDao;

    public EntrantInfoAdminServiceImpl(TransactionManager txManager, EntrantInfoAdminDao entrantInfoAdminDao) {
        this.txManager = txManager;
        this.entrantInfoAdminDao = entrantInfoAdminDao;
    }

    @Override
    public List<EntrantInfoAdminBean> getEntrantsForAdmin() throws DaoSystemException {
        return txManager.doInTransaction(new SqlCallable<List<EntrantInfoAdminBean>>() {
            @Override
            public List<EntrantInfoAdminBean> call(Connection connection) throws SQLException, CrudException {
                return entrantInfoAdminDao.selectAll(connection);
            }
        });
    }
}
