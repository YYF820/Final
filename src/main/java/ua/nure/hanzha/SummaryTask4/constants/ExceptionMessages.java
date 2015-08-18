package ua.nure.hanzha.SummaryTask4.constants;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public final class ExceptionMessages {
    public static final String INSERT_EXCEPTION_MESSAGE = "Can't insert entity...";
    public static final String UPDATE_EXCEPTION_MESSAGE = "Smth bad, while updating...";
    public static final String DELETE_EXCEPTION_MESSAGE = "Nothing was found by this ID...";
    public static final String SELECT_BY_ID_EXCEPTION_MESSAGE = "Nothing by this id...";
    public static final String SELECT_BY_SOME_VALUE_EXCEPTION_MESSAGE = "Nothing by this value...";
    public static final String SELECT_EXCEPTION_MESSAGE = "Empty table...";
    public static final String SQL_EXCEPTION = "SQL Exception";
    public static final String INSERT_FACULTY_SAME_NAME = "ERROR: duplicate key value violates unique constraint \"faculties_name_key\"";
    public static final String NO_ENTRANT_FINAL_SHEETS = "no entrant final sheets";

    private ExceptionMessages() {

    }
}
