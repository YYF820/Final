package ua.nure.hanzha.SummaryTask4.service.facultyAdmin;

import ua.nure.hanzha.SummaryTask4.entity.Faculty;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;

/**
 * @author Dmtro Hanzha
 *         Created by faffi-ubuntu on 09/08/15.
 */
public interface FacultyAdminService {

    void editFacultyInfoWithSubjects(Faculty faculty, Integer[] subjectsIdToAdd, Integer[] subjectsIdToDelete) throws DaoSystemException;

    void editFacultyInfoWithoutDelete(Faculty faculty, Integer[] subjectsIdToAdd) throws DaoSystemException;

    void editFacultyInfoWithoutAdd(Faculty faculty, Integer[] subjectsIdToDelete) throws DaoSystemException;

    void editFacultyInfoWithoutSubjects(Faculty faculty) throws DaoSystemException;

    void addFaculty(Faculty faculty, Integer[] subjectsIdToAdd) throws DaoSystemException;
}
