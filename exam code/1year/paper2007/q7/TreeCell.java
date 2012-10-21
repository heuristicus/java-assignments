/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paper2007.q7;

/**
 *
 * @author Michal
 */
public class TreeCell {

    String data;
    TreeCell leftBranch, rightBranch;

    public TreeCell(String data, TreeCell leftBranch, TreeCell rightBranch) {
        this.data = data;
        this.leftBranch = leftBranch;
        this.rightBranch = rightBranch;
    }

    public TreeCell(String data) {
        this(data, null, null);
    }

    public TreeCell() {
    }

}
