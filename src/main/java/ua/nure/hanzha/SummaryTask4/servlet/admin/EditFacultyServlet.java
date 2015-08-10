package ua.nure.hanzha.SummaryTask4.servlet.admin;

import ua.nure.hanzha.SummaryTask4.bean.FacultiesInfoAdminBean;
import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.Faculty;
import ua.nure.hanzha.SummaryTask4.entity.Subject;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.facultyAdmin.FacultyAdminService;
import ua.nure.hanzha.SummaryTask4.util.StringToDecimalArray;
import ua.nure.hanzha.SummaryTask4.validation.Validation;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author Dmytro Hanhza
 *         Created by faffi-ubuntu on 09/08/15.
 */
public class EditFacultyServlet extends HttpServlet {

    private static final String EMPTY_PARAM = "";
    private static final String PARAM_SUBJECTS_ID_TO_ADD = "subjectsIdToAdd";
    private static final String PARAM_SUBJECTS_ID_TO_DELETE = "subjectsIdToDelete";
    private static final String PARAM_FACULTY_NAME = "facultyName";
    private static final String PARAM_TOTAL_SPOTS = "totalSpots";
    private static final String PARAM_BUDGET_SPOTS = "budgetSpots";
    private static final int MINIMUM_SUBJECTS_PER_FACULTY = 3;


    private FacultyAdminService facultyAdminService;

    @Override
    public void init() throws ServletException {
        facultyAdminService =
                (FacultyAdminService) getServletContext().getAttribute(AppAttribute.FACULTY_ADMIN_SERVICE);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        cleanSessionPrg(session);
        String[] subjectsIdToAdd = request.getParameterValues(PARAM_SUBJECTS_ID_TO_ADD);
        String[] subjectsIdToDelete = request.getParameterValues(PARAM_SUBJECTS_ID_TO_DELETE);
        String facultyName = request.getParameter(PARAM_FACULTY_NAME);
        String totalSpots = request.getParameter(PARAM_TOTAL_SPOTS);
        String budgetSpots = request.getParameter(PARAM_BUDGET_SPOTS);
        boolean isAllFieldsEmpty = checkEmpty(
                session, subjectsIdToAdd, subjectsIdToDelete,
                facultyName, totalSpots, budgetSpots
        );
        if (isAllFieldsEmpty) {
            response.sendRedirect(Pages.FACULTY_EDIT_ADMIN_HTML);
        } else {
            if (!totalSpots.equals(EMPTY_PARAM) && !budgetSpots.equals(EMPTY_PARAM) &&
                    Integer.parseInt(totalSpots) < Integer.parseInt(budgetSpots)) {
                session.setAttribute(SessionAttribute.ADMIN_EDIT_TOTAL_LOWER_BUDGET, true);
                setUpValuesFields(session, facultyName, totalSpots, budgetSpots);
                response.sendRedirect(Pages.FACULTY_EDIT_ADMIN_HTML);
            } else {
                boolean isFacultyNameValid = checkFacultyNameValid(session, facultyName);
                session.setAttribute(SessionAttribute.ADMIN_EDIT_TOTAL_LOWER_BUDGET, false);
                if (!facultyName.equals(EMPTY_PARAM) && !isFacultyNameValid) {
                    setUpValuesFields(session, facultyName, totalSpots, budgetSpots);
                    response.sendRedirect(Pages.FACULTY_EDIT_ADMIN_HTML);
                } else {
                    FacultiesInfoAdminBean facultiesInfoAdminBean =
                            (FacultiesInfoAdminBean) session.getAttribute(SessionAttribute.ADMIN_FACULTY_FOR_EDIT);
                    Faculty facultyForEdit = facultiesInfoAdminBean.getFaculty();
                    List<Subject> currentSubjects = facultiesInfoAdminBean.getSubjects();
                    boolean isEnoughSubjectsForFaculty = checkEnoughSubjectsForFaculty(
                            session, subjectsIdToAdd,
                            subjectsIdToDelete, currentSubjects
                    );
                    if (!isEnoughSubjectsForFaculty) {
                        setUpValuesFields(session, facultyName, totalSpots, budgetSpots);
                        response.sendRedirect(Pages.FACULTY_EDIT_ADMIN_HTML);
                    } else {
                        prepareInfoBeforeEdit(facultyForEdit, facultyName, totalSpots, budgetSpots);
                        Integer[] subjectsIdToAddForService = StringToDecimalArray.convertToInteger(subjectsIdToAdd);
                        Integer[] subjectsIdToDeleteForService = StringToDecimalArray.convertToInteger(subjectsIdToDelete);
                        try {
                            if (subjectsIdToAddForService != null && subjectsIdToDeleteForService != null) {
                                facultyAdminService.editFacultyInfoWithSubjects(facultyForEdit, subjectsIdToAddForService, subjectsIdToDeleteForService);
                            } else if (subjectsIdToAddForService != null) {
                                facultyAdminService.editFacultyInfoWithoutDelete(facultyForEdit, subjectsIdToAddForService);
                            } else if (subjectsIdToDeleteForService != null) {
                                facultyAdminService.editFacultyInfoWithoutAdd(facultyForEdit, subjectsIdToDeleteForService);
                            } else {
                                facultyAdminService.editFacultyInfoWithoutSubjects(facultyForEdit);
                            }
                        } catch (DaoSystemException e) {
                            e.printStackTrace();
                        }
                        cleanSession(session);
                        response.sendRedirect(Pages.FACULTIES_ADMIN_SERVLET);
                    }
                }
            }
        }
    }

