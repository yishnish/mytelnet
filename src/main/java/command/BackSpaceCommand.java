package command;

import terminal.VTerminal;

public class BackSpaceCommand implements TerminalCommand{

    public void call(VTerminal terminal) {
        terminal.moveCursor(terminal.getCursorPosition().toTheLeft());
    }
}
