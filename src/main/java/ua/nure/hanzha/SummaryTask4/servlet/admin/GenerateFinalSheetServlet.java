package ua.nure.hanzha.SummaryTask4.servlet.admin;

import ua.nure.hanzha.SummaryTask4.bean.EntrantFinalSheetBean;
import ua.nure.hanzha.SummaryTask4.bean.FacultyFinalSheetBean;
import ua.nure.hanzha.SummaryTask4.constants.AppAttribute;
import ua.nure.hanzha.SummaryTask4.constants.Pages;
import ua.nure.hanzha.SummaryTask4.entity.Faculty;
import ua.nure.hanzha.SummaryTask4.entity.FacultyEntrant;
import ua.nure.hanzha.SummaryTask4.exception.DaoSystemException;
import ua.nure.hanzha.SummaryTask4.service.entrant.EntrantService;
import ua.nure.hanzha.SummaryTask4.service.faculty.FacultyService;
import ua.nure.hanzha.SummaryTask4.service.facultyEntrant.FacultyEntrantService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 13/08/15.
 */


public class GenerateFinalSheetServlet extends HttpServlet {


    private static final String EXCEPTION_MESSAGE_NOT_ENROLLED_STUDENT = "Entrant doesn't enroll to any faculty.";
    private FacultyEntrantService facultyEntrantService;
    private FacultyService facultyService;
    private EntrantService entrantService;

    @Override
    public void init() throws ServletException {
        facultyEntrantService = (FacultyEntrantService) getServletContext().getAttribute(AppAttribute.FACULTY_ENTRANT_SERVICE);
        facultyService = (FacultyService) getServletContext().getAttribute(AppAttribute.FACULTY_SERVICE);
        entrantService = (EntrantService) getServletContext().getAttribute(AppAttribute.ENTRANT_SERVICE);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            facultyEntrantService.summAllMarks();
            List<Faculty> allFaculties = facultyService.getAllFacultiesSubjectsMoreThanThree();
            List<FacultyFinalSheetBean> allFacultiesFinalSheetBeans = prepareFaculties(allFaculties);
            List<Integer> enrolledEntrantsId = entrantService.getAllIds();
            List<EntrantFinalSheetBean> enrolledEntrantsFinalSheetBeans = prepareEntrants(enrolledEntrantsId, response);
            sortEnrolledEntrants(enrolledEntrantsFinalSheetBeans);
           


        } catch (DaoSystemException e) {
            e.printStackTrace();
        }
    }

    private List<FacultyFinalSheetBean> prepareFaculties(List<Faculty> allFaculties) {
        List<FacultyFinalSheetBean> allFacultiesFinalSheetBeans = new ArrayList<>();
        for (Faculty faculty : allFaculties) {
            FacultyFinalSheetBean facultyFinalSheetBean = new FacultyFinalSheetBean();
            List<EntrantFinalSheetBean> entrantFinalSheetBeans = new ArrayList<>();
            facultyFinalSheetBean.setFacultyId(faculty.getId());
            facultyFinalSheetBean.setBudgetSpots(faculty.getBudgetSpots());
            facultyFinalSheetBean.setTotalSpots(faculty.getTotalSpots());
            facultyFinalSheetBean.setEntrants(entrantFinalSheetBeans);
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
        } catch (DaoSystemException e) {
            if (e.getMessage().equals(EXCEPTION_MESSAGE_NOT_ENROLLED_STUDENT)) {
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
}