    private boolean checkEmpty(HttpSession session, String[] subjectsIdToAdd, String[] subjectsIdToDelete, String... fields) {
        int k = 0;
        for (String field : fields) {
            if (field.equals(EMPTY_PARAM)) {
                k++;
            }
        }
        if (subjectsIdToAdd == null) {
            k++;
        }
        if (subjectsIdToDelete == null) {
            k++;
        }
        if (k == fields.length + 2) {
            session.setAttribute(SessionAttribute.ADMIN_EDIT_IS_ALL_FIELDS_EMPTY, true);
            return true;
        } else {
            session.setAttribute(SessionAttribute.ADMIN_EDIT_IS_ALL_FIELDS_EMPTY, false);
        }
        return false;
    }

    private boolean checkFacultyNameValid(HttpSession session, String facultyName) {
        boolean isFacultyNameValid = Validation.validateFacultyName(facultyName);
        if (!isFacultyNameValid) {
            session.setAttribute(SessionAttribute.ADMIN_EDIT_IS_FACULTY_NAME_VALID, false);
            return false;
        } else {
            session.setAttribute(SessionAttribute.ADMIN_EDIT_IS_FACULTY_NAME_VALID, true);
        }
        return true;
    }

    private boolean checkEnoughSubjectsForFaculty(HttpSession session, String[] subjectsIdToAdd,
                                                  String[] subjectsIdToDelete, List<Subject> currentSubjects) {
        if (subjectsIdToAdd == null && subjectsIdToDelete == null) {
            if (currentSubjects.size() < MINIMUM_SUBJECTS_PER_FACULTY) {
                session.setAttribute(SessionAttribute.ADMIN_EDIT_IS_ENOUGH_SUBJECTS, false);
                return false;
            } else {
                session.setAttribute(SessionAttribute.ADMIN_EDIT_IS_ENOUGH_SUBJECTS, true);
                return true;
            }
        } else if (subjectsIdToAdd == null || subjectsIdToDelete == null) {
            if (subjectsIdToAdd == null) {
                if (currentSubjects.size() - subjectsIdToDelete.length < MINIMUM_SUBJECTS_PER_FACULTY) {
                    session.setAttribute(SessionAttribute.ADMIN_EDIT_IS_ENOUGH_SUBJECTS, false);
                    return false;
                } else {
                    session.setAttribute(SessionAttribute.ADMIN_EDIT_IS_ENOUGH_SUBJECTS, true);
                    return true;
                }
            } else {
                if (currentSubjects.size() + subjectsIdToAdd.length < MINIMUM_SUBJECTS_PER_FACULTY) {
                    session.setAttribute(SessionAttribute.ADMIN_EDIT_IS_ENOUGH_SUBJECTS, false);
                    return false;
                } else {
                    session.setAttribute(SessionAttribute.ADMIN_EDIT_IS_ENOUGH_SUBJECTS, true);
                    return true;
                }
            }
        } else {
            if (currentSubjects.size() - subjectsIdToDelete.length + subjectsIdToAdd.length < MINIMUM_SUBJECTS_PER_FACULTY) {
                session.setAttribute(SessionAttribute.ADMIN_EDIT_IS_ENOUGH_SUBJECTS, false);
                return false;
            } else {
                session.setAttribute(SessionAttribute.ADMIN_EDIT_IS_ENOUGH_SUBJECTS, true);
                return true;
            }
        }
    }

    private void prepareInfoBeforeEdit(Faculty facultyForEdit, String facultyName,
                                       String totalSpots, String budgetSpots) {
        if (!facultyName.equals(EMPTY_PARAM)) {
            facultyForEdit.setName(facultyName);
        }
        if (!totalSpots.equals(EMPTY_PARAM)) {
            facultyForEdit.setTotalSpots(Integer.parseInt(totalSpots));
        }
        if (!budgetSpots.equals(EMPTY_PARAM)) {
            facultyForEdit.setBudgetSpots(Integer.parseInt(budgetSpots));
        }
    }

    private void setUpValuesFields(HttpSession session, String facultyName,
                                   String totalSpots, String budgetSpots) {
        session.setAttribute(SessionAttribute.ADMIN_EDIT_FACULTY_NAME, facultyName);
        session.setAttribute(SessionAttribute.ADMIN_EDIT_TOTAL_SPOTS, totalSpots);
        session.setAttribute(SessionAttribute.ADMIN_EDIT_BUDGET_SPOTS, budgetSpots);
    }

    private void cleanSession(HttpSession session) {
        session.removeAttribute(SessionAttribute.ADMIN_EDIT_IS_ALL_FIELDS_EMPTY);
        session.removeAttribute(SessionAttribute.ADMIN_EDIT_IS_FACULTY_NAME_VALID);
        session.removeAttribute(SessionAttribute.ADMIN_EDIT_FACULTY_NAME);
        session.removeAttribute(SessionAttribute.ADMIN_EDIT_TOTAL_SPOTS);
        session.removeAttribute(SessionAttribute.ADMIN_EDIT_BUDGET_SPOTS);
        session.removeAttribute(SessionAttribute.ADMIN_EDIT_TOTAL_LOWER_BUDGET);
        session.removeAttribute(SessionAttribute.ADMIN_FACULTY_FOR_EDIT);
    }

    private void cleanSessionPrg(HttpSession session) {
        session.removeAttribute(SessionAttribute.ADMIN_EDIT_IS_ALL_FIELDS_EMPTY);
        session.removeAttribute(SessionAttribute.ADMIN_EDIT_IS_FACULTY_NAME_VALID);
        session.removeAttribute(SessionAttribute.ADMIN_EDIT_TOTAL_LOWER_BUDGET);
        session.removeAttribute(SessionAttribute.ADMIN_EDIT_IS_ENOUGH_SUBJECTS);
    }


}
