/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package randomPatternDrawer;

import fyw.turtles.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUICreator extends JPanel implements ActionListener {

    public static void main(String[]args) {
    }

    /*
    public static void createWindow() {

        guiWindow.pack();

        Dimension windowLoc = GUIUtils.getWindowLoc();
        guiWindow.setBounds(windowLoc.width, windowLoc.height, 400, 500);

        guiWindow.setVisible(true);

    }
*/






    public void actionPerformed(ActionEvent e) {

    }

    public void turtleGUICreator() {

        JLabel whiteLabel = new JLabel();
        whiteLabel.setOpaque(true);
        whiteLabel.setBackground(new Color(248, 213, 131));
        whiteLabel.setPreferredSize(new Dimension(200, 180));

        JFrame guiWindow = new JFrame();
        guiWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        guiWindow.getContentPane().add(whiteLabel, BorderLayout.CENTER);
        JFrame.setDefaultLookAndFeelDecorated(true);


        JButton start, stop;

        // Creates a button used to stop the program.
        stop = new JButton("Stop");
        stop.setEnabled(false);
        stop.setMnemonic(KeyEvent.VK_ENTER);
        stop.setToolTipText("Stop drawing.");

        // Creates a button to start the program.
        start = new JButton("Start");
        start.setMnemonic(KeyEvent.VK_ENTER);
        start.setToolTipText("Start drawing!");

        JRadioButton blackAndWhite = new JRadioButton("Black & White");
        blackAndWhite.setSelected(true);
        blackAndWhite.setToolTipText("Your favourite '40s tones.");

        JRadioButton randomRecursion = new JRadioButton("Colour Madness!");
        randomRecursion.setToolTipText("Every time the move method is called a new " +
                " colour is generated, and a line is drawn with this colour until the next" +
                "time the method is called.");

        

        JRadioButton randomSingleColour = new JRadioButton("Random Colour");
        randomSingleColour.setToolTipText("Pseudo-randomly picks a colour " +
                "and uses that to draw all the lines.");

        JRadioButton pickColour = new JRadioButton("Pick your own colour.");
        pickColour.setToolTipText("RGB codes only, sorry!");

        ButtonGroup colours = new ButtonGroup();
        colours.add(blackAndWhite);
        colours.add(randomRecursion);
        colours.add(randomSingleColour);
        colours.add(pickColour);



        // Listens for actions on the buttons.
        start.addActionListener(this);
        stop.addActionListener(this);
        pickColour.addActionListener(this);

        JPanel radioPanel = new JPanel(new GridLayout(0, 1));
        radioPanel.add(blackAndWhite);
        radioPanel.add(randomRecursion);
        radioPanel.add(randomSingleColour);
        radioPanel.add(pickColour);

        JFormattedTextField seed = new JFormattedTextField();
        seed.setValue(new Double(0.0));
        seed.setColumns(9);

    }



}
