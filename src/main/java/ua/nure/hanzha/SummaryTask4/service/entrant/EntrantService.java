package ua.nure.hanzha.SummaryTask4.service.entrant;

import ua.nure.hanzha.SummaryTask4.entity.Entrant;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 02/08/15.
 */
public interface EntrantService {

    int getStatusIdByUserId(int userId) throws DaoSystemException;

    Entrant getById(int entrantId) throws DaoSystemException;

    Entrant getByUserId(int userId) throws DaoSystemException;

    void updateEntrantStatus(int statusId, int entrantId) throws DaoSystemException;

}
