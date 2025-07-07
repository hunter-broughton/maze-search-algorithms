/*
 * Hunter Broughton
 * CS231 A
 * April 2023
 * 
 * Exploration.java
 * 
 * This class implements the exploration portion of the maze search project.
 * It creates random mazes and tests different search algorithms (Depth First,
 * Breadth First, and A*) to find paths between randomly selected start and
 * end points in the maze.
 * 
 * To compile and run:
 * javac Exploration.java
 * java -ea Exploration
 */

import java.util.Random;

public class Exploration {
    
    /**
     * Main method that creates a random maze and performs pathfinding using
     * the specified search algorithm. Measures and reports the number of
     * cells reached during the search process.
     * 
     * @param args command line arguments (not used)
     * @throws InterruptedException if the thread is interrupted during execution
     */
    public static void main(String[] args) throws InterruptedException {
        // TODO: Uncomment the loop below to run multiple iterations for performance testing
        // for(int i = 0; i < 100; i++){
        
        // Record start time for performance measurement
        long startTime = System.nanoTime();
        
        // Initialize random number generator for selecting maze positions
        Random rand = new Random();
        
        // Create a 20x20 maze with 40% obstacle density and 40% free space
        Maze myMaze = new Maze(20, 20, 0, .4, .4);

        // IMPORTANT: Change the line below to use different search algorithms:
        // - MazeDepthFirstSearch: Uses depth-first search
        // - MazeBreadthFirstSearch: Uses breadth-first search  
        // - MazeAStarSearch: Uses A* heuristic search
        AbstractMazeSearch mySearch = new MazeDepthFirstSearch(myMaze);
        
        // Generate random starting position that must be a free cell
        int row1 = rand.nextInt(1, 20);
        int col1 = rand.nextInt(1, 20);
        while(!(myMaze.get(row1, col1).getType().equals(CellType.FREE))){
            // Keep generating new coordinates until we find a free cell
            row1 = rand.nextInt(1, 20);
            col1 = rand.nextInt(1, 20);
        }

        // Generate random ending position that must be free and different from start
        int row2 = rand.nextInt(1, 20);
        int col2 = rand.nextInt(1, 20);
        while(!(myMaze.get(row2, col2).getType().equals(CellType.FREE)) || (row2 == row1 && col2 == col1)){
            // Keep generating until we find a free cell that's not the same as start
            row2 = rand.nextInt(1, 20);
            col2 = rand.nextInt(1, 20);
        }
        
        // Perform the search from start to end position with visualization delay of 70ms
        mySearch.search(myMaze.get(row1, col1), myMaze.get(row2, col2), true, 70);
        
        // Output the number of cells explored during the search
        System.out.println("Cells reached during search: " + mySearch.getReachCount());
    }
}
    

