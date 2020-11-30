package battleship;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.text.Position;

import static org.junit.jupiter.api.Assertions.*;

class BattleFieldModelTest {

    BattleFieldModel battleFieldModel;

    @BeforeEach
    void setUp() {
        battleFieldModel = new BattleFieldModel();
    }

    @Test
    void airCraftCarrierNotHorizontalOrVertical_throwsWrongInput() {
        assertThrows(WrongInput.class, () -> {
            battleFieldModel.setAircraftCarrier(
                    new Coordinates(0, 0),
                    new Coordinates(4, 4));
        });
    }


    @Test
    void setAircraftCarrierTooLong_throwsException() {
        assertThrows(WrongInput.class, () -> {
            battleFieldModel.setAircraftCarrier(
                    new Coordinates(0, 0),
                    new Coordinates(0, 5));
        });
    }

    @Test
    void setAircraftCarrierTooShort_throwsWrongInput() {
        assertThrows(WrongInput.class, () -> {
            battleFieldModel.setAircraftCarrier(
                    new Coordinates(0, 0),
                    new Coordinates(3, 0));
        });
    }

    @Test
    void setAircraftCarrier() {
        var start = new Coordinates(0, 0);
        var end = new Coordinates(0, 4);

        battleFieldModel.setAircraftCarrier(start, end);

        assertEquals(Field.Status.SHIP, battleFieldModel.getField(start).getStatus());
        assertEquals(Field.Status.SHIP, battleFieldModel.getField(end).getStatus());
    }

    @Test
    void getField_00() {
        var field = battleFieldModel.getField(new Coordinates(0, 0));
        assertEquals(0, field.getxPos());
        assertEquals(0, field.getyPos());
    }

    @Test
    void getField_99() {
        var field = battleFieldModel.getField(new Coordinates(9, 9));

        assertEquals(9, field.getxPos());
        assertEquals(9, field.getyPos());
    }

    @Test
    void getField00_isEmpty() {
        var field = battleFieldModel.getField(new Coordinates(0, 0));
        assertEquals(Field.Status.EMPTY, field.getStatus());
    }

    @Test
    void getField99_isEmpty() {
        var field = battleFieldModel.getField(new Coordinates(9, 9));
        assertEquals(Field.Status.EMPTY, field.getStatus());
    }
}
