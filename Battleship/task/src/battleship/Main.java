package battleship;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        var battleFiled = new BattleField();

        System.out.println(battleFiled.getRepresentationString() + "\n");

        battleFiled.setAircraftCarrier();

        System.out.println(battleFiled.getRepresentationString());
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
        System.out.println("Enter the coordinates of the Aircraft Carrier (5 cells):");
        var rawInput = scanner.nextLine();
        CoordinatesPair coordinatesPair = getPositionPairFromRawInput(rawInput);
        battleFieldModel.setAircraftCarrier(coordinatesPair);
    }

    private CoordinatesPair getPositionPairFromRawInput(String rawInput) {
        var input = rawInput.strip().split(" ");
        Coordinates start = Coordinates.of(input[0]);
        var end = Coordinates.of(input[1]);
        return new CoordinatesPair(start, end);
    }
}

class BattleFieldModel {
    static final int FIELD_SIZE = 10;

    Field[][] fields = new Field[FIELD_SIZE][FIELD_SIZE];

    public BattleFieldModel() {

        for (int yPos = 0; yPos < FIELD_SIZE; yPos++) {
            for (int xPos = 0; xPos < FIELD_SIZE; xPos++) {
                fields[yPos][xPos] = new Field(new Coordinates(xPos, yPos));
            }
        }
    }

    public void setAircraftCarrier(Coordinates startCoordinates, Coordinates endCoordinates) {

        var isVertical = startCoordinates.getxPos() == endCoordinates.getxPos();
        var isHorizontal = startCoordinates.getyPos() == endCoordinates.getyPos();
        if (isVertical) {
            var start = startCoordinates.getyPos();
            var end = endCoordinates.getyPos();
            var shipLength = Math.abs(start - end);
            checkAircraftCarrierLength(shipLength);

            var xPos = startCoordinates.getxPos();
            for (int yPos = start; yPos <= end; yPos++) {
                fields[yPos][xPos].status = Field.Status.SHIP;
            }
        } else if (isHorizontal) {
            var start = startCoordinates.getxPos();
            var end = endCoordinates.getxPos();
            var shipLength = Math.abs(start - end);
            checkAircraftCarrierLength(shipLength);

            var row = startCoordinates.getyPos();
            for (int x = start; x <= end; x++) {
                fields[row][x].status = Field.Status.SHIP;
            }
        } else {
            throw new WrongInput("Aircraft Carrier must be either horizontal or vertical!");
        }


    }

    private void checkAircraftCarrierLength(int shipLength) {
        if (shipLength != 4) {
            throw new WrongInput("Aircraft carrier wrong length. Should be 4, but is "
                    + shipLength);
        }
    }

    public void setAircraftCarrier(CoordinatesPair coordinatesPair) {
        setAircraftCarrier(coordinatesPair.getStart(), coordinatesPair.getEnd());
    }

    public Field getField(Coordinates coordinates) {
        return fields[coordinates.getyPos()][coordinates.getxPos()];
    }
}

class Field {
    Coordinates coordinates;
    Status status = Status.EMPTY;

    public Field(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public int getxPos() {
        return coordinates.getxPos();
    }

    public int getyPos() {
        return coordinates.getyPos();
    }

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
    private int xPos;
    private int yPos;

    public Coordinates(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public static Coordinates of(String s) {
        var x = Integer.parseInt(s.substring(1)) - 1;
        int y = s.charAt(0) - 'A';
        return new Coordinates(x, y);
    }

    public int getxPos() {
        return xPos;
    }

    public void setxPos(int xPos) {
        this.xPos = xPos;
    }

    public int getyPos() {
        return yPos;
    }

    public void setyPos(int yPos) {
        this.yPos = yPos;
    }
}

class WrongInput extends IllegalArgumentException {

    public WrongInput() {

    }

    public WrongInput(String s) {
        super(s);
    }
}
