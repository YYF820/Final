package ua.nure.hanzha.SummaryTask4.servlet;

import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.service.entrantFinalSheet.EntrantFinalSheetService;
import ua.nure.hanzha.SummaryTask4.servlet.callable.finalSheet.FinalSheetCallable;
import ua.nure.hanzha.SummaryTask4.servlet.callable.finalSheet.FinalSheetOperationsMap;
import ua.nure.hanzha.SummaryTask4.util.ValidationUtilities;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 14/08/15.
 */
public class PaginationFinalSheetServlet extends HttpServlet {

    private static final String PARAM_COMMAND = "command";

    private static final String SEARCH_COMMAND_ONLY_LAST_NAME = "lastName";
    private static final String SEARCH_COMMAND_ONLY_FACULTY_NAME = "facultyName";
    private static final String SEARCH_COMMAND_LAST_NAME_AND_FACULTY_NAME = "searchLastNameAndFacultyName";

    private static final String EMPTY_PARAM = "";
    private static final String PARAM_LAST_NAME = "lastName";
    private static final String PARAM_FACULTY_NAME = "facultyName";


    private EntrantFinalSheetService entrantFinalSheetService;

    @Override
    public void init() throws ServletException {
        entrantFinalSheetService =
                (EntrantFinalSheetService) getServletContext().getAttribute(AppAttribute.ENTRANT_FINAL_SHEET_SERVICE);
        FinalSheetOperationsMap.getInstance();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        cleanSession(session);
        String command = request.getParameter(PARAM_COMMAND);
        String lastName = request.getParameter(PARAM_LAST_NAME);
        String facultyName = request.getParameter(PARAM_FACULTY_NAME);
        FinalSheetOperationsMap.initFinalSheetCallableMap(session, response,
                request, entrantFinalSheetService, lastName, facultyName);
        FinalSheetCallable finalSheetCallable = FinalSheetOperationsMap.getFinalSheetCallable(command);
        if (finalSheetCallable != null) {
            finalSheetCallable.call();
        } else {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        cleanSession(session);
        String lastName = request.getParameter(PARAM_LAST_NAME);
        String facultyName = request.getParameter(PARAM_FACULTY_NAME);
        boolean isFormEmpty = checkIsFormEmpty(session, lastName, facultyName);
        if (isFormEmpty) {
            setUpFields(session, facultyName, lastName);
            response.sendRedirect(Pages.PUBLIC_FINAL_SHEET_HTML);
        } else {
            boolean isValidFields = checkIsValidForm(session, lastName, facultyName);
            if (!isValidFields) {
                setUpFields(session, facultyName, lastName);
                response.sendRedirect(Pages.PUBLIC_FINAL_SHEET_HTML);
            } else {
                String searchCommand = getSearchCommand(lastName, facultyName);
                FinalSheetOperationsMap.initFinalSheetCallableMap(session, response,
                        request, entrantFinalSheetService, lastName, facultyName);
                FinalSheetOperationsMap.getFinalSheetCallable(searchCommand).call();
            }
        }
    }

    private boolean checkIsFormEmpty(HttpSession session, String... fields) {
        int counterEmptyFields = 0;
        for (String field : fields) {
            if (field.equals(EMPTY_PARAM)) {
                counterEmptyFields++;
            }
        }
        if (counterEmptyFields == fields.length) {
            session.setAttribute(SessionAttribute.FINAL_SHEET_IS_FORM_EMPTY, true);
            return true;
        }
        session.setAttribute(SessionAttribute.FINAL_SHEET_IS_FORM_EMPTY, false);
        return false;
    }

    private boolean checkIsValidForm(HttpSession session, String lastName, String facultyName) {
        boolean isValidLastName = true;
        boolean isValidFacultyName = true;
        if (!lastName.equals(EMPTY_PARAM)) {
            isValidLastName = ValidationUtilities.validateLastName(lastName);
            if (!isValidLastName) {
                session.setAttribute(SessionAttribute.FINAL_SHEET_IS_VALID_LAST_NAME, false);
            }
        }
        if (!facultyName.equals(EMPTY_PARAM)) {
            isValidFacultyName = ValidationUtilities.validateFacultyName(facultyName);
            if (!isValidFacultyName) {
                session.setAttribute(SessionAttribute.FINAL_SHEET_IS_VALID_FACULTY_NAME, false);
            }
        }
        return !(!isValidLastName || !isValidFacultyName);
    }

    private String getSearchCommand(String lastName, String facultyName) {
        if (!lastName.equals(EMPTY_PARAM) && !facultyName.equals(EMPTY_PARAM)) {
            return SEARCH_COMMAND_LAST_NAME_AND_FACULTY_NAME;
        } else if (!lastName.equals(EMPTY_PARAM) && facultyName.equals(EMPTY_PARAM)) {
            return SEARCH_COMMAND_ONLY_LAST_NAME;
        } else {
            return SEARCH_COMMAND_ONLY_FACULTY_NAME;
        }
    }

    private void cleanSession(HttpSession session) {
        session.removeAttribute(SessionAttribute.FINAL_SHEET_IS_VALID_LAST_NAME);
        session.removeAttribute(SessionAttribute.FINAL_SHEET_IS_VALID_FACULTY_NAME);
        session.removeAttribute(SessionAttribute.FINAL_SHEET_LAST_NAME);
        session.removeAttribute(SessionAttribute.FINAL_SHEET_FACULTY_NAME);
        session.removeAttribute(SessionAttribute.FINAL_SHEET_IS_FOUND_SOMETHING);
        session.removeAttribute(SessionAttribute.FINAL_SHEET_IS_FORM_EMPTY);
    }

    private void setUpFields(HttpSession session, String facultyName, String lastName) {
        session.setAttribute(SessionAttribute.FINAL_SHEET_LAST_NAME, lastName);
        session.setAttribute(SessionAttribute.FINAL_SHEET_FACULTY_NAME, facultyName);
    }
}
