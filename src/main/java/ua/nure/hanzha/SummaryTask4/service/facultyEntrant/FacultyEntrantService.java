package ua.nure.hanzha.SummaryTask4.service.facultyEntrant;

import ua.nure.hanzha.SummaryTask4.bean.EntrantFinalSheetBean;
import ua.nure.hanzha.SummaryTask4.entity.FacultyEntrant;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;

import java.sql.SQLException;
import java.util.Map;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 11/08/15.
 */
public interface FacultyEntrantService {

    Map<Integer, Integer> getFacultyIdPriorityByEntrantId(int entrantId) throws DaoSystemException;

    void removeFacultyByFacultyIdEntrantId(int facultyId, int entrantId) throws DaoSystemException;

    void addFaculty(FacultyEntrant facultyEntrant) throws DaoSystemException;

    void updatePriority(int priority, int facultyId, int entrantId) throws DaoSystemException;

    void summAllMarks() throws DaoSystemException;

    EntrantFinalSheetBean getEntrantBeanByEntrantId (int entrantId) throws DaoSystemException;



}
