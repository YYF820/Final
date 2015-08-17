package ua.nure.hanzha.SummaryTask4.servlet.admin;

import ua.nure.hanzha.SummaryTask4.bean.EntrantFinalSheetBean;
import ua.nure.hanzha.SummaryTask4.bean.FacultyFinalSheetBean;
import ua.nure.hanzha.SummaryTask4.bean.UniversityFinalSheetBean;
import ua.nure.hanzha.SummaryTask4.constants.*;
import ua.nure.hanzha.SummaryTask4.entity.EntrantFinalSheet;
import ua.nure.hanzha.SummaryTask4.entity.Faculty;
import ua.nure.hanzha.SummaryTask4.entity.User;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.entrant.EntrantService;
import ua.nure.hanzha.SummaryTask4.service.entrantFinalSheet.EntrantFinalSheetService;
import ua.nure.hanzha.SummaryTask4.service.faculty.FacultyService;
import ua.nure.hanzha.SummaryTask4.service.facultyEntrant.FacultyEntrantService;
import ua.nure.hanzha.SummaryTask4.service.user.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 13/08/15.
 */


public class GenerateFinalSheetServlet extends HttpServlet {


    private static final String EXCEPTION_MESSAGE_NOT_ENROLLED_STUDENT = "Entrant doesn't enroll to any faculty.";
    private static final String ADMIN_COMMAND_NOTIFY_ENTRANTS_WHO_PASSED = "notifyEntrantsWhoPassed";


    private FacultyEntrantService facultyEntrantService;
    private FacultyService facultyService;
    private EntrantService entrantService;
    private UserService userService;
    private EntrantFinalSheetService entrantFinalSheetService;

