package com.github.jlavigueure;
import com.github.jlavigueure.BoardCell;
import java.util.ArrayList;

/**
 * Board class representing the game board.
 * The board is a 2D array of BoardCell objects.
 */
public class Board { 
    /**
     * Enum representing the four possible directions for ship placement.
     */
    public static enum Direction {
        UP, 
        RIGHT,
        DOWN,
        LEFT;
    }

    private final BoardCell[][] board;
    private final ArrayList<Ship> ships;

    /**
     * Constructor for the Board class. Initializes the board with the specified width and height. All cells are initially empty.
     * @param width of board.
     * @param height of board.
     * @param boardState is the inital starting state of the board.
     */
    public Board(int width, int height, BoardCell.CellState boardState) {
        this.board = new BoardCell[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                board[i][j] = new BoardCell(boardState);
            }
        }
        ships = new ArrayList<>();
    }

    /**
     * Function to get the cell at the specified coordinates.
     * @param x The x-coordinate of the cell.   
     * @param y The y-coordinate of the cell.
     * @return The BoardCell object at the specified coordinates.
     * @throws IndexOutOfBoundsException if the coordinates are out of bounds.
     */
    public BoardCell getCell(int x, int y) {
        if (x < 0 || x >= board.length || y < 0 || y >= board[0].length) {
            throw new IndexOutOfBoundsException("Invalid cell coordinates: (" + x + ", " + y + ")");
        }
        return board[x][y];
    }

    /**
     * Function to place a ship on the board at the specified coordinates and direction. 
     * If the ship placement is invalid, an exception is thrown and no changes are made to the board.
     * @param x The x-coordinate of the starting cell.
     * @param y The y-coordinate of the starting cell.
     * @param direction The direction in which to place the ship (UP, RIGHT, DOWN, LEFT).
     * @param ship The Ship object to place on the board.
     * @throws IndexOutOfBoundsException if the ship placement goes out of bounds.
     * @throws IllegalArgumentException if invalid cells or a cell is not empty or ship is null.
     */
    public void placeShip(int x, int y, Direction direction, Ship ship) {
        int size = ship.getSize();
        BoardCell[] cellsToOccupy = new BoardCell[size];
        for (int i = 0; i < size; i++) {
            int newX = x;
            int newY = y;
            switch (direction) {
                case UP:
                    newY -= i;
                    break;
                case RIGHT:
                    newX += i;
                    break;
                case DOWN:
                    newY += i;
                    break;
                case LEFT:
                    newX -= i;
                    break;
            }
            BoardCell currentCell = this.getCell(newX, newY);
            if (currentCell.getState() != BoardCell.CellState.EMPTY) {
                throw new IllegalArgumentException("Cell (" + newX + ", " + newY + ") is not empty.");
            }
            cellsToOccupy[i] = currentCell;
        }
        for (BoardCell cell : cellsToOccupy) {
            cell.setOccupied(ship);
        }
        ships.add(ship);
    }

    /**
     * Function to attack the given cell. 
     * @param x The x-coordinate of the cell.   
     * @param y The y-coordinate of the cell.
     * @return the state of the cell after attacking.
     * @throws IndexOutOfBoundsException if the coordinates are out of bounds.
     * @throws IllegalStateException if cell already attacked or cell state is unknown.
     */
    public BoardCell.CellState hit(int x, int y) {
        return getCell(x, y).hit();
    }

    /**
     * Function to reveal the given cell as a new state.
     * @param x The x-coordinate of the cell.   
     * @param y The y-coordinate of the cell.
     * @param newState of the cell. Must be HIT or MISS.
     * @throws IllegalStateException if cell is not UNKNOWN.
     * @throws IllegalArgumentException if newState is not HIT or MISS. 
     */
    public void reveal(int x, int y, BoardCell.CellState newState) {
        getCell(x, y).reveal(newState);
    }

    /**
     * Function which returns true if all ships are sunk.
     * @return true if all ships sunk else false.
     */
    public boolean allShipsSunk() {
        for (Ship ship : ships){
            if (!ship.isSunk()) return false;
        }
        return true;
    }

    /**
     * Function to print the current state of the board.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < board[0].length; y++) {
            for (int x = 0; x < board.length; x++) {
                sb.append('[' + board[x][y].toString() + ']');
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
