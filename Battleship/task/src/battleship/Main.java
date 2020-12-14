package battleship;

import java.util.HashSet;
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

        battleFiled.startGame();


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
                switch (field.getStatus()) {
                    case EMPTY:
                        ret.append("~");
                        break;
                    case SHIP:
                        ret.append("O");
                        break;
                    case MISS:
                        ret.append("M");
                        break;
                    case HIT:
                        ret.append("X");
                        break;
                }
            }
            ret.append("\n");
        }
        return ret.toString();
    }

    public String getRepresentationStringWithFogOfWar() {
        var ret = new StringBuilder().append("  1 2 3 4 5 6 7 8 9 10\n");
        var rowLetter = 'A';
        for (var row : battleFieldModel.fields) {
            ret.append(rowLetter);
            rowLetter++;
            for (var field : row) {
                ret.append(" ");
                switch (field.getStatus()) {
                    case EMPTY:
                    case SHIP:
                        ret.append("~");
                        break;
                    case MISS:
                        ret.append("M");
                        break;
                    case HIT:
                        ret.append("X");
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

    public void takeShot() {
        var notFinished = true;

        while (notFinished) {
            System.out.println("Take a shot!");

            Coordinates shot;
            try {
                var rawInput = scanner.next();
                shot = Coordinates.of(rawInput);
            } catch (WrongLocation wrongLocation) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                continue;
            }

            var success = battleFieldModel.takeShot(shot);

            System.out.println(getRepresentationStringWithFogOfWar());

            switch (success) {
                case SHIP_HIT:
                    System.out.println("You hit a ship! Try again:");
                    break;
                case SHIP_SUNK:
                    System.out.println("You sank a ship! Specify a new target:");
                    break;
                case LAST_SHIP_SUNK:
                    System.out.println("You sank the last ship. You won. Congratulations!");
                    break;
                case MISS:
                    System.out.println("You missed! Try again:");
                    break;
            }

            notFinished = false;
        }

    }

    public void startGame() {
        System.out.println("The game starts!");

        System.out.println(getRepresentationStringWithFogOfWar());

        while (battleFieldModel.shipsRemain()) {
            takeShot();
        }
    }

    void setVessel(BattleFieldModel.VesselType vesselType) {
        var notSet = true;

        while (notSet) {
            System.out.printf("Enter the coordinates of the %s (%d cells):\n",
                    vesselType.getName(), vesselType.getLength());
            var rawInput = scanner.nextLine();
            try {
                CoordinatesPair coordinatesPair = getPositionPairFromRawInput(rawInput);
                battleFieldModel.setVessel(coordinatesPair, vesselType);
                notSet = false;
            } catch (WrongLength wrongLength) {
                System.out.printf("Error! Wrong length of the %s! Try again:\n", vesselType.getName());
            } catch (WrongLocation wrongLocation) {
                System.out.println("Error! Wrong ship location! Try again:");
            } catch (TooClose tooClose) {
                System.out.println("Error! You placed it too close to another one. Try again:\n");
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
    Set<Set<Field>> ships = new HashSet<>(new HashSet<>());

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

    public void setVessel(CoordinatesPair startAndEnd, VesselType vesselType) {

        var ship = new HashSet<Field>();

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
                var field = fields[yPos][xPos];
                field.setStatus(Field.Status.SHIP);
                ship.add(field);
            }
        } else if (isHorizontal) {
            var start = Math.min(startCoordinates.getHorizontalIndex(), endCoordinates.getHorizontalIndex());
            var end = Math.max(startCoordinates.getHorizontalIndex(), endCoordinates.getHorizontalIndex());
            var shipLength = end - start + 1;

            checkVesselLength(shipLength, vesselType);
            checkVesselVicinityHorizontal(startAndEnd);

            var row = startCoordinates.getVerticalIndex();
            for (int x = start; x <= end; x++) {
                var field = fields[row][x];
                field.setStatus(Field.Status.SHIP);
                ship.add(field);
            }
        } else {
            throw new WrongLocation("Vessel must be either horizontal or vertical!");
        }


        ships.add(ship);

    }

    public Field aboveOf(Field current) {
        if (current.getVerticalIndex() == 0)
            return null;

        var vertical = current.getVerticalIndex() - 1;
        var horizontal = current.getHorizontalIndex();

        return getField(vertical, horizontal);
    }

    public Field belowOf(Field current) {
        if (current.getVerticalIndex() == FIELD_SIZE - 1)
            return null;

        var vertical = current.getVerticalIndex() + 1;
        var horizontal = current.getHorizontalIndex();

        return getField(vertical, horizontal);
    }

    public Field leftOf(Field current) {
        if (current.getHorizontalIndex() == 0)
            return null;

        var horizontal = current.getHorizontalIndex() - 1;
        var vertical = current.getVerticalIndex();
        return getField(vertical, horizontal);
    }

    public Field rightOf(Field current) {
        if (current.getHorizontalIndex() == FIELD_SIZE - 1)
            return null;

        var horizontal = current.getHorizontalIndex() + 1;
        var vertical = current.getVerticalIndex();

        return getField(vertical, horizontal);
    }

    public ShotResult takeShot(int vertical, int horizontal) {
        var field = getField(vertical, horizontal);

        if (field.getStatus() == Field.Status.SHIP) {
            var shipsBeforeShot = getShipNumber();
            field.setStatus(Field.Status.HIT);
            updateShips(field);
            var shipsAfterShot = getShipNumber();

            if (shipsBeforeShot != shipsAfterShot) {
                if (shipsAfterShot == 0)
                    return ShotResult.LAST_SHIP_SUNK;

                return ShotResult.SHIP_SUNK;
            }


            return ShotResult.SHIP_HIT;
        }

        if (field.isEmpty()) {
            field.setStatus(Field.Status.MISS);
        }

        return ShotResult.MISS;
    }

    public ShotResult takeShot(Coordinates shot) {
        return takeShot(shot.getVerticalIndex(), shot.getHorizontalIndex());
    }

    public boolean shipsRemain() {
        return ships.size() > 0;
    }

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

    private void updateShips(Field field) {
        for (var ship : ships) {
            ship.removeIf(shipField -> shipField == field);
        }
        ships.removeIf(ship -> ship.isEmpty());
    }

    private int getShipNumber() {
        return ships.size();
    }

    private void checkVesselLength(int shipLength, VesselType vesselType) {
        if (shipLength != vesselType.length) {
            throw new WrongLength("Ship has wrong length. Should be " + vesselType.length + " , but is " + shipLength);
        }
    }

    private void checkVesselVicinityVertical(CoordinatesPair startAndEnd) {
        Set<Field> vicinity = getNeighboringFieldsVertical(startAndEnd);

        checkFreeVicinity(vicinity);
    }

    private void checkFreeVicinity(Set<Field> vicinity) {
        var anyNotFree = vicinity.stream().anyMatch(field -> !field.isEmpty());

        if (anyNotFree)
            throw new TooClose();
    }

    private void checkVesselVicinityHorizontal(CoordinatesPair startAndEnd) {
        Set<Field> vicinity = getNeighboringFieldsHorizontal(startAndEnd);

        checkFreeVicinity(vicinity);
    }

    private Set<Field> getNeighboringFieldsVertical(CoordinatesPair startAndEnd) {
        var neighbors = new HashSet<Field>();

        CoordinatesPair ordered = startAndEnd.orderVertical();

        var current = getField(ordered.getStart());
        var end = getField(ordered.getEnd());

        while (current != null && current.getVerticalIndex() <= end.getVerticalIndex()) {
            neighbors.add(current);
            neighbors.add(aboveOf(current));
            neighbors.add(belowOf(current));
            neighbors.add(leftOf(current));
            neighbors.add(rightOf(current));

            current = belowOf(current);
        }

        neighbors.remove(null);

        return neighbors;
    }

    private Set<Field> getNeighboringFieldsHorizontal(CoordinatesPair startAndEnd) {
        var neighbors = new HashSet<Field>();

        CoordinatesPair ordered = startAndEnd.orderHorizontal();

        var current = getField(ordered.getStart());
        var end = getField(ordered.getEnd());

        while (current != null && current.getHorizontalIndex() <= end.getHorizontalIndex()) {
            neighbors.add(current);
            neighbors.add(aboveOf(current));
            neighbors.add(belowOf(current));
            neighbors.add(leftOf(current));
            neighbors.add(rightOf(current));

            current = rightOf(current);
        }

        neighbors.remove(null);

        return neighbors;
    }

    enum ShotResult {
        SHIP_HIT, SHIP_SUNK, LAST_SHIP_SUNK, MISS;
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
    private final Coordinates coordinates;
    private Status status = Status.EMPTY;

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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    enum Status {
        EMPTY, SHIP, MISS, HIT
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

    public CoordinatesPair orderVertical() {
        if (start.getVerticalIndex() > end.getVerticalIndex())
            return new CoordinatesPair(end, start);
        else
            return this;
    }

    public CoordinatesPair orderHorizontal() {
        if (start.getHorizontalIndex() > end.getHorizontalIndex())
            return new CoordinatesPair(end, start);
        else
            return this;
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

        if (outsideField(x) || outsideField(y))
            throw new WrongLocation();

        return new Coordinates(y, x);
    }

    private static boolean outsideField(int coordinate) {
        if (coordinate < 0)
            return true;
        if (coordinate >= Constants.FIELD_SIZE)
            return true;
        return false;
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
