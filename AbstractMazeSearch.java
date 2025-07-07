/*
 * Hunter Broughton
 * CS231 A
 * April 2023
 * 
 * AbstractMazeSearch.java
 * 
 * This abstract class provides the framework for implementing different maze search
 * algorithms. It defines the common functionality and interface that all search
 * algorithms must implement, including pathfinding, visualization, and tracking
 * of visited cells. Concrete implementations include depth-first search,
 * breadth-first search, and A* search.
 * 
 * To compile: javac AbstractMazeSearch.java
 */

import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

public abstract class AbstractMazeSearch {

    // Instance fields for maze navigation and search state
    private Maze myMaze;              // The maze being searched
    private int cellReachCount;       // Counter for cells visited during search
    private Cell start;               // Starting cell for the search
    private Cell target;              // Target cell to find
    private Cell cur;                 // Current cell being examined

    /**
     * Constructor initializes a maze search with the given maze.
     * Cell objects are initially set to null.
     * 
     * @param maze the maze to be searched
     */
    public AbstractMazeSearch(Maze maze){
        myMaze = maze;
        cur = null;
        start = null;
        target = null;
    }

    /**
     * Abstract methods that must be implemented by subclasses.
     * These define the specific search algorithm behavior:
     * - findNextCell(): determines which cell to examine next
     * - addCell(): adds a cell to the search data structure
     * - numRemainingCells(): returns count of cells left to examine
     */
    public abstract Cell findNextCell();
    public abstract void addCell(Cell next);
    public abstract int numRemainingCells();

    /**
     * Returns the total number of cells reached during the search.
     * 
     * @return the count of cells visited
     */
    public int getReachCount(){
        return this.cellReachCount;
    }

    /**
     * Returns the maze object being searched.
     * 
     * @return the maze
     */
    public Maze getMaze(){
        return this.myMaze;
    }

    /**
     * Sets the target cell for the search.
     * 
     * @param target the cell to find
     */
    public void setTarget(Cell target){
        this.target = target;
    }

    /**
     * Returns the target cell.
     * 
     * @return the target cell
     */
    public Cell getTarget(){
        return this.target;
    }

    /**
     * Sets the current cell being examined.
     * 
     * @param cell the current cell
     */
    public void setCur(Cell cell){
        this.cur = cell;
    }

    /**
     * Returns the current cell being examined.
     * 
     * @return the current cell
     */
    public Cell getCur(){
        return this.cur;
    }

    /**
     * Sets the starting cell for the search and marks it as its own predecessor.
     * 
     * @param start the starting cell
     */
    public void setStart(Cell start){
        this.start = start;
        this.start.setPrev(start);
    }

    /**
     * Returns the starting cell.
     * 
     * @return the starting cell
     */
    public Cell getStart(){
        return this.start;
    }

    /**
     * Resets the search state by clearing all cell references.
     */
    public void reset(){
        this.start = null;
        this.cur = null;
        this.target = null;
    }

    /**
     * Traces back from the given cell to the starting cell to reconstruct
     * the path taken during the search.
     * 
     * @param cell the cell to trace back from (typically the target)
     * @return a LinkedList containing the path from start to the given cell,
     *         or null if no path exists
     */
    public LinkedList<Cell> traceback(Cell cell){
        Cell curCell = cell;
        LinkedList<Cell> path = new LinkedList<>();
    
        while (curCell != null){
            // Add current cell to the front of the path
            path.addFirst(curCell);

            // If we've reached the starting cell, return the complete path
            if(curCell == this.start){
                return path;
            }
            curCell = curCell.getPrev();
        } 
        return null; // No path found
    }