    @Override
    public void init() throws ServletException {
        facultyEntrantService = (FacultyEntrantService) getServletContext().getAttribute(AppAttribute.FACULTY_ENTRANT_SERVICE);
        facultyService = (FacultyService) getServletContext().getAttribute(AppAttribute.FACULTY_SERVICE);
        entrantService = (EntrantService) getServletContext().getAttribute(AppAttribute.ENTRANT_SERVICE);
        userService = (UserService) getServletContext().getAttribute(AppAttribute.USER_SERVICE);
        entrantFinalSheetService =
                (EntrantFinalSheetService) getServletContext().getAttribute(AppAttribute.ENTRANT_FINAL_SHEET_SERVICE);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            facultyEntrantService.summAllMarks();
            List<Faculty> allFaculties = facultyService.getAllFacultiesSubjectsMoreThanThree();
            List<FacultyFinalSheetBean> allFacultiesFinalSheetBeans = prepareFaculties(allFaculties);
            List<Integer> enrolledEntrantsId = entrantService.getAllIdsActiveStatus();
            List<EntrantFinalSheetBean> enrolledEntrantsFinalSheetBeans = prepareEntrants(enrolledEntrantsId, response);
            sortEnrolledEntrants(enrolledEntrantsFinalSheetBeans);
            List<EntrantFinalSheetBean> notPassedEntrants = new ArrayList<>();
            generateFinalSheet(enrolledEntrantsFinalSheetBeans, allFacultiesFinalSheetBeans, notPassedEntrants);
            addToDataBasePassedEntrants(allFacultiesFinalSheetBeans, response);
            UniversityFinalSheetBean universityFinalSheetBean =
                    prepareUniversityFinalSheetBean(allFacultiesFinalSheetBeans, notPassedEntrants);
            HttpSession session = request.getSession(false);
            session.setAttribute(SessionAttribute.COMMAND, ADMIN_COMMAND_NOTIFY_ENTRANTS_WHO_PASSED);
            request.setAttribute(RequestAttribute.UNIVERSITY_FINAL_SHEET_BEAN, universityFinalSheetBean);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(Pages.MAIL_SENDER_SERVLET);
            requestDispatcher.forward(request, response);
        } catch (DaoSystemException e) {
            e.printStackTrace();
        }
    }

    private List<FacultyFinalSheetBean> prepareFaculties(List<Faculty> allFaculties) {
        List<FacultyFinalSheetBean> allFacultiesFinalSheetBeans = new ArrayList<>();
        for (Faculty faculty : allFaculties) {
            FacultyFinalSheetBean facultyFinalSheetBean = new FacultyFinalSheetBean();
            List<EntrantFinalSheetBean> budgetEntrants = new ArrayList<>();
            List<EntrantFinalSheetBean> contractEntrants = new ArrayList<>();
            facultyFinalSheetBean.setFacultyId(faculty.getId());
            facultyFinalSheetBean.setFacultyName(faculty.getName());
            facultyFinalSheetBean.setBudgetSpots(faculty.getBudgetSpots());
            facultyFinalSheetBean.setTotalSpots(faculty.getTotalSpots());
            facultyFinalSheetBean.setBudgetEntrants(budgetEntrants);
            facultyFinalSheetBean.setContractEntrants(contractEntrants);
            allFacultiesFinalSheetBeans.add(facultyFinalSheetBean);
        }
        return allFacultiesFinalSheetBeans;
    }

    private List<EntrantFinalSheetBean> prepareEntrants(List<Integer> enrolledEntrantsId,
                                                        HttpServletResponse response) throws IOException {
        List<EntrantFinalSheetBean> enrolledEntrantsFinalSheetBeans = new ArrayList<>();
        try {
            for (Integer entrantId : enrolledEntrantsId) {
                EntrantFinalSheetBean enrolledEntrant = facultyEntrantService.getEntrantBeanByEntrantId(entrantId);
                if (enrolledEntrant != null) {
                    enrolledEntrantsFinalSheetBeans.add(enrolledEntrant);
                }
            }
            for (EntrantFinalSheetBean enrolledEntrantsFinalSheetBean : enrolledEntrantsFinalSheetBeans) {
                int entrantId = enrolledEntrantsFinalSheetBean.getEntrantId();
                int userId = entrantService.getUserIdByEntrantId(entrantId);
                User user = userService.getById(userId);
                enrolledEntrantsFinalSheetBean.setFirstName(user.getFirstName());
                enrolledEntrantsFinalSheetBean.setLastName(user.getLastName());
                enrolledEntrantsFinalSheetBean.setPatronymic(user.getPatronymic());
                enrolledEntrantsFinalSheetBean.setAccountName(user.getEmail());
            }
        } catch (DaoSystemException e) {
            if (e.getMessage().equals(EXCEPTION_MESSAGE_NOT_ENROLLED_STUDENT)) {
                // LOG4j NOP
            } else if (e.getMessage().equals(ExceptionMessages.SELECT_BY_ID_EXCEPTION_MESSAGE)) {
                // LOG4j NOP
            } else {
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }
        return enrolledEntrantsFinalSheetBeans;
    }

    private void sortEnrolledEntrants(List<EntrantFinalSheetBean> enrolledEntrants) {
        Collections.sort(enrolledEntrants, new Comparator<EntrantFinalSheetBean>() {
            @Override
            public int compare(EntrantFinalSheetBean o1, EntrantFinalSheetBean o2) {
                double firstEntrantSumOfMarks = o1.getSumOfMarks();
                double secondEntrantSumOfMarks = o2.getSumOfMarks();
                if (firstEntrantSumOfMarks < secondEntrantSumOfMarks) {
                    return 1;
                } else if (firstEntrantSumOfMarks > secondEntrantSumOfMarks) {
                    return -1;
                } else {
                    return 0;
                }
            }
        });
    }

    private boolean checkIsEndOfBudgetSpots(List<FacultyFinalSheetBean> allFacultiesFinalSheetBeans) {
        int checkCounter = 0;
        for (FacultyFinalSheetBean allFacultiesFinalSheetBean : allFacultiesFinalSheetBeans) {
            if (allFacultiesFinalSheetBean.getBudgetEntrants().size() > allFacultiesFinalSheetBean.getBudgetSpots()) {
                checkCounter++;
            }
        }
        return checkCounter == allFacultiesFinalSheetBeans.size();
    }

    private boolean checkIsEndOfTotalSpots(List<FacultyFinalSheetBean> allFacultiesFinalSheetBeans) {
        int checkCounter = 0;
        for (FacultyFinalSheetBean allFacultiesFinalSheetBean : allFacultiesFinalSheetBeans) {
            if (allFacultiesFinalSheetBean.getContractEntrants().size() >
                    allFacultiesFinalSheetBean.getTotalSpots() - allFacultiesFinalSheetBean.getBudgetSpots()) {
                checkCounter++;
            }
        }
        return checkCounter == allFacultiesFinalSheetBeans.size();
    }

    private void generateFinalSheet(List<EntrantFinalSheetBean> enrolledEntrantsFinalSheetBeans,
                                    List<FacultyFinalSheetBean> allFacultiesFinalSheetBeans,
                                    List<EntrantFinalSheetBean> notPassedEntrants) {
        m1:
        for (EntrantFinalSheetBean enrolledEntrantsFinalSheetBean : enrolledEntrantsFinalSheetBeans) {
            if (checkIsEndOfBudgetSpots(allFacultiesFinalSheetBeans)) {
                if (!checkIsEndOfTotalSpots(allFacultiesFinalSheetBeans)) {
                    Map<Integer, Integer> priorityFacultyId = enrolledEntrantsFinalSheetBean.getPriorityFacultyPair();
                    for (Map.Entry<Integer, Integer> pair : priorityFacultyId.entrySet()) {
                        int facultyId = pair.getValue();
                        for (FacultyFinalSheetBean allFacultiesFinalSheetBean : allFacultiesFinalSheetBeans) {
                            if (allFacultiesFinalSheetBean.getFacultyId() == facultyId &&
                                    allFacultiesFinalSheetBean.getContractEntrants().size() < allFacultiesFinalSheetBean.getTotalSpots() - allFacultiesFinalSheetBean.getBudgetSpots()) {
                                List<EntrantFinalSheetBean> contractEntrants = allFacultiesFinalSheetBean.getContractEntrants();
                                contractEntrants.add(enrolledEntrantsFinalSheetBean);
                                continue m1;
                            }
                        }
                        notPassedEntrants.add(enrolledEntrantsFinalSheetBean);
                        continue m1;
                    }
                } else {
                    notPassedEntrants.add(enrolledEntrantsFinalSheetBean);
                }
            } else {
                Map<Integer, Integer> priorityFacultyId = enrolledEntrantsFinalSheetBean.getPriorityFacultyPair();
                for (Map.Entry<Integer, Integer> pair : priorityFacultyId.entrySet()) {
                    int facultyId = pair.getValue();
                    for (FacultyFinalSheetBean allFacultiesFinalSheetBean : allFacultiesFinalSheetBeans) {
                        if (allFacultiesFinalSheetBean.getFacultyId() == facultyId && allFacultiesFinalSheetBean.getBudgetEntrants().size() < allFacultiesFinalSheetBean.getBudgetSpots()) {
                            List<EntrantFinalSheetBean> budgetEntrants = allFacultiesFinalSheetBean.getBudgetEntrants();
                            budgetEntrants.add(enrolledEntrantsFinalSheetBean);
                            continue m1;
                        }
                    }
                    for (FacultyFinalSheetBean allFacultiesFinalSheetBean : allFacultiesFinalSheetBeans) {
                        if (allFacultiesFinalSheetBean.getFacultyId() == facultyId &&
                                allFacultiesFinalSheetBean.getContractEntrants().size() < allFacultiesFinalSheetBean.getTotalSpots() - allFacultiesFinalSheetBean.getBudgetSpots()) {
                            List<EntrantFinalSheetBean> contractEntrants = allFacultiesFinalSheetBean.getContractEntrants();
                            contractEntrants.add(enrolledEntrantsFinalSheetBean);
                            continue m1;
                        }

                    }
                    notPassedEntrants.add(enrolledEntrantsFinalSheetBean);
                    continue m1;
                }
            }
        }
    }

    private void addToDataBasePassedEntrants(List<FacultyFinalSheetBean> allFacultiesFinalSheetBeans,
                                             HttpServletResponse response) throws IOException {
        try {
            Integer numberOfSheet = entrantFinalSheetService.getMaxIncrementedNumberOfSheet();
            for (FacultyFinalSheetBean allFacultiesFinalSheetBean : allFacultiesFinalSheetBeans) {
                List<EntrantFinalSheetBean> budgetEntrants = allFacultiesFinalSheetBean.getBudgetEntrants();
                for (EntrantFinalSheetBean budgetEntrant : budgetEntrants) {
                    int entrantId = budgetEntrant.getEntrantId();
                    int facultyId = allFacultiesFinalSheetBean.getFacultyId();
                    int enterUniversityStatusId = EnterUniversityStatuses.BUDGET;
                    EntrantFinalSheet entrantFinalSheet = new EntrantFinalSheet();
                    entrantFinalSheet.setFacultyId(facultyId);
                    entrantFinalSheet.setEntrantId(entrantId);
                    entrantFinalSheet.setEntrantUniversityStatusId(enterUniversityStatusId);
                    entrantFinalSheet.setNumberOfSheet(numberOfSheet);
                    entrantFinalSheetService.addEntrantToFinalSheet(entrantFinalSheet);
                }
                List<EntrantFinalSheetBean> contractEntrants = allFacultiesFinalSheetBean.getContractEntrants();
                for (EntrantFinalSheetBean contractEntrant : contractEntrants) {
                    int entrantId = contractEntrant.getEntrantId();
                    int facultyId = allFacultiesFinalSheetBean.getFacultyId();
                    int enterUniversityStatusId = EnterUniversityStatuses.CONTRACT;
                    EntrantFinalSheet entrantFinalSheet = new EntrantFinalSheet();
                    entrantFinalSheet.setFacultyId(facultyId);
                    entrantFinalSheet.setEntrantId(entrantId);
                    entrantFinalSheet.setEntrantUniversityStatusId(enterUniversityStatusId);
                    entrantFinalSheet.setNumberOfSheet(numberOfSheet);
                    entrantFinalSheetService.addEntrantToFinalSheet(entrantFinalSheet);
                }
            }
        } catch (DaoSystemException e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }

    private UniversityFinalSheetBean prepareUniversityFinalSheetBean(List<FacultyFinalSheetBean> allFacultiesFinalSheetBeans,
                                                                     List<EntrantFinalSheetBean> notPassedEntrants) {
        UniversityFinalSheetBean universityFinalSheetBean = new UniversityFinalSheetBean();
        universityFinalSheetBean.setFacultiesFinalSheetBean(allFacultiesFinalSheetBeans);
        universityFinalSheetBean.setNotPassedEntrants(notPassedEntrants);
        return universityFinalSheetBean;
    }
}
