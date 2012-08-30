/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package exercise01a;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.CubicCurve2D;
import java.awt.geom.Line2D;
import java.awt.geom.QuadCurve2D;
import java.awt.geom.Rectangle2D;
import javax.swing.JPanel;

/**
 *
 * @author Michal
 */
public class drawPanel extends JPanel {

    JPanel panel;
    final static BasicStroke lineStyle = new BasicStroke(15.0f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);

    public drawPanel() {
        panel = new JPanel();
        panel.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        AffineTransform transform = new AffineTransform();
        transform.translate(150, 0);
        transform.scale(1, 1.5);
        transform.rotate(0.3);
        g2.transform(transform);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(new BasicStroke(10));
        g2.setColor(Color.red);
        g2.draw(new Line2D.Double(10, 10, 180, 180));
        g2.setColor(Color.green);
        g2.fill(new Rectangle2D.Double(20, 20, 40, 40));
        g2.fill(new Rectangle2D.Double(120, 120, 40, 40));
        GradientPaint grad = new GradientPaint(0, 0, Color.blue, 50, 25, Color.green, true);
        g2.setPaint(grad);
        g2.draw(new Rectangle2D.Double(40, 40, 100, 100));
        g2.setStroke(lineStyle);
        g2.setPaint(new GradientPaint(0, 0, Color.red, 50, 25, Color.yellow, true));
        g2.draw(new QuadCurve2D.Double(10, 10, 240, 85, 180, 180));
        g2.draw(new CubicCurve2D.Double(10, 10, 5, 250, 50, 250, 180, 180));
    }
}
