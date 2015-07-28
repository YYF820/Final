package ua.nure.hanzha.SummaryTask4.db.dao.mark;

import ua.nure.hanzha.SummaryTask4.db.dao.Dao;
import ua.nure.hanzha.SummaryTask4.entity.Mark;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * MarkDao implements Dao<T> so we can specify, that return type will be Mark, where was <T>.
 * Contains methods MarkDao#deleteBySubjectId, MarkDao#deleteByEntrantId, MarkDao#deleteBySubjectIdEntrantId,
 * MarkDao#selectBySubjectId, MarkDao#selectByEntrantId, MarkDao#selectBySubjectIdEntrantId,
 * MarkDao#selectByMarkValue.
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public interface MarkDao extends Dao<Mark> {

    void deleteBySubjectId(int subjectId, Connection connection) throws SQLException, CrudException;

    void deleteByEntrantId(int entrantId, Connection connection) throws SQLException, CrudException;

    void deleteBySubjectIdEntrantId(int subjectId, int entrantId, Connection connection) throws SQLException, CrudException;

    void selectBySubjectId(int subjectId, Connection connection) throws SQLException, CrudException;

    void selectByEntrantId(int entrantId, Connection connection) throws SQLException, CrudException;

    void selectBySubjectIdEntrantId(int subjectId, int entrantId, Connection connection) throws SQLException, CrudException;

    List<Mark> selectByMarkValue(double markValue, Connection connection) throws SQLException, CrudException;
}
