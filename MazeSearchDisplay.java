/*
 * Hunter Broughton
 * CS231 A
 * April 2023
 * 
 * MazeSearchDisplay.java
 * 
 * This class provides a graphical user interface for visualizing maze search
 * algorithms in real-time. It creates a JFrame window with a custom JPanel
 * that displays the maze, search progress, and path finding visualization.
 * The display updates dynamically as search algorithms explore the maze.
 * 
 * Originally written by Bruce A. Maxwell
 * Updated by Brian Eastwood, Stephanie Taylor, and Bender
 * Updated with documentation by Hunter Broughton, April 2023
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MazeSearchDisplay {
    JFrame win;                           // Main application window
    protected AbstractMazeSearch searcher; // The search algorithm being visualized
    private Panel canvas;                 // Custom drawing panel
    private int gridScale;               // Size of each cell in pixels

    /**
     * Initializes a graphical display window for visualizing maze search algorithms.
     * Creates a JFrame with a custom drawing panel that shows the maze and search progress.
     * 
     * @param searcher the maze search algorithm to visualize
     * @param scale controls the pixel size of each cell in the display grid
     */
    public MazeSearchDisplay(AbstractMazeSearch searcher, int scale) {
        // Set up the main application window
        this.win = new JFrame("Maze Search Visualization - " + searcher.getClass().getSimpleName());
        this.win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.searcher = searcher;
        this.gridScale = scale;

        // Create a drawing panel with buffer space around the maze
        // Add 2 rows/columns of padding for visual clarity
        this.canvas = new Panel((int) (this.searcher.getMaze().getCols() + 2) * this.gridScale,
                (int) (this.searcher.getMaze().getRows() + 2) * this.gridScale);

        // Configure and display the window
        this.win.add(this.canvas, BorderLayout.CENTER);
        this.win.pack();
        this.win.setVisible(true);
    }

    public void setMazeSearch(AbstractMazeSearch searcher) {
        this.searcher = searcher;
    }

    public void closeWindow() {
        this.win.dispose();
    }

    /**
     * Saves an image of the display contents to a file. The supplied
     * filename should have an extension supported by javax.imageio, e.g.
     * "png" or "jpg".
     *
     * @param filename the name of the file to save
     */
    public void saveImage(String filename) {
        // get the file extension from the filename
        String ext = filename.substring(filename.lastIndexOf('.') + 1, filename.length());

        // create an image buffer to save this component
        Component tosave = this.win.getRootPane();
        BufferedImage image = new BufferedImage(tosave.getWidth(), tosave.getHeight(),
                BufferedImage.TYPE_INT_RGB);

        // paint the component to the image buffer
        Graphics g = image.createGraphics();
        tosave.paint(g);
        g.dispose();

        // save the image
        try {
            ImageIO.write(image, ext, new File(filename));
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
    }

    /**
     * This inner class provides the panel on which MazeSearcher elements
     * are drawn.
     */
    private class Panel extends JPanel {
        /**
         * Creates the panel.
         * 
         * @param width  the width of the panel in pixels
         * @param height the height of the panel in pixels
         */
        public Panel(int width, int height) {
            super();
            this.setPreferredSize(new Dimension(width, height));
            this.setBackground(Color.BLACK);
        }

        /**
         * Method overridden from JComponent that is responsible for
         * drawing components on the screen. The supplied Graphics
         * object is used to draw.
         * 
         * @param g the Graphics object used for drawing
         */
        public void paintComponent(Graphics g) {
            // take care of housekeeping by calling parent paintComponent
            super.paintComponent(g);
            g.translate(gridScale, gridScale);

            // call the MazeSearcher draw method here
            searcher.draw(g, gridScale);
        } // end paintComponent
    } // end Panel

    public void repaint() {
        this.win.repaint();
    }
}