package ua.nure.hanzha.SummaryTask4.servlet.callable.finalSheet;

import ua.nure.hanzha.SummaryTask4.bean.ReadyFinalEntrantSheetBean;
import ua.nure.hanzha.SummaryTask4.constants.ExceptionMessages;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.constants.RequestAttribute;
import ua.nure.hanzha.SummaryTask4.constants.SessionAttribute;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.entrantFinalSheet.EntrantFinalSheetService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 18/08/15.
 */
public class FinalSheetOperationsMap {

    private static final String COMMAND_FIND_ALL_ENTRANTS = "findAllEntrants";
    private static final String COMMAND_FIND_ME = "findMe";
    private static final String SEARCH_COMMAND = "search";

    private static final String SEARCH_COMMAND_ONLY_LAST_NAME = "lastName";
    private static final String SEARCH_COMMAND_ONLY_FACULTY_NAME = "facultyName";
    private static final String SEARCH_COMMAND_LAST_NAME_AND_FACULTY_NAME = "searchLastNameAndFacultyName";

    private static final String PARAM_PAGE = "page";
    private static final int RECORDS_PER_PAGE = 17;
    private static int page = 1;

    private static FinalSheetOperationsMap finalSheetOperationsMap;
    private static Map<String, FinalSheetCallable> finalSheetCallableMap;

    private FinalSheetOperationsMap() {

    }

    public static FinalSheetOperationsMap getInstance() {
        if (finalSheetOperationsMap == null) {
            finalSheetOperationsMap = new FinalSheetOperationsMap();
        }
        return finalSheetOperationsMap;
    }

    public static FinalSheetCallable getFinalSheetCallable(String command) {
        return finalSheetCallableMap.get(command);
    }

