/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GraphDB;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
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
        this.add(buttonPanel);

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
        JButton disconnect = new JButton("Disconnect");

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
        buttonPanel.add(disconnect);

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (regPoints != null) {
            int[] axRanges = getRanges();
            Point2D topLeft = new Point2D.Double(50, 90);
            Point2D origin = new Point2D.Double(50, 515);
            Point2D bottomRight = new Point2D.Double(700, 515);

            Line2D leftAxis = new Line2D.Double(topLeft, origin);
            g2.draw(leftAxis);
            Line2D bottomAxis = new Line2D.Double(origin, bottomRight);
            g2.draw(bottomAxis);

            // student label
            g2.drawString("Students", (float) topLeft.getX() - 30, (float) (origin.getY() - topLeft.getY())/2);
            // year label
            g2.drawString("Year", (float) (bottomRight.getX() - origin.getX())/2, (float) bottomRight.getY() + 30);

            // max year
            g2.drawString(String.valueOf(axRanges[0]), (float) bottomRight.getX(), (float) bottomRight.getY());
            // min year
            g2.drawString(String.valueOf(axRanges[1]), (float) origin.getX(), (float) origin.getY() + 15);
            // max students
            g2.drawString(String.valueOf(axRanges[2]), (float) topLeft.getX(), (float) topLeft.getY());
            // min students
            g2.drawString(String.valueOf(axRanges[3]), (float) origin.getX() - 10, (float) origin.getY());
            

        }
    }

    /**
     * gets the min an max values for the axes to be at.
     * @return
     */
    public int[] getRanges() {
        if (regPoints != null) {
            int maxYr = Integer.MIN_VALUE;
            int minYr = Integer.MAX_VALUE;
            int maxStud = Integer.MIN_VALUE;
            int minStud = Integer.MAX_VALUE;
            // y point is student count, x is year
            for (Point point : regPoints) {
                if (point.x > maxYr) {
                    maxYr = point.x;
                }
                if (point.x < minYr) {
                    minYr = point.x;
                }

                if (point.y > maxStud) {
                    maxStud = point.y;
                }
                if (point.y < minStud) {
                    minStud = point.y;
                }
            }
            System.out.printf("mxyr %d, minyr %d, maxst %d, minst %d\n", maxYr, minYr, maxStud, minStud);
            return new int[]{maxYr, minYr, maxStud, minStud};
        }

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
            System.out.println(regPoints);
            repaint();
        } else if (pressed.equals("Disconnect")) {
            gClient.disconnect();
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
