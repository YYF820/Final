package ua.nure.hanzha.SummaryTask4.entity;

/**
 * Main class for entities with field id.
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public class Entity implements SkeletonEntity {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ID = " + id;
    }
}
