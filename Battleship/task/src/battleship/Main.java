package battleship;

import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        var battleFiled = new BattleField();

        System.out.println(battleFiled.getRepresentationString() + "\n");

        battleFiled.setAircraftCarrier();
        System.out.println(battleFiled.getRepresentationString() + "\n");

        battleFiled.setBattleship();
        System.out.println(battleFiled.getRepresentationString() + "\n");

        battleFiled.setSubmarine();
        System.out.println(battleFiled.getRepresentationString() + "\n");

        battleFiled.setCruiser();
        System.out.println(battleFiled.getRepresentationString() + "\n");

        battleFiled.setDestroyer();
        System.out.println(battleFiled.getRepresentationString() + "\n");
    }
}

class BattleField {
    Scanner scanner = new Scanner(System.in);

    BattleFieldModel battleFieldModel = new BattleFieldModel();

    public String getRepresentationString() {

        var ret = new StringBuilder().append("  1 2 3 4 5 6 7 8 9 10\n");
        var rowLetter = 'A';
        for (var row : battleFieldModel.fields) {
            ret.append(rowLetter);
            rowLetter++;
            for (var field : row) {
                ret.append(" ");
                switch (field.status) {
                    case EMPTY:
                        ret.append("~");
                        break;
                    case SHIP:
                        ret.append("O");
                        break;
                }
            }
            ret.append("\n");
        }
        return ret.toString();
    }

    public void setAircraftCarrier() {
        setVessel(BattleFieldModel.VesselType.AIRCRAFT_CARRIER);
    }

    public void setBattleship() {
        setVessel(BattleFieldModel.VesselType.BATTLESHIP);
    }

    public void setSubmarine() {
        setVessel(BattleFieldModel.VesselType.SUBMARINE);
    }

    public void setCruiser() {
        setVessel(BattleFieldModel.VesselType.CRUISER);
    }

    public void setDestroyer() {
        setVessel(BattleFieldModel.VesselType.DESTROYER);
    }

    void setVessel(BattleFieldModel.VesselType vesselType) {
        var notSet = true;

        while (notSet) {
            System.out.printf("Enter the coordinates of the %s (%d cells):\n",
                    vesselType.getName(), vesselType.getLength());
            var rawInput = scanner.nextLine();
            CoordinatesPair coordinatesPair = getPositionPairFromRawInput(rawInput);
            try {
                battleFieldModel.setVessel(coordinatesPair, vesselType);
                notSet = false;
            } catch (WrongLength wrongLength) {
                System.out.printf("Error! Wrong length of the %s! Try again:\n", vesselType.getName());
            } catch (WrongLocation wrongLocation) {
                System.out.println("Error! Wrong ship location! Try again:");
            }
        }
    }

    private CoordinatesPair getPositionPairFromRawInput(String rawInput) {
        var input = rawInput.strip().split(" ");
        Coordinates start = Coordinates.of(input[0]);
        var end = Coordinates.of(input[1]);
        return new CoordinatesPair(start, end);
    }
}

abstract class Constants {
    static final int FIELD_SIZE = 10;
}

class BattleFieldModel {
    static final int FIELD_SIZE = Constants.FIELD_SIZE;

    Field[][] fields = new Field[FIELD_SIZE][FIELD_SIZE];

    BattleFieldModel() {

        for (int yPos = 0; yPos < FIELD_SIZE; yPos++) {
            for (int xPos = 0; xPos < FIELD_SIZE; xPos++) {
                fields[yPos][xPos] = new Field(yPos, xPos);
            }
        }
    }

    public Field getField(int vertical, int horizontal) {
        return fields[vertical][horizontal];
    }

    public Field getAbove(Field current) {
        return getField(0, 0);
    }

    public void setVessel(CoordinatesPair startAndEnd, VesselType vesselType) {

        var startCoordinates = startAndEnd.getStart();
        var endCoordinates = startAndEnd.getEnd();

        var isVertical = startCoordinates.getHorizontalIndex() == endCoordinates.getHorizontalIndex();
        var isHorizontal = startCoordinates.getVerticalIndex() == endCoordinates.getVerticalIndex();
        if (isVertical) {
            var start = Math.min(startCoordinates.getVerticalIndex(), endCoordinates.getVerticalIndex());
            var end = Math.max(startCoordinates.getVerticalIndex(), endCoordinates.getVerticalIndex());
            var shipLength = end - start + 1;

            checkVesselLength(shipLength, vesselType);
            checkVesselVicinityVertical(startAndEnd);

            var xPos = startCoordinates.getHorizontalIndex();
            for (int yPos = start; yPos <= end; yPos++) {
                fields[yPos][xPos].status = Field.Status.SHIP;
            }
        } else if (isHorizontal) {
            var start = Math.min(startCoordinates.getHorizontalIndex(), endCoordinates.getHorizontalIndex());
            var end = Math.max(startCoordinates.getHorizontalIndex(), endCoordinates.getHorizontalIndex());
            var shipLength = end - start + 1;

            checkVesselLength(shipLength, vesselType);
            checkVesselVicinityHorizontal(startAndEnd);

            var row = startCoordinates.getVerticalIndex();
            for (int x = start; x <= end; x++) {
                fields[row][x].status = Field.Status.SHIP;
            }
        } else {
            throw new WrongLocation("Vessel must be either horizontal or vertical!");
        }

    }

