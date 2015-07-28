package ua.nure.hanzha.SummaryTask4.db.transactionmanager;

import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;

public interface TransactionManager {

    public <T> T doInTransaction(SqlCallable<T> unitOfWork) throws DaoSystemException;

}
