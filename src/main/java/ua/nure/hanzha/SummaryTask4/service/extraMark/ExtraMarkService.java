package ua.nure.hanzha.SummaryTask4.service.extraMark;

import ua.nure.hanzha.SummaryTask4.entity.ExtraMark;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;

import java.util.List;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 10/08/15.
 */
public interface ExtraMarkService {

    ExtraMark getByEntrantId(int entrantId) throws DaoSystemException;
}
