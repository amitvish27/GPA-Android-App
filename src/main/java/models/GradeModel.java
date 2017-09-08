/**
 * Created by @ameetv on 3/25/2017.
 */

package models;

public class GradeModel {

    public String Grade;
    public double value;

    public GradeModel() {
    }

    public GradeModel(String grade, double value) {
        Grade = grade;
        this.value = value;
    }

    public String getGrade() {
        return Grade;
    }

    public void setGrade(String grade) {
        Grade = grade;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "GradeModel{" +
                "Grade='" + Grade + '\'' +
                ", value=" + value +
                '}';
    }
}
