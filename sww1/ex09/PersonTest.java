/*
 * Person.java
 */

package ex09;

/**
 * Test harness for Person and forefather relations.
 *
 * @author djb, mjs
 */
public class PersonTest {


    /**
     * tests Person by creating a tree of forefathers
     *   and then printing out the forefathers.
     */
    public static void main(String[] args) {
        // create the lineage
        Person frank = new Person("Frank");
        Person earl = new Person("Earl",frank);
        System.out.println("frank:" + frank);
        System.out.println("earl:" + earl);
        Person dean = new Person("Dean",earl);
        Person carl = new Person("Carl",dean);
        Person benjy = new Person("Benjy",carl);
        Person abe = new Person("Abe",benjy);

        // some tests - note that you should do your own tests too
        printForefathers(getForefathers(abe));
        printForefathers(getForefathers(frank));
        printForefathers(getForefathers(null));

        // My tests.
        printForefathers(getForefathersRecursiveHelper(0, abe));
        printForefathers(getForefathersRecursiveHelper(2, abe));
        printForefathers(getForefathersRecursive(abe));
    }

    /**
     * Method for printing a list of Forefathers.
     *
     * @param p the array of Person instances.
     */
    public static void printForefathers(Person[] p) {
        System.out.print("Forefathers: [");
        for(int i=0; i<p.length; i++) {
            if(i>0)
                System.out.print(","+p[i]);
            else
                System.out.print(p[i]);
        }
        System.out.println("]");
    }

    /**
     * Calculate an array whose elements are a given
     *    person and all her forefathers. <p>
     * requires: nothing
     * @param p person whose forefathers are to be found
     * @return array whose elements are <code>p</code>
     *    (if non-null) and all his forefathers, in order
     *    of increasing age.
     *    (If <code>p</code> is null, the result is empty.)
     */
    public static Person[] getForefathers(Person p) {

        // count the number of forefathers, including this person
        Person start = p;
        int ff = 0;
        while(start!=null) {
            start = start.getFather();
            ff++;
        }

        // initialise array of Forefathers
        Person[] forefathers = new Person[ff];

        // fill in array of Forefathers
        Person second = p;
        int ins = 0;
        while(second!=null) {
            forefathers[ins] = second;
            second = second.getFather();
            ins++;
        }

        return forefathers;
    }

    /**
     * 
     * @param p A person whose forefathers are to be output.
     * @return And array of the Person type, which contains the forefathers of
     * the entered person, in order of ascending age.
     */
    public static Person[] getForefathersRecursive(Person p) {
        Person[] ffArray = getForefathersRecursiveHelper(0,p);
        return ffArray;
    }

    /**
     * auxiliary method for <code>getForefathersRecursive</code>. <p>
     * requires: <code>n</code> >= 0
     * @param n number of initial null elements in result.
     * @param p Person whose forefathers are to be found.
     * @return array whose first n elements are null and
     *    whose remaining elements are p (if non-null) and
     *    all the forefathers, in order of increasing age.
     */
    private static Person[] getForefathersRecursiveHelper(int n, Person p) {
        Person[] forefatherArray;
        if (p == null) {
            forefatherArray = new Person[n];
        } else {
            forefatherArray = getForefathersRecursiveHelper(n + 1, p.getFather());
            forefatherArray[n] = p;
        }

        return forefatherArray;
    }

}