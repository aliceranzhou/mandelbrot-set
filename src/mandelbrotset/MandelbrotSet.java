/* TITLE:  MANDELBROT SET
 * PROGRAMMER: ALICE ZHOU
 * LAST MODIFIED MAR 21, 2014
 */
package mandelbrotset;

import java.io.*;
import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.awt.Color;

public class MandelbrotSet extends JFrame {

    //set up frame 
    static final int width = 800;
    static final int height = 600;
    //default grid
    static double startX;
    static double endX;
    static double startY;
    static double endY;
    static double intervalX;
    static double intervalY;
    //number of iterations--passed from setUp() to isinMS() method in Complex
    //also used for colour calculation
    public static int iterations;
    //input file name
    static String setUpFile;
    //stores information for each complex number
    static float[][] grid = new float[width][height];
    static Color[][] cols = new Color[width][height];

    //inputs data from file
    public void setUp() throws IOException {
        //ask for user preference--changes input file
        ask();

        //scans from input file
        Scanner scan = new Scanner(new FileReader(setUpFile));
        //sets up grid
        double cR = scan.nextDouble(); //centre of Real axis
        double cI = scan.nextDouble(); //centre of imaginary axis
        double dH = scan.nextDouble(); //horizontal diameter
        double rY = dH * 3 / 8; //vertical diameter/2--corresponds with ratio of frame
        //uses above numbers to calculate grid extents
        startX = cR - (dH / 2);
        endX = cR + (dH / 2);
        startY = cI - rY;
        endY = cI + rY;
        //used for isInMS() calculation
        iterations = scan.nextInt();
        //determines the grid value that the program should count up by to have highest resolution possible
        intervalX = (endX - startX) / (width);
        intervalY = (endY - startY) / (height);
    }

    //ask for user input for which image to display
    public void ask() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please enter a number from 1 to 9");
        System.out.println("1-Start");
        System.out.println("2-Seahorse Valley");
        System.out.println("3-Seahorses");
        System.out.println("4-Seahorse upside-down");
        System.out.println("5-Misiurewicz point");
        System.out.println("6-Part of seahorse tail");
        System.out.println("7-Satellite");
        System.out.println("8-Antenna of satellite");
        System.out.println("9-Seahorse Valley");
//        System.out.println("10-Double spirals");
//        System.out.println("11-Secondorder satellites");
//        System.out.println("12-Imitating Julia sets");
//        System.out.println("13-double-hook");

        String s = Integer.toString(scan.nextInt());
        //changing file name
        setUpFile = "input" + s + ".txt";

        System.out.println("Thank you. Now, please be patient as MS sets up for your optimal viewing pleasure.");
        System.out.println("Meanwhile, read up here on the Mandelbrot Set: http://mathworld.wolfram.com/MandelbrotSet.html.");
    }

    //paints the Mandelbrot Set
    public void paint(Graphics g) {
        //paints entire grid with nested loop
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                //changes colour based on previous calculations
                g.setColor(cols[i][j]);

                //colours the rectangle
                g.fillRect(i, j, 1, 1);
            }
        }
    }

    //calculates whether a given point is in the set.
    public void calculate() {
        double x = startX, y = startY; // x,y are the real and imaginary values of the tested complex number

        //run through each pizel on the screen
        for (int i = 0; i < width; i++) {
            for (int j = height - 1; j >= 0; j--) {
                Complex c = new Complex(x, y);

                //isinMS() returns a float, which is 0.0f if it is in the Mandelbrot set, and a real number between 0 and #iterations if it is not.
                grid[i][j] = c.isinMS();
                if (grid[i][j] == 0.0f) {
                    cols[i][j] = Color.black;
                } else {
                    //generate color
                    float s=(float)(Math.pow((double)grid[i][j], 0.3));
                    cols[i][j] = new Color(Color.HSBtoRGB(0.7f, 1f-s, s));
                }
                //move up grid
                y += intervalY;
            }
            //move right
            x += intervalX;
            //move to bottom of grid
            y = startY;
        }
    }
    
    //initializes JFrame window
    public void initializeWindow() {
        setTitle("Mandelbrot Set");
        setSize(width, height);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBackground(Color.black);
        setVisible(true);
    }

    //game plan for Mandelbrot Set
    public static void main(String[] args) throws IOException {
        MandelbrotSet ms = new MandelbrotSet();
        ms.setUp();
        ms.calculate();
        ms.initializeWindow();
        System.out.println("Finished!");
    }
}
