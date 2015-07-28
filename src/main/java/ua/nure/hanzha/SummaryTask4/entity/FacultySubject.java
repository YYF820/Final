package ua.nure.hanzha.SummaryTask4.entity;

/**
 * FacultySubject entity from table summarytask4#Faculties_Subjects
 *
 * @author Dmytro Hanzha
 *         Created by faffi-ubuntu on 28/07/15.
 */
public class FacultySubject implements SkeletonEntity {
    private int facultyId;
    private int subjectId;

    public int getFacultyId() {
        return facultyId;
    }

    public void setFacultyId(int facultyId) {
        this.facultyId = facultyId;
    }

    public int getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
    }

    @Override
    public String toString() {
        return "Faculty ID: " + facultyId +
                "Subject ID: " + subjectId;
    }
}
