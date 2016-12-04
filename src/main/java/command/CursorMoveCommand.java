package command;

import terminal.CursorPosition;
import terminal.VTerminal;

public class CursorMoveCommand implements TerminalCommand{
    private CursorPosition position;

    public CursorMoveCommand(CursorPosition position) {

        this.position = position;
    }

    public void call(VTerminal terminal) {
        terminal.moveCursor(position);
    }
}
