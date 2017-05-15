package terminal;

import command.TerminalCommand;
import locations.Coordinates;

import java.util.function.Consumer;

public interface VTerminal extends Consumer<TerminalCommand>, UpdateTimeTracking {
    int getHeight();

    int getWidth();

    void moveCursor(Coordinates position) ;

    Coordinates getCoordinates();

    void write(char character);

    char characterAt(Coordinates position);

    String getBufferAsString();

    void accept(TerminalCommand command);

    void advanceCursor();

    void newLine();

    void carriageReturn();

    void home();

    void clearFromCursorDown();

    void clearFromCursorToEndOfRow();

    ScreenBuffer getScreenBuffer();
}
