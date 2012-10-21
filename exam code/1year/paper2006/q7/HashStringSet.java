/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paper2006.q7;

/**
 *
 * @author Michal
 */
public class HashStringSet implements StringSet {

    private String[] strings;
    private int size;
    private int bucketNumber;

    public static void main(String[] args) {
        HashStringSet hash = new HashStringSet(20);
        hash.add("Cheese");
        hash.add("Peas");
        hash.add("cheese");
        hash.add("peas");
        hash.add("peas");
        hash.add("Hellothisisanannoyingteststring");

        System.out.println(hash.contains("Cheese"));
        System.out.println(hash.contains("peas"));
        System.out.println(hash.size());
    }

    public HashStringSet(int bucketNumber) {
        this.bucketNumber = bucketNumber;
        strings = new String[bucketNumber];
    }

    public boolean contains(String string) {
        return strings[string.hashCode() % bucketNumber] != null;
    }

    public boolean add(String string) {
        int hash = Math.abs(string.hashCode() % bucketNumber);
        System.out.println(hash);
        if (strings[hash] == null) {
            strings[hash] = string;
            size++;
            return true;
        } else {
            System.out.println("Hash collision occurred.  String " + string + " was not added.");
            return false;
        }
    }

    public int size() {
        return size;
    }
}
