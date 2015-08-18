package ua.nure.hanzha.SummaryTask4.db.dao;

import org.apache.log4j.Logger;
import ua.nure.hanzha.SummaryTask4.constants.ExceptionMessages;
import ua.nure.hanzha.SummaryTask4.entity.Entity;
import ua.nure.hanzha.SummaryTask4.entity.SkeletonEntity;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;
import ua.nure.hanzha.SummaryTask4.util.ClassNameUtilities;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * AbstractDao implements Dao and gives universal realisation for Dao methods + rest CRUD basic operations.
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public abstract class AbstractDao<T extends SkeletonEntity> implements Dao<T> {

    private static final Logger LOGGER = Logger.getLogger(ClassNameUtilities.getCurrentClassName());

    protected void insert(T entity, String sql, Connection connection) throws SQLException, CrudException {
        if (entity instanceof Entity) {
            try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
                prepareForInsert(entity, ps);
                if (ps.executeUpdate() > 0) {
                    ResultSet rs = ps.getGeneratedKeys();
                    while (rs.next()) {
                        Entity entitySetUpId = (Entity) entity;
                        entitySetUpId.setId(rs.getInt(1));
                    }
                } else {
                    throw new CrudException(ExceptionMessages.INSERT_EXCEPTION_MESSAGE);
                }
            }
        } else {
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                prepareForInsert(entity, ps);
                if (ps.executeUpdate() == 0) {
                    throw new CrudException(ExceptionMessages.INSERT_EXCEPTION_MESSAGE);
                }
            }
        }
    }

    protected void update(T entity, String sql, Connection connection) throws SQLException, CrudException {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            prepareForUpdate(entity, ps);
            if (ps.executeUpdate() == 0) {
                throw new CrudException(ExceptionMessages.UPDATE_EXCEPTION_MESSAGE);
            }
        }
    }

    protected void deleteById(int id, String sql, Connection connection) throws SQLException, CrudException {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            if (ps.executeUpdate() == 0) {
                throw new CrudException(ExceptionMessages.DELETE_EXCEPTION_MESSAGE);
            }
        }
    }

    protected void deleteByDoubleId(int idFirst, int idSecond, String sql, Connection connection) throws SQLException, CrudException {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idFirst);
            ps.setInt(2, idSecond);
            if (ps.executeUpdate() == 0) {
                throw new CrudException(ExceptionMessages.DELETE_EXCEPTION_MESSAGE);
            }
        }
    }

    protected T selectById(int id, String sql, Connection connection) throws SQLException, CrudException {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            List<T> result = executeQuery(ps);
            if (result.size() > 0) {
                return result.get(0);
            } else {
                throw new CrudException(ExceptionMessages.SELECT_BY_ID_EXCEPTION_MESSAGE);
            }
        }
    }

    protected List<T> selectByIdMultiRows(int id, String sql, Connection connection) throws SQLException, CrudException {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, id);
            List<T> result = executeQuery(ps);
            if (result.size() > 0) {
                return result;
            } else {
                throw new CrudException(ExceptionMessages.SELECT_BY_ID_EXCEPTION_MESSAGE);
            }
        }
    }

    protected T selectByDoubleId(int idFirst, int idSecond, String sql, Connection connection) throws SQLException, CrudException {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, idFirst);
            ps.setInt(2, idSecond);
            List<T> result = executeQuery(ps);
            if (result.size() > 0) {
                return result.get(0);
            } else {
                throw new CrudException(ExceptionMessages.SELECT_BY_ID_EXCEPTION_MESSAGE);
            }
        }
    }


    protected List<T> selectAll(String sql, Connection connection) throws SQLException, CrudException {
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            List<T> result = executeQuery(ps);
            if (result.size() > 0) {
                return result;
            } else {
                throw new CrudException(ExceptionMessages.SELECT_EXCEPTION_MESSAGE);
            }
        }
    }

    protected List<T> executeQuery(PreparedStatement preparedStatement) throws SQLException {
        try (ResultSet resultSet = preparedStatement.executeQuery()) {
            List<T> result = new ArrayList<>();
            while (resultSet.next()) {
                T entity = extractInfo(resultSet);
                result.add(entity);
            }
            return result;
        }
    }


    protected abstract void prepareForInsert(T entity, PreparedStatement preparedStatement) throws SQLException;

    protected abstract void prepareForUpdate(T entity, PreparedStatement preparedStatement) throws SQLException;

    protected abstract T extractInfo(ResultSet resultSet) throws SQLException;
}
