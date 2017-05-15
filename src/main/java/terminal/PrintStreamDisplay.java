package terminal;

import java.io.PrintStream;
import java.util.Arrays;

public class PrintStreamDisplay implements Display {

    private PrintStream stream;

    public PrintStreamDisplay(PrintStream outputStream) {
        this.stream = outputStream;
    }

    public void display(ScreenBuffer buffer) {
        for (char[] row : buffer.getScreenData()) {
            char[] copy = replaceNullWIthSpace(row);
            String s = new String(copy);
            stream.println(s);
        }
    }

    private char[] replaceNullWIthSpace(char[] row) {
        char[] copy = Arrays.copyOf(row, row.length);
        for (int i = 0; i < copy.length; i++) {
            if (copy[i] == Ascii.MIN) {
                copy[i] = Ascii.SPACE;
            }
        }
        return copy;
    }
}
