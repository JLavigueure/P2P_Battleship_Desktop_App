package com.github.jlavigueure;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BoardTest {

    private Board board;
    private Ship mockShip;

    @BeforeEach
    public void setup() {
        board = new Board(5, 5, BoardCell.CellState.EMPTY);
        mockShip = new Ship(Ship.ShipType.CRUISER);
    }

    @Test
    public void testBoardInitialization() {
        assertEquals(BoardCell.CellState.EMPTY, board.getCell(0, 0).getState());
    }

    @Test
    public void testPlaceShipSuccessfully() {
        board.placeShip(0, 0, Board.Direction.RIGHT, mockShip);
        assertEquals(BoardCell.CellState.OCCUPIED, board.getCell(0, 0).getState());
        assertEquals(BoardCell.CellState.OCCUPIED, board.getCell(1, 0).getState());
        assertEquals(BoardCell.CellState.OCCUPIED, board.getCell(2, 0).getState());
    }

    @Test
    public void testPlaceShipOutOfBoundsThrowsException() {
        assertThrows(IndexOutOfBoundsException.class, () -> {
            board.placeShip(4, 4, Board.Direction.RIGHT, mockShip);
        });
    }

    @Test
    public void testPlaceShipOverlappingThrowsException() {
        board.placeShip(0, 0, Board.Direction.RIGHT, mockShip);
        assertThrows(IllegalArgumentException.class, () -> {
            board.placeShip(1, 0, Board.Direction.RIGHT, mockShip);
        });
    }

    @Test
    public void testHitOccupiedCell() {
        board.placeShip(0, 0, Board.Direction.RIGHT, mockShip);
        BoardCell.CellState result = board.hit(0, 0);
        assertEquals(BoardCell.CellState.HIT, result);
    }

    @Test
    public void testHitEmptyCell() {
        BoardCell.CellState result = board.hit(4, 4);
        assertEquals(BoardCell.CellState.MISS, result);
    }

    @Test
    public void testHitAlreadyHitCellThrowsException() {
        board.hit(4, 4); // first hit, becomes MISS
        assertThrows(IllegalStateException.class, () -> board.hit(4, 4));
    }

    @Test
    public void testRevealCellSuccessfully() {
        Board fogBoard = new Board(5, 5, BoardCell.CellState.UNKNOWN);
        fogBoard.reveal(1, 1, BoardCell.CellState.MISS);
        assertEquals(BoardCell.CellState.MISS, fogBoard.getCell(1, 1).getState());
    }

    @Test
    public void testRevealNonUnknownCellThrowsException() {
        assertThrows(IllegalStateException.class, () -> {
            board.reveal(0, 0, BoardCell.CellState.HIT);
        });
    }

    @Test
    public void testRevealInvalidStateThrowsException() {
        Board fogBoard = new Board(5, 5, BoardCell.CellState.UNKNOWN);
        assertThrows(IllegalArgumentException.class, () -> {
            fogBoard.reveal(0, 0, BoardCell.CellState.OCCUPIED);
        });
    }

    @Test
    public void testAllShipsSunkReturnsTrueWhenSunk() {
        board.placeShip(0, 0, Board.Direction.RIGHT, mockShip);
        board.hit(0, 0);
        board.hit(1, 0);
        board.hit(2, 0);
        assertTrue(board.allShipsSunk());
    }

    @Test
    public void testAllShipsSunkReturnsFalseWhenNotSunk() {
        board.placeShip(0, 0, Board.Direction.RIGHT, mockShip);
        board.hit(0, 0);
        assertFalse(board.allShipsSunk());
    }

    @Test
    public void testSetOccupiedThrowsOnNonEmptyCell() {
        BoardCell cell = new BoardCell(BoardCell.CellState.HIT);
        assertThrows(IllegalStateException.class, () -> cell.setOccupied(mockShip));
    }

    @Test
    public void testSetOccupiedThrowsOnNullShip() {
        BoardCell cell = new BoardCell(BoardCell.CellState.EMPTY);
        assertThrows(IllegalArgumentException.class, () -> cell.setOccupied(null));
    }

    @Test
    public void testCannotPlaceSameShipTwice(){
        board.placeShip(1, 1, Board.Direction.RIGHT, mockShip);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            board.placeShip(0, 0, Board.Direction.DOWN, mockShip);
        });
    }

    @Test
    public void testBoardToStringWithHitsAndMisses() {
        // Place cruiser horizontally at (1, 1)
        board.placeShip(1, 1, Board.Direction.RIGHT, mockShip);

        // Hit part of the ship at (1,1)
        board.hit(1, 1);

        // Miss on (2,0)
        board.hit(2, 0);

        String expected =
                "[ ][ ][O][ ][ ]\n" +  // (2,0) is MISS
                "[ ][X][S][S][ ]\n" +  // (1,1) is HIT, rest of cruiser is OCCUPIED
                "[ ][ ][ ][ ][ ]\n" +
                "[ ][ ][ ][ ][ ]\n" +
                "[ ][ ][ ][ ][ ]\n";

        assertEquals(expected, board.toString(), "Board toString output did not match expected.");
    }
}