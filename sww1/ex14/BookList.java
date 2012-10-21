/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex14;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Michal
 */
public class BookList implements TableModel {

    private Book[] bookArray;
    private int arrayLocation = 0;
    private static final int DEFAULT_SIZE = 50;
    private static final int AUTH_FIRST = 0, AUTH_LAST = 1, TITLE = 2, NUMBER = 3;
    private int lastSort = 3;
    private boolean sortReverse = false;

//    public static void main(String[] args) {
//
//        // Tests the correct size of list is created with the standard constructor.
//        BookList test = new BookList();
//        System.out.println("Size of array is " + test.getBookArraySize());
//
//        // Tests the constructor which initialises an array of specific size.
//        BookList test2 = new BookList(4);
//        System.out.println("Size of array is " + test2.getBookArraySize());
//
//
//        // Tests creating a new bookList with the default constructor.
//        BookList test3 = new BookList();
//
//        // Tests adding new books.
//        test3.add(new Book(11, "S", "Smither", "Williams"));
//        test3.add(new Book(2, "Z", "Smith", "Williams"));
//        test3.add(new Book(8, "F", "Roger", "Dodger"));
//        test3.add(new Book(3, "X", "Bill", "Thrill"));
//        test3.add(new Book(9, "N", "Andy", "Aardvark"));
//        test3.add(new Book(12, "P", "Tom", "Williams"));
//        test3.add(new Book(7, "R", "Will", "Smith"));
//        test3.add(new Book(4, "L", "Reg", "Smithson"));
//        test3.add(new Book(24, "A", "Michael", "Euler"));
//
//        // Tests removing a specific book from the array.
//        test3.remove(4);
//        test3.printArray();
//
//
//        // Tests normal sorting
//        test3.printArray();
//        test3.listByNumber();
//        test3.printArray();
//        test3.listByTitle();
//        test3.printArray();
//        test3.listByAuthor();
//        test3.printArray();
//
//        // Tests reverse sorting (sorts to reverse order)
//        test3.setSortReverse(true);
//        test3.listByNumber();
//        test3.printArray();
//        test3.listByAuthorFirstName();
//        test3.printArray();
//        test3.listByTitle();
//        test3.printArray();
//    }

    /**
     * Default constructor.  Initialises the bookArray with a default size of 50 by calling the
     * constructor in this class which takes a single integer to initialise the array.
     */
    public BookList() {
        this(DEFAULT_SIZE);
    }

    /**
     * Creates a new BookList with array size max.
     * @param max Desired array size.
     */
    public BookList(int max) {
        this.bookArray = new Book[max];
    }

    /**
     * Constructor which allows loading from a file.
     *
     * Calls the simpler constructor to initialise the bookArray, and then loads data from
     * the specified file.
     * @param max Maximum size of the array (does not need to be exact, array can be resized.)
     * @param file File to load data from.
     * @throws IOException
     */
    public BookList(int max, String file) throws IOException { //do this
        this(max);
        loadFrom(file);
    }

    /**
     * Gets a book from a specific index in the array.
     * @param n Index of the book to return.
     * @return Book at index n.
     */
    public Book getBook(int n) throws ArrayIndexOutOfBoundsException {
        return bookArray[n];
    }

    /**
     * Adds a book to the array if possible.  If the array is too small, the array is resized.
     * @param bk Book to add to the array.
     */
    public void add(Book bk) {
        if (arrayLocation < bookArray.length) {
            bookArray[arrayLocation] = bk;
        } else {
            resizeArray();
            bookArray[arrayLocation] = bk;
        }
        arrayLocation++;
    }

    /**
     * Removes a book from the array and then re-sorts the array according to the last sort
     * used.
     * @param index The index of the book to remove from the array.
     * @throws ArrayIndexOutOfBoundsException Thrown if the index exceeds the size of
     * the array in the booklist object.
     */
    public void remove(int index) throws ArrayIndexOutOfBoundsException {
        bookArray[index] = null;
        for (int i = index; i <= getArrayLocation(); i++) {
            bookArray[i] = bookArray[i + 1];
        }
        arrayLocation--;
        sort();
    }

