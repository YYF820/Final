package ua.nure.hanzha.SummaryTask4.db.dao;

import ua.nure.hanzha.SummaryTask4.entity.SkeletonEntity;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Top of DAO hierarchy, contains 3 basic operations from CRUD: CREATE, READ, UPDATE.
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public interface Dao<T extends SkeletonEntity> {

    void insert(T entity, Connection connection) throws SQLException, CrudException;

    void update(T entity, Connection connection) throws SQLException, CrudException;

    List<T> selectAll(Connection connection) throws SQLException, CrudException;
}
