/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package randomPatternDrawer;


public class tester {

    public static void main(String[] args) {
        castingTest();
    }

    public static double randomSeed() {
        double initialSeed = Math.random();

        if (initialSeed < 0.5) {
            initialSeed = initialSeed * -1;
        }

        double Seed = initialSeed;
        Seed = Seed * 1000;

        return Seed;

    }

    public static void castingTest() {
        int x = 40;
        double y = 23.45;
        System.out.println((double)(int) x);
        System.out.println((int)(double) x);
        System.out.println((double)(int) y);
        System.out.println((int)(double) y);
    }

}
