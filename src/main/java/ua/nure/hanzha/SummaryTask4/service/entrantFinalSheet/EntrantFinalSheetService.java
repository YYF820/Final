package ua.nure.hanzha.SummaryTask4.service.entrantFinalSheet;

import ua.nure.hanzha.SummaryTask4.bean.ReadyFinalEntrantSheetBean;
import ua.nure.hanzha.SummaryTask4.entity.EntrantFinalSheet;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;

import java.util.List;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 14/08/15.
 */
public interface EntrantFinalSheetService {

    Integer getMaxNumberOfSheet() throws DaoSystemException;

    Integer getMaxIncrementedNumberOfSheet() throws DaoSystemException;

    void addEntrantToFinalSheet(EntrantFinalSheet entrantFinalSheet) throws DaoSystemException;

    List<ReadyFinalEntrantSheetBean> getPassedEntrants() throws DaoSystemException;

    ReadyFinalEntrantSheetBean getPassedEntrantByUserId(int userId) throws DaoSystemException;
}
