package battleship;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleFieldModelTest {

    @Test
    void setAircraftCarrierTooLong_throwsException() {
        var battleFieldModel = new BattleFieldModel();
        assertThrows(WrongInput.class, () ->
                battleFieldModel.setAircraftCarrier(
                        new Position(0, 0),
                        new Position(0, 5)));
    }
}
