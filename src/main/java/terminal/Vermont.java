package terminal;

import command.TerminalCommand;

import java.util.function.Consumer;

public class Vermont implements VTerminal, Consumer<TerminalCommand> {

    private final Display display;
    private final TimePiece timePiece;
    private long lastUpdated = 0;
    private int height;
    private int width;
    private char[][] screen;
    private CursorPosition cursorPosition = new CursorPosition(0, 0);

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

    public void accept(TerminalCommand command) {
        command.call(this);
        setLastUpdated();
        this.display.display(screen);
    }

    public void write(char character) {
        screen[cursorPosition.getRow()][cursorPosition.getCol()] = character;
        advanceCursor();
    }

    public void moveCursor(CursorPosition position) {
        validateCursorPosition(position);
        this.cursorPosition = position;
    }

    public void advanceCursor() {
        cursorPosition = new CursorPosition(cursorPosition.getRow(), Math.min(width - 1, cursorPosition.getCol() + 1));
    }

    public void newLine() {
        cursorPosition = new CursorPosition(Math.min(cursorPosition.getRow() + 1, height - 1), cursorPosition.getCol());
    }

    public void carriageReturn() {
        cursorPosition = new CursorPosition(cursorPosition.getRow(), 0);
    }

    public void home() {
        cursorPosition = CursorPosition.HOME;
    }

    public void clearFromCursorDown() {
        clearFromCursorToEndOfRow();
        clearRowsBelowCursor();
    }

    public void clearFromCursorToEndOfRow() {
        int column = cursorPosition.getCol();
        int row = cursorPosition.getRow();
        for (int i = column; i < width; i++) {
            screen[row][i] = Ascii.MIN;
        }
    }

    public char[][] getScreenBuffer() {
        return screen;
    }

    public CursorPosition getCursorPosition() {
        return cursorPosition;
    }

    public char characterAt(CursorPosition position) {
        return screen[position.getRow()][position.getCol()];
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

    private void validateCursorPosition(CursorPosition position) {
        int row = position.getRow();
        if(row >= this.getHeight() || row < 0) {
            throw new ScreenAccessOutOfBoundsException("Row out of bounds: row was " + row + " and height is " + this.getHeight());
        }
        int col = position.getCol();
        if(col >= this.getWidth() || col < 0) {
            throw new ScreenAccessOutOfBoundsException("Column out of bounds: column was " + col + " and width is " + this.getWidth());
        }
    }

    private void clearRowsBelowCursor() {
        for (int i = cursorPosition.getRow() + 1; i < height; i++) {
            clearRow(i);
        }
    }

    private void clearRow(int row) {
        for (int i = 0; i < width; i++) {
            screen[row][i] = Ascii.MIN;
        }
    }
}

