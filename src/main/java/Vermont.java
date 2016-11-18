class Vermont {

    private int height = 24;
    private int width = 80;
    private CursorPosition cursorPosition;
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

    private void validateCursorPosition(CursorPosition position) {
        int row = position.getRow();
        if(row >= this.getHeight() || row < 0) {
            throw new ScreenAccessOutOfBoundsException();
        }
        int col = position.getCol();
        if(col >= this.getWidth() || col < 0) {
            throw new ScreenAccessOutOfBoundsException();
        }
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

}
