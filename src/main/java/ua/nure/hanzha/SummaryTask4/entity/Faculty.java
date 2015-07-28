package ua.nure.hanzha.SummaryTask4.entity;

/**
 * Faculty entity from table summarytask4#Faculties
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public class Faculty extends Entity {
    private String name;
    private int totalSpots;
    private int budgetSpots;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Name: " + name +
                "Total spots: " + totalSpots +
                "Budget spots " + budgetSpots;
    }
}
