package ua.nure.hanzha.SummaryTask4.servlet;

import ua.nure.hanzha.SummaryTask4.bean.ReadyFinalEntrantSheetBean;
import ua.nure.hanzha.SummaryTask4.constants.*;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.entrantFinalSheet.EntrantFinalSheetService;
import ua.nure.hanzha.SummaryTask4.validation.Validation;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 14/08/15.
 */
public class PaginationFinalSheetServlet extends HttpServlet {

    private static final String PARAM_PAGE = "page";
    private static final int RECORDS_PER_PAGE = 17;
    private static final String COMMAND_FIND_ALL_ENTRANTS = "findAllEntrants";
    private static final String COMMAND_FIND_ME = "findMe";
    private static final String SEARCH_COMMAND = "search";
    private static final String PARAM_COMMAND = "command";

    private static final String SEARCH_COMMAND_ONLY_LAST_NAME = "lastName";
    private static final String SEARCH_COMMAND_ONLY_FACULTY_NAME = "facultyName";
    private static final String SEARCH_COMMAND_LAST_NAME_AND_FACULTY_NAME = "searchLastNameAndFacultyName";

    private static final String EMPTY_PARAM = "";
    private static final String PARAM_LAST_NAME = "lastName";
    private static final String PARAM_FACULTY_NAME = "facultyName";

    private static int page = 1;

    private EntrantFinalSheetService entrantFinalSheetService;

