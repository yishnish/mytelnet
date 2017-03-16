package command;

import terminal.VTerminal;

class CursorRightCommand implements TerminalCommand{
    public void call(VTerminal terminal) {
        terminal.moveCursor(terminal.getCursorPosition().toTheRight());
    }
}
