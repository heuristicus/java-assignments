
/* To change th choose Tools | Templates
 * and open the template in the editor.
 */
package radar;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Arc2D;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.Timer;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author michal
 */
public class radar extends JPanel {

    /**
     * The scalar value for the outer circle.  The minLength will be multiplied by this when the outer
     * circle is drawn.  Is also used to determine the length of the leading line, as well as the size of
     * the arc's radius.
     */
    private final double OUTER_CIRCLE_SCALAR = 0.9;
    // scalar value for middle circle
    private final double MID_CIRCLE_SCALAR = 0.6;
    // scalar value for inner circle
    private final double INNER_CIRCLE_SCALAR = 0.3;
    // The angle by which the line and arc are rotated each time step (in degrees)
    private final double LINE_ANGLE_INCREMENT = 0.5;
    Color backgroundGreen = new Color(0, 50, 0);
    Color vibrantGreen = new Color(63, 255, 0);
    Color fadedGreen = new Color(0.6f, 0.8f, 0f, 0.3f);
    GradientPaint arcPaint;
    JPanel panel;
    JFrame frame;
    Graphics2D g2;
    AffineTransform defaultTransform;
    double curLineAngle = 0;
    //smallest value of the frame's x and y lengths
    int minLength;
    Timer tim;
    Point spinLineEndPoint;
    Point origin = new Point(0, 0);
    Line2D spinLine;
    Arc2D spinArc;
    Point centrePoint;
    Area planeArea;
    Ellipse2D outsideCircle;
    int noDots = 100;
    Point2D[] dotArray = new Point2D[noDots];
    ArrayList<Shape> detectedShapes = new ArrayList<Shape>();
    boolean firstRun = true;

    public static void main(String[] args) {
        new radar();
    }

    /**
     * Basic constructor for the radar class.  Creates a new panel for use,
     * and then puts that panel into a frame.
     */
    public radar() {
        panel = new JPanel();
        setBackground(Color.black);
        createFrame();
        setVariables();
        createTimer();
    }

    /**
     * Called whenever the panel needs to be redrawn.  Calls various methods to update locations
     * of things on the radar and redraws the background, as well as updating some variables.
     * @param _g
     */
    @Override
    protected void paintComponent(Graphics _g) {
        super.paintComponent(_g);
        g2 = (Graphics2D) _g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        updateVariables();
        defaultGraphics();
        drawBackground();
        if (firstRun == true) {
            initDots();
            firstRun = false;
        }
        populateRadar();
        updateMovingObjects();
        drawMovingObjects();
        checkLineIntersects();
        updateDetected();
    }

    /**
     * Initialises some variables for use during runtime.
     * - smallest length of the width and height of the frame
     * - the end point of the spinning line
     * - the spin line
     * - the arc
     */
    private void setVariables() {
        minLength = Math.min(frame.getWidth(), frame.getHeight());
        spinLineEndPoint = new Point((int) (minLength / 2 * OUTER_CIRCLE_SCALAR), 0);
        spinLine = new Line2D.Double(origin, spinLineEndPoint);
        initArc();
    }

