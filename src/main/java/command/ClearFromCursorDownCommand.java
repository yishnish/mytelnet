package command;

import terminal.VTerminal;

public class ClearFromCursorDownCommand implements TerminalCommand {
    public void call(VTerminal terminal) {
        terminal.clearFromCursorDown();
    }
}
