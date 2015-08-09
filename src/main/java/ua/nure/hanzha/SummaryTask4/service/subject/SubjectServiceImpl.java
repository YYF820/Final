package ua.nure.hanzha.SummaryTask4.service.subject;

import ua.nure.hanzha.SummaryTask4.db.dao.subject.SubjectDao;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.SqlCallable;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.TransactionManager;
import ua.nure.hanzha.SummaryTask4.entity.Subject;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 08/08/15.
 */
public class SubjectServiceImpl implements SubjectService {

    private TransactionManager txManager;
    private SubjectDao subjectDao;

    public SubjectServiceImpl(TransactionManager txManager, SubjectDao subjectDao) {
        this.txManager = txManager;
        this.subjectDao = subjectDao;
    }

    @Override
    public List<Subject> getAllByFacultyId(final int facultyId) throws DaoSystemException {
        return txManager.doInTransaction(new SqlCallable<List<Subject>>() {
            @Override
            public List<Subject> call(Connection connection) throws SQLException, CrudException {
                return subjectDao.selectAllByFacultyId(facultyId, connection);
            }
        });
    }

    @Override
    public List<Subject> getAll() throws DaoSystemException {
        return txManager.doInTransaction(new SqlCallable<List<Subject>>() {
            @Override
            public List<Subject> call(Connection connection) throws SQLException, CrudException {
                return subjectDao.selectAll(connection);
            }
        });
    }
}
