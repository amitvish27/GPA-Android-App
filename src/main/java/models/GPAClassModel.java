/**
 * Created by @ameetv on 3/25/2017.
 */

package models;

public class GPAClassModel {

    public String Course;
    public double Units;
    public int isRepeat;
    public String previousGrade;
    public double previousGradeValue;
    public String Grade;
    public double GradeValue;
    public double GradePoints;

    public GPAClassModel() {
    }

    public GPAClassModel(String course) {
        Course = course;
    }

    public GPAClassModel(String course, double units, int isRepeat, String previousGrade, double previousGradeValue, String grade, double gradeValue, double gradePoints) {
        Course = course;
        Units = units;
        this.isRepeat = isRepeat;
        this.previousGrade = previousGrade;
        this.previousGradeValue = previousGradeValue;
        Grade = grade;
        GradeValue = gradeValue;
        GradePoints = gradePoints;
    }

    public String getCourse() {
        return Course;
    }

    public void setCourse(String course) {
        Course = course;
    }

    public double getUnits() {
        return Units;
    }

    public void setUnits(double units) {
        Units = units;
    }

    public int isRepeat() {
        return isRepeat;
    }

    public void setRepeat(int repeat) {
        isRepeat = repeat;
    }

    public String getPreviousGrade() {
        return previousGrade;
    }

    public void setPreviousGrade(String previousGrade) {
        this.previousGrade = previousGrade;
    }

    public double getPreviousGradeValue() {
        return previousGradeValue;
    }

    public void setPreviousGradeValue(double previousGradeValue) {
        this.previousGradeValue = previousGradeValue;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public double getGradeValue() {
        return GradeValue;
    }

    public void setGradeValue(double gradeValue) {
        GradeValue = gradeValue;
    }

    public double getGradePoints() {
        return GradePoints;
    }

    public void setGradePoints(double gradePoints) {
        GradePoints = gradePoints;
    }

    @Override
    public String toString() {
        return "GPAClassModel{" +
                ", Course='" + Course + '\'' +
                ", Units=" + Units +
                ", isRepeat=" + isRepeat +
                ", previousGrade=" + previousGrade +
                ", previousGradeValue=" + previousGradeValue +
                ", Grade='" + Grade + '\'' +
                ", GradeValue=" + GradeValue +
                ", GradePoints=" + GradePoints +
                '}';
    }
}