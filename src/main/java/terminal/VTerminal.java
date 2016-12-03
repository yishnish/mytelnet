package terminal;

import command.TerminalCommand;

public interface VTerminal {
    int getHeight();

    int getWidth();

    void moveCursor(CursorPosition position) ;

    CursorPosition getCursorPosition();

    void write(String character);

    String characterAt(CursorPosition position);

    String getScreenText();

    void accept(TerminalCommand command);
}
