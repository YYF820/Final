package ua.nure.hanzha.SummaryTask4.entity;

/**
 * EntrantFinalSheet entity from table summarytask4#Entrants_Final_Sheets
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public class EntrantFinalSheet implements SkeletonEntity {
    private int facultyId;
    private int entrantId;
    private int entrantUniversityStatusId;
    private int numberOfSheet;

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public int getEntrantId() {
        return entrantId;
    }

    public void setEntrantId(int entrantId) {
        this.entrantId = entrantId;
    }

    public int getEntrantUniversityStatusId() {
        return entrantUniversityStatusId;
    }

    public void setEntrantUniversityStatusId(int entrantUniversityStatusId) {
        this.entrantUniversityStatusId = entrantUniversityStatusId;
    }

    public int getNumberOfSheet() {
        return numberOfSheet;
    }

    public void setNumberOfSheet(int numberOfSheet) {
        this.numberOfSheet = numberOfSheet;
    }

    @Override
    public String toString() {
        return "\tFaculty ID: " + facultyId +
                "\tEntrant ID: " + entrantId +
                "\tentrant university status id: " + entrantUniversityStatusId +
                "\tNumber of entrant sheet: " + numberOfSheet;
    }
}
