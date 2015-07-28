package ua.nure.hanzha.SummaryTask4.db.dao.facultysubject;

import ua.nure.hanzha.SummaryTask4.db.dao.Dao;
import ua.nure.hanzha.SummaryTask4.entity.FacultySubject;
import ua.nure.hanzha.SummaryTask4.exception.CrudException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * FacultySubjectDao implements Dao<T> so we can specify, that return type will be FacultySubject, where was <T>.
 * Contains methods FacultySubjectDao#deleteByFacultyId, FacultySubjectDao#deleteBySubjectId, FacultySubjectDao#deleteByFacultyIdSubjectId,
 * FacultySubjectDao#selectByFacultyId, FacultySubjectDao#selectBySubjectId, FacultySubjectDao#selectByFacultyIdSubjectId.
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public interface FacultySubjectDao extends Dao<FacultySubject> {

    void deleteByFacultyId(int facultyId, Connection connection) throws SQLException, CrudException;

    void deleteBySubjectId(int subjectId, Connection connection) throws SQLException, CrudException;

    void deleteByFacultyIdSubjectId(int facultyId, int subjectId, Connection connection) throws SQLException, CrudException;

    List<FacultySubject> selectByFacultyId(int facultyId, Connection connection) throws SQLException, CrudException;

    List<FacultySubject> selectBySubjectId(int subjectId, Connection connection) throws SQLException, CrudException;

    FacultySubject selectByFacultyIdSubjectId(int facultyId, int subjectId, Connection connection) throws SQLException, CrudException;
}
