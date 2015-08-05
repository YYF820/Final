package ua.nure.hanzha.SummaryTask4.db.dao.faculty;

import ua.nure.hanzha.SummaryTask4.db.dao.Dao;
import ua.nure.hanzha.SummaryTask4.entity.Faculty;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * FacultyDao implements Dao<Faculty> so we can specify, that return type will be Faculty, where was <T>.
 * Contains methods FacultyDao#deleteById, FacultyDao#selectById.
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public interface FacultyDao extends Dao<Faculty> {

    void deleteById(int id, Connection connection) throws SQLException, CrudException;

    Faculty selectById(int id, Connection connection) throws SQLException, CrudException;

}
