package ua.nure.hanzha.SummaryTask4.db.dao.user;

import ua.nure.hanzha.SummaryTask4.db.dao.Dao;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * UserDao implements Dao<User> so we can specify, that return type will be User, where was <T>
 * Contains methods UserDao#deleteById, UserDao#selectById, UserDao#selectByEmail.
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public interface UserDao extends Dao<User> {

    void deleteById(int id, Connection connection) throws SQLException, CrudException;

    User selectById(int id, Connection connection) throws SQLException, CrudException;

    User selectByEmail(String login, Connection connection) throws SQLException, CrudException;

}
