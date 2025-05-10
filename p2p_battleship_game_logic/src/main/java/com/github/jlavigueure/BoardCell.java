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
        MISS, 
        UNKNOWN; // Unknown is used to represent opponent's board in fog-of-war. Can only be revealed as HIT or MISS. 
    }

    private Ship occupyingShip;
    private CellState state;

    /**
     * Constructor for the BoardCell class.
     * @param state the initial state of the cell.
     */
    public BoardCell(CellState state) {
        this.occupyingShip = null;
        this.state = state;
    }

    /**
     * Constructor for Boardcell initialized to EMPTY. 
     */
    public BoardCell() {
        this(CellState.EMPTY);
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
     * @throws IllegalArgumentException if ship is null.
     */
    public void setOccupied(Ship ship) {
        if (getState() != CellState.EMPTY) {
            throw new IllegalStateException("Cell is not empty and cannot be occupied.");
        } else if (ship == null) {
            throw new IllegalArgumentException("Ship cannot be null.");
        }
        occupyingShip = ship;
        state = CellState.OCCUPIED;
    }

    /**
     * Function to attack the cell.
     * @return new cell state.
     * @throws IllegalStateException if cell already attacked or cell state is unknown.
     */
    public CellState hit() {
        if (state == CellState.HIT || state == CellState.MISS){
            throw new IllegalStateException("Cell already attacked.");
        } else if (state == CellState.UNKNOWN){
            throw new IllegalStateException("Can not hit unknown cell. Must use reveal methods.");
        }else if (state == CellState.OCCUPIED){
            state = CellState.HIT;
            occupyingShip.hit();
        } else {
            state = CellState.MISS;
        } 
        return getState();
    }

    /**
     * Function for revealing opponent board cell to either HIT or MISS.
     * @param newState is the new state to set board to. Must be HIT or MISS.
     * @throws IllegalStateException if cell state is not UNKNOWN.
     * @throws IllegalArgumentException if newState is not HIT or MISS.
     */
    public void reveal(CellState newState) {
        if (state != CellState.UNKNOWN){
            throw new IllegalStateException("Can not reveal cell with known state.");
        } else if (newState != CellState.HIT && newState != CellState.MISS){
            throw new IllegalArgumentException("Can only reveal opponent board as HIT or MISS.");
        }
        this.state = newState;
    } 
}
