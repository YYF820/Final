package ua.nure.hanzha.SummaryTask4.entity;

/**
 * Entrant entity from table summarytask4#Entrant
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public class Entrant extends Entity {

    private String city;
    private String region;
    private int school;
    private boolean withoutCompetitiveEntry;
    private int userId;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public int getSchool() {
        return school;
    }

    public void setSchool(int school) {
        this.school = school;
    }

    public boolean isWithoutCompetitiveEntry() {
        return withoutCompetitiveEntry;
    }

    public void setWithoutCompetitiveEntry(boolean withoutCompetitiveEntry) {
        this.withoutCompetitiveEntry = withoutCompetitiveEntry;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "City: " + city +
                "Region: " + region +
                "school" + school +
                "Without competitive entry: " + withoutCompetitiveEntry +
                "User id: " + userId;
    }
}
