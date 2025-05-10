package com.github.jlavigueure;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    @Test
    public void testBoardInitialization() {
        int width = 5;
        int height = 5;
        Board board = new Board(width, height);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                assertEquals(BoardCell.CellState.EMPTY, board.getCell(i, j).getState());
            }
        }
    }

    @Test
    public void testGetCellValidCoordinates() {
        Board board = new Board(5, 5);
        assertNotNull(board.getCell(2, 2));
    }

    @Test
    public void testGetCellInvalidCoordinates() {
        Board board = new Board(5, 5);
        assertThrows(IndexOutOfBoundsException.class, () -> board.getCell(-1, 0));
        assertThrows(IndexOutOfBoundsException.class, () -> board.getCell(5, 5));
    }

    @Test
    public void testPlaceShipValid() {
        Board board = new Board(5, 5);
        Ship ship = new Ship(Ship.ShipType.CRUISER); // Using ShipType
        board.placeShip(2, 2, Board.Direction.RIGHT, ship);

        assertEquals(BoardCell.CellState.OCCUPIED, board.getCell(2, 2).getState());
        assertEquals(BoardCell.CellState.OCCUPIED, board.getCell(3, 2).getState());
        assertEquals(BoardCell.CellState.OCCUPIED, board.getCell(4, 2).getState());
    }

    @Test
    public void testPlaceShipOutOfBounds() {
        Board board = new Board(5, 5);
        Ship ship = new Ship(Ship.ShipType.CRUISER);

        assertThrows(IndexOutOfBoundsException.class, () -> board.placeShip(4, 4, Board.Direction.RIGHT, ship));
    }

    @Test
    public void testPlaceShipOverlap() {
        Board board = new Board(5, 5);
        Ship ship1 = new Ship(Ship.ShipType.CRUISER);
        Ship ship2 = new Ship(Ship.ShipType.DESTROYER);

        board.placeShip(2, 2, Board.Direction.RIGHT, ship1);
        assertThrows(IllegalArgumentException.class, () -> board.placeShip(3, 2, Board.Direction.DOWN, ship2));
    }

    @Test
    public void testHitEmptyCell() {
        Board board = new Board(5, 5);
        assertEquals(BoardCell.CellState.MISS, board.hit(2, 2));
    }

    @Test
    public void testHitOccupiedCell() {
        Board board = new Board(5, 5);
        Ship ship = new Ship(Ship.ShipType.DESTROYER);
        board.placeShip(2, 2, Board.Direction.RIGHT, ship);

        assertEquals(BoardCell.CellState.HIT, board.hit(2, 2));
    }

    @Test
    public void testHitOutOfBounds() {
        Board board = new Board(5, 5);
        assertThrows(IndexOutOfBoundsException.class, () -> board.hit(5, 5));
    }
}
