/*
 * Person.java
 */

package ex09;

/**
 * Class representing a Person object used for tracing forefathers, where each person has a name and a father.
 *
 * @author djb, mjs
 */
public class Person {
    private String name;
    private Person father;


    /**
     * Creates a new instance of Person with a name and no father.<br/>
     * requires: nothing<br/>
     * ensures: new Person has father field initialized
     *   to null
     *
     * @param name the name of this person
     */
    public Person(String name) {
        this.name = name;
        this.father = null; //this is the default behaviour for Objects anyway
    }

    /**
     * Creates a new instance of Person with a name and father.<br/>
     * requires: nothing<br/>
     * ensures: new Person has father field initialized
     *   from parameter.
     *
     * @param name the name of this person
     * @param father the Person instance representing this person's father
     */
    public Person(String name, Person father) {
        this(name);
        this.father = father;
    }

    /**
     * Gets the name for this person
     * @return the name of this Person
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name for this Person
     * @param name the new name for this person
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the father for this person.
     * @return the father for this person
     */
    public Person getFather() {
        return father;
    }

    /**
     * Generate a String representing this Person, consisting of their name;
     * @return the name of this Person
     */
    public String toString() {
        return this.name;
    }
}
