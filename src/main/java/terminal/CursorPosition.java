package terminal;

public class CursorPosition {

    public static CursorPosition HOME = new CursorPosition(0, 0);

    private int row;
    private int col;

    public CursorPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(o == null || getClass() != o.getClass()) return false;

        CursorPosition that = (CursorPosition) o;

        if(row != that.row) return false;
        return col == that.col;
    }

    @Override
    public String toString() {
        return "CursorPosition{" +
                "row=" + row +
                ", col=" + col +
                '}';
    }

    public int getRow() {
        return row;
    }

    public int getCol() {
        return col;
    }
}
