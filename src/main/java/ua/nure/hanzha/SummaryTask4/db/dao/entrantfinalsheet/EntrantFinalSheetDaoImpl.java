package ua.nure.hanzha.SummaryTask4.db.dao.entrantfinalsheet;

import ua.nure.hanzha.SummaryTask4.constants.ExceptionMessages;
import ua.nure.hanzha.SummaryTask4.constants.FieldsDataBase;
import ua.nure.hanzha.SummaryTask4.db.dao.AbstractDao;
import ua.nure.hanzha.SummaryTask4.db.util.SqlQueriesHolder;
import ua.nure.hanzha.SummaryTask4.entity.EntrantFinalSheet;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * EntrantFinalSheetDaoImpl extends AbstractDao and implements EntrantFinalSheetDao.In EntrantFinalSheetDaoImpl
 * I am using realisation from AbstractDao and for methods which in FEntrantFinalSheetDao and doesn't have realisation
 * in AbstractDao I solved here (selectByPassed, selectByNumberOfSheet, selectByPassedAndNumberOfSheet).
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 29/07/15.
 */
public class EntrantFinalSheetDaoImpl extends AbstractDao<EntrantFinalSheet> implements EntrantFinalSheetDao {

    @Override
    protected void prepareForInsert(EntrantFinalSheet entity, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setInt(k++, entity.getFacultyId());
        preparedStatement.setInt(k++, entity.getEntrantId());
        preparedStatement.setBoolean(k++, entity.isPassed());
        preparedStatement.setInt(k, entity.getNumberOfSheet());
    }

    @Override
    protected void prepareForUpdate(EntrantFinalSheet entity, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setBoolean(k++, entity.isPassed());
        preparedStatement.setInt(k++, entity.getNumberOfSheet());
        preparedStatement.setInt(k++, entity.getFacultyId());
        preparedStatement.setInt(k, entity.getEntrantId());
    }

    @Override
    protected EntrantFinalSheet extractInfo(ResultSet resultSet) throws SQLException {
        EntrantFinalSheet entrantFinalSheet = new EntrantFinalSheet();
        entrantFinalSheet.setFacultyId(resultSet.getInt(FieldsDataBase.ENTRANT_FINAL_SHEET_FACULTY_ID));
        entrantFinalSheet.setEntrantId(resultSet.getInt(FieldsDataBase.ENTRANT_FINAL_SHEET_ENTRANT_ID));
        entrantFinalSheet.setIsPassed(resultSet.getBoolean(FieldsDataBase.ENTRANT_FINAL_SHEET_PASSED));
        entrantFinalSheet.setNumberOfSheet(resultSet.getInt(FieldsDataBase.ENTRANT_FINAL_SHEET_NUMBER_OF_SHEET));
        return entrantFinalSheet;
    }

    @Override
    public void deleteByFacultyId(int facultyId, Connection connection) throws SQLException, CrudException {
        deleteById(
                facultyId,
                SqlQueriesHolder.getSqlQuery("faculty_final_sheet.delete.by.faculty.id"),
                connection
        );
    }

    @Override
    public void deleteByEntrantId(int entrantId, Connection connection) throws SQLException, CrudException {
        deleteById(
                entrantId,
                SqlQueriesHolder.getSqlQuery("faculty_final_sheet.delete.by.entrant.id"),
                connection
        );
    }

    @Override
    public void deleteByFacultyIdEntrantId(int facultyId, int entrantId, Connection connection) throws SQLException, CrudException {
        deleteByDoubleId(
                facultyId,
                entrantId,
                SqlQueriesHolder.getSqlQuery("faculty_final_sheet.delete.by.faculty.id.and.entrant.id"),
                connection
        );
    }

    @Override
    public List<EntrantFinalSheet> selectByFacultyId(int facultyId, Connection connection) throws SQLException, CrudException {
        return selectByIdMultiRows(
                facultyId,
                SqlQueriesHolder.getSqlQuery("faculty_final_sheet.select.by.faculty.id"),
                connection
        );
    }

    @Override
    public List<EntrantFinalSheet> selectByEntrantId(int entrantId, Connection connection) throws SQLException, CrudException {
        return selectByIdMultiRows(
                entrantId,
                SqlQueriesHolder.getSqlQuery("faculty_final_sheet.select.by.entrant.id"),
                connection
        );
    }

    @Override
    public EntrantFinalSheet selectByFacultyIdEntrantId(int facultyId, int entrantId, Connection connection) throws SQLException, CrudException {
        return selectByDoubleId(
                facultyId,
                entrantId,
                SqlQueriesHolder.getSqlQuery("faculty_final_sheet.select.by.faculty.id.and.entrant.id"),
                connection
        );
    }

    @Override
    public List<EntrantFinalSheet> selectByPassed(boolean passed, Connection connection) throws SQLException, CrudException {
        try (PreparedStatement ps = connection.prepareStatement(
                SqlQueriesHolder.getSqlQuery("faculty_final_sheet.select.by.passed"))) {
            ps.setBoolean(1, passed);
            List<EntrantFinalSheet> result = executeQuery(ps);
            if (result.size() > 0) {
                return result;
            } else {
                throw new CrudException(ExceptionMessages.SELECT_BY_SOME_VALUE_EXCEPTION_MESSAGE);
            }
        }
    }

    @Override
    public List<EntrantFinalSheet> selectByNumberOfSheet(int numberOfSheet, Connection connection) throws SQLException, CrudException {
        try (PreparedStatement ps = connection.prepareStatement(
                SqlQueriesHolder.getSqlQuery("faculty_final_sheet.select.by.number_of_sheet"))) {
            ps.setInt(1, numberOfSheet);
            List<EntrantFinalSheet> result = executeQuery(ps);
            if (result.size() > 0) {
                return result;
            } else {
                throw new CrudException(ExceptionMessages.SELECT_BY_SOME_VALUE_EXCEPTION_MESSAGE);
            }
        }
    }

    @Override
    public List<EntrantFinalSheet> selectByPassedAndNumberOfSheet(boolean passed, int numberOfSheet, Connection connection) throws SQLException, CrudException {
        try (PreparedStatement ps = connection.prepareStatement(
                SqlQueriesHolder.getSqlQuery("faculty_final_sheet.select.by.passed.and.number_of_sheet"))) {
            ps.setBoolean(1, passed);
            ps.setInt(2, numberOfSheet);
            List<EntrantFinalSheet> result = executeQuery(ps);
            if (result.size() > 0) {
                return result;
            } else {
                throw new CrudException(ExceptionMessages.SELECT_BY_SOME_VALUE_EXCEPTION_MESSAGE);
            }
        }
    }

    @Override
    public void insert(EntrantFinalSheet entity, Connection connection) throws SQLException, CrudException {
        insert(
                entity,
                SqlQueriesHolder.getSqlQuery("faculty_final_sheet.insert"),
                connection
        );
    }

    @Override
    public void update(EntrantFinalSheet entity, Connection connection) throws SQLException, CrudException {
        update(
                entity,
                SqlQueriesHolder.getSqlQuery("faculty_final_sheet.update"),
                connection
        );
    }

    @Override
    public List<EntrantFinalSheet> selectAll(Connection connection) throws SQLException, CrudException {
        return selectAll(
                SqlQueriesHolder.getSqlQuery("faculty_final_sheet.select.all"),
                connection
        );
    }
}
