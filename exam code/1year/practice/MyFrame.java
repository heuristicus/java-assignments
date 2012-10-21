/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package practice;

import javax.swing.*;

public class MyFrame extends JFrame {

    public static void main(String[] args) {
        new MyFrame();
    }

	public MyFrame(){

		MyPanel panel = new MyPanel();
		JPanel auxPanel = new JPanel();

		JButton red = new JButton("red");
		JButton black = new JButton("black");
		JButton green = new JButton("green");
		green.addActionListener(panel);
		red.addActionListener(panel);
		black.addActionListener(panel);
		auxPanel.add(green);
		auxPanel.add(black);
		auxPanel.add(red);

		JFrame frame = new JFrame();
                frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.add("Center", panel);
		frame.add("South", auxPanel);
		frame.setSize(800, 600);
		frame.setVisible(true);

	}

}


