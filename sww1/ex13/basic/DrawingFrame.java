package ex13.basic;

import java.awt.event.*;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DrawingFrame extends JFrame implements ActionListener {

    DrawingCanvas canvas = new DrawingCanvas();

    /**
     * Creates the panels and buttons to be used on the GUI, and adds action listeners to
     * the buttons so that when they are clicked, a method can be called to execute an
     * action.
     * @param w Width of the window.
     * @param h Height of the window.
     */
    public DrawingFrame(int w, int h) {
        setTitle("Drawing demonstration");
        setSize(w, h);
        add("Center", canvas);

        JPanel panel = new JPanel();
        JButton rectangle = new JButton("Rectangle");
        JButton oval = new JButton("Oval");
        JButton line = new JButton("Line");
        JButton exit = new JButton("Exit");
        exit.setActionCommand("exit");
        oval.setActionCommand("oval");
        rectangle.setActionCommand("rectangle");
        line.setActionCommand("line");

        panel.add(exit);
        panel.add(line);
        panel.add(rectangle);
        panel.add(oval);
        add("South", panel);
        exit.addActionListener(this);
        oval.addActionListener(this);
        rectangle.addActionListener(this);
        line.addActionListener(this);


        /*
         * Adds a window listener which listens to clicks on the top right of the window, and closes the window if
         * a click is detected.
         */
        addWindowListener(
                new WindowAdapter() {

                    @Override
                    public void windowClosing(WindowEvent e) {
                        System.exit(0);
                    }
                });

        setVisible(true);

    }

    /**
     * Called if an action is performed.  This redirects the action on any of the buttons to
     * its specific method.
     * @param e
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("exit")) {
            exit();
        } else if (e.getActionCommand().equals("line")) {
            line();
        } else if (e.getActionCommand().equals("rectangle")) {
            rectangle();
        } else if (e.getActionCommand().equals("oval")) {
            oval();
        }
    }

    /**
     * Exits the application when the Exit button is pressed.
     */
    public void exit() {
        System.exit(0);
    }

    /**
     * Sets the value currentShape to 1 (makes the DrawingCanvas draw lines)
     */
    public void line() {

        canvas.setCurrentShape(1);
    }

    /**
     * Sets the value of currentShape to 2 (makes the DrawingCanvas draw rectangles)
     */
    public void rectangle() {
        canvas.setCurrentShape(2);
    }

    /**
     * Sets the value of currentShape to 3 (makes the DrawingCanvas draw ovals)
     */
    public void oval() {
        canvas.setCurrentShape(3);
    }
}
