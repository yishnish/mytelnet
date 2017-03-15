package command;

import terminal.VTerminal;

public class CursorUpCommand implements TerminalCommand{
    public void call(VTerminal terminal) {
        terminal.moveCursor(terminal.getCursorPosition().upOne());
    }
}
