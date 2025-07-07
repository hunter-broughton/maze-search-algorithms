/*
 * Hunter Broughton
 * CS231 A
 * April 2023
 * 
 * MazeDepthFirstSearch.java
 * 
 * This class implements a depth-first search algorithm for maze pathfinding.
 * It extends AbstractMazeSearch and uses a stack data structure to maintain
 * the LIFO (Last In, First Out) order characteristic of depth-first search.
 * This approach explores as far as possible along each branch before backtracking.
 * 
 * To compile: javac MazeDepthFirstSearch.java
 */

import java.util.LinkedList;
import java.util.Stack;

/**
 * Depth-first search implementation for maze pathfinding.
 * Uses a stack to explore paths deeply before backtracking.
 */
public class MazeDepthFirstSearch extends AbstractMazeSearch{

    // Stack to maintain cells to be explored (LIFO order)
    private Stack<Cell> stack;

    /**
     * Constructor creates a depth-first search instance for the given maze.
     * Initializes the stack data structure for cell exploration.
     * 
     * @param maze the maze to be searched
     */
    public MazeDepthFirstSearch(Maze maze){
        super(maze);
        this.stack = new Stack<Cell>();
    }

    /**
     * Finds the next cell to explore using depth-first strategy.
     * Pops the most recently added cell from the stack.
     * 
     * @return the next cell to examine
     */
    @Override
    public Cell findNextCell() {
        Cell nextCell = stack.pop();
        return nextCell;
    }

    /**
     * Adds a cell to the exploration stack.
     * New cells are added to the top of the stack for LIFO processing.
     * 
     * @param next the cell to add for future exploration
     */
    @Override
    public void addCell(Cell next) {
       stack.add(next);
    }

    /**
     * Returns the number of cells remaining to be explored.
     * 
     * @return the size of the exploration stack
     */
    @Override
    public int numRemainingCells() {
       return stack.size();
    }
}
