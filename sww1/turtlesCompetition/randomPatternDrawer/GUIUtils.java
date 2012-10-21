/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package randomPatternDrawer;

import fyw.turtles.*;
import java.awt.*;
import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.ImageIcon;
import java.util.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

/**
 *
 * @author Michal
 */
public class GUIUtils {

    /**
     * Used to get a point at which to open the iput window.
     * @return A Dimension of 2/3 the of the screen resolution.
     */
    public static Dimension getWindowLoc() {
        Toolkit t = Toolkit.getDefaultToolkit();
        Dimension res = t.getScreenSize();
        if (res.width/res.height == 16/9) {
            res.height = (res.height/5);
            res.width = (res.width/3);
            return res;
        } else {
            res.height = ((res.height/3));
            res.width = ((res.width/2));
            return res; 
        }
        
    }

    public static void makeWindow() {

    }

}
