package ua.nure.hanzha.SummaryTask4.enums;

/**
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 14/08/15.
 */
public enum EnterUniversityStatus {
    BUDGET, CONTRACT, NOT_PASSED;

    public static EnterUniversityStatus getEnterUniversityStatusById(int enterUniversityStatusId) {
        return EnterUniversityStatus.values()[enterUniversityStatusId - 1];
    }

    public String getName() {
        return name().toLowerCase().replaceAll("_", "");
    }
}
