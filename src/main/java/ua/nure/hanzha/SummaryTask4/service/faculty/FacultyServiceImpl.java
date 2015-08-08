package ua.nure.hanzha.SummaryTask4.service.faculty;

import ua.nure.hanzha.SummaryTask4.db.dao.faculty.FacultyDao;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.SqlCallable;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.TransactionManager;
import ua.nure.hanzha.SummaryTask4.entity.Faculty;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by faffi-ubuntu on 08/08/15.
 */
public class FacultyServiceImpl implements FacultyService {

    private TransactionManager txManager;
    private FacultyDao facultyDao;

    public FacultyServiceImpl(TransactionManager txManager, FacultyDao facultyDao) {
        this.txManager = txManager;
        this.facultyDao = facultyDao;
    }

    @Override
    public List<Faculty> getAllFaculties() throws DaoSystemException {
        return txManager.doInTransaction(new SqlCallable<List<Faculty>>() {
            @Override
            public List<Faculty> call(Connection connection) throws SQLException, CrudException {
                return facultyDao.selectAll(connection);
            }
        });
    }
}
