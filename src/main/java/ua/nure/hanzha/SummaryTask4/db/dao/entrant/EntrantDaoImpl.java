package ua.nure.hanzha.SummaryTask4.db.dao.entrant;

import ua.nure.hanzha.SummaryTask4.constants.Fields;
import ua.nure.hanzha.SummaryTask4.db.dao.AbstractDao;
import ua.nure.hanzha.SummaryTask4.db.util.SqlQueriesHolder;
import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * EntrantDaoImpl extends AbstractDao and implements EntrantDao, in EntrantDaoImpl I am using realisation from AbstractDao
 * and for methods which in UserDao and doesn't have realisation in AbstractDao I solved here.
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public class EntrantDaoImpl extends AbstractDao<Entrant> implements EntrantDao {
    @Override
    protected void prepareForInsert(Entrant entity, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setString(k++, entity.getCity());
        preparedStatement.setString(k++, entity.getRegion());
        preparedStatement.setInt(k++, entity.getSchool());
        preparedStatement.setBoolean(k++, entity.isWithoutCompetitiveEntry());
        preparedStatement.setInt(k, entity.getUserId());
    }

    @Override
    protected void prepareForUpdate(Entrant entity, PreparedStatement preparedStatement) throws SQLException {
        int k = 1;
        preparedStatement.setString(k++, entity.getCity());
        preparedStatement.setString(k++, entity.getRegion());
        preparedStatement.setInt(k++, entity.getSchool());
        preparedStatement.setBoolean(k++, entity.isWithoutCompetitiveEntry());
        preparedStatement.setInt(k++, entity.getUserId());
        preparedStatement.setInt(k, entity.getId());
    }

    @Override
    protected Entrant extractInfo(ResultSet resultSet) throws SQLException {
        Entrant entrant = new Entrant();
        entrant.setId(resultSet.getInt(Fields.ENTITY_ID));
        entrant.setCity(resultSet.getString(Fields.ENTRANT_CITY));
        entrant.setRegion(resultSet.getString(Fields.ENTRANT_REGION));
        entrant.setSchool(resultSet.getInt(Fields.ENTRANT_SCHOOL));
        entrant.setWithoutCompetitiveEntry(resultSet.getBoolean(Fields.ENTRANT_WITHOUT_COMPETITIVE_ENTRY));
        entrant.setUserId(resultSet.getInt(Fields.ENTRANT_USER_ID));
        return entrant;
    }

    @Override
    public void deleteById(int id, Connection connection) throws SQLException, CrudException {
        deleteById(
                id,
                SqlQueriesHolder.getSqlQuery("entrant.delete.by.id"),
                connection
        );
    }

    @Override
    public Entrant selectById(int id, Connection connection) throws SQLException, CrudException {
        return selectById(
                id,
                SqlQueriesHolder.getSqlQuery("entrant.select.by.id"),
                connection
        );
    }

    @Override
    public void insert(Entrant entity, Connection connection) throws SQLException, CrudException {
        insert(
                entity,
                SqlQueriesHolder.getSqlQuery("entrant.insert"),
                connection
        );
    }

    @Override
    public void update(Entrant entity, Connection connection) throws SQLException, CrudException {
        update(
                entity,
                SqlQueriesHolder.getSqlQuery("entrant.update"),
                connection
        );
    }

    @Override
    public List<Entrant> selectAll(Connection connection) throws SQLException, CrudException {
        return selectAll(
                SqlQueriesHolder.getSqlQuery("entrant.select.all"),
                connection
        );
    }
}
