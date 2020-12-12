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
                "J ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n";
        assertEquals(emptyFieldRepresentation, fieldRepresentation);
    }

    @Test
    void print_field_with_vertical_aircraft_carrier_starting_at_00() {
        battleField.battleFieldModel.setAircraftCarrier(
                new Coordinates(0, 0),
                new Coordinates(4, 0));
        var fieldRepresentation = battleField.getRepresentationString();

        var expectedFieldRepresentation = "  1 2 3 4 5 6 7 8 9 10\n" +
                "A O ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "B O ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "C O ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "D O ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "E O ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "F ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "J ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n";
        assertEquals(expectedFieldRepresentation, fieldRepresentation);
    }

    @Test
    void print_field_with_horizontal_aircraft_carrier_ending_at_99() {
        battleField.battleFieldModel.setAircraftCarrier(
                new Coordinates(9, 5),
                new Coordinates(9, 9));
        var fieldRepresentation = battleField.getRepresentationString();

        var expectedFieldRepresentation = "  1 2 3 4 5 6 7 8 9 10\n" +
                "A ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "B ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "C ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "D ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "E ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "F ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "J ~ ~ ~ ~ ~ O O O O O\n";
        assertEquals(expectedFieldRepresentation, fieldRepresentation);
    }

    @Test
    void print_field_with_horizontal_battleship_ending_at_99() {
        battleField.battleFieldModel.setBattleship(
                new Coordinates(9, 6),
                new Coordinates(9, 9));
        var fieldRepresentation = battleField.getRepresentationString();

        var expectedFieldRepresentation = "  1 2 3 4 5 6 7 8 9 10\n" +
                "A ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "B ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "C ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "D ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "E ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "F ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "J ~ ~ ~ ~ ~ ~ O O O O\n";
        assertEquals(expectedFieldRepresentation, fieldRepresentation);
    }

    @Test
    void print_field_with_vertical_submarine_ending_at_99() {
        battleField.battleFieldModel.setSubmarine(
                new Coordinates(7, 9),
                new Coordinates(9, 9));
        var fieldRepresentation = battleField.getRepresentationString();

        var expectedFieldRepresentation = "  1 2 3 4 5 6 7 8 9 10\n" +
                "A ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "B ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "C ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "D ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "E ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "F ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "H ~ ~ ~ ~ ~ ~ ~ ~ ~ O\n" +
                "I ~ ~ ~ ~ ~ ~ ~ ~ ~ O\n" +
                "J ~ ~ ~ ~ ~ ~ ~ ~ ~ O\n";
        assertEquals(expectedFieldRepresentation, fieldRepresentation);
    }

    @Test
    void print_field_with_two_vessels() {
        battleField.battleFieldModel.setBattleship(
                new Coordinates(9, 6),
                new Coordinates(9, 9));

        battleField.battleFieldModel.setAircraftCarrier(
                new Coordinates(0, 0),
                new Coordinates(4, 0));

        var fieldRepresentation = battleField.getRepresentationString();

        var expectedFieldRepresentation = "  1 2 3 4 5 6 7 8 9 10\n" +
                "A O ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "B O ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "C O ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "D O ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "E O ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "F ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~\n" +
                "J ~ ~ ~ ~ ~ ~ O O O O\n";
        assertEquals(expectedFieldRepresentation, fieldRepresentation);
    }


}
