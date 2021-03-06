package ua.nure.hanzha.SummaryTask4.constants;

/**
 * Fields with DB names.
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public final class FieldsDataBase {

    //===========================ENTITY FIELDS===================================

    public static final String ENTITY_ID = "id";

    //===========================USER FIELDS=====================================

    public static final String USER_PASSWORD = "password";
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_LAST_NAME = "last_name";
    public static final String USER_PATRONYMIC = "patronymic";
    public static final String USER_EMAIL = "email";
    public static final String USER_ROLE_ID = "role_id";

    //===========================ENTRANT FIELDS==================================

    public static final String ENTRANT_CITY = "city";
    public static final String ENTRANT_REGION = "region";
    public static final String ENTRANT_SCHOOL = "school";
    public static final String ENTRANT_WITHOUT_COMPETITIVE_ENTRY = "without_competitive_entry";
    public static final String ENTRANT_STATUS = "entrant_status_id";
    public static final String ENTRANT_USER_ID = "user_id";

    //===========================ENTRANT INFO ADMIN BEAN=========================

    public static final String ENTRANT_INFO_ADMIN_FIRST_NAME = "first_name";
    public static final String ENTRANT_INFO_ADMIN_LAST_NAME = "last_name";
    public static final String ENTRANT_INFO_ADMIN_PATRONYMIC = "patronymic";
    public static final String ENTRANT_INFO_ADMIN_EMAIL = "email";
    public static final String ENTRANT_INFO_ADMIN_CITY = "city";
    public static final String ENTRANT_INFO_ADMIN_REGION = "region";
    public static final String ENTRANT_INFO_ADMIN_SCHOOL = "school";
    public static final String ENTRANT_INFO_ADMIN_STATUS = "entrant_status_id";

    //===========================FACULTY FIELDS==================================

    public static final String FACULTY_NAME = "name";
    public static final String FACULTY_TOTAL_SPOTS = "total_spots";
    public static final String FACULTY_BUDGET_SPOTS = "budget_spots";

    //===========================SUBJECT FIELDS==================================

    public static final String SUBJECT_NAME = "name";

    //===========================EXTRA MARK FIELDS===============================

    public static final String EXTRA_MARK_ENTRANT_ID = "entrant_id";
    public static final String EXTRA_MARK_CERTIFICATE_POINTS = "certificate_points";
    public static final String EXTRA_MARK_EXTRA_POINTS = "extra_points";

    //===========================FACULTY SUBJECT FIELDS==========================

    public static final String FACULTY_SUBJECT_FACULTY_ID = "faculty_id";
    public static final String FACULTY_SUBJECT_SUBJECT_ID = "subject_id";

    //===========================FACULTY SUBJECT FIELDS==========================

    public static final String MARK_SUBJECT_ID = "subject_id";
    public static final String MARK_ENTRANT_ID = "entrant_id";
    public static final String MARK_VALUE = "mark_value";

    //===========================FACULTY ENTRANT FIELDS==========================

    public static final String FACULTY_ENTRANT_FACULTY_ID = "faculty_id";
    public static final String FACULTY_ENTRANT_ENTRANT_ID = "entrant_id";
    public static final String FACULTY_ENTRANT_PRIORITY = "priority";
    public static final String FACULTY_ENTRANT_SUM_MARKS = "sum_marks";

    //===========================FACULTY ENTRANT FIELDS==========================
    public static final String ENTRANT_FINAL_SHEET_FACULTY_ID = "faculty_id";
    public static final String ENTRANT_FINAL_SHEET_ENTRANT_ID = "entrant_id";
    public static final String ENTRANT_FINAL_SHEET_ENTER_UNIVERSITY_STATUS_ID = "enter_university_status_id";
    public static final String ENTRANT_FINAL_SHEET_NUMBER_OF_SHEET = "number_of_sheet";

    private FieldsDataBase() {

    }
}
