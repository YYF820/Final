package ua.nure.hanzha.SummaryTask4.db.dao.entrantfinalsheet;

import ua.nure.hanzha.SummaryTask4.bean.ReadyFinalEntrantSheetBean;
import ua.nure.hanzha.SummaryTask4.constants.ExceptionMessages;
import ua.nure.hanzha.SummaryTask4.constants.FieldsDataBase;
import ua.nure.hanzha.SummaryTask4.db.dao.AbstractDao;
import ua.nure.hanzha.SummaryTask4.db.util.SqlQueriesUtilities;
import ua.nure.hanzha.SummaryTask4.entity.EntrantFinalSheet;
import ua.nure.hanzha.SummaryTask4.enums.EnterUniversityStatus;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * EntrantFinalSheetDaoImpl extends AbstractDao and implements EntrantFinalSheetDao.In EntrantFinalSheetDaoImpl
 * I am using realisation from AbstractDao and for methods which in FEntrantFinalSheetDao and doesn't have realisation
 * in AbstractDao I solved here (selectByEnterUniversityStatusId, selectByNumberOfSheet, selectByEnterUniversityStatusIdAndNumberOfSheet).
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
        preparedStatement.setInt(k++, entity.getEntrantUniversityStatusId());
        preparedStatement.setInt(k, entity.getNumberOfSheet());
    }

    @Override
    protected void prepareForUpdate(EntrantFinalSheet entity, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setInt(k++, entity.getEntrantUniversityStatusId());
        preparedStatement.setInt(k, entity.getNumberOfSheet());
        preparedStatement.setInt(k++, entity.getFacultyId());
        preparedStatement.setInt(k, entity.getEntrantId());
    }

    @Override
    protected EntrantFinalSheet extractInfo(ResultSet resultSet) throws SQLException {
        EntrantFinalSheet entrantFinalSheet = new EntrantFinalSheet();
        entrantFinalSheet.setFacultyId(resultSet.getInt(FieldsDataBase.ENTRANT_FINAL_SHEET_FACULTY_ID));
        entrantFinalSheet.setEntrantId(resultSet.getInt(FieldsDataBase.ENTRANT_FINAL_SHEET_ENTRANT_ID));
        entrantFinalSheet.setEntrantUniversityStatusId
                (resultSet.getInt(FieldsDataBase.ENTRANT_FINAL_SHEET_ENTER_UNIVERSITY_STATUS_ID));
        entrantFinalSheet.setNumberOfSheet
                (resultSet.getInt(FieldsDataBase.ENTRANT_FINAL_SHEET_NUMBER_OF_SHEET));
        return entrantFinalSheet;
    }

    @Override
    public void deleteByFacultyId(int facultyId, Connection connection) throws SQLException, CrudException {
        deleteById(
                facultyId,
                SqlQueriesUtilities.getSqlQuery("faculty_final_sheet.delete.by.faculty.id"),
                connection
        );
    }

    @Override
    public void deleteByEntrantId(int entrantId, Connection connection) throws SQLException, CrudException {
        deleteById(
                entrantId,
                SqlQueriesUtilities.getSqlQuery("faculty_final_sheet.delete.by.entrant.id"),
                connection
        );
    }

    @Override
    public void deleteByFacultyIdEntrantId(int facultyId, int entrantId, Connection connection) throws SQLException, CrudException {
        deleteByDoubleId(
                facultyId,
                entrantId,
                SqlQueriesUtilities.getSqlQuery("faculty_final_sheet.delete.by.faculty.id.and.entrant.id"),
                connection
        );
    }

    @Override
    public List<EntrantFinalSheet> selectByFacultyId(int facultyId, Connection connection) throws SQLException, CrudException {
        return selectByIdMultiRows(
                facultyId,
                SqlQueriesUtilities.getSqlQuery("faculty_final_sheet.select.by.faculty.id"),
                connection
        );
    }

    @Override
    public List<EntrantFinalSheet> selectByEntrantId(int entrantId, Connection connection) throws SQLException, CrudException {
        return selectByIdMultiRows(
                entrantId,
                SqlQueriesUtilities.getSqlQuery("faculty_final_sheet.select.by.entrant.id"),
                connection
        );
    }

    @Override
    public EntrantFinalSheet selectByFacultyIdEntrantId(int facultyId, int entrantId, Connection connection) throws SQLException, CrudException {
        return selectByDoubleId(
                facultyId,
                entrantId,
                SqlQueriesUtilities.getSqlQuery("faculty_final_sheet.select.by.faculty.id.and.entrant.id"),
                connection
        );
    }

    @Override
    public List<EntrantFinalSheet> selectByEnterUniversityStatusId(int enterUniversityStatusId, Connection connection) throws SQLException, CrudException {
        try (PreparedStatement ps = connection.prepareStatement(
                SqlQueriesUtilities.getSqlQuery("faculty_final_sheet.select.by.enter_university_status_id"))) {
            ps.setInt(1, enterUniversityStatusId);
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
                SqlQueriesUtilities.getSqlQuery("faculty_final_sheet.select.by.number_of_sheet"))) {
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
    public List<EntrantFinalSheet> selectByEnterUniversityStatusIdAndNumberOfSheet(int enterUniversityStatusId, int numberOfSheet, Connection connection) throws SQLException, CrudException {
        try (PreparedStatement ps = connection.prepareStatement(
                SqlQueriesUtilities.getSqlQuery("faculty_final_sheet.select.by.enter_university_status_id.and.number_of_sheet"))) {
            ps.setInt(1, enterUniversityStatusId);
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
    public Integer selectMaxNumberOfPage(Connection connection) throws SQLException, CrudException {
        try (Statement st = connection.createStatement()) {
            try (ResultSet rs = st.executeQuery(SqlQueriesUtilities.getSqlQuery("faculty_final_sheet.select.max.number_of_sheet"))) {
                Integer numberOfPage = null;
                if (rs.next()) {
                    numberOfPage = rs.getInt(FieldsDataBase.ENTRANT_FINAL_SHEET_NUMBER_OF_SHEET);
                }
                if (numberOfPage != null) {
                    return numberOfPage;
                } else {
                    throw new CrudException(ExceptionMessages.NO_ENTRANT_FINAL_SHEETS);
                }
            }
        }
    }

    @Override
    public Integer selectIncrementedNumberOfPage(Connection connection) throws SQLException {
        try (Statement st = connection.createStatement()) {
            try (ResultSet rs = st.executeQuery(SqlQueriesUtilities.getSqlQuery("faculty_final_sheet.select.max.number_of_sheet"))) {
                if (rs.next()) {
                    return rs.getInt(FieldsDataBase.ENTRANT_FINAL_SHEET_NUMBER_OF_SHEET) + 1;
                } else {
                    return 1;
                }
            }
        }
    }

    @Override
    public List<ReadyFinalEntrantSheetBean> selectPassedEntrants(Connection connection) throws SQLException, CrudException {
        try (Statement st = connection.createStatement()) {
            try (ResultSet rs = st.executeQuery(SqlQueriesUtilities.getSqlQuery("faculty_final_sheet_select.entrant.ready.final.sheet"))) {
                List<ReadyFinalEntrantSheetBean> result = new ArrayList<>();
                while (rs.next()) {
                    ReadyFinalEntrantSheetBean readyFinalEntrantSheetBean = new ReadyFinalEntrantSheetBean();
                    readyFinalEntrantSheetBean.setFacultyName(rs.getString(FieldsDataBase.FACULTY_NAME));
                    readyFinalEntrantSheetBean.setFirstName(rs.getString(FieldsDataBase.USER_FIRST_NAME));
                    readyFinalEntrantSheetBean.setLastName(rs.getString(FieldsDataBase.USER_LAST_NAME));
                    readyFinalEntrantSheetBean.setPatronymic(rs.getString(FieldsDataBase.USER_PATRONYMIC));
                    readyFinalEntrantSheetBean.setSumOfMarks(rs.getDouble(FieldsDataBase.FACULTY_ENTRANT_SUM_MARKS));
                    int enterUniversityStatusId = rs.getInt(FieldsDataBase.ENTRANT_FINAL_SHEET_ENTER_UNIVERSITY_STATUS_ID);
                    readyFinalEntrantSheetBean.setEnterUniversityStatus
                            (EnterUniversityStatus.getEnterUniversityStatusById(enterUniversityStatusId).getName());
                    result.add(readyFinalEntrantSheetBean);
                }
                if (result.size() > 0) {
                    return result;
                } else {
                    throw new CrudException(ExceptionMessages.SELECT_EXCEPTION_MESSAGE);
                }
            }
        }
    }

    @Override
    public ReadyFinalEntrantSheetBean selectPassedEntrantByUserId(int userId, Connection connection) throws SQLException, CrudException {
        try (PreparedStatement ps = connection.prepareStatement(SqlQueriesUtilities.getSqlQuery("faculty_final_sheet_select.entrant.by.user.id"))) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                ReadyFinalEntrantSheetBean readyFinalEntrantSheetBean = null;
                if (rs.next()) {
                    readyFinalEntrantSheetBean = new ReadyFinalEntrantSheetBean();
                    readyFinalEntrantSheetBean.setFacultyName(rs.getString(FieldsDataBase.FACULTY_NAME));
                    readyFinalEntrantSheetBean.setFirstName(rs.getString(FieldsDataBase.USER_FIRST_NAME));
                    readyFinalEntrantSheetBean.setLastName(rs.getString(FieldsDataBase.USER_LAST_NAME));
                    readyFinalEntrantSheetBean.setPatronymic(rs.getString(FieldsDataBase.USER_PATRONYMIC));
                    readyFinalEntrantSheetBean.setSumOfMarks(rs.getDouble(FieldsDataBase.FACULTY_ENTRANT_SUM_MARKS));
                    int enterUniversityStatusId = rs.getInt(FieldsDataBase.ENTRANT_FINAL_SHEET_ENTER_UNIVERSITY_STATUS_ID);
                    readyFinalEntrantSheetBean.setEnterUniversityStatus
                            (EnterUniversityStatus.getEnterUniversityStatusById(enterUniversityStatusId).getName());
                }
                if (readyFinalEntrantSheetBean != null) {
                    return readyFinalEntrantSheetBean;
                } else {
                    throw new CrudException(ExceptionMessages.SELECT_BY_ID_EXCEPTION_MESSAGE);
                }
            }
        }
    }

    @Override
    public void insert(EntrantFinalSheet entity, Connection connection) throws SQLException, CrudException {
        insert(
                entity,
                SqlQueriesUtilities.getSqlQuery("faculty_final_sheet.insert"),
                connection
        );
    }

    @Override
    public void update(EntrantFinalSheet entity, Connection connection) throws SQLException, CrudException {
        update(
                entity,
                SqlQueriesUtilities.getSqlQuery("faculty_final_sheet.update"),
                connection
        );
    }

    @Override
    public List<EntrantFinalSheet> selectAll(Connection connection) throws SQLException, CrudException {
        return selectAll(
                SqlQueriesUtilities.getSqlQuery("faculty_final_sheet.select.all"),
                connection
        );
    }
}
