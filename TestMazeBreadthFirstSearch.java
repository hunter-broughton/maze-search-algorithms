/*
 * Hunter Broughton
 * CS231 A
 * April 2023
 * 
 * TestMazeBreadthFirstSearch.java
 * 
 * Unit test class for the MazeBreadthFirstSearch implementation and its
 * inherited methods from AbstractMazeSearch. Tests queue-based operations
 * to ensure FIFO ordering and proper breadth-first search behavior.
 * 
 * To compile and run:
 * javac TestMazeBreadthFirstSearch.java
 * java -ea TestMazeBreadthFirstSearch
 */

public class TestMazeBreadthFirstSearch {

    /**
     * Main method that runs all test cases for MazeBreadthFirstSearch.
     * Uses assertions to verify correct FIFO behavior and method functionality.
     * 
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Create a simple 5x5 maze with no obstacles for testing
        Maze maze = new Maze(5, 5, 0, 0, 0);
        Cell start = maze.get(0, 0);
        Cell target = maze.get(4, 4);
        MazeBreadthFirstSearch bfs = new MazeBreadthFirstSearch(maze);

        // Test cell getter and setter methods inherited from AbstractMazeSearch
        bfs.setStart(start);
        assert bfs.getStart() == start : "Error: Start cell not set correctly.";
        System.out.println("✓ Start cell set and retrieved successfully.");

        bfs.setTarget(target);
        assert bfs.getTarget() == target : "Error: Target cell not set correctly.";
        System.out.println("✓ Target cell set and retrieved successfully.");

        bfs.setCur(start);
        assert bfs.getCur() == start : "Error: Current cell not set correctly.";
        System.out.println("Current cell set and retrieved successfully.");

        // Test addCell and findNextCell methods
        bfs.addCell(start);
        assert bfs.numRemainingCells() == 1 : "Error: addCell not working correctly.";
        System.out.println("addCell method works correctly.");

        Cell nextCell = bfs.findNextCell();
        assert nextCell == start : "Error: findNextCell not working correctly.";
        System.out.println("findNextCell method works correctly.");

        // Test numRemainingCells method
        assert bfs.numRemainingCells() == 0 : "Error: numRemainingCells not working correctly.";
        System.out.println("numRemainingCells method works correctly.");
    }
}

