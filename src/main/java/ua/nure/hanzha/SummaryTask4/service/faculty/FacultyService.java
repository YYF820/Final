package ua.nure.hanzha.SummaryTask4.service.faculty;

import ua.nure.hanzha.SummaryTask4.entity.Faculty;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;

import java.util.List;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 08/08/15.
 */
public interface FacultyService {

    List<Faculty> getAllFaculties() throws DaoSystemException;
}
