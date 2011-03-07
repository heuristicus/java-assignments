/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphDB;

import java.awt.Graphics;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author michal
 */
public class Grapher extends JPanel {

    Client gClient;
    JPanel panel;
    JFrame frame;

    public static void main(String[] args) {
        Grapher g = new Grapher("localhost", 2000);
    }

    public Grapher(String host, int port) {
        initWindow();
        gClient = new Client(host, port);
    }

    public void initWindow() {
        panel = new JPanel();

        frame = new JFrame("RegGraph");
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel, "NORTH");

        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
    }

    public void getRegistrationPoints(String modiD) {
        try {
            gClient.getRegistrationPoints(modiD);
        } catch (IOException ex) {
            System.out.println("IO exception while getting registration points.");
        } catch (ClassNotFoundException ex) {
            System.out.println("Class not found while getting regpoints.");
        } catch (ClassCastException ex) {
            System.out.println("Not able to cast the object received from the server.");
        }
    }
}
