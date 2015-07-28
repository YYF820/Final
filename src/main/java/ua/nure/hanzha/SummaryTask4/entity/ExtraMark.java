package ua.nure.hanzha.SummaryTask4.entity;

/**
 * ExtraMark entity from table summarytask4#Extra_Marks
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public class ExtraMark implements SkeletonEntity {
    private int entrantId;
    private double certificatePoints;
    private double extraPoints;

    public int getEntrantId() {
        return entrantId;
    }

    public void setEntrantId(int entrantId) {
        this.entrantId = entrantId;
    }

    public double getCertificatePoints() {
        return certificatePoints;
    }

    public void setCertificatePoints(double certificatePoints) {
        this.certificatePoints = certificatePoints;
    }

    public double getExtraPoints() {
        return extraPoints;
    }

    public void setExtraPoints(double extraPoints) {
        this.extraPoints = extraPoints;
    }

    @Override
    public String toString() {
        return "Entrant id: " + entrantId +
                "Cerificate points" + certificatePoints +
                "Extra points " + extraPoints;
    }
}
