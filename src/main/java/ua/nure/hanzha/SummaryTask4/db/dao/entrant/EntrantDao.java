package ua.nure.hanzha.SummaryTask4.db.dao.entrant;

import ua.nure.hanzha.SummaryTask4.db.dao.Dao;
import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * EntrantDao implements Dao<Entrant> so we can specify, that return type will be Entrant, where was <T>.
 * Contains methods EntrantDao#deleteById, EntrantDao#selectById
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public interface EntrantDao extends Dao<Entrant> {

    void deleteById(int id, Connection connection) throws SQLException, CrudException;

    Entrant selectById(int id, Connection connection) throws SQLException, CrudException;

    int selectStatusByUserId(int userId, Connection connection) throws SQLException;

    Entrant selectByUserId(int userId, Connection connection) throws SQLException, CrudException;

    void updateEntrantStatus(int statusId, int entrantId, Connection connection) throws SQLException, CrudException;

    List<Integer> selectAllEntrantsId(Connection connection) throws SQLException;
}
