/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphDB;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author michal
 */
public class Grapher extends JPanel implements ActionListener {

    Client gClient;
    Map textFieldMap;
    JPanel graphPanel;
    JPanel buttonPanel;
    JFrame frame;
    boolean authenticated = false;
    ArrayList<Point> regPoints;

    public static void main(String[] args) {
        Grapher g = new Grapher("localhost", 2000);
    }

    public Grapher(String host, int port) {
        initWindow();
        gClient = new Client(host, port);
    }

    public void initWindow() {
        graphPanel = new JPanel();

        makeButtonPanel();


        frame = new JFrame("RegGraph");
        frame.setSize(800, 600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.add(buttonPanel);

        frame.setVisible(true);
    }

    private void makeButtonPanel() {
        buttonPanel = new JPanel();

        JLabel userL = new JLabel("User:");
        JLabel passL = new JLabel("Password:");
        JLabel modL = new JLabel("Module ID");

        JTextField modID = new JTextField(3);
        JTextField user = new JTextField(6);
        JTextField password = new JPasswordField(10);

        JButton login = new JButton("Login");
        JButton submitQuery = new JButton("Get Data");

        login.addActionListener(this);
        submitQuery.addActionListener(this);

        // Puts the text fields into a hashmap so that we can access them
        // later.
        textFieldMap = new HashMap<String, JTextField>();
        textFieldMap.put("user", user);
        textFieldMap.put("pass", password);
        textFieldMap.put("module", modID);

        buttonPanel.add(userL);
        buttonPanel.add(user);
        buttonPanel.add(passL);
        buttonPanel.add(password);
        buttonPanel.add(login);
        buttonPanel.add(modL);
        buttonPanel.add(modID);
        buttonPanel.add(submitQuery);

    }

    @Override
    protected void paintComponent(Graphics g) {
        for (Point point : regPoints) {
            System.out.println(point);
        }
        int[] axRanges = getRanges();
    }

    public int[] getRanges(){
        return null;
    }

    public void getRegistrationPoints(String modiD) {
        gClient.getRegistrationPoints(modiD);
    }

    public void actionPerformed(ActionEvent e) {
        String pressed = e.getActionCommand();
        if (pressed.equals("Login")) {
            JTextField password = (JTextField) textFieldMap.get("pass");
            JTextField username = (JTextField) textFieldMap.get("user");
            doServerAuth(password.getText(), username.getText());
        } else if (pressed.equals("Get Data")) {
            if (!authenticated) {
                JOptionPane.showMessageDialog(buttonPanel, "You've not yet authenticated.");
            }
            JTextField module = (JTextField) textFieldMap.get("module");
            regPoints = gClient.getRegistrationPoints(module.getText());
            repaint();
        }
    }

    public void doServerAuth(String user, String password) {
        System.out.println(gClient);
        boolean hand = gClient.handshakeServ();
        if (hand) {
            boolean auth = gClient.authenticate(user, password);
            if (auth) {
                authenticated = true;
                JOptionPane.showMessageDialog(buttonPanel, "Successfully authenticated.");
                return;
            }
        }
        authenticated = false;
        JOptionPane.showMessageDialog(buttonPanel, "Authentication failed.");
    }
}
