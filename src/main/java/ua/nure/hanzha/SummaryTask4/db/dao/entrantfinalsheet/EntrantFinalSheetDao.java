package ua.nure.hanzha.SummaryTask4.db.dao.entrantfinalsheet;

import ua.nure.hanzha.SummaryTask4.bean.ReadyFinalEntrantSheetBean;
import ua.nure.hanzha.SummaryTask4.db.dao.Dao;
import ua.nure.hanzha.SummaryTask4.entity.EntrantFinalSheet;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * EntrantFinalSheetDao implements Dao<T> so we can specify, that return type will be EntrantFinalSheet, where was <T>.
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 29/07/15.
 */
public interface EntrantFinalSheetDao extends Dao<EntrantFinalSheet> {

    void deleteByFacultyId(int facultyId, Connection connection) throws SQLException, CrudException;

    void deleteByEntrantId(int entrantId, Connection connection) throws SQLException, CrudException;

    void deleteByFacultyIdEntrantId(int facultyId, int entrantId, Connection connection) throws SQLException, CrudException;

    List<EntrantFinalSheet> selectByFacultyId(int facultyId, Connection connection) throws SQLException, CrudException;

    List<EntrantFinalSheet> selectByEntrantId(int entrantId, Connection connection) throws SQLException, CrudException;

    EntrantFinalSheet selectByFacultyIdEntrantId(int facultyId, int entrantId, Connection connection) throws SQLException, CrudException;

    List<EntrantFinalSheet> selectByEnterUniversityStatusId(int enterUniversityStatusId, Connection connection) throws SQLException, CrudException;

    List<EntrantFinalSheet> selectByNumberOfSheet(int numberOfSheet, Connection connection) throws SQLException, CrudException;

    List<EntrantFinalSheet> selectByEnterUniversityStatusIdAndNumberOfSheet(int enterUniversityStatusId, int numberOfSheet, Connection connection) throws SQLException, CrudException;

    Integer selectMaxNumberOfPage(Connection connection) throws SQLException, CrudException;

    Integer selectIncrementedNumberOfPage(Connection connection) throws SQLException;

    List<ReadyFinalEntrantSheetBean> selectPassedEntrants(Connection connection) throws SQLException, CrudException;

    ReadyFinalEntrantSheetBean selectPassedEntrantByUserId(int userId, Connection connection) throws SQLException, CrudException;
}
