package terminal;

import java.io.PrintStream;

public class PrintStreamDisplay implements Display {

    private PrintStream stream;

    public PrintStreamDisplay(PrintStream outputStream) {
        this.stream = outputStream;
    }

    public void display(char[][] buffer) {
        writeBufferRowsToScreen(buffer);
    }

    private void writeBufferRowsToScreen(char[][] buffer) {
        for (char[] row : buffer) {
            String s = new String(row);
            stream.println(s);
        }
    }
}
