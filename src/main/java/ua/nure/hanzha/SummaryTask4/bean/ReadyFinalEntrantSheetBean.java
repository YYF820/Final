package ua.nure.hanzha.SummaryTask4.bean;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 14/08/15.
 */
public class ReadyFinalEntrantSheetBean {

    private String facultyName;
    private String lastName;
    private String firstName;
    private String patronymic;
    private double sumOfMarks;
    private String enterUniversityStatus;

    public String getEnterUniversityStatus() {
        return enterUniversityStatus;
    }

    public void setEnterUniversityStatus(String enterUniversityStatus) {
        this.enterUniversityStatus = enterUniversityStatus;
    }

    public double getSumOfMarks() {
        return sumOfMarks;
    }

    public void setSumOfMarks(double sumOfMarks) {
        this.sumOfMarks = sumOfMarks;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFacultyName() {
        return facultyName;
    }

    public void setFacultyName(String facultyName) {
        this.facultyName = facultyName;
    }
}
