package ua.nure.hanzha.SummaryTask4.bean;

import java.util.List;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 13/08/15.
 */
public class FacultyFinalSheetBean {

    private int facultyId;
    private List<EntrantFinalSheetBean> entrants;
    private int totalSpots;
    private int budgetSpots;

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public List<EntrantFinalSheetBean> getEntrants() {
        return entrants;
    }

    public void setEntrants(List<EntrantFinalSheetBean> entrants) {
        this.entrants = entrants;
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
}
