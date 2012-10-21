/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ex06;


public class MarkRecord {

    //Testing some methods.
    /*
    public static void main(String[]args) {
        Student test = new Student("Will", "Smith");
        MarkRecord alsotest = new MarkRecord(test, 50.2, 25.9);
        System.out.println(alsotest.toString());
    }
    */

    // Creates variables to be used in each instance of MarkRecord.
    private String student = "";
    private double examMark = 0;
    private double courseworkMark = 0;
    private Student thisStudent;

    // Constructor for this class.
    public MarkRecord(Student theStudent, double exam, double course) {
        student = theStudent.getForename() + theStudent.getSurname();
        examMark = exam;
        courseworkMark = course;
        thisStudent = theStudent;
    }

    // Following 3 methods return contents of variables.
    public double getExamMark() {
        return examMark;
    }

    public double getCourseworkMark() {
        return courseworkMark;
    }

    public String getStudent() {
        return student;
    }

    /* Allows updates to the student's name by the passing of the first and
     * last names into the method.
     */
    public void setStudent(String firstName, String lastName) {
        student = firstName + lastName;
    }

    public void setExamMark(double mark) {
        examMark = mark;
    }

    public void setCourseworkMark(double mark) {
        courseworkMark = mark;
    }

    /* Should return a weighted value, 70% of the examMark and 30% of the courseworkMark
     * rounded to 2 decimal places.
     */
    public double getTotalMark() {
        double totalMark = ((examMark*0.7) + (courseworkMark*0.3));
        totalMark = ((double)(int) ((totalMark * 100) + 0.5)) / 100;
        return totalMark;
    }

    public String toString() {
        /* Double.toString(getTotalMark) should convert the result of getTotalMark for
         * the specific instance of the class to a string, and then concatenate that with
         * a tab character and then the result of the Student.toString() method from
         * Student.java.
         */
        String record = "" +  getTotalMark() + "\t" + thisStudent.toString();
        return record;
    }

}
