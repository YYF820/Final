package ua.nure.hanzha.SummaryTask4.db.dao.faculty;

import ua.nure.hanzha.SummaryTask4.constants.Fields;
import ua.nure.hanzha.SummaryTask4.db.dao.AbstractDao;
import ua.nure.hanzha.SummaryTask4.db.util.SqlQueriesHolder;
import ua.nure.hanzha.SummaryTask4.entity.Faculty;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * FacultyDaoImpl extends AbstractDao and implements FacultyDao.In FacultyDaoImpl I am using realisation from AbstractDao
 * and for methods which in FacultyDao and doesn't have realisation in AbstractDao I solved here.
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public class FacultyDaoImpl extends AbstractDao<Faculty> implements FacultyDao {
    @Override
    protected void prepareForInsert(Faculty entity, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setString(k++, entity.getName());
        preparedStatement.setInt(k++, entity.getTotalSpots());
        preparedStatement.setInt(k, entity.getBudgetSpots());
    }

    @Override
    protected void prepareForUpdate(Faculty entity, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setString(k++, entity.getName());
        preparedStatement.setInt(k++, entity.getTotalSpots());
        preparedStatement.setInt(k++, entity.getBudgetSpots());
        preparedStatement.setInt(k, entity.getId());
    }

    @Override
    protected Faculty extractInfo(ResultSet resultSet) throws SQLException {
        Faculty faculty = new Faculty();
        faculty.setId(resultSet.getInt(Fields.ENTITY_ID));
        faculty.setName(resultSet.getString(Fields.FACULTY_NAME));
        faculty.setTotalSpots(resultSet.getInt(Fields.FACULTY_TOTAL_SPOTS));
        faculty.setBudgetSpots(resultSet.getInt(Fields.FACULTY_BUDGET_SPOTS));
        return faculty;
    }

    @Override
    public void deleteById(int id, Connection connection) throws SQLException, CrudException {
        deleteById(
                id,
                SqlQueriesHolder.getSqlQuery("faculty.delete.by.id"),
                connection
        );
    }

    @Override
    public Faculty selectById(int id, Connection connection) throws SQLException, CrudException {
        return selectById(
                id,
                SqlQueriesHolder.getSqlQuery("faculty.select.by.id"),
                connection
        );
    }

    @Override
    public void insert(Faculty entity, Connection connection) throws SQLException, CrudException {
        insert(
                entity,
                SqlQueriesHolder.getSqlQuery("faculty.insert"),
                connection
        );
    }

    @Override
    public void update(Faculty entity, Connection connection) throws SQLException, CrudException {
        update(
                entity,
                SqlQueriesHolder.getSqlQuery("faculty.update"),
                connection
        );
    }

    @Override
    public List<Faculty> selectAll(Connection connection) throws SQLException, CrudException {
        return selectAll(
                SqlQueriesHolder.getSqlQuery("faculty.select.all"),
                connection
        );
    }
}
