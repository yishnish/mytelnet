package terminal;

import command.TerminalCommand;

import java.util.function.Consumer;

public interface VTerminal extends Consumer<TerminalCommand>{
    int getHeight();

    int getWidth();

    void moveCursor(CursorPosition position) ;

    CursorPosition getCursorPosition();

    void write(String character);

    String characterAt(CursorPosition position);

    String getScreenText();

    void accept(TerminalCommand command);

    void advanceCursor();

    void newLine();

    void carriageReturn();

    void home();

    void clearFromCursorDown();

    void clearFromCursorToEndOfRow();
}
