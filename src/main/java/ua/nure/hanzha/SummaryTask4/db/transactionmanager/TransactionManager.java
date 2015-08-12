package ua.nure.hanzha.SummaryTask4.db.transactionmanager;

import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;

public interface TransactionManager {

    <T> T doInTransaction(SqlCallable<T> unitOfWork) throws DaoSystemException;

}
