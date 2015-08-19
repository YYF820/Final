package ua.nure.hanzha.SummaryTask4.service.facultySubject;

import ua.nure.hanzha.SummaryTask4.db.dao.facultysubject.FacultySubjectDao;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.SqlCallable;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.TransactionManager;
import ua.nure.hanzha.SummaryTask4.entity.FacultySubject;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 19/08/15.
 */
public class FacultySubjectServiceImpl implements FacultySubjectService {

    private TransactionManager txManager;
    private FacultySubjectDao facultySubjectDao;

    public FacultySubjectServiceImpl(TransactionManager txManager, FacultySubjectDao facultySubjectDao) {
        this.txManager = txManager;
        this.facultySubjectDao = facultySubjectDao;
    }

    @Override
    public List<FacultySubject> getAllByFacultyId(final int facultyId) throws DaoSystemException {
        return txManager.doInTransaction(new SqlCallable<List<FacultySubject>>() {
            @Override
            public List<FacultySubject> call(Connection connection) throws SQLException, CrudException {
                return facultySubjectDao.selectByFacultyId(facultyId, connection);
            }
        });
    }
}
