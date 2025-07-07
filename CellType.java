/*
 * Hunter Broughton
 * CS231 A
 * April 2023
 * 
 * CellType.java
 * 
 * Enumeration defining the different types of cells that can exist in a maze:
 * - FREE: Normal passable cells
 * - OBSTACLE: Impassable barrier cells
 * - ICE: Special terrain type (potentially with different movement costs)
 * - MUD: Special terrain type (potentially with different movement costs)
 */

public enum CellType {
    FREE, OBSTACLE, ICE, MUD;
}