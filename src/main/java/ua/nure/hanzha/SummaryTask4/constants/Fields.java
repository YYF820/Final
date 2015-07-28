package ua.nure.hanzha.SummaryTask4.constants;

/**
 * Created by faffi-ubuntu on 28/07/15.
 */
public class Fields {

    //===========================ENTITY FIELDS===================================

    public static final String ENTITY_ID = "id";

    //===========================USER FIELDS=====================================

    public static final String USER_LOGIN = "login";
    public static final String USER_PASSWORD = "password";
    public static final String USER_FIRST_NAME = "first_name";
    public static final String USER_LAST_NAME = "first_name";
    public static final String USER_PATRONYMIC = "patronymic";
    public static final String USER_EMAIL = "email";
    public static final String USER_ROLE_ID = "role_id";

    //===========================ENTRANT FIELDS==================================

    public static final String ENTRANT_CITY = "city";
    public static final String ENTRANT_REGION = "region";
    public static final String ENTRANT_SCHOOL = "school";
    public static final String ENTRANT_WITHOUT_COMPETITIVE_ENTRY = "without_competitive_entry";
    public static final String ENTRANT_USER_ID = "user_id";

    //===========================FACULTY FIELDS==================================

    public static final String FACULTY_NAME = "name";
    public static final String FACULTY_TOTAL_SPOTS = "total_spots";
    public static final String FACULTY_BUDGET_SPOTS = "budget_spots";

    //===========================SUBJECT FIELDS==================================

    public static final String SUBJECT_NAME = "name";

    //===========================EXTRA MARK FIELDS===============================

    public static final String EXTRA_MARK_ENTRANT_ID = "entrant_id";
    public static final String EXTRA_MARK_CERTIFICATE_POINTS = "certificate_points";
    public static final String EXTRA_MARK_EXTRA_POINTS = "extra_mark";

    //===========================FACULTY SUBJECT FIELDS==========================

    public static final String FACULTY_SUBJECT_FACULTY_ID = "faculty_id";
    public static final String FACULTY_SUBJECT_SUBJECT_ID = "subject_id";

    //===========================FACULTY SUBJECT FIELDS==========================

    public static final String MARK_SUBJECT_ID = "subject_id";
    public static final String MARK_ENTRANT_ID = "entrant_id";
    public static final String MARK_VALUE = "value";

    //===========================FACULTY ENTRANT FIELDS==========================

    public static final String FACULTY_ENTRANT_FACULTY_ID = "faculty_id";
    public static final String FACULTY_ENTRANT_ENTRANT_ID = "entrant_id";
    public static final String FACULTY_ENTRANT_PRIORITY = "priority";
    public static final String FACULTY_ENTRANT_SUM_MARKS = "sum_marks";
}
