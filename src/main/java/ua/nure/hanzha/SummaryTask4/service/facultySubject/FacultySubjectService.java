package ua.nure.hanzha.SummaryTask4.service.facultySubject;

import ua.nure.hanzha.SummaryTask4.entity.FacultySubject;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;

import java.util.List;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 19/08/15.
 */
public interface FacultySubjectService {

    List<FacultySubject> getAllByFacultyId(int facultyId) throws DaoSystemException;
}
