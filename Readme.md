# Maze Search Algorithm Visualization

A comprehensive Java implementation of popular pathfinding algorithms with real-time visualization. This project demonstrates **Depth-First Search (DFS)**, **Breadth-First Search (BFS)**, and **A\* Search** algorithms navigating through randomly generated mazes with special terrain types.

**Created by Hunter Broughton - April 2023**

## Demo

![Maze Search Visualization](Screenshot%202025-07-06%20at%208.31.56%20PM.png)
_Real-time visualization showing the search algorithm exploring a maze with different terrain types and finding the optimal path._

## Features

- **Three Search Algorithms**: Compare DFS, BFS, and A\* pathfinding strategies
- **Real-time Visualization**: Watch algorithms explore mazes step-by-step
- **Special Terrain Types**:
  - **ICE**: Faster straight movement, slower turns
  - **MUD**: Slower movement in all directions
  - **OBSTACLES**: Impassable barriers
  - **FREE**: Normal passable cells
- **Configurable Mazes**: Customize size and terrain density
- **Performance Metrics**: Track cells explored during search
- **Unit Testing**: Comprehensive test suite included

## Quick Start

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Terminal/Command Prompt access

### Compilation

```bash
javac *.java
```

### Running the Main Simulation

```bash
java -ea Exploration
```

This will:

1. Generate a random 20×20 maze
2. Select random start and end points
3. Visualize the search algorithm in action
4. Display the number of cells explored

## Customization

### Changing Search Algorithms

Edit line 47 in `Exploration.java`:

```java
// For Depth-First Search (default)
AbstractMazeSearch mySearch = new MazeDepthFirstSearch(myMaze);

// For Breadth-First Search
AbstractMazeSearch mySearch = new MazeBreadthFirstSearch(myMaze);

// For A* Search
AbstractMazeSearch mySearch = new MazeAStarSearch(myMaze);
```

### Maze Configuration

Modify the maze parameters in `Exploration.java`:

```java
// Maze(rows, columns, obstacleeDensity, iceDensity, mudDensity)
Maze myMaze = new Maze(20, 20, 0.4, 0.1, 0.1);
```

### Visualization Speed

Adjust the delay parameter in the search call:

```java
// Slower visualization (500ms delay)
mySearch.search(start, end, true, 500);

// Faster visualization (10ms delay)
mySearch.search(start, end, true, 10);

// No visualization (fastest)
mySearch.search(start, end, false, 0);
```

## Testing

Run individual algorithm tests:

```bash
# Test Depth-First Search
java -ea TestMazeDepthFirstSearch

# Test Breadth-First Search
java -ea TestMazeBreadthFirstSearch

# Test A* Search
java -ea TestMazeAStarSearch

# Test Heap data structure
java -ea HeapTest
```

## Project Structure

```
Project7 cs231/
├── Exploration.java           # Main program runner
├── AbstractMazeSearch.java    # Base class for search algorithms
├── MazeDepthFirstSearch.java  # DFS implementation (Stack-based)
├── MazeBreadthFirstSearch.java # BFS implementation (Queue-based)
├── MazeAStarSearch.java       # A* implementation (Priority Queue)
├── Maze.java                  # 2D maze grid with terrain generation
├── Cell.java                  # Individual maze cell representation
├── CellType.java              # Enumeration for cell types
├── MazeSearchDisplay.java     # GUI visualization component
├── Heap.java                  # Binary heap for priority queue
├── PriorityQueue.java         # Priority queue interface
├── Test*.java                 # Unit test files
└── README.md                  # This file
```

## How It Works

### Algorithm Comparison

| Algorithm | Data Structure | Guarantees Shortest Path        | Exploration Pattern              |
| --------- | -------------- | ------------------------------- | -------------------------------- |
| **DFS**   | Stack (LIFO)   | No                              | Deep exploration, then backtrack |
| **BFS**   | Queue (FIFO)   | Yes (unweighted)                | Level-by-level expansion         |
| **A\***   | Priority Queue | Yes (with admissible heuristic) | Guided by distance + heuristic   |

### Visualization Color Scheme

- **Blue**: Start position
- **Red**: Target position
- **Magenta**: Current position being explored
- **Yellow**: Visited cells
- **Green**: Optimal path (when found)
- **Gray**: Unvisited free cells
- **Cyan**: Ice terrain
- **Pink**: Mud terrain
- **Black**: Obstacles

### Special Terrain Effects

- **ICE Cells**:
  - Straight movement: 4x faster
  - Turning: 6x slower
- **MUD Cells**:
  - All movement: 5-6x slower

## Performance Analysis

To run performance comparisons across multiple iterations, uncomment the loop in `Exploration.java`:

```java
for(int i = 0; i < 100; i++){
    // ... existing code ...
}
```

This will run 100 iterations and help you analyze:

- Average cells explored per algorithm
- Performance consistency
- Algorithm efficiency on different maze configurations

## Understanding the Output

When running `Exploration.java`, you'll see:

```
Cells reached during search: 245
```

This number represents how many cells the algorithm visited before finding the target. Lower numbers generally indicate more efficient pathfinding.

## Development Notes

- **Thread Safety**: GUI updates are handled on the Event Dispatch Thread
- **Memory Efficiency**: Node-based heap implementation for dynamic sizing
- **Extensibility**: Easy to add new search algorithms by extending `AbstractMazeSearch`
- **Testing**: All core components include comprehensive unit tests

## Educational Value

This project demonstrates:

- **Algorithm Design**: Different approaches to pathfinding problems
- **Data Structures**: Practical use of stacks, queues, and heaps
- **GUI Programming**: Swing-based visualization
- **Object-Oriented Design**: Inheritance, interfaces, and polymorphism
- **Performance Analysis**: Comparing algorithmic efficiency

## Contributing

Feel free to extend this project by:

- Adding new search algorithms (Dijkstra's, Greedy Best-First, etc.)
- Implementing additional terrain types
- Creating maze import/export functionality
- Adding performance benchmarking tools

---

_This project was developed for CS231 A coursework, demonstrating fundamental computer science concepts in pathfinding and algorithm visualization._
