/*
 * Hunter Broughton
 * CS231 A
 * April 2023
 * 
 * Maze.java
 * 
 * This class represents a 2D maze grid composed of cells with different types.
 * The maze can contain FREE cells (passable), OBSTACLE cells (impassable),
 * ICE cells, and MUD cells (special terrain types with different movement
 * characteristics). The maze is randomly generated based on specified density
 * parameters for each cell type.
 */

import java.awt.Graphics;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

public class Maze implements Iterable<Cell> {

    /**
     * Iterator that traverses all cells in the maze row by row, column by column.
     * 
     * @return an iterator for traversing maze cells
     */
    public Iterator<Cell> iterator() {
        return new Iterator<Cell>() {
            int r, c;

            public boolean hasNext() {
                return r < getRows();
            }

            public Cell next() {
                Cell next = get(r, c);
                c++;
                if (c == getCols()) {
                    r++;
                    c = 0;
                }
                return next;
            }
        };
    }

    // Dimensions of the maze
    private int rows, cols;

    // Density parameters for different cell types (probability of generation)
    private double densityOfObstacles;  // Probability of obstacle cells
    private double densityOfIce;        // Probability of ice cells
    private double densityOfMud;        // Probability of mud cells

    // 2D array storing all cells in the maze
    private Cell[][] landscape;

    /**
     * Constructs a maze with specified dimensions and cell type densities.
     * Each cell is randomly assigned a type based on the density parameters.
     * 
     * @param rows the number of rows in the maze
     * @param columns the number of columns in the maze
     * @param densityOfObstacles probability that any cell will be an obstacle
     * @param densityOfIce probability that any cell will be ice terrain
     * @param densityOfMud probability that any cell will be mud terrain
     */
    public Maze(int rows, int columns, double densityOfObstacles, double densityOfIce, double densityOfMud) {
        this.rows = rows;
        this.cols = columns;
        this.densityOfObstacles = densityOfObstacles;
        this.densityOfIce = densityOfIce;
        this.densityOfMud = densityOfMud;
        landscape = new Cell[rows][columns];
        reinitialize();
    }

    /**
     * Initializes all cells in the maze by randomly assigning cell types
     * based on the density parameters. Uses cumulative probability to
     * determine cell types in order: OBSTACLE, ICE, MUD, then FREE.
     */
    public void reinitialize() {
        Random rand = new Random();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                double randDouble = rand.nextDouble();
                
                // Assign cell types based on cumulative probabilities
                if(randDouble < densityOfObstacles){
                    landscape[r][c] = new Cell(r, c, CellType.OBSTACLE);
                } else if(randDouble < densityOfObstacles + densityOfIce){
                    landscape[r][c] = new Cell(r, c, CellType.ICE);
                } else if(randDouble < densityOfObstacles + densityOfIce + densityOfMud){
                    landscape[r][c] = new Cell(r, c, CellType.MUD);
                } else {
                    landscape[r][c] = new Cell(r, c, CellType.FREE);
                }
            }
        }
    }

    /**
     * Resets all cells in the maze to their initial state.
     * Calls the reset method on each cell to clear search-related data.
     */
    public void reset() {
        for (Cell cell : this)
            cell.reset();
    }

    /**
     * Returns the number of rows in the maze.
     * 
     * @return the number of rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Returns the number of columns in the maze.
     * 
     * @return the number of columns
     */
    public int getCols() {
        return cols;
    }

    /**
     * Returns the cell at the specified position in the maze.
     * 
     * @param row the row index
     * @param col the column index
     * @return the cell at the specified position
     */
    public Cell get(int row, int col) {
        return landscape[row][col];
    }

    /**
     * Returns a list of neighboring cells that are not obstacles.
     * Considers the four cardinal directions (up, down, left, right).
     * Only includes neighbors that are within maze bounds and not obstacles.
     * 
     * @param c the cell whose neighbors to find
     * @return a LinkedList of passable neighboring cells
     */
    public LinkedList<Cell> getNeighbors(Cell c) {
        LinkedList<Cell> cells = new LinkedList<Cell>();
        // Define the four cardinal directions: up, down, right, left
        int[][] steps = new int[][] { { -1, 0 }, { 1, 0 }, { 0, 1 }, { 0, -1 } };
        
        for (int[] step : steps) {
            int nextRow = c.getRow() + step[0];
            int nextCol = c.getCol() + step[1];
            
            // Check bounds and ensure the neighbor is not an obstacle
            if (nextRow >= 0 && nextRow < getRows() && nextCol >= 0 && nextCol < getCols()
                    && get(nextRow, nextCol).getType() != CellType.OBSTACLE)
                cells.addLast(get(nextRow, nextCol));
        }
        return cells;
    }

    /**
     * Returns a string representation of the maze.
     * Uses 'X' for obstacles and spaces for other cell types.
     * 
     * @return string representation of the maze
     */
    public String toString() {
        StringBuilder output = new StringBuilder();
        output.append("-".repeat(cols + 3) + "\n");
        for (Cell[] cells : landscape) {
            output.append("| ");
            for (Cell cell : cells) {
                output.append(cell.getType() == CellType.OBSTACLE ? 'X' : ' ');
            }
            output.append("|\n");
        }
        return output.append("-".repeat(cols + 3)).toString();
    }

    /**
     * Draws the maze by calling the drawType method on each cell.
     * 
     * @param g the Graphics object to draw on
     * @param scale the scale factor for drawing
     */
    public void draw(Graphics g, int scale) {
        for (Cell cell : this)
            cell.drawType(g, scale);
    }

    /**
     * Main method for testing the Maze class.
     * Creates a sample maze and prints it to the console.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        Maze ls = new Maze(7, 7, .2, .05, .05);
        System.out.println(ls);
    }
}