package ua.nure.hanzha.SummaryTask4.bean;

import java.util.Map;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 13/08/15.
 */
public class EntrantFinalSheetBean {

    private int entrantId;
    private double sumOfMarks;
    private Map<Integer, Integer> priorityFacultyPair;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String accountName;

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getEntrantId() {
        return entrantId;
    }

    public void setEntrantId(int entrantId) {
        this.entrantId = entrantId;
    }

    public double getSumOfMarks() {
        return sumOfMarks;
    }

    public void setSumOfMarks(double sumOfMarks) {
        this.sumOfMarks = sumOfMarks;
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

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public Map<Integer, Integer> getPriorityFacultyPair() {
        return priorityFacultyPair;
    }

    public void setPriorityFacultyPair(Map<Integer, Integer> priorityFacultyPair) {
        this.priorityFacultyPair = priorityFacultyPair;
    }

    @Override
    public String toString() {
        String s = "";
        for (Map.Entry<Integer, Integer> pair : priorityFacultyPair.entrySet()) {
            s += " [" + pair.getKey() + " : " + pair.getValue() + " ] ";
        }
        return "entrant id = " + entrantId + " sumOfMarks = " + sumOfMarks + "priority : faculty Id" + s;
    }
}
