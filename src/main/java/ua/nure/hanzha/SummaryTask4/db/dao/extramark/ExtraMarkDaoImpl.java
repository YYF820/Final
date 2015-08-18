package ua.nure.hanzha.SummaryTask4.db.dao.extramark;

import ua.nure.hanzha.SummaryTask4.constants.FieldsDataBase;
import ua.nure.hanzha.SummaryTask4.db.dao.AbstractDao;
import ua.nure.hanzha.SummaryTask4.db.util.SqlQueriesUtilities;
import ua.nure.hanzha.SummaryTask4.entity.ExtraMark;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * ExtraMarkDaoImpl extends AbstractDao and implements ExtraMarkDao.In ExtraMarkDaoImpl I am using realisation from AbstractDao
 * and for methods which in ExtraMarkDao and doesn't have realisation in AbstractDao I solved here.
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public class ExtraMarkDaoImpl extends AbstractDao<ExtraMark> implements ExtraMarkDao {
    @Override
    protected void prepareForInsert(ExtraMark entity, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setInt(k++, entity.getEntrantId());
        preparedStatement.setDouble(k++, entity.getCertificatePoints());
        preparedStatement.setDouble(k, entity.getExtraPoints());
    }

    @Override
    protected void prepareForUpdate(ExtraMark entity, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setDouble(k++, entity.getCertificatePoints());
        preparedStatement.setDouble(k++, entity.getExtraPoints());
        preparedStatement.setInt(k, entity.getEntrantId());
    }

    @Override
    protected ExtraMark extractInfo(ResultSet resultSet) throws SQLException {
        ExtraMark extraMark = new ExtraMark();
        extraMark.setEntrantId(resultSet.getInt(FieldsDataBase.EXTRA_MARK_ENTRANT_ID));
        extraMark.setCertificatePoints(resultSet.getDouble(FieldsDataBase.EXTRA_MARK_CERTIFICATE_POINTS));
        extraMark.setExtraPoints(resultSet.getDouble(FieldsDataBase.EXTRA_MARK_EXTRA_POINTS));
        return extraMark;
    }

    @Override
    public void deleteByEntrantId(int entrantId, Connection connection) throws SQLException, CrudException {
        deleteById(
                entrantId,
                SqlQueriesUtilities.getSqlQuery("extra_mark.delete.by.entrant.id"),
                connection
        );
    }

    @Override
    public ExtraMark selectByEntrantId(int entrantId, Connection connection) throws SQLException, CrudException {
        return selectById(
                entrantId,
                SqlQueriesUtilities.getSqlQuery("extra_mark.select.by.entrant.id"),
                connection
        );
    }

    @Override
    public void insert(ExtraMark entity, Connection connection) throws SQLException, CrudException {
        insert(
                entity,
                SqlQueriesUtilities.getSqlQuery("extra_mark.insert"),
                connection
        );
    }

    @Override
    public void update(ExtraMark entity, Connection connection) throws SQLException, CrudException {
        update(
                entity,
                SqlQueriesUtilities.getSqlQuery("extra_mark.update"),
                connection
        );
    }

    @Override
    public List<ExtraMark> selectAll(Connection connection) throws SQLException, CrudException {
        return selectAll(
                SqlQueriesUtilities.getSqlQuery("extra_mark.select.all"),
                connection
        );
    }

}
