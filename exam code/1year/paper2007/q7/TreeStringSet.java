/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paper2007.q7;

/**
 *
 * @author Michal
 */
public class TreeStringSet extends TreeCell implements StringSet {

    private TreeCell root;
    private int size;

    public TreeStringSet() {
        root = null;
        size = 0;
    }

    public TreeStringSet(String s) {
        this.root = new TreeCell(s);
    }

    public boolean contains(String string) {
        return contains(string, root);
    }

    private boolean contains(String string, TreeCell cell) {
        if (cell.data == null) {
            return false;
        } else if (string.compareTo(cell.data) == 0) {
            return true;
        } else if (string.compareTo(cell.data) < 0) {
            return contains(string, cell.leftBranch);
        } else {
            return contains(string, cell.rightBranch);
        }
    }

    public boolean add(String string) {
        return add(string, root);
    }

    private boolean add(String string, TreeCell cell) {
        if (cell.data == null) {
            cell = new TreeCell(string);
            size++;
            return true;
        } else if (string.compareTo(cell.data) == 0) {
            return false;
        } else if (string.compareTo(cell.data) < 0) {
            return add(string, cell.leftBranch);
        } else {
            return add(string, cell.rightBranch);
        }
    }

    public int size() {
        return size;
    }
}