    /**
     * Performs the maze search from start to target cell using the specific
     * algorithm implemented by the subclass. Supports visualization with
     * special handling for ICE and MUD cell types that affect movement speed.
     * 
     * ICE cells: Faster movement when going straight, slower when turning
     * MUD cells: Slower movement in all directions
     * 
     * @param start the starting cell
     * @param target the target cell to find
     * @param display whether to show visual representation of the search
     * @param delay base delay in milliseconds between search steps
     * @return the path from start to target, or null if no path exists
     * @throws InterruptedException if the search is interrupted
     */
    public LinkedList<Cell> search(Cell start, Cell target, boolean display, int delay) throws InterruptedException{

        // Initialize display if requested
        MazeSearchDisplay myDisplay = null;
        if(display){
            myDisplay = new MazeSearchDisplay(this, 35);
        }

        // Initialize search state
        setStart(start);
        setTarget(target);
        setCur(start);

        // Begin search by adding the starting cell
        addCell(start);
        cellReachCount++;
        
        // Continue searching until no more cells to examine
        while(numRemainingCells() > 0){
            Cell nextCell = findNextCell();
            
            // Handle visualization with special terrain effects
            if(display){
                LinkedList<Cell> path = traceback(this.cur);
                if(path.getFirst() == null){  
                    // On the first cell - handle special terrain types
                    if(cur.getType() == CellType.ICE){
                        Thread.sleep(delay / 4);  // Ice speeds up straight movement
                    } 
                    if(nextCell.getType() == CellType.MUD){
                        Thread.sleep(delay * 6);  // Mud slows down movement
                    }
                } else {
                    // Check if we're turning (changing direction)
                    if((cur.getRow() == path.getFirst().getRow()) && nextCell.getRow() != cur.getRow()){
                        if((cur.getCol() == path.getFirst().getCol()) && nextCell.getCol() != cur.getCol()){
                            // Turning on ice is slower
                            if(cur.getType() == CellType.ICE){
                                Thread.sleep(delay * 6);
                            }
                            // Turning on mud is also slower
                            if(cur.getType() == CellType.MUD){
                                Thread.sleep(delay * 5);
                            }
                        }
                    } else if(cur.getType() == CellType.ICE){
                        Thread.sleep(delay / 4);  // Straight movement on ice is faster
                    } else if(nextCell.getType() == CellType.MUD){
                        Thread.sleep(delay * 6);  // Moving into mud is slower
                    } else {
                        Thread.sleep(delay);  // Normal movement delay
                    }     
                }
                myDisplay.repaint();  
            }

            // Move to the next cell and explore its neighbors
            setCur(nextCell);
            
            for(Cell neighbor: myMaze.getNeighbors(cur)){
                if(neighbor.getPrev() == null){
                    cellReachCount++;
                    neighbor.setPrev(cur);
                    addCell(neighbor);
                    // Check if we've found the target
                    if(neighbor.equals(target)){
                        return traceback(target);
                    }
                }
            }
        }

        return null; // No path found
    }

    /**
     * Draws the current state of the search on the given Graphics object.
     * Shows the maze, search paths, start/target/current cells, and the
     * optimal path if the target has been found.
     * 
     * @param g the Graphics object to draw on
     * @param scale the scale factor for drawing
     */
    public void draw(Graphics g, int scale) {
        // Draw the base maze structure
        getMaze().draw(g, scale);
        // Draw all paths explored during the search in red
        getStart().drawAllPrevs(getMaze(), g, scale, Color.RED);
        // Draw the starting cell in blue
        getStart().draw(g, scale, Color.BLUE);
        // Draw the target cell in red
        getTarget().draw(g, scale, Color.RED);
        // Draw the current cell being examined in magenta
        getCur().draw(g, scale, Color.MAGENTA);
    
        // If target has been found, highlight the optimal path
        if (getTarget().getPrev() != null) {
            Cell traceBackCur = getTarget().getPrev();
            // Draw intermediate cells in the optimal path in green
            while (!traceBackCur.equals(getStart())) {
                traceBackCur.draw(g, scale, Color.GREEN);
                traceBackCur = traceBackCur.getPrev();
            }
            // Draw the complete optimal path as blue lines
            getTarget().drawPrevPath(g, scale, Color.BLUE);
        }
    }
}
