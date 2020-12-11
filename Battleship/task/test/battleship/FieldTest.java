package battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class FieldTest {

    @Nested
    class NewSingleField {
        Field field;

        @BeforeEach
        void setup() {
            field = new Field(1, 1);
        }

        @Test
        void isEmpty() {
            assertTrue(field.isEmpty());
        }
    }

}
