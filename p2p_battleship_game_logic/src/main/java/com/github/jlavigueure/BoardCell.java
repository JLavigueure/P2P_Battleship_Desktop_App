package com.github.jlavigueure;

import com.github.jlavigueure.Ship;

/**
 * BoardCell class representing an individual cell on the board. 
 */
public class BoardCell {
    /**
     * Enum representing the possible states of each BoardCell. 
     */
    public static enum CellState {
        EMPTY,
        OCCUPIED,
        HIT,
        MISS;
    }

    private CellState state;
    private Ship occupyingShip;

    /**
     * Constructor for the BoardCell class.
     * @param ship the occupying ship or null if empty
     */
    public BoardCell(Ship ship) {
        this.occupyingShip = ship;
        state = (ship != null) ? CellState.OCCUPIED : CellState.EMPTY;
    }

    /**
     * Constructor for Boardcell initialized to EMPTY. 
     */
    public BoardCell() {
        this(null);
    }

    /**
     * Function to get the current state of the cell.
     * @return The cell state.
     */
    public CellState getState() {
        return state;
    }

    /**
     * Mark the cell as occupied.
     * @throws IllegalStateException if cell is anything except empty.
     */
    public void setOccupied(){
        if (state != CellState.EMPTY) {
            throw new IllegalStateException("Cell is not empty and cannot be occupied.");
        }
        state = CellState.OCCUPIED;
    }

    /**
     * Function to attack the cell. Does not prevent multiple hits to cell.
     * @return new cell state.
     */
    public CellState hit(){
        if(state == CellState.EMPTY){
            state = CellState.MISS;
        } else if(state == CellState.OCCUPIED){
            state = CellState.HIT;
        }
        return state;
    }
}
