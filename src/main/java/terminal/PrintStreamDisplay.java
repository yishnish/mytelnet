package terminal;

import java.io.PrintStream;

public class PrintStreamDisplay implements Display {

    private char[][] buffer;
    private PrintStream stream;

    public PrintStreamDisplay(char[][] buffer, PrintStream outputStream) {
        this.buffer = buffer;
        this.stream = outputStream;
    }

    public void display() {
        writeBufferRowsToScreen();
    }

    private void writeBufferRowsToScreen() {
        for (char[] row : buffer) {
            String s = new String(row);
            stream.println(s);
        }
    }
}
