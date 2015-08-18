package ua.nure.hanzha.SummaryTask4.db.dao.entrantInfoAdmin;

import ua.nure.hanzha.SummaryTask4.bean.EntrantInfoAdminBean;
import ua.nure.hanzha.SummaryTask4.constants.FieldsDataBase;
import ua.nure.hanzha.SummaryTask4.db.dao.AbstractDao;
import ua.nure.hanzha.SummaryTask4.db.util.SqlQueriesUtilities;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by faffi-ubuntu on 06/08/15.
 */
public class EntrantInfoAdminImpl extends AbstractDao<EntrantInfoAdminBean> implements EntrantInfoAdminDao {
    @Override
    protected void prepareForInsert(EntrantInfoAdminBean entity, PreparedStatement preparedStatement) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected void prepareForUpdate(EntrantInfoAdminBean entity, PreparedStatement preparedStatement) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    protected EntrantInfoAdminBean extractInfo(ResultSet resultSet) throws SQLException {
        EntrantInfoAdminBean entrantInfoAdminBean = new EntrantInfoAdminBean();
        entrantInfoAdminBean.setId(resultSet.getInt(FieldsDataBase.ENTITY_ID));
        entrantInfoAdminBean.setFirstName(resultSet.getString(FieldsDataBase.ENTRANT_INFO_ADMIN_FIRST_NAME));
        entrantInfoAdminBean.setLastName(resultSet.getString(FieldsDataBase.ENTRANT_INFO_ADMIN_LAST_NAME));
        entrantInfoAdminBean.setPatronymic(resultSet.getString(FieldsDataBase.ENTRANT_INFO_ADMIN_PATRONYMIC));
        entrantInfoAdminBean.setEmail(resultSet.getString(FieldsDataBase.ENTRANT_INFO_ADMIN_EMAIL));
        entrantInfoAdminBean.setCity(resultSet.getString(FieldsDataBase.ENTRANT_INFO_ADMIN_CITY));
        entrantInfoAdminBean.setRegion(resultSet.getString(FieldsDataBase.ENTRANT_INFO_ADMIN_REGION));
        entrantInfoAdminBean.setSchool(resultSet.getInt(FieldsDataBase.ENTRANT_INFO_ADMIN_SCHOOL));
        entrantInfoAdminBean.setStatusId(resultSet.getInt(FieldsDataBase.ENTRANT_INFO_ADMIN_STATUS));
        return entrantInfoAdminBean;
    }

    @Override
    public List<EntrantInfoAdminBean> selectAll(Connection connection) throws SQLException, CrudException {
        return selectAll(
                SqlQueriesUtilities.getSqlQuery("entrant.info.admin.select.all"),
                connection
        );
    }

    @Override
    public void update(EntrantInfoAdminBean entity, Connection connection) throws SQLException, CrudException {
        throw new UnsupportedOperationException();
    }


    @Override
    public void insert(EntrantInfoAdminBean entity, Connection connection) throws SQLException, CrudException {
        throw new UnsupportedOperationException();
    }


}
