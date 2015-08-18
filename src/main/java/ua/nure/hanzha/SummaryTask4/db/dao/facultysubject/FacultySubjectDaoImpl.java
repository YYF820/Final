package ua.nure.hanzha.SummaryTask4.db.dao.facultysubject;

import ua.nure.hanzha.SummaryTask4.constants.FieldsDataBase;
import ua.nure.hanzha.SummaryTask4.db.dao.AbstractDao;
import ua.nure.hanzha.SummaryTask4.db.util.SqlQueriesUtilities;
import ua.nure.hanzha.SummaryTask4.entity.FacultySubject;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * FacultySubjectDaoImpl extends AbstractDao and implements FacultySubjectDao.In FacultySubjectDaoImpl
 * I am using realisation from AbstractDao and for methods which in FacultySubjectDao and doesn't have realisation
 * in AbstractDao I solved here.
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public class FacultySubjectDaoImpl extends AbstractDao<FacultySubject> implements FacultySubjectDao {

    @Override
    protected void prepareForInsert(FacultySubject entity, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setInt(k++, entity.getFacultyId());
        preparedStatement.setInt(k, entity.getSubjectId());
    }

    @Override
    protected void prepareForUpdate(FacultySubject entity, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setInt(k++, entity.getFacultyId());
        preparedStatement.setInt(k, entity.getSubjectId());
    }

    @Override
    protected FacultySubject extractInfo(ResultSet resultSet) throws SQLException {
        FacultySubject facultySubject = new FacultySubject();
        facultySubject.setFacultyId(resultSet.getInt(FieldsDataBase.FACULTY_SUBJECT_FACULTY_ID));
        facultySubject.setSubjectId(resultSet.getInt(FieldsDataBase.FACULTY_SUBJECT_SUBJECT_ID));
        return facultySubject;
    }

    @Override
    public void deleteByFacultyId(int facultyId, Connection connection) throws SQLException, CrudException {
        deleteById(
                facultyId,
                SqlQueriesUtilities.getSqlQuery("faculty_subject.delete.by.faculty.id"),
                connection
        );
    }

    @Override
    public void deleteBySubjectId(int subjectId, Connection connection) throws SQLException, CrudException {
        deleteById(
                subjectId,
                SqlQueriesUtilities.getSqlQuery("faculty_subject.delete.by.subject.id"),
                connection
        );
    }

    @Override
    public void deleteByFacultyIdSubjectId(int facultyId, int subjectId, Connection connection) throws SQLException, CrudException {
        deleteByDoubleId(
                facultyId,
                subjectId,
                SqlQueriesUtilities.getSqlQuery("faculty_subject.delete.by.faculty.id.and.subject.id"),
                connection
        );
    }

    @Override
    public List<FacultySubject> selectByFacultyId(int facultyId, Connection connection) throws SQLException, CrudException {
        return selectByIdMultiRows(
                facultyId,
                SqlQueriesUtilities.getSqlQuery("faculty_subject.select.by.faculty.id"),
                connection
        );
    }

    @Override
    public List<FacultySubject> selectBySubjectId(int subjectId, Connection connection) throws SQLException, CrudException {
        return selectByIdMultiRows(
                subjectId,
                SqlQueriesUtilities.getSqlQuery("faculty_subject.select.by.subject.id"),
                connection
        );
    }

    @Override
    public FacultySubject selectByFacultyIdSubjectId(int facultyId, int subjectId, Connection connection) throws SQLException, CrudException {
        return selectByDoubleId(
                facultyId,
                subjectId,
                SqlQueriesUtilities.getSqlQuery("faculty_subject.select.by.faculty.id.and.subject.id"),
                connection
        );
    }

    @Override
    public void insert(FacultySubject entity, Connection connection) throws SQLException, CrudException {
        insert(
                entity,
                SqlQueriesUtilities.getSqlQuery("faculty_subject.insert"),
                connection
        );
    }

    @Override
    public void update(FacultySubject entity, Connection connection) throws SQLException, CrudException {
        throw new UnsupportedOperationException("No realisation for FacultySubjectDaoIml#update");
    }

    @Override
    public List<FacultySubject> selectAll(Connection connection) throws SQLException, CrudException {
        return selectAll(
                SqlQueriesUtilities.getSqlQuery("faculty_subject.select.all"),
                connection
        );
    }
}
