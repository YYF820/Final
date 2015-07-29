package ua.nure.hanzha.SummaryTask4.db.dao.subject;

import ua.nure.hanzha.SummaryTask4.constants.FieldsDataBase;
import ua.nure.hanzha.SummaryTask4.db.dao.AbstractDao;
import ua.nure.hanzha.SummaryTask4.db.util.SqlQueriesHolder;
import ua.nure.hanzha.SummaryTask4.entity.Subject;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * SubjectDaoImpl extends AbstractDao and implements SubjectDao.In SubjectDaoImpl I am using realisation
 * from AbstractDao and for methods which in SubjectDao and doesn't have realisation in AbstractDao I solved here.
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public class SubjectDaoImpl extends AbstractDao<Subject> implements SubjectDao {
    @Override
    protected void prepareForInsert(Subject entity, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setString(k, entity.getName());
    }

    @Override
    protected void prepareForUpdate(Subject entity, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setInt(k++, entity.getId());
        preparedStatement.setString(k, entity.getName());
    }

    @Override
    protected Subject extractInfo(ResultSet resultSet) throws SQLException {
        Subject subject = new Subject();
        subject.setId(resultSet.getInt(FieldsDataBase.ENTITY_ID));
        subject.setName(resultSet.getString(FieldsDataBase.SUBJECT_NAME));
        return subject;
    }

    @Override
    public void deleteById(int id, Connection connection) throws SQLException, CrudException {
        deleteById(
                id,
                SqlQueriesHolder.getSqlQuery("subject.delete.by.id"),
                connection
        );
    }

    @Override
    public Subject selectById(int id, Connection connection) throws SQLException, CrudException {
        return selectById(
                id,
                SqlQueriesHolder.getSqlQuery("subject.select.by.id"),
                connection
        );
    }

    @Override
    public void insert(Subject entity, Connection connection) throws SQLException, CrudException {
        insert(
                entity,
                SqlQueriesHolder.getSqlQuery("subject.insert"),
                connection
        );
    }

    @Override
    public void update(Subject entity, Connection connection) throws SQLException, CrudException {
        update(
                entity,
                SqlQueriesHolder.getSqlQuery("subject.update"),
                connection
        );
    }

    @Override
    public List<Subject> selectAll(Connection connection) throws SQLException, CrudException {
        return selectAll(
                SqlQueriesHolder.getSqlQuery("subject.select.all"),
                connection
        );
    }
}
