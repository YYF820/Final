package ua.nure.hanzha.SummaryTask4.db.dao.faculty;

import org.apache.log4j.Logger;
import ua.nure.hanzha.SummaryTask4.constants.ExceptionMessages;
import ua.nure.hanzha.SummaryTask4.constants.FieldsDataBase;
import ua.nure.hanzha.SummaryTask4.db.dao.AbstractDao;
import ua.nure.hanzha.SummaryTask4.db.util.SqlQueriesUtilities;
import ua.nure.hanzha.SummaryTask4.entity.Faculty;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;
import ua.nure.hanzha.SummaryTask4.util.ClassNameUtilities;

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

    private static final Logger LOGGER = Logger.getLogger(ClassNameUtilities.getCurrentClassName());

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
        faculty.setId(resultSet.getInt(FieldsDataBase.ENTITY_ID));
        faculty.setName(resultSet.getString(FieldsDataBase.FACULTY_NAME));
        faculty.setTotalSpots(resultSet.getInt(FieldsDataBase.FACULTY_TOTAL_SPOTS));
        faculty.setBudgetSpots(resultSet.getInt(FieldsDataBase.FACULTY_BUDGET_SPOTS));
        return faculty;
    }

    @Override
    public void deleteById(int id, Connection connection) throws SQLException, CrudException {
        deleteById(
                id,
                SqlQueriesUtilities.getSqlQuery("faculty.delete.by.id"),
                connection
        );
    }

    @Override
    public Faculty selectById(int id, Connection connection) throws SQLException, CrudException {
        return selectById(
                id,
                SqlQueriesUtilities.getSqlQuery("faculty.select.by.id"),
                connection
        );
    }

    @Override
    public int selectIdByName(String name, Connection connection) throws SQLException, CrudException {
        String sql = SqlQueriesUtilities.getSqlQuery("faculty.select.id.by.name");
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, name);
            LOGGER.trace("selectIdByName['{ " + ps.toString() + " }']");
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(FieldsDataBase.ENTITY_ID);
            } else {
                LOGGER.warn("Throw SELECT Exception, while selecting id by name : " + name + " sql :" + sql);
                throw new CrudException(ExceptionMessages.SELECT_BY_SOME_VALUE_EXCEPTION_MESSAGE);
            }
        }
    }

    @Override
    public List<Faculty> selectAllSubjectsMoreThanThree(Connection connection) throws SQLException, CrudException {
        String sql = SqlQueriesUtilities.getSqlQuery("faculty.function.select.only.with.subjects.more.than.three");
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            List<Faculty> result = executeQuery(ps);
            LOGGER.trace("selectAllSubjectsMoreThanThree['{ " + ps.toString() + " }']");
            if (result.size() > 0) {
                return result;
            } else {
                LOGGER.warn("Throw SELECT Exception, while selecting List<Faculty>, sql :" + sql);
                throw new CrudException(ExceptionMessages.SELECT_EXCEPTION_MESSAGE);
            }
        }
    }

    @Override
    public void insert(Faculty entity, Connection connection) throws SQLException, CrudException {
        insert(
                entity,
                SqlQueriesUtilities.getSqlQuery("faculty.insert"),
                connection
        );
    }

    @Override
    public void update(Faculty entity, Connection connection) throws SQLException, CrudException {
        update(
                entity,
                SqlQueriesUtilities.getSqlQuery("faculty.update"),
                connection
        );
    }

    @Override
    public List<Faculty> selectAll(Connection connection) throws SQLException, CrudException {
        return selectAll(
                SqlQueriesUtilities.getSqlQuery("faculty.select.all"),
                connection
        );
    }
}
