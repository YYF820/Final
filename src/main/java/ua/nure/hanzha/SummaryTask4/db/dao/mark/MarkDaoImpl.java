package ua.nure.hanzha.SummaryTask4.db.dao.mark;

import org.apache.log4j.Logger;
import ua.nure.hanzha.SummaryTask4.constants.ExceptionMessages;
import ua.nure.hanzha.SummaryTask4.constants.FieldsDataBase;
import ua.nure.hanzha.SummaryTask4.db.dao.AbstractDao;
import ua.nure.hanzha.SummaryTask4.db.util.SqlQueriesUtilities;
import ua.nure.hanzha.SummaryTask4.entity.Mark;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;
import ua.nure.hanzha.SummaryTask4.util.ClassNameUtilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * MarkDaoImpl extends AbstractDao and implements MarkDao.In MarkDaoImpl I am using realisation from AbstractDao
 * and for methods which in MarkDao and doesn't have realisation in AbstractDao I solved here(selectByMarkValue).
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public class MarkDaoImpl extends AbstractDao<Mark> implements MarkDao {

    private static final Logger LOGGER = Logger.getLogger(ClassNameUtilities.getCurrentClassName());

    @Override
    protected void prepareForInsert(Mark entity, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setInt(k++, entity.getSubjectId());
        preparedStatement.setInt(k++, entity.getEntrantId());
        preparedStatement.setDouble(k, entity.getMarkValue());
    }

    @Override
    protected void prepareForUpdate(Mark entity, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setDouble(k++, entity.getMarkValue());
        preparedStatement.setInt(k++, entity.getSubjectId());
        preparedStatement.setInt(k, entity.getEntrantId());
    }

    @Override
    protected Mark extractInfo(ResultSet resultSet) throws SQLException {
        Mark mark = new Mark();
        mark.setSubjectId(resultSet.getInt(FieldsDataBase.MARK_SUBJECT_ID));
        mark.setEntrantId(resultSet.getInt(FieldsDataBase.MARK_ENTRANT_ID));
        mark.setMarkValue(resultSet.getDouble(FieldsDataBase.MARK_VALUE));
        return mark;
    }

    @Override
    public void deleteBySubjectId(int subjectId, Connection connection) throws SQLException, CrudException {
        deleteById(
                subjectId,
                SqlQueriesUtilities.getSqlQuery("mark.delete.by.subject.id"),
                connection
        );
    }

    @Override
    public void deleteByEntrantId(int entrantId, Connection connection) throws SQLException, CrudException {
        deleteById(
                entrantId,
                SqlQueriesUtilities.getSqlQuery("mark.delete.by.entrant.id"),
                connection
        );
    }

    @Override
    public void deleteBySubjectIdEntrantId(int subjectId, int entrantId, Connection connection) throws SQLException, CrudException {
        deleteByDoubleId(
                subjectId,
                entrantId,
                SqlQueriesUtilities.getSqlQuery("mark.delete.by.subject.id.and.entrant.id"),
                connection
        );
    }

    @Override
    public List<Mark> selectBySubjectId(int subjectId, Connection connection) throws SQLException, CrudException {
        return selectByIdMultiRows(
                subjectId,
                SqlQueriesUtilities.getSqlQuery("mark.select.by.subject.id"),
                connection
        );
    }

    @Override
    public List<Mark> selectByEntrantId(int entrantId, Connection connection) throws SQLException, CrudException {
        return selectByIdMultiRows(
                entrantId,
                SqlQueriesUtilities.getSqlQuery("mark.select.by.entrant.id"),
                connection
        );
    }

    @Override
    public Mark selectBySubjectIdEntrantId(int subjectId, int entrantId, Connection connection) throws SQLException, CrudException {
        return selectByDoubleId(
                subjectId,
                entrantId,
                SqlQueriesUtilities.getSqlQuery("mark.select.by.subject.id.and.entrant.id"),
                connection
        );
    }

    @Override
    public List<Mark> selectByMarkValue(double markValue, Connection connection) throws SQLException, CrudException {
        String sql = SqlQueriesUtilities.getSqlQuery("mark.select.by.value");
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, markValue);
            LOGGER.trace("selectByMarkValue['{ " + ps.toString() + " }']");
            List<Mark> result = executeQuery(ps);
            if (result.size() > 0) {
                return result;
            } else {
                LOGGER.warn("Throw SELECT Exception, while selecting List<Mark> by markValue : "
                        + markValue + " sql :" + sql);
                throw new CrudException(ExceptionMessages.SELECT_BY_ID_EXCEPTION_MESSAGE);
            }
        }
    }


    @Override
    public void insert(Mark entity, Connection connection) throws SQLException, CrudException {
        insert(
                entity,
                SqlQueriesUtilities.getSqlQuery("mark.insert"),
                connection
        );
    }

    @Override
    public void update(Mark entity, Connection connection) throws SQLException, CrudException {
        update(
                entity,
                SqlQueriesUtilities.getSqlQuery("mark.update"),
                connection
        );
    }

    @Override
    public List<Mark> selectAll(Connection connection) throws SQLException, CrudException {
        return selectAll(
                SqlQueriesUtilities.getSqlQuery("mark.select.all"),
                connection
        );
    }
}
