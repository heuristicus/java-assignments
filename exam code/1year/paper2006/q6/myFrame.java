/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package paper2006.q6;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author Michal
 */
public class myFrame extends JFrame implements ActionListener {

    private JButton lastButtonClicked;
    private JPanel mainPanel;
    private JButton whiteButton, grayButton;
    private JFrame mainFrame;

    public static void main(String[] args) {
        new myFrame();
    }

    public myFrame() {
        mainFrame = new JFrame();
        whiteButton = new JButton("white");
        grayButton = new JButton("gray");
        whiteButton.addActionListener(this);
        grayButton.addActionListener(this);
        mainPanel = new myPanel();
        mainPanel.setBackground(Color.white);
        whiteButton.setEnabled(false);
        lastButtonClicked = whiteButton;

        JPanel auxPanel = new JPanel();
        auxPanel.add(whiteButton);
        auxPanel.add(grayButton);
        mainFrame.add("South", auxPanel);
        mainFrame.add("Center", mainPanel);
        mainFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        mainFrame.setSize(800, 600);
        mainFrame.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {

        lastButtonClicked.setEnabled(true);
        if (e.getActionCommand().equals("white")) {
            mainPanel.setBackground(Color.white);
            lastButtonClicked = whiteButton;
            lastButtonClicked.setEnabled(false);
        } else if (e.getActionCommand().equals("gray")) {
            mainPanel.setBackground(Color.gray);
            lastButtonClicked = grayButton;
            lastButtonClicked.setEnabled(false);
        }
    }
}