    /**
     * Loads a series of book objects from a file in the format
     * number:title:first name: second name.
     *
     * This method creates a new array to store the data, so any data previously held is erased.
     *
     * @param fName Name of the file to load from.
     * @throws IOException
     */
    public void loadFrom(String fName) throws IOException {
        bookArray = new Book[10];
        setArrayLocation(0);
        BufferedReader bookFile =
                new BufferedReader(
                new FileReader(fName));
        String nextLine = bookFile.readLine();
        while (nextLine != null) {
            String[] splitValues = nextLine.split(":");
            this.add(new Book(Integer.valueOf(splitValues[0]), splitValues[1], splitValues[2], splitValues[3]));
            nextLine = bookFile.readLine();
            System.out.println("Adding " + nextLine + " to the array");
        }
        listByNumber();
    }

    /**
     * Saves the data in the current array to a file fName in the format
     *
     * number:title:first name: second name
     *
     * @param fName Name of the file to save to.
     * @throws IOException
     */
    public void saveTo(String fName) throws IOException {
        PrintWriter saveFile =
                new PrintWriter(
                new FileWriter(fName));
        for (Book b : bookArray) {
            if (b != null) {
                saveFile.println(b.toString());
            }
        }
        saveFile.close();
        System.out.println("Save successful!");
    }

    /**
     *Sorts the array by title using merge sort.  If  the sortReverse variable is true,
     * it will sort the array into descending order rather than ascending.
     */
    public void listByTitle() {
        if (sortReverse == false) {
            Arrays.sort(bookArray, new TitleComparator());
        } else {
            Arrays.sort(bookArray, new ReverseTitleComparator());
        }
        setLastSort(TITLE);
    }

    /**
     * Sorts the array by book the author's second name using merge sort.  If  the sortReverse variable is true,
     * it will sort the array into descending order rather than ascending.
     */
    public void listByAuthor() {
        if (sortReverse == false) {
            Arrays.sort(bookArray, new AuthorComparatorSecondName());
        } else {
            Arrays.sort(bookArray, new ReverseAuthorSecondNameComparator());
        }
        setLastSort(AUTH_LAST);
    }

    /**
     * Sorts the array by author's first name using merge sort.  If  the sortReverse variable is true,
     * it will sort the array into descending order rather than ascending.
     */
    public void listByAuthorFirstName() {
        if (sortReverse == false) {
            Arrays.sort(bookArray, new AuthorComparatorFirstName());
        } else {
            Arrays.sort(bookArray, new ReverseAuthorFirstNameComparator());
        }
        setLastSort(AUTH_FIRST);
    }

    /**
     * Sorts the array by book number using merge sort.  If  the sortReverse variable is true,
     * it will sort the array into descending order rather than ascending.
     */
    public void listByNumber() {
        if (sortReverse == false) {
            Arrays.sort(bookArray, new NumberComparator());
        } else {
            Arrays.sort(bookArray, new ReverseNumberComparator());
        }
        setLastSort(NUMBER);
    }

    /**
     *
     * @return Number of rows in the table.
     */
    public int getRowCount() {
        return getArrayLocation() - 1;
    }

    /**
     * Returns the number of fields in each Book.  This should be a static value of 4
     * unless more fields have been added.
     * @return
     */
    public int getColumnCount() {
        return 4;
    }

    /**
     * Returns the column header.
     * @param columnIndex Index of the selected column.
     * @return
     */
    public String getColumnName(int columnIndex) {
        switch (columnIndex) {
            case 0:
                return "Number";
            case 1:
                return "Title";
            case 2:
                return "Author First Name";
            case 3:
                return "Author Surname";
        }
        return "unknown";
    }

    public Class<?> getColumnClass(int columnIndex) {
        return String.class;
    }

