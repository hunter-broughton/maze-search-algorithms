/*
 * Hunter Broughton
 * CS231 A
 * April 2023
 * 
 * MazeBreadthFirstSearch.java
 * 
 * This class implements a breadth-first search algorithm for maze pathfinding.
 * It extends AbstractMazeSearch and uses a queue data structure to maintain
 * the FIFO (First In, First Out) order characteristic of breadth-first search.
 * This approach explores all neighbors at the current depth before moving to
 * the next depth level, guaranteeing the shortest path in unweighted graphs.
 * 
 * To compile: javac MazeBreadthFirstSearch.java
 */

import java.util.LinkedList;
import java.util.Queue;

/**
 * Breadth-first search implementation for maze pathfinding.
 * Uses a queue to explore cells level by level, ensuring shortest path.
 */
public class MazeBreadthFirstSearch extends AbstractMazeSearch{

    // Queue to maintain cells to be explored (FIFO order)
    private Queue<Cell> queue;
    
    /**
     * Constructor creates a breadth-first search instance for the given maze.
     * Initializes the queue data structure for level-order cell exploration.
     * 
     * @param maze the maze to be searched
     */
    public MazeBreadthFirstSearch(Maze maze){
        super(maze);
        this.queue = new LinkedList<>();
    }

    /**
     * Finds the next cell to explore using breadth-first strategy.
     * Removes and returns the first cell from the queue.
     * 
     * @return the next cell to examine
     */
    @Override
    public Cell findNextCell() {
        return this.queue.poll();
    }

    /**
     * Adds a cell to the exploration queue.
     * New cells are added to the end of the queue for FIFO processing.
     * 
     * @param next the cell to add for future exploration
     */
    @Override
    public void addCell(Cell next) {
        this.queue.add(next);
    }

    /**
     * Returns the number of cells remaining to be explored.
     * 
     * @return the size of the exploration queue
     */
    @Override
    public int numRemainingCells() {
        return this.queue.size();
    }
}