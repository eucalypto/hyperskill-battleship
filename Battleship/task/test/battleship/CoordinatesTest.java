package battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinatesTest {

    @Test
    void of_A1() {
        var coordinates = Coordinates.of("A1");

        assertEquals(0, coordinates.getHorizontalIndex());
        assertEquals(0, coordinates.getVerticalIndex());
    }

    @Test
    void of_B2() {
        var coordinates = Coordinates.of("B2");

        assertEquals(1, coordinates.getHorizontalIndex());
        assertEquals(1, coordinates.getVerticalIndex());
    }

    @Test
    void of_I10() {
        var coordinates = Coordinates.of("I10");

        assertEquals(9, coordinates.getHorizontalIndex());
        assertEquals(8, coordinates.getVerticalIndex());
    }
}
