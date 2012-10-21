/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex14;

/**
 *
 * @author Michal
 */
public class Book {

    private int number;
    private String title, authPersName, authFamName;

//    public static void main(String[] args) {
//
//        Book test = new Book(001, "Some Book Wot I Rote", "Bill", "Smith");
//        System.out.println(test);
//        test.setAuthorFamilyName("SECONDNAME CHANGE!");
//        System.out.println(test);
//        test.setAuthorPersonalName("FIRSTNAME CHANGE!");
//        System.out.println(test);
//        test.setBookNumber(30);
//        System.out.println(test);
//        test.setTitle("NEW TITLE!");
//        System.out.println(test);
//        System.out.println(test.getAuthorFamilyName());
//        System.out.println(test.getAuthorPersonalName());
//        System.out.println(test.getBookNumber());
//        System.out.println(test.getTitle());
//
//    }

    /**
     * Constructor for this class.
     * @param number Book number.
     * @param title Title of the book.
     * @param authPersName Author's first name.
     * @param authFamName Author's surname.
     */
    public Book(int number, String title, String authPersName, String authFamName) {
        this.number = number;
        this.title = title;
        this.authPersName = authPersName;
        this.authFamName = authFamName;
    }

    /**
     *
     * @return Author's second name
     */
    public String getAuthorFamilyName() {
        return authFamName;
    }

    /**
     * Sets a new surname.
     * @param authFamName New surname.
     */
    public void setAuthorFamilyName(String authFamName) {
        this.authFamName = authFamName;
    }

    /**
     *
     * @return Author's first name
     */
    public String getAuthorPersonalName() {
        return authPersName;
    }

    /**
     * Sets a new first name.
     * @param authPersName
     */
    public void setAuthorPersonalName(String authPersName) {
        this.authPersName = authPersName;
    }

    /**
     *
     * @return Book number.
     */
    public int getBookNumber() {
        return number;
    }

    /**
     * Sets a new book number.
     * @param number New book number.
     */
    public void setBookNumber(int number) {
        this.number = number;
    }

    /**
     *
     * @return Title of the book.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets a new title for the book.
     * @param title New title.
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Converts the object to a String.
     * @return String in the format - number:title:first name: second name.
     */
    @Override
    public String toString() {
        return ("" + number + ":" + title + ":" + authPersName + ":" + authFamName);
    }

}