    /**
     * Checks whether the cell at a specific location is editable.  This defaults to true, so all
     * cells can be edited.  The edited values are saved in the array.
     * @param rowIndex
     * @param columnIndex
     * @return
     */
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return true;
    }

    /**
     * Edits the value at a specific cell using the setValueAt method.
     * @param rowIndex Row of the cell to edit.
     * @param columnIndex Column of the cell to edit.
     */
    public void editCellValue(int rowIndex, int columnIndex) {
        setValueAt(getValueAt(rowIndex, columnIndex), rowIndex, columnIndex);
    }

    /**
     * Returns the String or Integer in the selected cell of the table.
     * @param rowIndex Row index in the table.
     * @param columnIndex Column index in the table.
     * @return
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (bookArray[rowIndex] != null) {
            switch (columnIndex) {
                case 0:
                    return bookArray[rowIndex].getBookNumber();
                case 1:
                    return bookArray[rowIndex].getTitle();
                case 2:
                    return bookArray[rowIndex].getAuthorPersonalName();
                case 3:
                    return bookArray[rowIndex].getAuthorFamilyName();
            }
        }
        return null;
    }

    /**
     * Sets the value of a specific cell in the table.
     * @param aValue Value to be inserted into the cell.
     * @param rowIndex Index of the row of the cell.
     * @param columnIndex Index of the column of the cell.
     */
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        switch (columnIndex) {
            case 0:
                bookArray[rowIndex].setBookNumber((Integer) aValue);
                break;
            case 1:
                bookArray[rowIndex].setTitle((String) aValue);
                break;
            case 2:
                bookArray[rowIndex].setAuthorPersonalName((String) aValue);
                break;
            case 3:
                bookArray[rowIndex].setAuthorFamilyName((String) aValue);
                break;
        }
    }

    public void addTableModelListener(TableModelListener l) {
    }

    public void removeTableModelListener(TableModelListener l) {
    }

    /**
     * Creates a new array 1.5 times the size of the original, and copies all the values in the
     * old array into the new one.
     */
    private void resizeArray() {

        int newSize = (int) (bookArray.length * 1.5);
        Book[] newArray = new Book[newSize];
        System.out.println("Resizing the array.  New size will be " + newSize);
        System.arraycopy(bookArray, 0, newArray, 0, bookArray.length);
        bookArray = newArray;
    }

    /**
     *
     * @return The size of the bookArray.
     */
    public int getBookArraySize() {
        return bookArray.length;
    }

    /**
     * Prints the bookArray.  Does not print null values.
     */
    public void printArray() {
        System.out.println("Start printing Array..." + "\n");
        for (Book b : bookArray) {
            if (b != null) {
                System.out.println(b);
            }
        }
        System.out.println("\n" + "Finished.  Array contains " + getArrayLocation() + " books.");
    }

    /**
     * Gets the current array pointer location (can be used to decuce the number of elements
     * in the array)
     * @return Current value of arrayLocation
     */
    public int getArrayLocation() {
        return arrayLocation;
    }

    /**
     * Sets the value of the array pointer.
     * @param arrayLocation Desired value of the pointer.
     */
    private void setArrayLocation(int arrayLocation) {
        this.arrayLocation = arrayLocation;
    }

    /**
     * Gets the value of the last sort used.
     * @return
     */
    public int getLastSort() {
        return lastSort;
    }

    /**
     * Sets the value of the last sort.
     * @param lastSort
     */
    public void setLastSort(int lastSort) {
        this.lastSort = lastSort;
    }

    /**
     * Re-sorts the array using the last sort method used.
     */
    public void sort() {
        switch (getLastSort()) {
            case 0:
                listByAuthorFirstName();
                break;
            case 1:
                listByAuthor();
                break;
            case 2:
                listByTitle();
                break;
            case 3:
                listByNumber();
                break;
        }
    }

    /**
     * Returns a boolean indicating whether sort methods are being reversed or not.
     * @return Value of the sortReverse variable
     */
    public boolean isSortReverse() {
        return sortReverse;
    }

    /**
     * Sets the value of the sortReverse variable.  False will sort in ascending order, true
     * will sort in descending.
     * @param sortReverse Boolean value to set this variable to.
     */
    public void setSortReverse(boolean sortReverse) {
        this.sortReverse = sortReverse;
    }
}


