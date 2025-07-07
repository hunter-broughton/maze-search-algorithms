/*
 * Hunter Broughton
 * CS231 A
 * April 2023
 * 
 * MazeAStarSearch.java
 * 
 * This class implements the A* (A-star) search algorithm for maze pathfinding.
 * It extends AbstractMazeSearch and uses a priority queue to select cells based
 * on the A* heuristic: f(n) = g(n) + h(n), where g(n) is the actual distance
 * from start to the current cell, and h(n) is the Manhattan distance heuristic
 * from the current cell to the target. This combines the benefits of uniform-cost
 * search with the efficiency of greedy best-first search.
 * 
 * To compile: javac MazeAStarSearch.java
 */

import java.util.Comparator;

/**
 * A* search implementation for maze pathfinding.
 * Uses a priority queue with heuristic-based ordering for optimal pathfinding.
 */
public class MazeAStarSearch extends AbstractMazeSearch{

    // Priority queue to store cells ordered by A* heuristic value
    private PriorityQueue<Cell> priorityQueue;

    /**
     * Constructor creates an A* search instance for the given maze.
     * Initializes the priority queue with a custom comparator that implements
     * the A* heuristic function: f(n) = g(n) + h(n)
     * - g(n): actual path length from start to current cell
     * - h(n): Manhattan distance from current cell to target
     * 
     * @param maze the maze to be searched
     */
    public MazeAStarSearch(Maze maze){
        super(maze);
        
        priorityQueue = new Heap(new Comparator<Cell>(){
            
            /**
             * Comparator implementing the A* heuristic function.
             * Compares two cells based on their total estimated cost to reach the target.
             * 
             * @param cell1 the first cell to compare
             * @param cell2 the second cell to compare
             * @return negative if cell1 has lower cost, positive if cell2 has lower cost, 0 if equal
             */
            public int compare(Cell cell1, Cell cell2){
                // g(n): Calculate actual path length from start (past steps taken)
                int pastSteps1 = traceback(cell1).size();
                int pastSteps2 = traceback(cell2).size();
        
                // h(n): Calculate Manhattan distance heuristic to target
                int stepsToTarget1 = (Math.abs((getTarget().getRow()) - cell1.getRow())) + 
                                   Math.abs(((getTarget().getCol()) - cell1.getCol()));
                int stepsToTarget2 = (Math.abs((getTarget().getRow()) - cell2.getRow())) + 
                                   Math.abs(((getTarget().getCol()) - cell2.getCol()));
                
                // f(n) = g(n) + h(n): Total estimated cost
                double sum1 = pastSteps1 + stepsToTarget1;
                double sum2 = pastSteps2 + stepsToTarget2;
        
                return Double.compare(sum1, sum2);
            }
        });
    }

    /**
     * Finds the next cell to explore using A* strategy.
     * Returns the cell with the lowest f(n) value from the priority queue.
     * 
     * @return the next cell to examine (lowest estimated total cost)
     */
    @Override
    public Cell findNextCell() {
        Cell next = priorityQueue.poll();
        return next;
    }

    /**
     * Adds a cell to the priority queue for future exploration.
     * The cell will be positioned according to its A* heuristic value.
     * 
     * @param next the cell to add for future exploration
     */
    @Override
    public void addCell(Cell next) {
        priorityQueue.offer(next);
    }

    /**
     * Returns the number of cells remaining to be explored.
     * 
     * @return the size of the priority queue
     */
    @Override
    public int numRemainingCells() {
        return priorityQueue.size();
    }
}
