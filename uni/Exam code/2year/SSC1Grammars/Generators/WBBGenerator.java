/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Generators;

import java.util.Random;

/**
 * D -> [D]D
 * D ->
 * @author michal
 */
public class WBBGenerator {

    public static void main(String[] args) {
        WBBGenerator g = new WBBGenerator();
        for (int i = 0; i < 5; i++) {
            System.out.println(g.generateRandomWBB());
        }
    }

    public String generateRandomWBB() {
        Random r = new Random();
        String genned = "";
        while (r.nextBoolean()) {
            genned += genD();
        }
        return genned;
    }

    public String genD() {
        Random r = new Random();
        String genned = "";
        if (r.nextBoolean()) {
            genned += genD1();
        } else {
            genned += genD2();
        }
        return genned;
    }

    public String genD1() {
        return "[" + genD() + "]" + genD();
    }

    public String genD2() {
        return "";
    }
}
