package battleship;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BattleFieldModelTest {

    BattleFieldModel battleFieldModel;

    @BeforeEach
    void setUp() {
        battleFieldModel = new BattleFieldModel();
    }


    @Test
    void airCraftCarrierNotHorizontalOrVertical_throwsWrongInput() {
        var thrown = assertThrows(WrongLocation.class,
                () -> battleFieldModel.setVessel(
                        new CoordinatesPair(
                                new Coordinates(0, 0),
                                new Coordinates(4, 4)),
                        BattleFieldModel.VesselType.AIRCRAFT_CARRIER));


        assertTrue(thrown.getMessage().contains("either horizontal or vertical"));
    }

    @Test
    void verticalDestroyersRightNextToEachOther_throwsTooClose() {
        // Avoid this situation: ships too close!
        //   1 2 3 4 5 6 7 8 9 10
        // A ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // B ~ O O ~ ~ ~ ~ ~ ~ ~
        // C ~ O O ~ ~ ~ ~ ~ ~ ~
        // D ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // E ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // F ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // J ~ ~ ~ ~ ~ ~ ~ ~ ~ ~


        battleFieldModel.setVessel(new CoordinatesPair(
                        new Coordinates(1, 1),
                        new Coordinates(2, 1)),
                BattleFieldModel.VesselType.DESTROYER);

        assertThrows(TooClose.class,
                () -> battleFieldModel.setVessel(new CoordinatesPair(
                                new Coordinates(1, 2),
                                new Coordinates(2, 2)),
                        BattleFieldModel.VesselType.DESTROYER));
    }

    @Test
    void verticalDestroyersRightAfterEachOther_throwsTooClose() {
        // Avoid this situation: ships too close!
        //   1 2 3 4 5 6 7 8 9 10
        // A ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // B ~ O ~ ~ ~ ~ ~ ~ ~ ~
        // C ~ O ~ ~ ~ ~ ~ ~ ~ ~
        // D ~ O O ~ ~ ~ ~ ~ ~ ~
        // E ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // F ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // J ~ ~ ~ ~ ~ ~ ~ ~ ~ ~


        battleFieldModel.setVessel(new CoordinatesPair(
                        new Coordinates(3, 1),
                        new Coordinates(3, 2)),
                BattleFieldModel.VesselType.DESTROYER);

        assertThrows(TooClose.class,
                () -> battleFieldModel.setVessel(new CoordinatesPair(
                                new Coordinates(1, 1),
                                new Coordinates(2, 1)),
                        BattleFieldModel.VesselType.DESTROYER));
    }

    @Test
    void verticalCruisersRightNextToEachOther_throwsTooClose() {
        // Avoid this situation: ships too close!
        //   1 2 3 4 5 6 7 8 9 10
        // A ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // B ~ ~ ~ ~ ~ ~ ~ O ~ ~
        // C ~ ~ ~ ~ ~ ~ ~ O ~ ~
        // D ~ ~ ~ ~ ~ ~ ~ O O ~
        // E ~ ~ ~ ~ ~ ~ ~ ~ O ~
        // F ~ ~ ~ ~ ~ ~ ~ ~ O ~
        // G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // J ~ ~ ~ ~ ~ ~ ~ ~ ~ ~


        battleFieldModel.setVessel(new CoordinatesPair(
                        new Coordinates(1, 8),
                        new Coordinates(3, 8)),
                BattleFieldModel.VesselType.CRUISER);

        assertThrows(TooClose.class,
                () -> battleFieldModel.setVessel(new CoordinatesPair(
                                new Coordinates(3, 9),
                                new Coordinates(5, 9)),
                        BattleFieldModel.VesselType.CRUISER));
    }

    @Test
    void horizontalCruisersRightOfOtherCruiser_throwsTooClose() {
        // Avoid this situation: ships too close!
        //   1 2 3 4 5 6 7 8 9 10
        // A ~ ~ ~ ~ ~ ~ O O O O
        // B ~ ~ ~ ~ ~ ~ O ~ ~ ~
        // C ~ ~ ~ ~ ~ ~ O ~ ~ ~
        // D ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // E ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // F ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // G ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // H ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // I ~ ~ ~ ~ ~ ~ ~ ~ ~ ~
        // J ~ ~ ~ ~ ~ ~ ~ ~ ~ ~


        battleFieldModel.setVessel(new CoordinatesPair(
                        new Coordinates(0, 6),
                        new Coordinates(2, 6)),
                BattleFieldModel.VesselType.CRUISER);

        assertThrows(TooClose.class,
                () -> battleFieldModel.setVessel(new CoordinatesPair(
                                new Coordinates(0, 7),
                                new Coordinates(0, 9)),
                        BattleFieldModel.VesselType.CRUISER));
    }


    @Nested
    class VerticalAircraftCarrier {

        @Test
        void setAircraftCarrierVerticalTooLong_throwsWrongInput() {
            var thrown = assertThrows(WrongLength.class,
                    () -> battleFieldModel.setAircraftCarrier(
                            new Coordinates(0, 0),
                            new Coordinates(5, 0)));

            assertTrue(thrown.getMessage().contains("wrong length"));
        }

        @Test
        void setAircraftCarrierVerticalTooShort_throwsWrongInput() {
            var thrown = assertThrows(WrongLength.class,
                    () -> battleFieldModel.setAircraftCarrier(
                            new Coordinates(0, 0),
                            new Coordinates(3, 0)));

            assertTrue(thrown.getMessage().contains("wrong length"));

        }

        @Test
        void set_aircraft_carrier_vertical_starting_at_00() {
            var start = new Coordinates(0, 0);
            var end = new Coordinates(4, 0);

            battleFieldModel.setAircraftCarrier(start, end);

            assertFalse(battleFieldModel.getField(start).isEmpty());
            assertFalse(battleFieldModel.getField(end).isEmpty());
        }

        @Test
        void set_aircraft_carrier_vertical_starting_at_00_inverted_input() {
            var start = new Coordinates(0, 0);
            var end = new Coordinates(4, 0);

            battleFieldModel.setAircraftCarrier(end, start);

            assertFalse(battleFieldModel.getField(start).isEmpty());
            assertFalse(battleFieldModel.getField(end).isEmpty());
        }

        @Test
        void set_aircraft_carrier_vertical_starting_at_09() {
            var start = new Coordinates(0, 9);
            var end = new Coordinates(4, 9);

            battleFieldModel.setAircraftCarrier(start, end);

            assertEquals(Field.Status.SHIP, battleFieldModel.getField(start).getStatus());
            assertEquals(Field.Status.SHIP, battleFieldModel.getField(end).getStatus());
        }

        @Test
        void set_aircraft_carrier_vertical_ending_at_99() {
            var start = new Coordinates(5, 9);
            var end = new Coordinates(9, 9);

            battleFieldModel.setAircraftCarrier(start, end);

            assertEquals(Field.Status.SHIP, battleFieldModel.getField(start).getStatus());
            assertEquals(Field.Status.SHIP, battleFieldModel.getField(end).getStatus());
        }

    }


    @Nested
    class HorizontalAircraftCarrier {

        @Test
        void setAircraftCarrierHorizontalTooLong_throwsWrongInput() {
            var thrown = assertThrows(WrongLength.class,
                    () -> battleFieldModel.setAircraftCarrier(
                            new Coordinates(0, 0),
                            new Coordinates(0, 5)));

            assertTrue(thrown.getMessage().contains("wrong length"));

        }

        @Test
        void set_aircraft_carrier_horizontal_too_short_throws_WrongInput() {
            var thrown = assertThrows(WrongLength.class,
                    () -> battleFieldModel.setAircraftCarrier(
                            new Coordinates(0, 0),
                            new Coordinates(0, 3)));

            assertTrue(thrown.getMessage().contains("wrong length"));

        }


        @Test
        void set_aircraft_carrier_horizontal_starting_at_00() {
            var start = new Coordinates(0, 0);
            var end = new Coordinates(0, 4);

            battleFieldModel.setAircraftCarrier(start, end);

            assertEquals(Field.Status.SHIP, battleFieldModel.getField(start).getStatus());
            assertEquals(Field.Status.SHIP, battleFieldModel.getField(end).getStatus());
        }

        @Test
        void set_aircraft_carrier_horizontal_starting_at_00_inverted_input() {
            var start = new Coordinates(0, 0);
            var end = new Coordinates(0, 4);

            battleFieldModel.setAircraftCarrier(end, start);

            assertEquals(Field.Status.SHIP, battleFieldModel.getField(start).getStatus());
            assertEquals(Field.Status.SHIP, battleFieldModel.getField(end).getStatus());
        }

        @Test
        void set_aircraft_carrier_horizontal_starting_at_90() {
            var start = new Coordinates(9, 0);
            var end = new Coordinates(9, 4);

            battleFieldModel.setAircraftCarrier(start, end);

            assertEquals(Field.Status.SHIP, battleFieldModel.getField(start).getStatus());
            assertEquals(Field.Status.SHIP, battleFieldModel.getField(end).getStatus());
        }

        @Test
        void set_aircraft_carrier_horizontal_ending_at_99() {
            var start = new Coordinates(9, 5);
            var end = new Coordinates(9, 9);

            battleFieldModel.setAircraftCarrier(start, end);

            assertEquals(Field.Status.SHIP, battleFieldModel.getField(start).getStatus());
            assertEquals(Field.Status.SHIP, battleFieldModel.getField(end).getStatus());
        }
    }

    @Nested
    class BattleShip {
        @Test
        void vertical_battleship_too_short() {
            var start = new Coordinates(0, 0);
            var end = new Coordinates(2, 0);
            var thrown = assertThrows(WrongLength.class,
                    () -> battleFieldModel.setBattleship(start, end));

            assertTrue(thrown.getMessage().contains("wrong length"));
        }

        @Test
        void horizontal_battleship_too_long() {
            var start = new Coordinates(0, 0);
            var end = new Coordinates(0, 4);
            var thrown = assertThrows(WrongLength.class,
                    () -> battleFieldModel.setBattleship(start, end));

            assertTrue(thrown.getMessage().contains("wrong length"));
        }

        @Test
        void battleship_switched_start_end_positions_still_ok() {
            var end = new Coordinates(0, 0);
            var start = new Coordinates(0, 3);

            battleFieldModel.setBattleship(start, end);

            assertEquals(Field.Status.SHIP, battleFieldModel.getField(start).getStatus());
            assertEquals(Field.Status.SHIP, battleFieldModel.getField(end).getStatus());
        }


    }


    @Nested
    class VerticalBattleship {
        @Test
        void battleship() {
            var start = new Coordinates(6, 9);
            var end = new Coordinates(9, 9);
            battleFieldModel.setBattleship(start, end);

            assertEquals(Field.Status.SHIP, battleFieldModel.getField(start).getStatus());
            assertEquals(Field.Status.SHIP, battleFieldModel.getField(end).getStatus());
        }
    }

    @Nested
    class FieldTest {

        @Nested
        class Get {

            @Test
            void getField00_checkCoordinates() {
                var field = battleFieldModel.getField(0, 0);
                assertEquals(0, field.getHorizontalIndex());
                assertEquals(0, field.getVerticalIndex());
            }

            @Test
            void getField01_checkCoordinates() {
                var field = battleFieldModel.getField(1, 0);
                assertEquals(0, field.getHorizontalIndex());
                assertEquals(1, field.getVerticalIndex());
            }

            @Test
            void getField99_checkCoordinates() {
                var field = battleFieldModel.getField(9, 9);

                assertEquals(9, field.getHorizontalIndex());
                assertEquals(9, field.getVerticalIndex());
            }

            @Test
            void getField00_isEmpty() {
                var field = battleFieldModel.getField(0, 0);
                assertTrue(field.isEmpty());
            }

            @Test
            void getField99_isEmpty() {
                var field = battleFieldModel.getField(9, 9);
                assertTrue(field.isEmpty());
            }
        }

        @Nested
        class Left {
            @Test
            void leftOf00_isNull() {
                var current = battleFieldModel.getField(0, 0);
                var left = battleFieldModel.leftOf(current);

                assertNull(left);
            }

            @Test
            void leftOf09_is08() {
                var current = battleFieldModel.getField(0, 9);
                var left = battleFieldModel.leftOf(current);

                assertEquals(8, left.getHorizontalIndex());
                assertEquals(0, left.getVerticalIndex());
            }
        }

        @Nested
        class Above {

            @Test
            void of10_is00() {
                var current = battleFieldModel.getField(1, 0);
                var above = battleFieldModel.aboveOf(current);

                assertEquals(0, above.getVerticalIndex());
                assertEquals(0, above.getHorizontalIndex());
            }

            @Test
            void of00_isNull() {
                var current = battleFieldModel.getField(0, 0);
                var above = battleFieldModel.aboveOf(current);

                assertNull(above);
            }

            @Test
            void of09_isNull() {
                var current = battleFieldModel.getField(0, 9);
                var above = battleFieldModel.aboveOf(current);

                assertNull(above);
            }

            @Test
            void of99_is89() {
                var field01 = battleFieldModel.getField(9, 9);
                var above = battleFieldModel.aboveOf(field01);

                assertEquals(8, above.getVerticalIndex());
                assertEquals(9, above.getHorizontalIndex());
            }
        }

        @Nested
        class Below {

            @Test
            void of99_isNull() {
                var current = battleFieldModel.getField(9, 9);
                Field below = battleFieldModel.belowOf(current);

                assertNull(below);
            }

            @Test
            void of00_is10() {
                var current = battleFieldModel.getField(0, 0);

                var below = battleFieldModel.belowOf(current);

                assertEquals(1, below.getVerticalIndex());
                assertEquals(0, below.getHorizontalIndex());
            }
        }

        @Nested
        class Right {
            @Test
            void of99_isNull() {
                var current = battleFieldModel.getField(9, 9);

                Field right = battleFieldModel.rightOf(current);

                assertNull(right);
            }

            @Test
            void of00_is01() {
                var current = battleFieldModel.getField(0, 0);

                var right = battleFieldModel.rightOf(current);

                assertEquals(1, right.getHorizontalIndex());
                assertEquals(0, right.getVerticalIndex());
            }
        }


    }

    @Nested
    class Hit {

        @Test
        void hitWater_isMiss() {
            assertFalse(battleFieldModel.takeShot(0, 0));
        }

        @Test
        void hitWater_isMarkedMiss() {
            var shot = new Coordinates(1, 1);
            battleFieldModel.takeShot(shot);

            var target = battleFieldModel.getField(shot);

            assertEquals(Field.Status.MISS, target.getStatus());
        }

        @Test
        void hitShip_isMarkedHit() {
            battleFieldModel.setVessel(
                    new CoordinatesPair(
                            new Coordinates(0, 0),
                            new Coordinates(0, 1)),
                    BattleFieldModel.VesselType.DESTROYER);

            var shot = new Coordinates(0, 0);

            battleFieldModel.takeShot(shot);

            var target = battleFieldModel.getField(shot);

            assertEquals(Field.Status.HIT, target.getStatus());
        }
    }


}