    /**
     * Creates a timer which is used to update the spin line, and call repaint at every time step.
     */
    private void createTimer() {
        tim = new Timer(40, new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                updateSpinLine();
                repaint();
            }
        });
        tim.start();
    }

    /**
     * updates variables to new values based on the size of the frame.
     */
    private void updateVariables() {
        minLength = Math.min(frame.getWidth(), frame.getHeight());
        centrePoint = new Point(frame.getHeight() / 2, frame.getWidth() / 2);
        origin = new Point(0, 0);
        updatePlaneArea();
    }

    /**
     * Updates the spinline end point to its new location, and recreates the spinline as a new object.
     */
    private void updateSpinLine() {
        updateLineEndPoint();
        spinLine = new Line2D.Double(origin, spinLineEndPoint);
    }

    /**
     * Draws the spin line
     */
    private void drawSpinLine() {
        g2.setColor(Color.white);
        g2.draw(spinLine);
    }

    /**
     * Updates positions of moving objects
     */
    private void updateMovingObjects() {
        updateSpinLine();
    }

    /**
     * Draws the moving objects on the radar.
     */
    private void drawMovingObjects() {
        drawSpinLine();
        drawArc();
    }

    /**
     * populates the radar with planes, dots and raster images.
     */
    private void populateRadar() {
        drawPlanes();
        drawPlaneFormation();
        drawDots();
        drawRaster();
    }

    /**
     * Updates the line end point to a new value which will allow the line to be drawn
     * LINE_ANGLE_INCREMENT degrees further along the spinning path.
     */
    private void updateLineEndPoint() {
        double xVal = 0 + minLength / 2 * Math.cos(Math.toRadians(curLineAngle) + Math.toRadians(LINE_ANGLE_INCREMENT));
        double yVal = 0 + minLength / 2 * Math.sin(Math.toRadians(curLineAngle) + Math.toRadians(LINE_ANGLE_INCREMENT));
        spinLineEndPoint = new Point((int) (xVal * OUTER_CIRCLE_SCALAR), (int) (yVal * OUTER_CIRCLE_SCALAR));
        curLineAngle = curLineAngle >= 360 ? 0 : curLineAngle + LINE_ANGLE_INCREMENT;
//        System.out.println("new end point is (" + xVal + ", " + yVal + ")");
//        System.out.println(curLineAngle);
//        System.out.println("length of line is " + Math.sqrt(Math.pow(yVal, 2) + Math.pow(xVal, 2)));
    }

    /**
     * Draws the background of the radar - the concentric circles, background circle, the triangle on the second concentric circle,
     * and lines.
     */
    private void drawBackground() {
        // Creates the circles to be used as the background, and some other tasks - the outsideCircle is used to check if points are on the radar.
        Shape mainCircle = new Ellipse2D.Double(0, 0, minLength * OUTER_CIRCLE_SCALAR, minLength * OUTER_CIRCLE_SCALAR); //should be filled to create main radar BG
        Shape outerCircle = mainCircle;  // do this drawn, or simply redraw the mainCircle object
        outsideCircle = new Ellipse2D.Double(-300 * OUTER_CIRCLE_SCALAR, -300 * OUTER_CIRCLE_SCALAR, minLength * OUTER_CIRCLE_SCALAR, minLength * OUTER_CIRCLE_SCALAR);
        Shape midCircle = new Ellipse2D.Double(0, 0, minLength * MID_CIRCLE_SCALAR, minLength * MID_CIRCLE_SCALAR);
        Shape innerCircle = new Ellipse2D.Double(0, 0, minLength * INNER_CIRCLE_SCALAR, minLength * INNER_CIRCLE_SCALAR);
        // Puts circles into an array for easier processing
        Shape[] circles = new Shape[]{outerCircle, midCircle, innerCircle};
        g2.setColor(backgroundGreen);
        centreCircle(mainCircle, true);
        g2.setColor(vibrantGreen);
        for (Shape shape : circles) {
            centreCircle(shape, false);
        }
        // Draws a gradiented circle on the radar.
        GradientPaint gradCirclePaint = new GradientPaint(15, 30, Color.red, 35, 60, vibrantGreen, true);
        g2.setPaint(gradCirclePaint);
        g2.setStroke(new BasicStroke(4));
        g2.draw(new Ellipse2D.Double(20, 40, 20, 20));

        g2.setStroke(new BasicStroke(1));
        g2.setColor(vibrantGreen);


        // Creates a triangle on the middle ring, and then subtracts the area of the middle circle from it.
        int triangleXPoints[] = new int[]{0, (int) (-minLength * 0.04), (int) (minLength * 0.04)};
        int triangleYPoints[] = new int[]{(int) ((-minLength / 2 * MID_CIRCLE_SCALAR) - minLength * 0.03), (int) (-minLength * 0.29), (int) (-minLength * 0.29)};
        Shape triangle = new Polygon(triangleXPoints, triangleYPoints, 3);
        Area triangleArea = new Area(triangle);
        Area midCircleArea = new Area(new Ellipse2D.Double((-minLength * MID_CIRCLE_SCALAR) / 2, (-minLength * MID_CIRCLE_SCALAR) / 2, minLength * MID_CIRCLE_SCALAR, minLength * MID_CIRCLE_SCALAR));
        triangleArea.subtract(midCircleArea);
        g2.fill(triangleArea);

        g2.setTransform(defaultTransform);  //sets transform to default after it is changed by the centrecircle method

    }

    /**
     * makes the plane area.  Can also be used to update the size of the plane if the window is made resizable,
     * but currently this does not work particularly well.
     */
    private void updatePlaneArea() {
        g2.setColor(vibrantGreen);
        //sharp plane
        int[] t1x = new int[]{0, (int) (-minLength * 0.007), (int) (minLength * 0.007)};
        int[] t1y = new int[]{0, (int) (minLength * 0.05), (int) (minLength * 0.05)};
        int[] t2x = new int[]{0, (int) (-minLength * 0.006), (int) (minLength * 0.006)};
        int[] t2y = new int[]{(int) (minLength * 0.03), (int) (minLength * 0.06), (int) (minLength * 0.06)};
        //fat plane
//        int[] t1x = new int[]{0, (int) (-minLength * 0.007), (int) (minLength * 0.007)};
//        int[] t1y = new int[]{0, (int) (minLength * 0.05), (int) (minLength * 0.05)};
//        int[] t2x = new int[]{0, (int) (-minLength * 0.009), (int) (minLength * 0.009)};
//        int[] t2y = new int[]{(int) (minLength * 0.01), (int) (minLength * 0.04), (int) (minLength * 0.04)};
        Shape t1 = new Polygon(t1x, t1y, 3);
        Shape t2 = new Polygon(t2x, t2y, 3);
        Area t1area = new Area(t1);
        Area t2area = new Area(t2);
        t1area.add(t2area);
        // Puts the created area to a global variable so it can be used elsewhere.
        planeArea = t1area;
    }

    /**
     * Draws a plane on the radar.
     */
    private void drawPlanes() {
        AffineTransform pTransform = new AffineTransform();
        pTransform.rotate(Math.toRadians(60));
        pTransform.translate(centrePoint.x / 2, centrePoint.y / 2);
        g2.setTransform(pTransform);
//        g2.fill(planeArea);
        Area transPlane = planeArea.createTransformedArea(pTransform);
        g2.setTransform(defaultTransform);
        g2.fill(transPlane);
    }

    /**
     * Initialises an array of points within the bounds of the outer circle.  Won't populate very well if window is made resizable, as it uses
     * a static value as a limit for random point generation (i.e. points will be between (0,0) and (500,500).  Also,
     * outsideCircle is defined when the class is created, and will not change should the window be resized, so
     * the dots present will be concentrated in a specific area.
     */
    private void initDots() {
        Point2D randPoint;
        g2.translate(0, 0);
        for (int i = 0; i < noDots; i++) {
            boolean pointChosen = false;
            while (!pointChosen) {
                // Generates a point with coordinates between -600 and 600.
                randPoint = new Point2D.Double((-1 + Math.random() * 2) * 600 * OUTER_CIRCLE_SCALAR, (-1 + Math.random() * 2) * 600 * OUTER_CIRCLE_SCALAR);
                System.out.println(randPoint);
                //Checks if the outside circle contains the point, and if it does, adds that point to the dotArray, and starts finding a new point.
                if (outsideCircle.contains(randPoint)) {
                    dotArray[i] = randPoint;
                    pointChosen = true;
                }
            }
        }
    }

    /**
     * Fills circles from the point array.
     */
    private void drawDots() {
        g2.setColor(fadedGreen);
        g2.translate(0, 0);
        // Draws circles from the points in the dotArray
        for (Point2D point : dotArray) {
            g2.fill(new Ellipse2D.Double(point.getX(), point.getY(), 10, 10));
//            g2.draw(new Line2D.Double(origin, point));
        }
        g2.setTransform(defaultTransform);
    }

    /**
     * Checks if a point is less than 5 units away from the spinning line, and if it is,
     * draws a box around the point.
     */
    private void checkLineIntersects() {
        g2.setColor(Color.red);
        for (Point2D point : dotArray) {
            // Checks distance from point to line is < 5
            if (spinLine.ptSegDist(point) < 5) {
                // Creates a bounding square for the circle and adds it to the detected shapes array.
                Shape boundSquare = new Rectangle2D.Double(point.getX(), point.getY(), 10, 10);
                // draws a line from the origin to the point.
                g2.draw(new Line2D.Double(origin, point));
                // Adds the bounding rectangle to the detected shapes array if it is not there already
                if (!detectedShapes.contains(boundSquare)) {
                    detectedShapes.add(boundSquare);
                }
                // Draws the bounding square
                g2.draw(boundSquare);
            }
        }
    }

    /**
     * Updates the arraylist of detected shapes.  If the size of the arraylist is larger than a certain percentage of the
     * total number of dots on the radar, it gets rid of a percentage of these dots, giving a rudimentary radar sweep.
     */
    private void updateDetected() {
        if (detectedShapes.size() > noDots * 0.2) {
            for (int i = 0; i < noDots * 0.1; i++) {
                detectedShapes.remove(i);
            }
        }
        // Draws each shape in the detectedShapes array (these are bounding boxes for the shapes that have been detected.
        for (Shape s : detectedShapes) {
            g2.draw(s);
        }
    }

    /**
     * Draws some more planes with various transformations applied.
     */
    private void drawPlaneFormation() {
        Area plane1 = (Area) planeArea.clone();
        Area plane2 = (Area) planeArea.clone();
        Area plane3 = (Area) planeArea.clone();

        AffineTransform t1 = new AffineTransform();
        t1.shear(1, 2);
        t1.translate(100, 200);
        g2.setTransform(t1);
        g2.fill(plane1);

        AffineTransform t2 = new AffineTransform();
        t2.shear(0.3, 0.5);
        t2.translate(180, 120);
        g2.setTransform(t2);
        g2.fill(plane2);

        AffineTransform t3 = new AffineTransform();
        t3.translate(410, 90);
        t3.shear(0.9, 4);
        g2.setTransform(t3);
        g2.fill(plane3);
        g2.setTransform(defaultTransform);
    }

    /**
     * imports a raster image from the working directory and draws it with some translation, rotation and shearing.
     */
    private void drawRaster() {
        // gets the image from the working directory

        Image e = null;
        try {
            e = ImageIO.read(new File("face.png"));
        } catch (IOException ex) {
            System.out.println("Image not found");
        }
        g2.drawImage(e, 50, 50, null);

        // Applies some transforms to the raster image and then draws it.
        AffineTransform trans = new AffineTransform();
        trans.translate(50, -180);
        trans.rotate(60);
        trans.scale(0.4, 0.9);
        g2.drawImage(e, trans, this);

        g2.setTransform(defaultTransform);
    }

    /**
     * Draws the arc with a rotation of a certain number of degrees.  This is based on
     * how many timesteps there have been since starting the program.
     */
    private void drawArc() {
        AffineTransform arcTransform = new AffineTransform();
        arcTransform.translate(centrePoint.x, centrePoint.y);
        arcTransform.rotate(Math.toRadians(curLineAngle));
        g2.setTransform(arcTransform);
        g2.setPaint(arcPaint);
        g2.fill(spinArc);
        g2.setTransform(defaultTransform);
    }

    /**
     * Initialises the arc with some initial values.  Also makes a gradient paint with which to paint the arc.
     */
    private void initArc() {
        spinArc = new Arc2D.Double();
        spinArc.setArcByCenter(0, 0, minLength / 2 * OUTER_CIRCLE_SCALAR, 0, 30, Arc2D.PIE);
//        arcPaint = new GradientPaint((int) (minLength / 2 * 0.9), 0, vibrantGreen, (int) (minLength / 2 * 0.6), (int) (-minLength / 2 * 0.3), new Color(0f, 0f, 0f, 0f));
        arcPaint = new GradientPaint(spinArc.getStartPoint(), vibrantGreen, spinArc.getEndPoint(), new Color(0f, 0f, 0f, 0f));
    }

    /**
     *  Moves a circle shape from its standard creation position (to the right and below the origin), so that the
     * circle's centre is on the origin.
     * @param _circle The circle to centre.
     * @param _fill Whether the circle is to be filled or not.
     */
    private void centreCircle(Shape _circle, boolean _fill) {
        AffineTransform circleTransform = new AffineTransform();
        circleTransform.translate(-_circle.getBounds().height / 2, -_circle.getBounds().width / 2);
        g2.transform(circleTransform);
        if (_fill == true) {
            g2.fill(_circle);
        } else {
            g2.draw(_circle);
        }
        g2.setTransform(defaultTransform);
    }

    /**
     * Translates the origin to the centre of the frame, and sets this as a transform to be used to
     * reset the graphics if a different transform is applied.
     */
    private void defaultGraphics() {
        g2.translate(frame.getWidth() / 2, frame.getHeight() / 2);
        defaultTransform = g2.getTransform();
    }

    /**
     * Creates a frame to enclose the panel object.  Its initial location is based on the screen
     * size.
     */
    private void createFrame() {
        frame = new JFrame("r");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setLocation(screenSize.width / 5, screenSize.height / 5);
        frame.add(this);
        frame.setResizable(false);
        frame.setVisible(true);
    }
}
