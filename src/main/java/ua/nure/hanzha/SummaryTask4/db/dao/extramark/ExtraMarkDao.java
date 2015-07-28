package ua.nure.hanzha.SummaryTask4.db.dao.extramark;

import ua.nure.hanzha.SummaryTask4.db.dao.Dao;
import ua.nure.hanzha.SummaryTask4.entity.ExtraMark;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * ExtraMarkDao implements Dao<T> so we can specify, that return type will be ExtraMark, where was <T>.
 * Contains methods ExtraMarkDao#deleteByEntrantId, ExtraMarkDao#selectByEntrantId.
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public interface ExtraMarkDao extends Dao<ExtraMark> {

    void deleteByEntrantId(int entrantId, Connection connection) throws SQLException, CrudException;

    ExtraMark selectByEntrantId(int entrantId, Connection connection) throws SQLException, CrudException;
}
