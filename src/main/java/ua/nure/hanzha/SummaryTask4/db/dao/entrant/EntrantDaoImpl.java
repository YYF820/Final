package ua.nure.hanzha.SummaryTask4.db.dao.entrant;

import org.apache.log4j.Logger;
import ua.nure.hanzha.SummaryTask4.constants.ExceptionMessages;
import ua.nure.hanzha.SummaryTask4.constants.FieldsDataBase;
import ua.nure.hanzha.SummaryTask4.db.dao.AbstractDao;
import ua.nure.hanzha.SummaryTask4.db.util.SqlQueriesUtilities;
import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;
import ua.nure.hanzha.SummaryTask4.util.ClassNameUtilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * EntrantDaoImpl extends AbstractDao and implements EntrantDao, in EntrantDaoImpl I am using realisation from AbstractDao
 * and for methods which in UserDao and doesn't have realisation in AbstractDao I solved here.
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public class EntrantDaoImpl extends AbstractDao<Entrant> implements EntrantDao {

    private static final Logger LOGGER = Logger.getLogger(ClassNameUtilities.getCurrentClassName());

    @Override
    protected void prepareForInsert(Entrant entity, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setString(k++, entity.getCity());
        preparedStatement.setString(k++, entity.getRegion());
        preparedStatement.setInt(k++, entity.getSchool());
        preparedStatement.setInt(k, entity.getUserId());
    }

    @Override
    protected void prepareForUpdate(Entrant entity, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setString(k++, entity.getCity());
        preparedStatement.setString(k++, entity.getRegion());
        preparedStatement.setInt(k++, entity.getSchool());
        preparedStatement.setInt(k++, entity.getEntrantStatus());
        preparedStatement.setInt(k++, entity.getUserId());
        preparedStatement.setInt(k, entity.getId());
    }

    @Override
    protected Entrant extractInfo(ResultSet resultSet) throws SQLException {
        Entrant entrant = new Entrant();
        entrant.setId(resultSet.getInt(FieldsDataBase.ENTITY_ID));
        entrant.setCity(resultSet.getString(FieldsDataBase.ENTRANT_CITY));
        entrant.setRegion(resultSet.getString(FieldsDataBase.ENTRANT_REGION));
        entrant.setSchool(resultSet.getInt(FieldsDataBase.ENTRANT_SCHOOL));
        entrant.setEntrantStatus(resultSet.getInt(FieldsDataBase.ENTRANT_STATUS));
        entrant.setUserId(resultSet.getInt(FieldsDataBase.ENTRANT_USER_ID));
        return entrant;
    }

    @Override
    public void deleteById(int id, Connection connection) throws SQLException, CrudException {
        deleteById(
                id,
                SqlQueriesUtilities.getSqlQuery("entrant.delete.by.id"),
                connection
        );
    }

    @Override
    public Entrant selectById(int id, Connection connection) throws SQLException, CrudException {
        return selectById(
                id,
                SqlQueriesUtilities.getSqlQuery("entrant.select.by.id"),
                connection
        );
    }

    @Override
    public int selectStatusByUserId(int userId, Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SqlQueriesUtilities.getSqlQuery("entrant.select.by.user.id"))) {
            ps.setInt(1, userId);
            LOGGER.trace("selectStatusByUserId['{ " + ps.toString() + " }']");
            try (ResultSet resultSet = ps.executeQuery()) {
                resultSet.next();
                return resultSet.getInt(FieldsDataBase.ENTRANT_STATUS);
            }
        }
    }

    @Override
    public Entrant selectByUserId(int userId, Connection connection) throws SQLException, CrudException {
        return selectById(
                userId,
                SqlQueriesUtilities.getSqlQuery("entrant.select.by.user.id"),
                connection
        );
    }


    @Override
    public void insert(Entrant entity, Connection connection) throws SQLException, CrudException {
        insert(
                entity,
                SqlQueriesUtilities.getSqlQuery("entrant.insert"),
                connection
        );
    }

    @Override
    public void update(Entrant entity, Connection connection) throws SQLException, CrudException {
        update(
                entity,
                SqlQueriesUtilities.getSqlQuery("entrant.update"),
                connection
        );
    }

    @Override
    public void updateEntrantStatus(int statusId, int entrantId, Connection connection) throws SQLException, CrudException {
        String sql = SqlQueriesUtilities.getSqlQuery("entrant.update.status");
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, statusId);
            ps.setInt(2, entrantId);
            LOGGER.trace("selectStatusByUserId['{ " + ps.toString() + " }']");
            if (ps.executeUpdate() == 0) {
                LOGGER.warn("Throw UPDATE Exception, while update by entrantId : "
                        + entrantId + " with  statusId : " + statusId + " sql :" + sql);
                throw new CrudException(ExceptionMessages.UPDATE_EXCEPTION_MESSAGE);
            }
        }
    }

    @Override
    public List<Integer> selectAllEntrantsIdStatusActive(Connection connection) throws SQLException, CrudException {
        String sql = SqlQueriesUtilities.getSqlQuery("entrant.select.all.id.status.active");
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            LOGGER.trace("selectAllEntrantsIdStatusActive['{ " + ps.toString() + " }']");
            try (ResultSet resultSet = ps.executeQuery()) {
                List<Integer> result = new ArrayList<>();
                while (resultSet.next()) {
                    result.add(resultSet.getInt(FieldsDataBase.ENTITY_ID));
                }
                if (result.size() > 0) {
                    return result;
                } else {
                    LOGGER.warn("Throw SELECT Exception,  sql :" + sql);
                    throw new CrudException(ExceptionMessages.SELECT_BY_SOME_VALUE_EXCEPTION_MESSAGE);
                }
            }
        }
    }

    @Override
    public Integer selectUserIdByEntrantId(int entrantId, Connection connection) throws SQLException, CrudException {
        String sql = SqlQueriesUtilities.getSqlQuery("entrant.select.user.id.by.id");
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, entrantId);
            LOGGER.trace("selectAllEntrantsIdStatusActive['{ " + ps.toString() + " }']");
            Integer userId = null;
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    userId = resultSet.getInt(FieldsDataBase.ENTRANT_USER_ID);
                }
            }
            if (userId != null) {
                return userId;
            } else {
                LOGGER.warn("Throw SELECT Exception, entrantId : " + entrantId + "  sql : " + sql);
                throw new CrudException(ExceptionMessages.SELECT_BY_ID_EXCEPTION_MESSAGE);
            }
        }
    }

    @Override
    public List<Entrant> selectAll(Connection connection) throws SQLException, CrudException {
        return selectAll(
                SqlQueriesUtilities.getSqlQuery("entrant.select.all"),
                connection
        );
    }
}
