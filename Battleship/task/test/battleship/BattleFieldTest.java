package battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BattleFieldTest {

    BattleField battleField;

    @BeforeEach
    public void setup() {
        battleField = new BattleField();
    }


    @Test
    void printEmptyTest() {
        var fieldRepresentation = battleField.getRepresentationString();

        var emptyFieldRepresentation = "  1 2 3 4 5 6 7 8 9 10\n" +
                "A ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "B ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "C ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "D ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "E ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "F ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "J ~ ~ ~ ~ ~ ~ ~ ~ ~ ~";
        assertEquals(emptyFieldRepresentation, fieldRepresentation);
    }

    @Test
    void print_field_with_aircraft_carrier() {
        battleField.battleFieldModel.setAircraftCarrier(
                new Coordinates(0, 0),
                new Coordinates(0, 4));
        var fieldRepresentation = battleField.getRepresentationString();

        var emptyFieldRepresentation = "  1 2 3 4 5 6 7 8 9 10\n" +
                "A O ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "B O ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "C O ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "D O ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "E O ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "F ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "J ~ ~ ~ ~ ~ ~ ~ ~ ~ ~";
        assertEquals(emptyFieldRepresentation, fieldRepresentation);
    }


}