    public static void initFinalSheetCallableMap(final HttpSession session,
                                                 final HttpServletResponse response,
                                                 final HttpServletRequest request,
                                                 final EntrantFinalSheetService entrantFinalSheetService,
                                                 final String lastName,
                                                 final String facultyName) {
        finalSheetCallableMap = new HashMap<>();
        finalSheetCallableMap.put(
                COMMAND_FIND_ALL_ENTRANTS,
                new FinalSheetCallable() {
                    @Override
                    public void call() throws IOException, ServletException {
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
                    }
                }
        );
        finalSheetCallableMap.put(
                COMMAND_FIND_ME,
                new FinalSheetCallable() {
                    @Override
                    public void call() throws IOException, ServletException {
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
                    }
                }
        );
        finalSheetCallableMap.put(
                SEARCH_COMMAND,
                new FinalSheetCallable() {
                    @Override
                    public void call() throws IOException, ServletException {
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
                    }
                }
        );
        finalSheetCallableMap.put(
                SEARCH_COMMAND_LAST_NAME_AND_FACULTY_NAME,
                new FinalSheetCallable() {
                    @Override
                    public void call() throws IOException, ServletException {
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
                            response.sendRedirect(Pages.PUBLIC_FINAL_SHEET_HTML);
                        } else {
                            session.setAttribute(SessionAttribute.FINAL_SHEET_IS_FOUND_SOMETHING, true);
                            int numberOfRecords = searchedPassedEntrants.size();
                            int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);
                            page = 1;
                            List<ReadyFinalEntrantSheetBean> searchedPassedEntrantsPagination = new ArrayList<>();
                            copyList(searchedPassedEntrants, searchedPassedEntrantsPagination, page, RECORDS_PER_PAGE);
                            setAttrSearchCommand(session, request, numberOfPages, searchedPassedEntrants, searchedPassedEntrantsPagination);
                            setUpFields(session, facultyName, lastName);
                            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.PUBLIC_FINAL_SHEET_HTML);
                            requestDispatcher.forward(request, response);
                        }
                    }
                }
        );
        finalSheetCallableMap.put(
                SEARCH_COMMAND_ONLY_FACULTY_NAME,
                new FinalSheetCallable() {
                    @Override
                    public void call() throws IOException, ServletException {
                        List<ReadyFinalEntrantSheetBean> passedEntrants =
                                (List<ReadyFinalEntrantSheetBean>) session.getAttribute(SessionAttribute.PASSED_ENTRANTS);
                        List<ReadyFinalEntrantSheetBean> searchedPassedEntrants = new ArrayList<>();
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
                            int numberOfRecords = searchedPassedEntrants.size();
                            int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);
                            page = 1;
                            List<ReadyFinalEntrantSheetBean> searchedPassedEntrantsPagination = new ArrayList<>();
                            copyList(searchedPassedEntrants, searchedPassedEntrantsPagination, page, RECORDS_PER_PAGE);
                            setAttrSearchCommand(session, request, numberOfPages, searchedPassedEntrants, searchedPassedEntrantsPagination);
                            setUpFields(session, facultyName, lastName);
                            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.PUBLIC_FINAL_SHEET_HTML);
                            requestDispatcher.forward(request, response);
                        }
                    }
                }
        );
        finalSheetCallableMap.put(
                SEARCH_COMMAND_ONLY_LAST_NAME,
                new FinalSheetCallable() {
                    @Override
                    public void call() throws IOException, ServletException {
                        List<ReadyFinalEntrantSheetBean> passedEntrants =
                                (List<ReadyFinalEntrantSheetBean>) session.getAttribute(SessionAttribute.PASSED_ENTRANTS);
                        List<ReadyFinalEntrantSheetBean> searchedPassedEntrants = new ArrayList<>();
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
                            int numberOfRecords = searchedPassedEntrants.size();
                            int numberOfPages = (int) Math.ceil(numberOfRecords * 1.0 / RECORDS_PER_PAGE);
                            page = 1;
                            List<ReadyFinalEntrantSheetBean> searchedPassedEntrantsPagination = new ArrayList<>();
                            copyList(searchedPassedEntrants, searchedPassedEntrantsPagination, page, RECORDS_PER_PAGE);
                            setAttrSearchCommand(session, request, numberOfPages, searchedPassedEntrants, searchedPassedEntrantsPagination);
                            setUpFields(session, facultyName, lastName);
                            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.PUBLIC_FINAL_SHEET_HTML);
                            requestDispatcher.forward(request, response);
                        }
                    }
                }
        );
    }

    private static void copyList(
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

    private static void setAttrSearchCommand(HttpSession session,
                                             HttpServletRequest request, int numberOfPages,
                                             List<ReadyFinalEntrantSheetBean> searchedPassedEntrants,
                                             List<ReadyFinalEntrantSheetBean> searchedPassedEntrantsPagination) {
        session.setAttribute(SessionAttribute.NUMBER_OF_PAGES, numberOfPages);
        session.setAttribute(SessionAttribute.CURRENT_PAGE, page);
        session.setAttribute(SessionAttribute.SEARCHED_PASSED_ENTRANTS, searchedPassedEntrants);
        session.setAttribute(SessionAttribute.PASSED_ENTRANTS_PAGINATION, searchedPassedEntrantsPagination);
        request.setAttribute(RequestAttribute.COMMAND_FIND, SEARCH_COMMAND);
    }


    private static void setAttrFindAllCommand(HttpSession session, HttpServletRequest request,
                                              int numberOfPages,
                                              List<ReadyFinalEntrantSheetBean> passedEntrantsPagination) {
        session.setAttribute(SessionAttribute.NUMBER_OF_PAGES, numberOfPages);
        session.setAttribute(SessionAttribute.CURRENT_PAGE, page);
        session.setAttribute(SessionAttribute.PASSED_ENTRANTS_PAGINATION, passedEntrantsPagination);
        request.setAttribute(RequestAttribute.COMMAND_FIND, COMMAND_FIND_ALL_ENTRANTS);
    }

    private static void setUpFields(HttpSession session, String facultyName, String lastName) {
        session.setAttribute(SessionAttribute.FINAL_SHEET_LAST_NAME, lastName);
        session.setAttribute(SessionAttribute.FINAL_SHEET_FACULTY_NAME, facultyName);
    }


}
