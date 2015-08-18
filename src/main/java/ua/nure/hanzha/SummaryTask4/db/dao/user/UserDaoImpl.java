package ua.nure.hanzha.SummaryTask4.db.dao.user;

import org.apache.log4j.Logger;
import ua.nure.hanzha.SummaryTask4.constants.ExceptionMessages;
import ua.nure.hanzha.SummaryTask4.constants.FieldsDataBase;
import ua.nure.hanzha.SummaryTask4.db.dao.AbstractDao;
import ua.nure.hanzha.SummaryTask4.db.util.SqlQueriesUtilities;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;
import ua.nure.hanzha.SummaryTask4.util.ClassNameUtilities;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * UserDaoImpl extends AbstractDao and implements UserDao, in UserDaoImpl I am using realisation from AbstractDao
 * and for methods which in UserDao and doesn't have realisation in AbstractDao I solved here.
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public class UserDaoImpl extends AbstractDao<User> implements UserDao {

    private static final Logger LOGGER = Logger.getLogger(ClassNameUtilities.getCurrentClassName());

    @Override
    protected void prepareForInsert(User entity, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setString(k++, entity.getPassword());
        preparedStatement.setString(k++, entity.getFirstName());
        preparedStatement.setString(k++, entity.getLastName());
        preparedStatement.setString(k++, entity.getPatronymic());
        preparedStatement.setString(k++, entity.getEmail());
        preparedStatement.setInt(k, entity.getRoleId());
    }

    @Override
    protected void prepareForUpdate(User entity, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setString(k++, entity.getPassword());
        preparedStatement.setString(k++, entity.getFirstName());
        preparedStatement.setString(k++, entity.getLastName());
        preparedStatement.setString(k++, entity.getPatronymic());
        preparedStatement.setInt(k, entity.getRoleId());
        preparedStatement.setInt(k, entity.getId());
    }

    @Override
    protected User extractInfo(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt(FieldsDataBase.ENTITY_ID));
        user.setPassword(resultSet.getString(FieldsDataBase.USER_PASSWORD));
        user.setFirstName(resultSet.getString(FieldsDataBase.USER_FIRST_NAME));
        user.setLastName(resultSet.getString(FieldsDataBase.USER_LAST_NAME));
        user.setPatronymic(resultSet.getString(FieldsDataBase.USER_PATRONYMIC));
        user.setEmail(resultSet.getString(FieldsDataBase.USER_EMAIL));
        user.setRoleId(resultSet.getInt(FieldsDataBase.USER_ROLE_ID));
        return user;
    }

    @Override
    public void deleteById(int id, Connection connection) throws SQLException, CrudException {
        deleteById(
                id,
                SqlQueriesUtilities.getSqlQuery("user.delete.by.id"),
                connection
        );
    }

    @Override
    public User selectById(int id, Connection connection) throws SQLException, CrudException {
        return selectById(
                id,
                SqlQueriesUtilities.getSqlQuery("user.select.by.id"),
                connection
        );
    }

    @Override
    public User selectByEmail(String email, Connection connection) throws SQLException, CrudException {
        String sql = SqlQueriesUtilities.getSqlQuery("user.select.by.email");
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, email);
            LOGGER.trace("selectByEmail['{ " + ps.toString() + " }']");
            List<User> result = executeQuery(ps);
            if (result.size() > 0) {
                return result.get(0);
            } else {
                LOGGER.warn("Throw SELECT Exception, while selecting user by email : " + email + " sql :" + sql);
                throw new CrudException(ExceptionMessages.SELECT_BY_SOME_VALUE_EXCEPTION_MESSAGE);
            }
        }
    }

    @Override
    public boolean userExistsByEmail(String email, Connection connection) throws SQLException {
        try {
            LOGGER.trace("userExistsByEmail [ASK] email :" + email);
            selectByEmail(email, connection);
            LOGGER.trace("userExistsByEmail [ANSWER = YES] email :" + email);
            return true;
        } catch (CrudException e) {
            LOGGER.warn("userExistsByEmail [ANSWER = NO] email :" + email);
            return false;
        }
    }

    @Override
    public void updatePasswordById(int id, String password, Connection connection) throws SQLException, CrudException {
        String sql = SqlQueriesUtilities.getSqlQuery("user.update.by.id");
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, password);
            ps.setInt(2, id);
            LOGGER.trace("updatePasswordById['{ " + ps.toString() + " }']");
            if (ps.executeUpdate() == 0) {
                LOGGER.warn("Throw UPDATE Exception, while updating by id : " + id + " sql :" + sql);
                throw new CrudException(ExceptionMessages.UPDATE_EXCEPTION_MESSAGE);
            }
        }
    }

    @Override
    public void insert(User entity, Connection connection) throws SQLException, CrudException {
        insert(
                entity,
                SqlQueriesUtilities.getSqlQuery("user.insert"),
                connection
        );
    }

    @Override
    public void update(User entity, Connection connection) throws SQLException, CrudException {
        update(
                entity,
                SqlQueriesUtilities.getSqlQuery("user.update"),
                connection
        );
    }

    @Override
    public List<User> selectAll(Connection connection) throws SQLException, CrudException {
        return selectAll(
                SqlQueriesUtilities.getSqlQuery("user.select.all"),
                connection
        );
    }

}
