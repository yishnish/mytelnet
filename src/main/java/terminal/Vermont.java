package terminal;

import command.TerminalCommand;
import locations.Coordinates;

import java.util.function.Consumer;

public class Vermont implements VTerminal, Consumer<TerminalCommand> {

    private final Display display;
    private final TimePiece timePiece;
    private long lastUpdated = 0;
    private int height;
    private int width;
    private char[][] screen;
    private Coordinates coordinates = new Coordinates(0, 0);

    public Vermont(Display display) {
        this(24, 80, display, new UTCTimePiece());
    }

    public Vermont(int height, int width, Display display) {
        this(height, width, display, new UTCTimePiece());
    }

    public Vermont(int height, int width, Display display, TimePiece timePiece) {
        this.height = height;
        this.width = width;
        this.screen = new char[height][width];
        this.display = display;
        this.timePiece = timePiece;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }

    public synchronized void accept(TerminalCommand command) {
        command.call(this);
        setLastUpdated();
        this.display.display(screen);
    }

    public synchronized char[][] getScreenBuffer() {
        return screen;
    }

    public void write(char character) {
        screen[coordinates.getRow()][coordinates.getColumn()] = character;
        advanceCursor();
    }

    public void moveCursor(Coordinates coordinates) {
        validateCoordinates(coordinates);
        this.coordinates = coordinates;
    }

    public void advanceCursor() {
        coordinates = new Coordinates(coordinates.getRow(), Math.min(width - 1, coordinates.getColumn() + 1));
    }

    public void newLine() {
        coordinates = new Coordinates(Math.min(coordinates.getRow() + 1, height - 1), coordinates.getColumn());
    }

    public void carriageReturn() {
        coordinates = new Coordinates(coordinates.getRow(), 0);
    }

    public void home() {
        coordinates = coordinates.HOME;
    }

    public void clearFromCursorDown() {
        clearFromCursorToEndOfRow();
        clearRowsBelowCursor();
    }

    public void clearFromCursorToEndOfRow() {
        int column = coordinates.getColumn();
        int row = coordinates.getRow();
        for (int i = column; i < width; i++) {
            screen[row][i] = Ascii.MIN;
        }
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public char characterAt(Coordinates position) {
        return screen[position.getRow()][position.getColumn()];
    }

    public String getBufferAsString() {
        StringBuilder sb = new StringBuilder();
        for (char[] row : screen) {
            for (char character : row) {
                sb.append(character == Ascii.MIN ? " " : character);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public long getLastUpdateTime() {
        return lastUpdated;
    }

    public long unchangedFor() {
        return timePiece.getTimeMillis() - lastUpdated;
    }

    public void setLastUpdated() {
        this.lastUpdated = this.timePiece.getTimeMillis();
    }

    private void validateCoordinates(Coordinates position) {
        int row = position.getRow();
        if(row >= this.getHeight() || row < 0) {
            throw new ScreenAccessOutOfBoundsException("Row out of bounds: row was " + row + " and height is " + this.getHeight());
        }
        int col = position.getColumn();
        if(col >= this.getWidth() || col < 0) {
            throw new ScreenAccessOutOfBoundsException("Column out of bounds: column was " + col + " and width is " + this.getWidth());
        }
    }

    private void clearRowsBelowCursor() {
        for (int i = coordinates.getRow() + 1; i < height; i++) {
            clearRow(i);
        }
    }

    private void clearRow(int row) {
        for (int i = 0; i < width; i++) {
            screen[row][i] = Ascii.MIN;
        }
    }
}

