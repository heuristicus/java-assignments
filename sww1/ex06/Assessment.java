/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ex06;

import fyw.turtles.*;

public class Assessment {


    public static void main(String[] args) {
        //Uncomment the following line to use the for loop to insert students.
        //InputFrame theInputFrame = new InputFrame();
        Student [] studentArray = new Student[5];
        MarkRecord [] markArray = new MarkRecord[5];
        /* This for loop can be used if one wants to enter different names.  I've
         * just gone through each record and input stuff directly just to take up
         * less time for a demonstration.
         *
        for (int i = 0; i < studentArray.length; i++) {
            studentArray[i] = new Student(theInputFrame.getString("Enter the student's forename."), theInputFrame.getString("Enter the student's surname"));
            markArray[i] = new MarkRecord(studentArray[i].toString(), theInputFrame.getDouble("Enter the exam mark."), theInputFrame.getDouble("Enter the coursework mark."));
        }
         */
        studentArray[0] = new Student("Andy", "Anderson");
        markArray[0] = new MarkRecord(studentArray[0], 56.7, 76.8);
        studentArray[1] = new Student("Barry", "Byrne");
        markArray[1] = new MarkRecord(studentArray[1], 76.8, 56.7);
        studentArray[2] = new Student("Carrie", "Clark");
        markArray[2] = new MarkRecord(studentArray[2], 25.9, 35.7);
        studentArray[3] = new Student("Danny", "Davis");
        markArray[3] = new MarkRecord(studentArray[3], 62.3, 67.6);
        studentArray[4] = new Student("Ernie", "Evans");
        markArray[4] = new MarkRecord(studentArray[4], 81.9, 99.7);
        System.out.println("Marks");
        System.out.println("-----------");

        for (int i = 0; i < markArray.length; i++) {
            System.out.println(markArray[i].toString());
        }
        System.out.println("-----------");
    }
}
