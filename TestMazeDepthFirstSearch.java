/*
 * Hunter Broughton
 * CS231 A
 * April 2023
 * 
 * TestMazeDepthFirstSearch.java
 * 
 * Unit test class for the MazeDepthFirstSearch implementation and its
 * inherited methods from AbstractMazeSearch. Tests basic functionality
 * including cell management, search data structure operations, and
 * getter/setter methods to ensure proper implementation.
 * 
 * To compile and run:
 * javac TestMazeDepthFirstSearch.java
 * java -ea TestMazeDepthFirstSearch
 */

public class TestMazeDepthFirstSearch {

    /**
     * Main method that runs all test cases for MazeDepthFirstSearch.
     * Uses assertions to verify correct behavior of all methods.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Create a simple 5x5 maze with no obstacles for testing
        Maze maze = new Maze(5, 5, 0, 0, 0);
        Cell start = maze.get(0, 0);
        Cell target = maze.get(4, 4);
        MazeDepthFirstSearch dfs = new MazeDepthFirstSearch(maze);

        // Test cell getter and setter methods
        dfs.setStart(start);
        assert dfs.getStart() == start : "Error: Start cell not set correctly.";
        System.out.println("✓ Start cell set and retrieved successfully.");

        dfs.setTarget(target);
        assert dfs.getTarget() == target : "Error: Target cell not set correctly.";
        System.out.println("✓ Target cell set and retrieved successfully.");

        dfs.setCur(start);
        assert dfs.getCur() == start : "Error: Current cell not set correctly.";
        System.out.println("✓ Current cell set and retrieved successfully.");

        // Test stack-based search operations (depth-first specific)
        dfs.addCell(start);
        assert dfs.numRemainingCells() == 1 : "Error: addCell not working correctly.";
        System.out.println("✓ addCell method works correctly.");

        Cell nextCell = dfs.findNextCell();
        assert nextCell == start : "Error: findNextCell not working correctly.";
        System.out.println("✓ findNextCell method works correctly (LIFO order).");

        // Verify stack is empty after polling
        assert dfs.numRemainingCells() == 0 : "Error: numRemainingCells not working correctly.";
        System.out.println("✓ numRemainingCells method works correctly.");
        
        System.out.println("\nAll tests passed! MazeDepthFirstSearch is working correctly.");
    }
}

