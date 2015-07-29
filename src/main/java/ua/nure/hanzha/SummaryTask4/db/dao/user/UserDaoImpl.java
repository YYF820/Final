package ua.nure.hanzha.SummaryTask4.db.dao.user;

import ua.nure.hanzha.SummaryTask4.constants.ExceptionMessages;
import ua.nure.hanzha.SummaryTask4.constants.FieldsDataBase;
import ua.nure.hanzha.SummaryTask4.db.dao.AbstractDao;
import ua.nure.hanzha.SummaryTask4.db.util.SqlQueriesHolder;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;

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

    @Override
    protected void prepareForInsert(User entity, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setString(k++, entity.getPassword());
        preparedStatement.setString(k++, entity.getFirstName());
        preparedStatement.setString(k++, entity.getLastName());
        preparedStatement.setString(k++, entity.getPatronymic());
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
                SqlQueriesHolder.getSqlQuery("user.delete.by.id"),
                connection
        );
    }

    @Override
    public User selectById(int id, Connection connection) throws SQLException, CrudException {
        return selectById(
                id,
                SqlQueriesHolder.getSqlQuery("user.select.by.id"),
                connection
        );
    }

    @Override
    public User selectByEmail(String email, Connection connection) throws SQLException, CrudException {
        try (PreparedStatement ps = connection.prepareStatement(SqlQueriesHolder.getSqlQuery("user.select.by.email"))) {
            ps.setString(1, email);
            List<User> result = executeQuery(ps);
            if (result.size() > 0) {
                return result.get(0);
            } else {
                throw new CrudException(ExceptionMessages.SELECT_BY_SOME_VALUE_EXCEPTION_MESSAGE);
            }
        }
    }

    @Override
    public void insert(User entity, Connection connection) throws SQLException, CrudException {
        insert(
                entity,
                SqlQueriesHolder.getSqlQuery("user.insert"),
                connection
        );
    }

    @Override
    public void update(User entity, Connection connection) throws SQLException, CrudException {
        update(
                entity,
                SqlQueriesHolder.getSqlQuery("user.update"),
                connection
        );
    }

    @Override
    public List<User> selectAll(Connection connection) throws SQLException, CrudException {
        return selectAll(
                SqlQueriesHolder.getSqlQuery("user.select.all"),
                connection
        );
    }

}
