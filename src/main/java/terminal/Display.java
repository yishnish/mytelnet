package terminal;

import java.io.IOException;
import java.io.PrintStream;

public class Display {

    private char[][] buffer;
    private PrintStream stream;

    public Display(char[][] buffer, PrintStream outputStream) {
        this.buffer = buffer;
        this.stream = outputStream;
    }

    public void display() throws IOException {
        clearScreen();
        writeBufferRowsToScreen();
    }

    private void clearScreen() {
        for (int i = 0; i < buffer.length; i++) {
            stream.println();
        }
    }

    private void writeBufferRowsToScreen() {
        for (char[] row : buffer) {
            String s = new String(row);
            stream.println(s);
        }
    }
}
