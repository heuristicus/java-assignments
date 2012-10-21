package ex13.extended.pictures;

import java.io.Serializable;

public class Picture implements Serializable {

    private static final int DEFAULT_MAX_ELEMENTS = 100;
    private PictureElement[] elementList;
    private int elements, maxElements;

    /**
     * Constructor with no parameters.  Calls the other constructor with a default max
     * value for the number of elements.
     */
    public Picture() {
        this(DEFAULT_MAX_ELEMENTS);
        elements = 0;
    }

    /**
     * Constructor - creates a new PictureElement array of a specified length and initialises
     * the array pointer to zero.
     * @param max Maximum number of elements to be stored in the array.
     */
    public Picture(int max) {
        elementList = new PictureElement[max];
        maxElements = max;
        elements = 0;
    }

    /**
     * Adds a PictureElement to the elementList array.
     * @param comp
     */
    public void add(PictureElement comp) {
        if (elements < maxElements) {
            elementList[elements] = comp;
            elements++;
        }
    }

    /**
     * Returns the number of elements currently in the array.
     * @return
     */
    public int getNumberOfDrawingElements() {
        return elements;
    }

    /**
     * Returns the PictureElement at an array location.
     * @param n Array location to get the PictureElement from.
     * @return
     */
    public PictureElement getDrawingElement(int n) {
        return elementList[n];
    }
}
