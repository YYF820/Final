package ua.nure.hanzha.SummaryTask4.db.dao.facultyentrant;

import ua.nure.hanzha.SummaryTask4.db.dao.Dao;
import ua.nure.hanzha.SummaryTask4.entity.FacultyEntrant;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * FacultySubjectDao implements Dao<T> so we can specify, that return type will be FacultySubject, where was <T>.
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 29/07/15.
 */
public interface FacultyEntrantDao extends Dao<FacultyEntrant> {

    void deleteByFacultyId(int facultyId, Connection connection) throws SQLException, CrudException;

    void deleteByEntrantId(int entrantId, Connection connection) throws SQLException, CrudException;

    void deleteByFacultyIdEntrantId(int facultyId, int entrantId, Connection connection) throws SQLException, CrudException;

    List<FacultyEntrant> selectByFacultyId(int facultyId, Connection connection) throws SQLException, CrudException;

    List<FacultyEntrant> selectBySubjectId(int entrantId, Connection connection) throws SQLException, CrudException;

    FacultyEntrant selectByFacultyIdSubjectId(int facultyId, int entrantId, Connection connection) throws SQLException, CrudException;

    List<FacultyEntrant> selectByPriority(int priority, Connection connection) throws SQLException, CrudException;

    List<FacultyEntrant> selectBySumMarks(double sumMarks, Connection connection) throws SQLException, CrudException;

    List<FacultyEntrant> selectByPrioritySumMarks(int priority, double sumMarks, Connection connection) throws SQLException, CrudException;

}
