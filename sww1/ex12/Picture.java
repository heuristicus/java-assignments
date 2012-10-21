/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ex12;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 *
 * @author Michal
 */
public class Picture {

    private Line[] lineArray;
    private int nextLinePos = 0;
    private String fileName;


//    public static void main(String[] args) throws FileNotFoundException {
//        /*
//         * Tests the add and getLine methods.
//         */
//        Line testLine = new Line(20, 40, 60, 80);
//        Picture testPic = new Picture();
//        testPic.add(testLine);
//        System.out.println(testPic.getLine(1));
//
//        /*
//         * Tests the getNumberOfLines method with an invalid fileName.
//         */
//        int numberTest = testPic.getNumberOfLines("test");
//        System.out.println(numberTest);
//
//        /*
//         * Tests the getNumberOfLines method with a valid fileName.
//         */
//        System.out.println(testPic.getNumberOfLines("picture.txt"));
//
//        /*
//         * Tests the other getNumberOfLines method.
//         */
//        Line[] lineTest3 = new Line[16];
//        for (int i = 0; i < 8; i++) {
//            Line line = new Line(20 + i, 30 + i, 40 + i, 50 + i);
//            lineTest3[i] = line;
//        }
//
//        System.out.println("Number of lines is " + getNumberOfLines(lineTest3));
//
//        /*
//         * This tests the functionality of the cropArray method.
//         */
//        Line[] lineTest2 = new Line[16];
//        for (int i = 0; i < 8; i++) {
//            Line line = new Line(20 + i, 30 + i, 40 + i, 50 + i);
//            lineTest2[i] = line;
//        }
//
//        Picture test2 = new Picture(lineTest2);
//        System.out.println("array before crop method : " + lineTest2.length);
//        lineTest2 = test2.cropArray(lineTest2);
//        System.out.println("array length after crop method : " + lineTest2.length);
//
//        /*
//         * Tests the printArray methods.
//         */
//        printArray(lineTest2);
//        test2.printArray();
//
//        /*
//         * Tests the exception handling in the add method.
//         */
//
//        Line test4 = new Line(1, 2, 3, 4);
//        Line[] test5 = new Line[4];
//        for (int i = 0; i < test5.length; i++) {
//            test5[i] = test4;
//        }
//        Picture exceptionTest = new Picture(test5);
//        exceptionTest.add(test4);
//    }

    /*
     * Basic constructor with no parameters.  Creates an array of size 20 that can
     * hold Line objects.
     */
    public Picture() {
        this.lineArray = new Line[20];
        this.nextLinePos = 0;
    }

    /**
     * Extra constructor.  Initialises some of the variables in the object with
     * values specified in the parameters.
     * @param lineArray An array of Line objects
     * @param fileName The fileName (with extension) of the file that the picture
     * coordinates are stored in.
     */
    public Picture(Line[] lineArray, String filename) {
        this.lineArray = lineArray;
        this.fileName = filename;
        cropArray(lineArray);
        nextLinePos = lineArray.length;
    }

    /**
     * Another constructor which allows the input of a Line[] object to the
     * Picture class.
     * @param lineArray An array of Line objects.
     */
    public Picture(Line[] lineArray) {
        this.lineArray = lineArray;
        cropArray(lineArray);
        nextLinePos = lineArray.length;
    }

    /**
     * Adds a Line to the array.
     * @param l An instance of Line.
     * @throws ArrayIndexOutOfBoundsException Is caught by this method.  When
     * this happens, the array is automatically resized.
     */
    public void add(Line l) throws ArrayIndexOutOfBoundsException {
        System.out.println("starting add");
        try {
            System.out.println("added the following line: " + l);
            lineArray[nextLinePos] = l;
            nextLinePos += 1;
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Array index is out of bounds.");
            System.out.println("resizing array");
            resizeArray(lineArray);
            lineArray[nextLinePos - 1] = l;
        }

    }

    /**
     *
     * @return The filename initialised in the constructor.
     */
    public String getFileName() {
        return this.fileName;
    }

    /**
     *
     * @return Number of lines in the instance of Picture.
     */
    public int getNumberOfLines() {
        return lineArray.length;
    }

    /**
     * Gets the number of lines in a file.
     * @param fileName Name of the file containing picture coordinates.  Note
     * that it must have the file extension.
     * @return Number of lines in the file.
     * @throws FileNotFoundException
     */
    public int getNumberOfLines(String filename) throws FileNotFoundException {
        int lines = 0;
        try {
            Scanner pictureFile = new Scanner(new File(filename));
            while (pictureFile.hasNext()) {
                pictureFile.nextLine();
                lines += 1;
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.  Please try again.");
        }
        return lines;
    }

    /**
     * Returns the number of non-null elements in a Line array.  Since all elements in the array will be lines,
     * it can be assumed that there will be the same number of lines as non-null elements.
     * @param l An array of Lines.
     * @return The number of non-null elements in the array.
     */
    public static int getNumberOfLines(Line[] l) {
        int length = 0;
        for (int i = 0; i < l.length; i++) {
            if (l[i] != null) {
                length += 1;
            }
        }
        return length;
    }

    /**
     * Returns a Line from the lineArray.
     * @param i Number of the array location whose line you wish to check.
     * @return Line in the memory location specified.
     */
    public Line getLine(int i) {
        return lineArray[i];
    }

    /**
     * Resizes an array to a size that is 1.5 times larger than the size of the
     * original.  Used if there are many lines in a picture.
     * @param l An array of Line objects.
     * @return A larger array of type Line, with the contents of the original
     * array copied into it.
     */
    private void resizeArray(Line[] l) {
        int oldSize = l.length;
        int newSize = oldSize + (oldSize / 2);
        System.out.println("Resizing array to hold " + newSize + " elements.");
        Line[] newArray = new Line[newSize];
        System.arraycopy(l, 0, newArray, 0, l.length);
        l = newArray;
    }

    /**
     * This method is used to remove additional array locations that are
     * not needed to store a picture.  For example, if the array has 100 locations,
     * but there are only 50 lines in the picture, this method will reduce the
     * array to length 50.
     * @param l An array of lines.
     * @return A full array of lines from the entered array.
     */
    public Line[] cropArray(Line[] l) {
        int oldSize = l.length;
        int newSize = 0;

        /*
         * Invariant: 0 <= i < l.length
         */
        for (int i = 0; i < oldSize; i++) {
            if (l[i] != null) {
                newSize += 1;
            }
        }

        Line[] newArray = new Line[newSize];
        System.arraycopy(l, 0, newArray, 0, newSize);
        l = newArray;
        return l;
    }

    /**
     * Prints the lineArray.
     */
    public void printArray() {
        System.out.println("Start printing Array...");
        for (int i = 0; lineArray[i] != null; i++) {
            System.out.println(lineArray[i]);
        }
        System.out.println("Finished.  Array contains " + getNumberOfLines(lineArray) + " lines.");
    }

    /**
     * Prints the array input as a parameter.
     * @param l An array of Line objects.
     */
    public static void printArray(Line[] l) {
        System.out.println("Start printing Array...");
        for (int i = 0; i < l.length; i++) {
            System.out.println(l[i].toString());
        }
        System.out.println("Finished.  Array contains " + getNumberOfLines(l) + " lines.");
    }

    @Override
    public String toString() {
       return ("This picture contains " + getNumberOfLines() + " lines, and was scanned from the file " + fileName);
    }



}