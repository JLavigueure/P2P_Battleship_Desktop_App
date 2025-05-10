package com.github.jlavigueure;

/**
 * Ship class representing a ship in the game.
 * Each ship has a name, size, and number of hits taken.
 */
public class Ship {
    /**
     * Enum representing the types of ships in the game.
     * Each ship type has a size associated with it.
     */
    public static enum ShipType {
        CARRIER(5),
        BATTLESHIP(4),
        CRUISER(3),
        SUBMARINE(3),
        DESTROYER(2);

        private final int size;
        ShipType(int size) {
            this.size = size;
        }
        public int getSize() {
            return size;
        }
    }

    private String name;
    private final int size;
    private int hits;

    /**
     * Constructor for the Ship class.
     * @param type The type of ship (e.g., CARRIER, BATTLESHIP, etc.)
     */
    public Ship(ShipType type) {
        this.name = type.name();
        this.size = type.getSize();
        this.hits = 0;
    }

    /**
     * Function to get the name of the ship.
     * @return The name of the ship.
     */
    public String getName() {
        return name;
    }

    /**
     * function to get the size of the ship.
     * @return The size of the ship.
     */
    public int getSize() {
        return size;
    }

    /**
     * Function to get the number of hits taken by the ship.
     * @return The number of hits taken by the ship.
     */
    public int getHits() {
        return hits;
    }

    /**
     * Function to add a hit to the ship.
     * @return true if the hit was counted, false if the ship is already sunk.
     */
    public boolean hit() {
        if (!this.isSunk()) {
            hits++;
            return true;
        }
        return false;
    }

    /**
     * Function to check if the ship is sunk.
     * @return true if the ship is sunk, false otherwise.
     */
    public boolean isSunk() {
        return hits >= size;
    }
}