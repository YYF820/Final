package ua.nure.hanzha.SummaryTask4.service.facultyAdmin;

import ua.nure.hanzha.SummaryTask4.db.dao.faculty.FacultyDao;
import ua.nure.hanzha.SummaryTask4.db.dao.facultysubject.FacultySubjectDao;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.SqlCallable;
import ua.nure.hanzha.SummaryTask4.db.transactionmanager.TransactionManager;
import ua.nure.hanzha.SummaryTask4.entity.Faculty;
import ua.nure.hanzha.SummaryTask4.entity.FacultySubject;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 09/08/15.
 */
public class FacultyAdminServiceImpl implements FacultyAdminService {

    private TransactionManager txManager;
    private FacultyDao facultyDao;
    private FacultySubjectDao facultySubjectDao;

    public FacultyAdminServiceImpl(TransactionManager txManager, FacultyDao facultyDao, FacultySubjectDao facultySubjectDao) {
        this.txManager = txManager;
        this.facultyDao = facultyDao;
        this.facultySubjectDao = facultySubjectDao;
    }

    @Override
    public void editFacultyInfoWithSubjects(final Faculty faculty, final Integer[] subjectsIdToAdd, final Integer[] subjectsIdToDelete) throws DaoSystemException {
        txManager.doInTransaction(new SqlCallable<Void>() {
            @Override
            public Void call(Connection connection) throws SQLException, CrudException {
                facultyDao.update(faculty, connection);
                FacultySubject facultySubject = new FacultySubject();
                int facultyId = faculty.getId();
                for (Integer subjectIdToAdd : subjectsIdToAdd) {
                    facultySubject.setFacultyId(facultyId);
                    facultySubject.setSubjectId(subjectIdToAdd);
                    facultySubjectDao.insert(facultySubject, connection);
                }
                for (Integer subjectIdToAdd : subjectsIdToDelete) {
                    facultySubjectDao.deleteByFacultyIdSubjectId(facultyId, subjectIdToAdd, connection);
                }
                return null;
            }
        });
    }

    @Override
    public void editFacultyInfoWithoutDelete(final Faculty faculty, final Integer[] subjectsIdToAdd) throws DaoSystemException {
        txManager.doInTransaction(new SqlCallable<Void>() {
            @Override
            public Void call(Connection connection) throws SQLException, CrudException {
                facultyDao.update(faculty, connection);
                FacultySubject facultySubject = new FacultySubject();
                int facultyId = faculty.getId();
                for (Integer subjectIdToAdd : subjectsIdToAdd) {
                    facultySubject.setFacultyId(facultyId);
                    facultySubject.setSubjectId(subjectIdToAdd);
                    facultySubjectDao.insert(facultySubject, connection);
                }
                return null;
            }
        });
    }

    @Override
    public void editFacultyInfoWithoutAdd(final Faculty faculty, final Integer[] subjectsIdToDelete) throws DaoSystemException {
        txManager.doInTransaction(new SqlCallable<Void>() {
            @Override
            public Void call(Connection connection) throws SQLException, CrudException {
                facultyDao.update(faculty, connection);
                int facultyId = faculty.getId();
                for (Integer subjectIdToAdd : subjectsIdToDelete) {
                    facultySubjectDao.deleteByFacultyIdSubjectId(facultyId, subjectIdToAdd, connection);
                }
                return null;
            }
        });
    }

    @Override
    public void editFacultyInfoWithoutSubjects(final Faculty faculty) throws DaoSystemException {
        txManager.doInTransaction(new SqlCallable<Void>() {
            @Override
            public Void call(Connection connection) throws SQLException, CrudException {
                System.out.println(faculty);
                facultyDao.update(faculty, connection);
                return null;
            }
        });
    }

    @Override
    public void addFaculty(final Faculty faculty, final Integer[] subjectsIdToAdd) throws DaoSystemException {
        txManager.doInTransaction(new SqlCallable<Void>() {
            @Override
            public Void call(Connection connection) throws SQLException, CrudException {
                facultyDao.insert(faculty, connection);
                String facultyName = faculty.getName();
                int facultyId = facultyDao.selectIdByName(facultyName, connection);
                FacultySubject facultySubject = new FacultySubject();
                for (Integer subjectIdToAdd : subjectsIdToAdd) {
                    facultySubject.setFacultyId(facultyId);
                    facultySubject.setSubjectId(subjectIdToAdd);
                    facultySubjectDao.insert(facultySubject, connection);
                }
                return null;
            }
        });
    }
}
