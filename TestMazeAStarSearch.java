/*
 * Hutner Broughton 
 * CS231A 
 * 4/20/2023
 * TestMazeAStarSearch.java
 * 
 * This file tests the MazeAStarSearch class and the methods in its abstract superclass, AbstractMazeSearch.
 * 
 * How to compile and run:
 * javac TestMazeAStarSearch.java
 * java -ea TestMazeAStarSearch
 */

 public class TestMazeAStarSearch {

    public static void main(String[] args) {
        // Create a simple maze for testing
        Maze maze = new Maze(5, 5, 0, 0, 0);
        Cell start = maze.get(0, 0);
        Cell target = maze.get(4, 4);
        MazeAStarSearch astar = new MazeAStarSearch(maze);

        // Test getters and setters
        astar.setStart(start);
        assert astar.getStart() == start : "Error: Start cell not set correctly.";
        System.out.println("Start cell set and retrieved successfully.");

        astar.setTarget(target);
        assert astar.getTarget() == target : "Error: Target cell not set correctly.";
        System.out.println("Target cell set and retrieved successfully.");

        astar.setCur(start);
        assert astar.getCur() == start : "Error: Current cell not set correctly.";
        System.out.println("Current cell set and retrieved successfully.");

        // Test addCell and findNextCell methods
        astar.addCell(start);
        assert astar.numRemainingCells() == 1 : "Error: addCell not working correctly.";
        System.out.println("addCell method works correctly.");

        Cell nextCell = astar.findNextCell();
        assert nextCell == start : "Error: findNextCell not working correctly.";
        System.out.println("findNextCell method works correctly.");

        // Test numRemainingCells method
        assert astar.numRemainingCells() == 0 : "Error: numRemainingCells not working correctly.";
        System.out.println("numRemainingCells method works correctly.");
    }
}
