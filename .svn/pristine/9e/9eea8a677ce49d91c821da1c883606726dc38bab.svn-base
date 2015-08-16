package ua.nure.hanzha.SummaryTask4.db.dao.entrant;

import ua.nure.hanzha.SummaryTask4.constants.ExceptionMessages;
import ua.nure.hanzha.SummaryTask4.constants.FieldsDataBase;
import ua.nure.hanzha.SummaryTask4.db.dao.AbstractDao;
import ua.nure.hanzha.SummaryTask4.db.util.SqlQueriesHolder;
import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;

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
                SqlQueriesHolder.getSqlQuery("entrant.delete.by.id"),
                connection
        );
    }

    @Override
    public Entrant selectById(int id, Connection connection) throws SQLException, CrudException {
        return selectById(
                id,
                SqlQueriesHolder.getSqlQuery("entrant.select.by.id"),
                connection
        );
    }

    @Override
    public int selectStatusByUserId(int userId, Connection connection) throws SQLException {
        int entrantStatusId;
        try (PreparedStatement ps = connection.prepareStatement(SqlQueriesHolder.getSqlQuery("entrant.select.by.user.id"))) {
            ps.setInt(1, userId);
            try (ResultSet resultSet = ps.executeQuery()) {
                resultSet.next();
                entrantStatusId = resultSet.getInt(FieldsDataBase.ENTRANT_STATUS);
            }
        }
        return entrantStatusId;
    }

    @Override
    public Entrant selectByUserId(int userId, Connection connection) throws SQLException, CrudException {
        return selectById(
                userId,
                SqlQueriesHolder.getSqlQuery("entrant.select.by.user.id"),
                connection
        );
    }


    @Override
    public void insert(Entrant entity, Connection connection) throws SQLException, CrudException {
        insert(
                entity,
                SqlQueriesHolder.getSqlQuery("entrant.insert"),
                connection
        );
    }

    @Override
    public void update(Entrant entity, Connection connection) throws SQLException, CrudException {
        update(
                entity,
                SqlQueriesHolder.getSqlQuery("entrant.update"),
                connection
        );
    }

    @Override
    public void updateEntrantStatus(int statusId, int entrantId, Connection connection) throws SQLException, CrudException {
        try (PreparedStatement ps = connection.prepareStatement(SqlQueriesHolder.getSqlQuery("entrant.update.status"))) {
            ps.setInt(1, statusId);
            ps.setInt(2, entrantId);
            if (ps.executeUpdate() == 0) {
                throw new CrudException(ExceptionMessages.UPDATE_EXCEPTION_MESSAGE);
            }
        }
    }

    @Override
    public List<Integer> selectAllEntrantsId(Connection connection) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(SqlQueriesHolder.getSqlQuery("entrant.select.all.id"))) {
            try (ResultSet resultSet = ps.executeQuery()) {
                List<Integer> result = new ArrayList<>();
                while (resultSet.next()) {
                    result.add(resultSet.getInt(FieldsDataBase.ENTITY_ID));
                }
                return result;
            }
        }
    }

    @Override
    public Integer selectUserIdByEntrantId(int entrantId, Connection connection) throws SQLException, CrudException {
        try (PreparedStatement ps = connection.prepareStatement(SqlQueriesHolder.getSqlQuery("entrant.select.user.id.by.id"))) {
            ps.setInt(1, entrantId);
            Integer userId = null;
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    userId = resultSet.getInt(FieldsDataBase.ENTRANT_USER_ID);
                }
            }
            if (userId != null) {
                return userId;
            } else {
                throw new CrudException(ExceptionMessages.SELECT_BY_ID_EXCEPTION_MESSAGE);
            }
        }
    }

    @Override
    public List<Entrant> selectAll(Connection connection) throws SQLException, CrudException {
        return selectAll(
                SqlQueriesHolder.getSqlQuery("entrant.select.all"),
                connection
        );
    }
}
