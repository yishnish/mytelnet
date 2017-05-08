package terminal;

import command.TerminalCommand;

import java.util.function.Consumer;

public interface VTerminal extends Consumer<TerminalCommand>, UpdateTimeTracking {
    int getHeight();

    int getWidth();

    void moveCursor(CursorPosition position) ;

    CursorPosition getCursorPosition();

    void write(char character);

    char characterAt(CursorPosition position);

    String getBufferAsString();

    void accept(TerminalCommand command);

    void advanceCursor();

    void newLine();

    void carriageReturn();

    void home();

    void clearFromCursorDown();

    void clearFromCursorToEndOfRow();

    char[][] getScreenBuffer();
}
