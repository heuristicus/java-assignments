/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paper2005.q4;

/**
 *
 * @author Michal
 */
public class DiscountedItem extends BasicItem {

    public static void main(String[] args) {
        BasicItem x = new DiscountedItem(5, 100);
//        DiscountedItem y = (DiscountedItem)x;
//        y.getDiscount();

        (DiscountedItem)
        System.out.println(x.getPrice());
    }

    private int discount;

    public DiscountedItem(int discount, int price) {
        super(price);
        this.discount = discount;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    @Override
    public int getPrice() {
        return super.getPrice() * (100 - discount) / 100;
    }
}
