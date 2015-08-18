package ua.nure.hanzha.SummaryTask4.db.dao.facultyentrant;

import org.apache.log4j.Logger;
import ua.nure.hanzha.SummaryTask4.bean.EntrantFinalSheetBean;
import ua.nure.hanzha.SummaryTask4.constants.ExceptionMessages;
import ua.nure.hanzha.SummaryTask4.constants.FieldsDataBase;
import ua.nure.hanzha.SummaryTask4.db.dao.AbstractDao;
import ua.nure.hanzha.SummaryTask4.db.util.SqlQueriesUtilities;
import ua.nure.hanzha.SummaryTask4.entity.FacultyEntrant;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;
import ua.nure.hanzha.SummaryTask4.util.ClassNameUtilities;

import java.sql.*;
import java.util.*;

/**
 * FacultyEntrantDaoImpl extends AbstractDao and implements FacultyEntrantDao.In FacultyEntrantDaoImpl
 * I am using realisation from AbstractDao and for methods which in FacultyEntrantDao and doesn't have realisation
 * in AbstractDao I solved here (selectByPriority, selectBySumMarks, selectByPrioritySumMarks).
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public class FacultyEntrantDaoImpl extends AbstractDao<FacultyEntrant> implements FacultyEntrantDao {

    private static final Logger LOGGER = Logger.getLogger(ClassNameUtilities.getCurrentClassName());

    @Override
    protected void prepareForInsert(FacultyEntrant entity, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setInt(k++, entity.getFacultyId());
        preparedStatement.setInt(k++, entity.getEntrantId());
        preparedStatement.setInt(k++, entity.getPriority());
        preparedStatement.setDouble(k, entity.getSumMarks());
    }

    @Override
    protected void prepareForUpdate(FacultyEntrant entity, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setInt(k++, entity.getPriority());
        preparedStatement.setDouble(k++, entity.getSumMarks());
        preparedStatement.setInt(k++, entity.getFacultyId());
        preparedStatement.setInt(k, entity.getEntrantId());

    }

    @Override
    protected FacultyEntrant extractInfo(ResultSet resultSet) throws SQLException {
        FacultyEntrant facultyEntrant = new FacultyEntrant();
        facultyEntrant.setFacultyId(resultSet.getInt(FieldsDataBase.FACULTY_ENTRANT_FACULTY_ID));
        facultyEntrant.setFacultyId(resultSet.getInt(FieldsDataBase.FACULTY_ENTRANT_ENTRANT_ID));
        facultyEntrant.setPriority(resultSet.getInt(FieldsDataBase.FACULTY_ENTRANT_PRIORITY));
        facultyEntrant.setSumMarks(resultSet.getDouble(FieldsDataBase.FACULTY_ENTRANT_SUM_MARKS));
        return facultyEntrant;
    }

    @Override
    public void deleteByFacultyId(int facultyId, Connection connection) throws SQLException, CrudException {
        deleteById(
                facultyId,
                SqlQueriesUtilities.getSqlQuery("faculty_entrant.delete.by.faculty.id"),
                connection
        );
    }

    @Override
    public void deleteByEntrantId(int entrantId, Connection connection) throws SQLException, CrudException {
        deleteById(
                entrantId,
                SqlQueriesUtilities.getSqlQuery("faculty_entrant.delete.by.entrant.id"),
                connection
        );
    }

    @Override
    public void deleteByFacultyIdEntrantId(int facultyId, int entrantId, Connection connection) throws SQLException, CrudException {
        deleteByDoubleId(
                facultyId,
                entrantId,
                SqlQueriesUtilities.getSqlQuery("faculty_entrant.delete.by.faculty.id.and.entrant.id"),
                connection
        );
    }

    @Override
    public List<FacultyEntrant> selectByFacultyId(int facultyId, Connection connection) throws SQLException, CrudException {
        return selectByIdMultiRows(
                facultyId,
                SqlQueriesUtilities.getSqlQuery("faculty_entrant.select.by.faculty.id"),
                connection
        );
    }

    @Override
    public List<FacultyEntrant> selectByEntrantId(int entrantId, Connection connection) throws SQLException, CrudException {
        return selectByIdMultiRows(
                entrantId,
                SqlQueriesUtilities.getSqlQuery("faculty_entrant.select.by.entrant.id"),
                connection
        );
    }

    @Override
    public FacultyEntrant selectByFacultyIdEntrantId(int facultyId, int entrantId, Connection connection) throws SQLException, CrudException {
        return selectByDoubleId(
                facultyId,
                entrantId,
                SqlQueriesUtilities.getSqlQuery("faculty_entrant.select.by.faculty.id.and.entrant.id"),
                connection
        );
    }

    @Override
    public List<FacultyEntrant> selectByPriority(int priority, Connection connection) throws SQLException, CrudException {
        String sql = SqlQueriesUtilities.getSqlQuery("faculty_entrant.select.by.priority");
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, priority);
            LOGGER.trace("selectByPriority['{ " + ps.toString() + " }']");
            List<FacultyEntrant> result = executeQuery(ps);
            if (result.size() > 0) {
                return result;
            } else {
                LOGGER.warn("Throw SELECT Exception, while selecting List<FacultyEntrant> by priority : "
                        + priority + " sql :" + sql);
                throw new CrudException(ExceptionMessages.SELECT_BY_SOME_VALUE_EXCEPTION_MESSAGE);
            }
        }
    }

    @Override
    public List<FacultyEntrant> selectBySumMarks(double sumMarks, Connection connection) throws SQLException, CrudException {
        String sql = SqlQueriesUtilities.getSqlQuery("faculty_entrant.select.by.sum.marks");
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setDouble(1, sumMarks);
            LOGGER.trace("selectBySumMarks['{ " + ps.toString() + " }']");
            List<FacultyEntrant> result = executeQuery(ps);
            if (result.size() > 0) {
                return result;
            } else {
                LOGGER.warn("Throw SELECT Exception, while selecting List<FacultyEntrant> by sum_marks : "
                        + sumMarks + " sql :" + sql);
                throw new CrudException(ExceptionMessages.SELECT_BY_SOME_VALUE_EXCEPTION_MESSAGE);
            }
        }
    }

    @Override
    public List<FacultyEntrant> selectByPrioritySumMarks(int priority, double sumMarks, Connection connection) throws SQLException, CrudException {
        String sql = SqlQueriesUtilities.getSqlQuery("faculty_entrant.select.by.priority.and.sum.marks");
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, priority);
            ps.setDouble(2, sumMarks);
            LOGGER.trace("selectByPrioritySumMarks['{ " + ps.toString() + " }']");
            List<FacultyEntrant> result = executeQuery(ps);
            if (result.size() > 0) {
                return result;
            } else {
                LOGGER.warn("Throw SELECT Exception, while selecting List<FacultyEntrant> by sum_marks and priority : "
                        + sumMarks + " " + priority + " sql :" + sql);
                throw new CrudException(ExceptionMessages.SELECT_BY_SOME_VALUE_EXCEPTION_MESSAGE);
            }
        }
    }

    @Override
    public List<Integer> selectAllPriorityByEntrantId(int entrantId, Connection connection) throws SQLException, CrudException {
        String sql = SqlQueriesUtilities.getSqlQuery("faculty_entrant.select.priority.by.entrant.id");
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, entrantId);
            LOGGER.trace("selectAllPriorityByEntrantId['{ " + ps.toString() + " }']");
            List<Integer> result = new ArrayList<>();
            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    result.add(resultSet.getInt(FieldsDataBase.FACULTY_ENTRANT_PRIORITY));
                }
            }
            if (result.size() > 0) {
                return result;
            } else {
                LOGGER.warn("Throw SELECT Exception, while selecting List<Integer> priorities by entrantId : "
                        + entrantId + " sql :" + sql);
                throw new CrudException(ExceptionMessages.SELECT_BY_SOME_VALUE_EXCEPTION_MESSAGE);
            }
        }
    }

    @Override
    public Map<Integer, Integer> selectAllFacultyIdPriorityByEntrantId(int entrantId, Connection connection) throws SQLException, CrudException {
        String sql = SqlQueriesUtilities.getSqlQuery("faculty_entrant.select.faculty.id.priority.by.entrant.id");
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, entrantId);
            LOGGER.trace("selectAllFacultyIdPriorityByEntrantId['{ " + ps.toString() + " }']");
            Map<Integer, Integer> result = new HashMap<>();
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    result.put(rs.getInt(FieldsDataBase.FACULTY_ENTRANT_FACULTY_ID),
                            rs.getInt(FieldsDataBase.FACULTY_ENTRANT_PRIORITY));
                }
            }
            return result;
        }
    }

    @Override
    public EntrantFinalSheetBean selectEntrantBeanByEntrantId(int entrantId, Connection connection) throws SQLException {
        String sql = SqlQueriesUtilities.getSqlQuery("faculty_entrant.select.by.entrant.id");
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, entrantId);
            LOGGER.trace("selectEntrantBeanByEntrantId['{ " + ps.toString() + " }']");
            Map<Integer, Integer> tempMap = new HashMap<>();
            EntrantFinalSheetBean entrantFinalSheetBean = null;
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    entrantFinalSheetBean = new EntrantFinalSheetBean();
                    entrantFinalSheetBean.setEntrantId(entrantId);
                    entrantFinalSheetBean.setSumOfMarks(rs.getDouble(FieldsDataBase.FACULTY_ENTRANT_SUM_MARKS));
                    tempMap.put(rs.getInt(FieldsDataBase.FACULTY_ENTRANT_PRIORITY),
                            rs.getInt(FieldsDataBase.FACULTY_ENTRANT_FACULTY_ID));
                }
                while (rs.next()) {
                    tempMap.put(rs.getInt(FieldsDataBase.FACULTY_ENTRANT_PRIORITY),
                            rs.getInt(FieldsDataBase.FACULTY_ENTRANT_FACULTY_ID));
                }
                if (entrantFinalSheetBean != null) {
                    Map<Integer, Integer> priorityFacultyIdPair = new TreeMap<>(tempMap);
                    entrantFinalSheetBean.setPriorityFacultyPair(priorityFacultyIdPair);
                    return entrantFinalSheetBean;
                } else {
                    return null;
                }
            }
        }
    }


    @Override
    public void updatePriorityByFacultyIdEntrantId(int priority, int facultyId, int entrantId, Connection connection) throws SQLException, CrudException {
        String sql = SqlQueriesUtilities.getSqlQuery("faculty_entrant.update.priority.by.faculty.id.entrant.id");
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, priority);
            ps.setInt(2, facultyId);
            ps.setInt(3, entrantId);
            LOGGER.trace("updatePriorityByFacultyIdEntrantId['{ " + ps.toString() + " }']");
            if (ps.executeUpdate() == 0) {
                LOGGER.warn("Throw UPDATE Exception, while updating priority by faculty id : " + facultyId + " entrant id :"
                        + entrantId + " sql :" + sql);
                throw new CrudException(ExceptionMessages.UPDATE_EXCEPTION_MESSAGE);
            }
        }
    }

    @Override
    public void sumAllMarks(Connection connection) throws SQLException {
        try (Statement st = connection.createStatement()) {
            LOGGER.trace("Sum up all marks");
            st.executeQuery(SqlQueriesUtilities.getSqlQuery("faculty.entrant.summ.all.marks"));
        }
    }

    @Override
    public void insert(FacultyEntrant entity, Connection connection) throws SQLException, CrudException {
        insert(
                entity,
                SqlQueriesUtilities.getSqlQuery("faculty_entrant.insert"),
                connection
        );
    }

    @Override
    public void update(FacultyEntrant entity, Connection connection) throws SQLException, CrudException {
        update(
                entity,
                SqlQueriesUtilities.getSqlQuery("faculty_entrant.update"),
                connection
        );

    }

    @Override
    public List<FacultyEntrant> selectAll(Connection connection) throws SQLException, CrudException {
        return selectAll(
                SqlQueriesUtilities.getSqlQuery("faculty_entrant.select.all"),
                connection
        );
    }
}
