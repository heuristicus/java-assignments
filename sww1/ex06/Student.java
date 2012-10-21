/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ex06;

import fyw.turtles.*;

public class Student {

    /*Testing the methods in this class
    public static void main(String [] Args) {
        Student test = new Student("Joe", "Bloggs");
        System.out.println(test.toString());
        test.setForename();
        System.out.println(test.toString());
        test.setSurname();
        System.out.println(test.toString());
        test.setForename("otherForenameMethod");
        System.out.println(test.toString());
        test.setSurname("otherSurnameMethod");
        System.out.println(test.toString());
        Student test2 = new Student("Will", "Smith");
        System.out.println(test2.toString());
    }
     */


    // Constructor
    public Student(String firstName, String lastName) {
        forename = firstName;
        surname = lastName;
        id = uniqueID;
        // Should increment the uniqueID each time a new Student is created.
        uniqueID = uniqueID + 1;
    }

    // Defining variables.
    private String forename = "";
    private String surname = "";
    private int id = 0;
    // Sets the inital value of the unique ID.
    private static int uniqueID = 1010000;

    // Getter methods attempt to return the value of that variable for the instance of Student.
    public String getForename() {
        return forename;
    }

    public String getSurname() {
        return surname;
    }

    public int getID() {
        return id;
    }

    /* Setter methods allow you to change values in the object.  One can be
     * used to prompt the user to enter a string, the other takes a string
     * as a parameter and changes the string using that.
     */
    public void setForename(String newName) {
        forename = newName;
    }

    public void setForename() {
        InputFrame theInputFrame = new InputFrame();
        forename = theInputFrame.getString("Enter the new forename.");
    }

    public void setSurname(String newSurname) {
        surname = newSurname;
    }

    public void setSurname() {
        InputFrame theInputFrame = new InputFrame();
        surname = theInputFrame.getString("Enter the new surname.");
    }

    // Attempts to convert the surname variable to upper case and then add a comma and the forename.
    public String getName() {
        String combination = surname.toUpperCase() + ", " + forename;
        return combination;
    }

    /* Attempts to create a string which consists of the id
     * a tab character and the result of the getName() method.
     */
    public String toString() {
        String idName = id + "\t" + getName();
        return idName;
    }





}
