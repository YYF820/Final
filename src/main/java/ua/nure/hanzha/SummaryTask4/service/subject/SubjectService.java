package ua.nure.hanzha.SummaryTask4.service.subject;

import ua.nure.hanzha.SummaryTask4.entity.Subject;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;

import java.util.List;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 08/08/15.
 */
public interface SubjectService {

    List<Subject> getAllByFacultyId(int facultyId) throws DaoSystemException;

    List<Subject> getAll() throws DaoSystemException;
}
