package ua.nure.hanzha.SummaryTask4.bean;

import java.util.List;
import java.util.Map;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 13/08/15.
 */
public class FacultyFinalSheetBean {

    private int facultyId;
    private String facultyName;


    private List<EntrantFinalSheetBean> budgetEntrants;
    private List<EntrantFinalSheetBean> contractEntrants;
    private int totalSpots;
    private int budgetSpots;

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public List<EntrantFinalSheetBean> getBudgetEntrants() {
        return budgetEntrants;
    }

    public void setBudgetEntrants(List<EntrantFinalSheetBean> budgetEntrants) {
        this.budgetEntrants = budgetEntrants;
    }

    public List<EntrantFinalSheetBean> getContractEntrants() {
        return contractEntrants;
    }

    public void setContractEntrants(List<EntrantFinalSheetBean> contractEntrants) {
        this.contractEntrants = contractEntrants;
    }

    public int getTotalSpots() {
        return totalSpots;
    }

    public void setTotalSpots(int totalSpots) {
        this.totalSpots = totalSpots;
    }

    public int getBudgetSpots() {
        return budgetSpots;
    }

    public void setBudgetSpots(int budgetSpots) {
        this.budgetSpots = budgetSpots;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }


    @Override
    public String toString() {
        StringBuilder sbBudgetEntrants = new StringBuilder();
        for (EntrantFinalSheetBean entrant : budgetEntrants) {
            sbBudgetEntrants.append("\tEntrant Id:").append(entrant.getEntrantId())
                    .append("\tFirst name: ").append(entrant.getFirstName())
                    .append("\tLast name: ").append(entrant.getLastName())
                    .append("\tPatronymic: ").append(entrant.getPatronymic())
                    .append("\tsum of marks: ").append(entrant.getSumOfMarks());
            for (Map.Entry<Integer, Integer> priorityFacultyIdPair : entrant.getPriorityFacultyPair().entrySet()) {
                if (priorityFacultyIdPair.getValue() == facultyId) {
                    sbBudgetEntrants.append("\t").append(priorityFacultyIdPair.getKey());
                }
            }
            sbBudgetEntrants.append("\n");
        }

        StringBuilder sbContractEntrants = new StringBuilder();
        for (EntrantFinalSheetBean entrant : contractEntrants) {
            sbContractEntrants.append("\tEntrant Id:").append(entrant.getEntrantId())
                    .append("\tFirst name: ").append(entrant.getFirstName())
                    .append("\tLast name: ").append(entrant.getLastName())
                    .append("\tPatronymic: ").append(entrant.getPatronymic())
                    .append("\tsum of marks: ").append(entrant.getSumOfMarks());
            for (Map.Entry<Integer, Integer> priorityFacultyIdPair : entrant.getPriorityFacultyPair().entrySet()) {
                if (priorityFacultyIdPair.getValue() == facultyId) {
                    sbContractEntrants.append("\t").append(priorityFacultyIdPair.getKey());
                }
            }
            sbContractEntrants.append("\n");
        }
        return "FACULTY id = " + facultyId +
                " BUDGET LIST SIZE: " + budgetEntrants.size() +
                " CONTRACT LIST SIZE: " + contractEntrants.size() +
                " totalSpots = " + totalSpots +
                " budget spots: " + budgetSpots +
                "\n\t BUDGET ENTRANTS:\n" + sbBudgetEntrants.toString() +
                "\tCONTRACT ENTRANTS:\n" + sbContractEntrants;
    }
}
