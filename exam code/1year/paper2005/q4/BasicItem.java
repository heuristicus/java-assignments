/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paper2005.q4;

/**
 *
 * @author Michal
 */
public class BasicItem implements Item{

    private int price;

    public BasicItem(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void SetPrice(int price) {
        this.price = price;
    }

}