    @Deprecated
    Field getField(Coordinates coordinates) {
        return fields[coordinates.getVerticalIndex()][coordinates.getHorizontalIndex()];
    }

    /**
     * @deprecated use {@link BattleFieldModel#setVessel(CoordinatesPair, VesselType)} instead.
     */
    @Deprecated
    void setAircraftCarrier(Coordinates startCoordinates, Coordinates endCoordinates) {
        setVessel(new CoordinatesPair(startCoordinates, endCoordinates), VesselType.AIRCRAFT_CARRIER);
    }

    @Deprecated
    void setBattleship(Coordinates startCoordinates, Coordinates endCoordinates) {
        setVessel(new CoordinatesPair(startCoordinates, endCoordinates), VesselType.BATTLESHIP);
    }

    @Deprecated
    void setSubmarine(Coordinates startCoordinates, Coordinates endCoordinates) {
        setVessel(new CoordinatesPair(startCoordinates, endCoordinates), VesselType.SUBMARINE);
    }

    private void checkVesselLength(int shipLength, VesselType vesselType) {
        if (shipLength != vesselType.length) {
            throw new WrongLength("Ship has wrong length. Should be " + vesselType.length + " , but is " + shipLength);
        }
    }

    private void checkVesselVicinityVertical(CoordinatesPair startAndEnd) {
        Set<Field> vicinity = getNeighboringFieldsVertical(startAndEnd);
    }

    private void checkVesselVicinityHorizontal(CoordinatesPair startAndEnd) {
        List<Field> vicinity = getNeighboringFieldsHorizontal(startAndEnd);
    }

    private Set<Field> getNeighboringFieldsVertical(CoordinatesPair startAndEnd) {
        Coordinates start;
        if (startAndEnd.getStart().getVerticalIndex() < startAndEnd.getEnd().getVerticalIndex())
            start = startAndEnd.getStart();


        return null;
    }

    private List<Field> getNeighboringFieldsHorizontal(CoordinatesPair startAndEnd) {

        return null;
    }


    enum VesselType {
        AIRCRAFT_CARRIER(5, "Aircraft Carrier"),
        BATTLESHIP(4, "Battleship"),
        SUBMARINE(3, "Submarine"),
        CRUISER(3, "Cruiser"),
        DESTROYER(2, "Destroyer");


        private final int length;
        private final String name;

        VesselType(int shipLength, String name) {
            this.length = shipLength;
            this.name = name;
        }

        public int getLength() {
            return length;
        }

        public String getName() {
            return name;
        }
    }
}

class Field {
    Coordinates coordinates;
    Status status = Status.EMPTY;

    public Field(int verticalIndex, int horizontalIndex) {
        this.coordinates = new Coordinates(verticalIndex, horizontalIndex);
    }

    public int getHorizontalIndex() {
        return coordinates.getHorizontalIndex();
    }

    public int getVerticalIndex() {
        return coordinates.getVerticalIndex();
    }

    public boolean isEmpty() {
        return status == Status.EMPTY;
    }

    /**
     * @deprecated use isEmpty() instead
     */
    @Deprecated
    public Status getStatus() {
        return status;
    }


    enum Status {
        EMPTY, SHIP
    }
}

class CoordinatesPair {
    private Coordinates start;
    private Coordinates end;

    public CoordinatesPair(Coordinates start, Coordinates end) {
        this.start = start;
        this.end = end;
    }

    public Coordinates getStart() {
        return start;
    }

    public void setStart(Coordinates start) {
        this.start = start;
    }

    public Coordinates getEnd() {
        return end;
    }

    public void setEnd(Coordinates end) {
        this.end = end;
    }
}

class Coordinates {
    private int horizontalIndex;
    private int verticalIndex;

    Coordinates(int vertical, int horizontal) {
        this.horizontalIndex = horizontal;
        this.verticalIndex = vertical;
    }

    public static Coordinates of(String s) {
        var x = Integer.parseInt(s.substring(1)) - 1;
        int y = s.charAt(0) - 'A';
        return new Coordinates(y, x);
    }

    public int getHorizontalIndex() {
        return horizontalIndex;
    }

    public void setHorizontalIndex(int horizontalIndex) {
        this.horizontalIndex = horizontalIndex;
    }

    public int getVerticalIndex() {
        return verticalIndex;
    }

    public void setVerticalIndex(int verticalIndex) {
        this.verticalIndex = verticalIndex;
    }
}

class WrongLength extends IllegalArgumentException {

    public WrongLength() {

    }

    public WrongLength(String s) {
        super(s);
    }
}

class WrongLocation extends IllegalArgumentException {
    public WrongLocation() {
    }

    public WrongLocation(String s) {
        super(s);
    }
}

class TooClose extends IllegalArgumentException {
    public TooClose() {
    }

    public TooClose(String s) {
        super(s);
    }
}
