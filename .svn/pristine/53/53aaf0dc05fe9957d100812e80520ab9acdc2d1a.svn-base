package ua.nure.hanzha.SummaryTask4.service.facultyEntrant;

import ua.nure.hanzha.SummaryTask4.bean.EntrantFinalSheetBean;
import ua.nure.hanzha.SummaryTask4.db.dao.facultyentrant.FacultyEntrantDao;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.SqlCallable;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.TransactionManager;
import ua.nure.hanzha.SummaryTask4.entity.FacultyEntrant;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 11/08/15.
 */
public class FacultyEntrantServiceImpl implements FacultyEntrantService {

    private TransactionManager txManager;
    private FacultyEntrantDao facultyEntrantDao;

    public FacultyEntrantServiceImpl(TransactionManager txManager, FacultyEntrantDao facultyEntrantDao) {
        this.txManager = txManager;
        this.facultyEntrantDao = facultyEntrantDao;
    }

    @Override
    public Map<Integer, Integer> getFacultyIdPriorityByEntrantId(final int entrantId) throws DaoSystemException {
        return txManager.doInTransaction(new SqlCallable<Map<Integer, Integer>>() {
            @Override
            public Map<Integer, Integer> call(Connection connection) throws SQLException, CrudException {
                return facultyEntrantDao.selectAllFacultyIdPriorityByEntrantId(entrantId, connection);
            }
        });
    }

    @Override
    public void removeFacultyByFacultyIdEntrantId(final int facultyId, final int entrantId) throws DaoSystemException {
        txManager.doInTransaction(new SqlCallable<Void>() {
            @Override
            public Void call(Connection connection) throws SQLException, CrudException {
                facultyEntrantDao.deleteByFacultyIdEntrantId(facultyId, entrantId, connection);
                return null;
            }
        });
    }

    @Override
    public void addFaculty(final FacultyEntrant facultyEntrant) throws DaoSystemException {
        txManager.doInTransaction(new SqlCallable<Void>() {
            @Override
            public Void call(Connection connection) throws SQLException, CrudException {
                facultyEntrantDao.insert(facultyEntrant, connection);
                return null;
            }
        });
    }

    @Override
    public void updatePriority(final int priority, final int facultyId, final int entrantId) throws DaoSystemException {
        txManager.doInTransaction(new SqlCallable<Void>() {
            @Override
            public Void call(Connection connection) throws SQLException, CrudException {
                facultyEntrantDao.updatePriorityByFacultyIdEntrantId(priority, facultyId, entrantId, connection);
                return null;
            }
        });
    }

    @Override
    public void summAllMarks() throws DaoSystemException {
        txManager.doInTransaction(new SqlCallable<Void>() {
            @Override
            public Void call(Connection connection) throws SQLException, CrudException {
                facultyEntrantDao.sumAllMarks(connection);
                return null;
            }
        });
    }

    @Override
    public EntrantFinalSheetBean getEntrantBeanByEntrantId(final int entrantId) throws DaoSystemException {
        return txManager.doInTransaction(new SqlCallable<EntrantFinalSheetBean>() {
            @Override
            public EntrantFinalSheetBean call(Connection connection) throws SQLException, CrudException {
                return facultyEntrantDao.selectEntrantBeanByEntrantId(entrantId, connection);
            }
        });
    }

}