    @Override
    public void init() throws ServletException {
        entrantFinalSheetService =
                (EntrantFinalSheetService) getServletContext().getAttribute(AppAttribute.ENTRANT_FINAL_SHEET_SERVICE);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        cleanSession(session);
        String command = request.getParameter(PARAM_COMMAND);
        switch (command) {
            case COMMAND_FIND_ALL_ENTRANTS:
                session.removeAttribute(SessionAttribute.FINAL_SHEET_IS_FORM_EMPTY);
                List<ReadyFinalEntrantSheetBean> passedEntrants =
                        (List<ReadyFinalEntrantSheetBean>) session.getAttribute(SessionAttribute.PASSED_ENTRANTS);
                if (passedEntrants == null) {
                    response.sendRedirect(Pages.PUBLIC_FINAL_SHEET_SERVLET);
                } else {
                    if (request.getParameter(PARAM_PAGE) != null)
                        page = Integer.parseInt(request.getParameter(PARAM_PAGE));
                    int numberOfRecords = passedEntrants.size();
                    int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);
                    if (page > numberOfPages) {
                        page = 1;
                        List<ReadyFinalEntrantSheetBean> passedEntrantsPagination = new ArrayList<>();
                        copyList(passedEntrants, passedEntrantsPagination, page, RECORDS_PER_PAGE);
                        setAttrFindAllCommand(session, request, numberOfPages, passedEntrantsPagination);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.PUBLIC_FINAL_SHEET_HTML);
                        requestDispatcher.forward(request, response);
                    } else {
                        List<ReadyFinalEntrantSheetBean> passedEntrantsPagination = new ArrayList<>();
                        copyList(passedEntrants, passedEntrantsPagination, page, RECORDS_PER_PAGE);
                        setAttrFindAllCommand(session, request, numberOfPages, passedEntrantsPagination);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.PUBLIC_FINAL_SHEET_HTML);
                        requestDispatcher.forward(request, response);
                    }
                }
                break;
            case COMMAND_FIND_ME:
                User user = (User) session.getAttribute(SessionAttribute.ACCOUNT);
                if (user == null) {
                    response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
                } else {
                    int userId = user.getId();
                    try {
                        ReadyFinalEntrantSheetBean currentEntrant = entrantFinalSheetService.getPassedEntrantByUserId(userId);
                        request.setAttribute(RequestAttribute.COMMAND_FIND, COMMAND_FIND_ME);
                        request.setAttribute(RequestAttribute.PASSED_ENTRANT_BY_USER_ID, currentEntrant);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.PUBLIC_FINAL_SHEET_HTML);
                        requestDispatcher.forward(request, response);
                    } catch (DaoSystemException e) {
                        e.printStackTrace();
                        if (e.getMessage().equals(ExceptionMessages.SELECT_BY_ID_EXCEPTION_MESSAGE)) {
                            request.setAttribute(RequestAttribute.IS_PASSED_ENTRANT_FINAL_SHEET, false);
                            request.setAttribute(RequestAttribute.COMMAND_FIND, COMMAND_FIND_ME);
                            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.PUBLIC_FINAL_SHEET_HTML);
                            requestDispatcher.forward(request, response);
                        } else {
                            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        }
                    }
                }
                break;
            case SEARCH_COMMAND:
                List<ReadyFinalEntrantSheetBean> searchedPassedEntrants =
                        (List<ReadyFinalEntrantSheetBean>) session.getAttribute(SessionAttribute.SEARCHED_PASSED_ENTRANTS);
                if (searchedPassedEntrants == null) {
                    session.setAttribute(SessionAttribute.FINAL_SHEET_IS_FOUND_SOMETHING, false);
                    response.sendRedirect(Pages.PUBLIC_FINAL_SHEET_HTML);
                } else {
                    session.setAttribute(SessionAttribute.FINAL_SHEET_IS_FOUND_SOMETHING, true);
                    if (request.getParameter(PARAM_PAGE) != null)
                        page = Integer.parseInt(request.getParameter(PARAM_PAGE));
                    int numberOfRecords = searchedPassedEntrants.size();
                    int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);
                    if (page > numberOfPages) {
                        page = 1;
                        List<ReadyFinalEntrantSheetBean> searchedPassedEntrantsPagination = new ArrayList<>();
                        copyList(searchedPassedEntrants, searchedPassedEntrantsPagination, page, RECORDS_PER_PAGE);
                        setAttrSearchCommand(session, request, numberOfPages, searchedPassedEntrants, searchedPassedEntrantsPagination);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.PUBLIC_FINAL_SHEET_HTML);
                        requestDispatcher.forward(request, response);
                    } else {
                        List<ReadyFinalEntrantSheetBean> searchedPassedEntrantsPagination = new ArrayList<>();
                        copyList(searchedPassedEntrants, searchedPassedEntrantsPagination, page, RECORDS_PER_PAGE);
                        setAttrSearchCommand(session, request, numberOfPages, searchedPassedEntrants, searchedPassedEntrantsPagination);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.PUBLIC_FINAL_SHEET_HTML);
                        requestDispatcher.forward(request, response);
                    }
                }
                break;
            default:
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                break;

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
                switch (searchCommand) {
                    case SEARCH_COMMAND_LAST_NAME_AND_FACULTY_NAME:
                        List<ReadyFinalEntrantSheetBean> passedEntrants =
                                (List<ReadyFinalEntrantSheetBean>) session.getAttribute(SessionAttribute.PASSED_ENTRANTS);
                        List<ReadyFinalEntrantSheetBean> searchedPassedEntrants = new ArrayList<>();
                        for (ReadyFinalEntrantSheetBean passedEntrant : passedEntrants) {
                            if (passedEntrant.getLastName().equals(lastName) && passedEntrant.getFacultyName().equals(facultyName)) {
                                searchedPassedEntrants.add(passedEntrant);
                            }
                        }
                        if (searchedPassedEntrants.size() == 0) {
                            session.setAttribute(SessionAttribute.FINAL_SHEET_IS_FOUND_SOMETHING, false);
                            response.sendRedirect(Pages.PUBLIC_FINAL_SHEET_SERVLET);
                        } else {
                            session.setAttribute(SessionAttribute.FINAL_SHEET_IS_FOUND_SOMETHING, true);
                            if (request.getParameter(PARAM_PAGE) != null)
                                page = Integer.parseInt(request.getParameter(PARAM_PAGE));
                            int numberOfRecords = searchedPassedEntrants.size();
                            int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);
                            if (page > numberOfPages) {
                                page = 1;
                                List<ReadyFinalEntrantSheetBean> searchedPassedEntrantsPagination = new ArrayList<>();
                                copyList(searchedPassedEntrants, searchedPassedEntrantsPagination, page, RECORDS_PER_PAGE);
                                setAttrSearchCommand(session, request, numberOfPages, searchedPassedEntrants, searchedPassedEntrantsPagination);
                                setUpFields(session, facultyName, lastName);
                                RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.PUBLIC_FINAL_SHEET_HTML);
                                requestDispatcher.forward(request, response);
                            } else {
                                List<ReadyFinalEntrantSheetBean> searchedPassedEntrantsPagination = new ArrayList<>();
                                copyList(searchedPassedEntrants, searchedPassedEntrantsPagination, page, RECORDS_PER_PAGE);
                                setAttrSearchCommand(session, request, numberOfPages, searchedPassedEntrants, searchedPassedEntrantsPagination);
                                setUpFields(session, facultyName, lastName);
                                RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.PUBLIC_FINAL_SHEET_HTML);
                                requestDispatcher.forward(request, response);
                            }
                        }
                        break;
                    case SEARCH_COMMAND_ONLY_FACULTY_NAME:
                        passedEntrants =
                                (List<ReadyFinalEntrantSheetBean>) session.getAttribute(SessionAttribute.PASSED_ENTRANTS);
                        searchedPassedEntrants = new ArrayList<>();
                        for (ReadyFinalEntrantSheetBean passedEntrant : passedEntrants) {
                            if (passedEntrant.getFacultyName().equals(facultyName)) {
                                searchedPassedEntrants.add(passedEntrant);
                            }
                        }
                        if (searchedPassedEntrants.size() == 0) {
                            session.setAttribute(SessionAttribute.FINAL_SHEET_IS_FOUND_SOMETHING, false);
                            response.sendRedirect(Pages.PUBLIC_FINAL_SHEET_HTML);
                        } else {
                            session.setAttribute(SessionAttribute.FINAL_SHEET_IS_FOUND_SOMETHING, true);
                            if (request.getParameter(PARAM_PAGE) != null)
                                page = Integer.parseInt(request.getParameter(PARAM_PAGE));
                            int numberOfRecords = searchedPassedEntrants.size();
                            int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);
                            if (page > numberOfPages) {
                                page = 1;
                                List<ReadyFinalEntrantSheetBean> searchedPassedEntrantsPagination = new ArrayList<>();
                                copyList(searchedPassedEntrants, searchedPassedEntrantsPagination, page, RECORDS_PER_PAGE);
                                setAttrSearchCommand(session, request, numberOfPages, searchedPassedEntrants, searchedPassedEntrantsPagination);
                                setUpFields(session, facultyName, lastName);
                                RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.PUBLIC_FINAL_SHEET_HTML);
                                requestDispatcher.forward(request, response);
                            } else {
                                List<ReadyFinalEntrantSheetBean> searchedPassedEntrantsPagination = new ArrayList<>();
                                copyList(searchedPassedEntrants, searchedPassedEntrantsPagination, page, RECORDS_PER_PAGE);
                                setAttrSearchCommand(session, request, numberOfPages, searchedPassedEntrants, searchedPassedEntrantsPagination);
                                setUpFields(session, facultyName, lastName);
                                RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.PUBLIC_FINAL_SHEET_HTML);
                                requestDispatcher.forward(request, response);
                            }
                        }
                        break;
                    case SEARCH_COMMAND_ONLY_LAST_NAME:
                        passedEntrants =
                                (List<ReadyFinalEntrantSheetBean>) session.getAttribute(SessionAttribute.PASSED_ENTRANTS);
                        searchedPassedEntrants = new ArrayList<>();
                        for (ReadyFinalEntrantSheetBean passedEntrant : passedEntrants) {
                            if (passedEntrant.getLastName().equals(lastName)) {
                                searchedPassedEntrants.add(passedEntrant);
                            }
                        }
                        if (searchedPassedEntrants.size() == 0) {
                            session.setAttribute(SessionAttribute.FINAL_SHEET_IS_FOUND_SOMETHING, false);
                            response.sendRedirect(Pages.PUBLIC_FINAL_SHEET_HTML);
                        } else {
                            session.setAttribute(SessionAttribute.FINAL_SHEET_IS_FOUND_SOMETHING, true);
                            if (request.getParameter(PARAM_PAGE) != null)
                                page = Integer.parseInt(request.getParameter(PARAM_PAGE));
                            int numberOfRecords = searchedPassedEntrants.size();
                            int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);
                            if (page > numberOfPages) {
                                page = 1;
                                List<ReadyFinalEntrantSheetBean> searchedPassedEntrantsPagination = new ArrayList<>();
                                copyList(searchedPassedEntrants, searchedPassedEntrantsPagination, page, RECORDS_PER_PAGE);
                                setAttrSearchCommand(session, request, numberOfPages, searchedPassedEntrants, searchedPassedEntrantsPagination);
                                setUpFields(session, facultyName, lastName);
                                RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.PUBLIC_FINAL_SHEET_HTML);
                                requestDispatcher.forward(request, response);
                            } else {
                                List<ReadyFinalEntrantSheetBean> searchedPassedEntrantsPagination = new ArrayList<>();
                                copyList(searchedPassedEntrants, searchedPassedEntrantsPagination, page, RECORDS_PER_PAGE);
                                setAttrSearchCommand(session, request, numberOfPages, searchedPassedEntrants, searchedPassedEntrantsPagination);
                                setUpFields(session, facultyName, lastName);
                                RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.PUBLIC_FINAL_SHEET_HTML);
                                requestDispatcher.forward(request, response);
                            }
                        }
                        break;
                }
            }
        }
    }

    private void copyList(
            List<ReadyFinalEntrantSheetBean> passedEntrants,
            List<ReadyFinalEntrantSheetBean> passedEntrantsPagination,
            int page,
            int recordsPerPage) {
        passedEntrantsPagination.clear();
        int lastElementOnPage = (page - 1) * recordsPerPage + recordsPerPage;
        if (lastElementOnPage > passedEntrants.size()) {
            lastElementOnPage = passedEntrants.size();
        }
        for (int i = (page - 1) * recordsPerPage; i < lastElementOnPage; i++) {
            passedEntrantsPagination.add(passedEntrants.get(i));
        }
    }

    private void setAttrSearchCommand(HttpSession session,
                                      HttpServletRequest request, int numberOfPages,
                                      List<ReadyFinalEntrantSheetBean> searchedPassedEntrants,
                                      List<ReadyFinalEntrantSheetBean> searchedPassedEntrantsPagination) {
        session.setAttribute(SessionAttribute.NUMBER_OF_PAGES, numberOfPages);
        session.setAttribute(SessionAttribute.CURRENT_PAGE, page);
        session.setAttribute(SessionAttribute.SEARCHED_PASSED_ENTRANTS, searchedPassedEntrants);
        session.setAttribute(SessionAttribute.PASSED_ENTRANTS_PAGINATION, searchedPassedEntrantsPagination);
        request.setAttribute(RequestAttribute.COMMAND_FIND, SEARCH_COMMAND);
    }


    private void setAttrFindAllCommand(HttpSession session, HttpServletRequest request,
                                       int numberOfPages,
                                       List<ReadyFinalEntrantSheetBean> passedEntrantsPagination) {
        session.setAttribute(SessionAttribute.NUMBER_OF_PAGES, numberOfPages);
        session.setAttribute(SessionAttribute.CURRENT_PAGE, page);
        session.setAttribute(SessionAttribute.PASSED_ENTRANTS_PAGINATION, passedEntrantsPagination);
        request.setAttribute(RequestAttribute.COMMAND_FIND, COMMAND_FIND_ALL_ENTRANTS);
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
            isValidLastName = Validation.validateLastName(lastName);
            if (!isValidLastName) {
                session.setAttribute(SessionAttribute.FINAL_SHEET_IS_VALID_LAST_NAME, false);
            }
        }
        if (!facultyName.equals(EMPTY_PARAM)) {
            isValidFacultyName = Validation.validateFacultyName(facultyName);
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
    }

    private void setUpFields(HttpSession session, String facultyName, String lastName) {
        session.setAttribute(SessionAttribute.FINAL_SHEET_LAST_NAME, lastName);
        session.setAttribute(SessionAttribute.FINAL_SHEET_FACULTY_NAME, facultyName);
    }
}
