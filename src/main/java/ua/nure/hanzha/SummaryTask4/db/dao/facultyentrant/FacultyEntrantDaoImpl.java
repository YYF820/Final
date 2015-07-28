package ua.nure.hanzha.SummaryTask4.db.dao.facultyentrant;

import ua.nure.hanzha.SummaryTask4.constants.ExceptionMessages;
import ua.nure.hanzha.SummaryTask4.constants.Fields;
import ua.nure.hanzha.SummaryTask4.db.dao.AbstractDao;
import ua.nure.hanzha.SummaryTask4.db.util.SqlQueriesHolder;
import ua.nure.hanzha.SummaryTask4.entity.FacultyEntrant;
import ua.nure.hanzha.SummaryTask4.entity.Mark;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * FacultyEntrantDaoImpl extends AbstractDao and implements FacultyEntrantDao.In FacultyEntrantDaoImpl
 * I am using realisation from AbstractDao and for methods which in FacultyEntrantDao and doesn't have realisation
 * in AbstractDao I solved here (selectByPriority, selectBySumMarks, selectByPrioritySumMarks).
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public class FacultyEntrantDaoImpl extends AbstractDao<FacultyEntrant> implements FacultyEntrantDao {

    @Override
    protected void prepareForInsert(FacultyEntrant entity, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setInt(k++, entity.getFacultyId());
        preparedStatement.setInt(k++, entity.getEntrantId());
        preparedStatement.setInt(k++, entity.getPriority());
        preparedStatement.setDouble(k, entity.getSumMarks());
    }

    @Override
    protected void prepareForUpdate(FacultyEntrant entity, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setInt(k++, entity.getPriority());
        preparedStatement.setDouble(k++, entity.getSumMarks());
        preparedStatement.setInt(k++, entity.getFacultyId());
        preparedStatement.setInt(k, entity.getEntrantId());

    }

    @Override
    protected FacultyEntrant extractInfo(ResultSet resultSet) throws SQLException {
        FacultyEntrant facultyEntrant = new FacultyEntrant();
        facultyEntrant.setFacultyId(resultSet.getInt(Fields.FACULTY_ENTRANT_FACULTY_ID));
        facultyEntrant.setFacultyId(resultSet.getInt(Fields.FACULTY_ENTRANT_ENTRANT_ID));
        facultyEntrant.setPriority(resultSet.getInt(Fields.FACULTY_ENTRANT_PRIORITY));
        facultyEntrant.setSumMarks(resultSet.getDouble(Fields.FACULTY_ENTRANT_SUM_MARKS));
        return facultyEntrant;
    }

    @Override
    public void deleteByFacultyId(int facultyId, Connection connection) throws SQLException, CrudException {
        deleteById(
                facultyId,
                SqlQueriesHolder.getSqlQuery("faculty_entrant.delete.by.faculty.id"),
                connection
        );
    }

    @Override
    public void deleteByEntrantId(int entrantId, Connection connection) throws SQLException, CrudException {
        deleteById(
                entrantId,
                SqlQueriesHolder.getSqlQuery("faculty_entrant.delete.by.entrant.id"),
                connection
        );
    }

    @Override
    public void deleteByFacultyIdEntrantId(int facultyId, int entrantId, Connection connection) throws SQLException, CrudException {
        deleteByDoubleId(
                facultyId,
                entrantId,
                SqlQueriesHolder.getSqlQuery("faculty_entrant.delete.by.faculty.id.and.entrant.id"),
                connection
        );
    }

    @Override
    public List<FacultyEntrant> selectByFacultyId(int facultyId, Connection connection) throws SQLException, CrudException {
        return selectByIdMultiRows(
                facultyId,
                SqlQueriesHolder.getSqlQuery("faculty_entrant.select.by.faculty.id"),
                connection
        );
    }

    @Override
    public List<FacultyEntrant> selectBySubjectId(int entrantId, Connection connection) throws SQLException, CrudException {
        return selectByIdMultiRows(
                entrantId,
                SqlQueriesHolder.getSqlQuery("faculty_entrant.select.by.entrant.id"),
                connection
        );
    }

    @Override
    public FacultyEntrant selectByFacultyIdSubjectId(int facultyId, int entrantId, Connection connection) throws SQLException, CrudException {
        return selectByDoubleId(
                facultyId,
                entrantId,
                SqlQueriesHolder.getSqlQuery("faculty_entrant.select.by.faculty.id.and.entrant.id"),
                connection
        );
    }

    @Override
    public List<FacultyEntrant> selectByPriority(int priority, Connection connection) throws SQLException, CrudException {
        try (PreparedStatement ps = connection.prepareStatement(
                SqlQueriesHolder.getSqlQuery("faculty_entrant.select.by.priority"))) {
            ps.setInt(1, priority);
            List<FacultyEntrant> result = executeQuery(ps);
            if (result.size() > 0) {
                return result;
            } else {
                throw new CrudException(ExceptionMessages.SELECT_BY_SOME_VALUE_EXCEPTION_MESSAGE);
            }
        }
    }

    @Override
    public List<FacultyEntrant> selectBySumMarks(double sumMarks, Connection connection) throws SQLException, CrudException {
        try (PreparedStatement ps = connection.prepareStatement(
                SqlQueriesHolder.getSqlQuery("faculty_entrant.select.by.sum.marks"))) {
            ps.setDouble(1, sumMarks);
            List<FacultyEntrant> result = executeQuery(ps);
            if (result.size() > 0) {
                return result;
            } else {
                throw new CrudException(ExceptionMessages.SELECT_BY_SOME_VALUE_EXCEPTION_MESSAGE);
            }
        }
    }

    @Override
    public List<FacultyEntrant> selectByPrioritySumMarks(int priority, double sumMarks, Connection connection) throws SQLException, CrudException {
        try (PreparedStatement ps = connection.prepareStatement(
                SqlQueriesHolder.getSqlQuery("faculty_entrant.select.by.priority.and.sum.marks"))) {
            ps.setInt(1, priority);
            ps.setDouble(2, sumMarks);
            List<FacultyEntrant> result = executeQuery(ps);
            if (result.size() > 0) {
                return result;
            } else {
                throw new CrudException(ExceptionMessages.SELECT_BY_SOME_VALUE_EXCEPTION_MESSAGE);
            }
        }
    }

    @Override
    public void insert(FacultyEntrant entity, Connection connection) throws SQLException, CrudException {
        insert(
                entity,
                SqlQueriesHolder.getSqlQuery("faculty_entrant.insert"),
                connection
        );
    }

    @Override
    public void update(FacultyEntrant entity, Connection connection) throws SQLException, CrudException {
        update(
                entity,
                SqlQueriesHolder.getSqlQuery("faculty_entrant.update"),
                connection
        );

    }

    @Override
    public List<FacultyEntrant> selectAll(Connection connection) throws SQLException, CrudException {
        return selectAll(
                SqlQueriesHolder.getSqlQuery("faculty_entrant.select.all"),
                connection
        );
    }
}
