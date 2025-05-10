package com.github.jlavigueure;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ShipTest {

    @Test
    void testShipInitialization() {
        Ship ship = new Ship(Ship.ShipType.CARRIER);
        assertEquals("CARRIER", ship.getName());
        assertEquals(5, ship.getSize());
        assertEquals(0, ship.getHits());
    }

    @Test
    void testHitIncrementsHits() {
        Ship ship = new Ship(Ship.ShipType.DESTROYER);
        assertTrue(ship.hit());
        assertEquals(1, ship.getHits());
    }

    @Test
    void testHitDoesNotExceedSize() {
        Ship ship = new Ship(Ship.ShipType.SUBMARINE);
        ship.hit();
        ship.hit();
        ship.hit();
        assertFalse(ship.hit());
        assertEquals(3, ship.getHits());
    }

    @Test
    void testIsSunkWhenHitsEqualSize() {
        Ship ship = new Ship(Ship.ShipType.BATTLESHIP);
        ship.hit();
        ship.hit();
        ship.hit();
        ship.hit();
        assertTrue(ship.isSunk());
    }

    @Test
    void testIsNotSunkWhenHitsLessThanSize() {
        Ship ship = new Ship(Ship.ShipType.CRUISER);
        ship.hit();
        ship.hit();
        assertFalse(ship.isSunk());
    }

    @Test
    void testHitReturnsFalseWhenSunk() {
        Ship ship = new Ship(Ship.ShipType.DESTROYER);
        ship.hit();
        ship.hit();
        assertFalse(ship.hit());
    }
}