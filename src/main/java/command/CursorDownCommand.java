package command;

import terminal.VTerminal;

class CursorDownCommand implements TerminalCommand{

    public void call(VTerminal terminal) {
        terminal.moveCursor(terminal.getCursorPosition().downOne());
    }
}
