package ua.nure.hanzha.SummaryTask4.db.dao.subject;

import ua.nure.hanzha.SummaryTask4.db.dao.Dao;
import ua.nure.hanzha.SummaryTask4.entity.Subject;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * SubjectDao implements Dao<Subject> so we can specify, that return type will be Subject, where was <T>.
 * Contains methods SubjectDao#selectById.
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public interface SubjectDao extends Dao<Subject> {

    void deleteById(int id, Connection connection) throws SQLException, CrudException;

    Subject selectById(int id, Connection connection) throws SQLException, CrudException;

    List<Subject> selectAllByFacultyId(int facultyId, Connection connection) throws SQLException, CrudException;
}
