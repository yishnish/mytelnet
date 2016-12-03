package terminal;

import command.TerminalCommand;

public class Vermont implements VTerminal {

    private int height = 24;
    private int width = 80;
    private CursorPosition cursorPosition = new CursorPosition(0, 0);
    private String[][] screen = new String[height][width];

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void moveCursor(CursorPosition position) {
        validateCursorPosition(position);
        this.cursorPosition = position;
    }

    public CursorPosition getCursorPosition() {
        return cursorPosition;
    }

    public void write(String character) {
        screen[cursorPosition.getRow()][cursorPosition.getCol()] = character;
    }

    public String characterAt(CursorPosition position) {
        return screen[position.getRow()][position.getCol()];
    }

    public String getScreenText() {
        StringBuilder sb = new StringBuilder();
        for (String[] row : screen) {
            for (String character : row) {
                sb.append(character == null ? " " : character);
            }
            sb.append("\n");
        }
        return sb.toString();
    }

    public void accept(TerminalCommand command) {
        command.call(this);
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
}

