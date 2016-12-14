package command;

import terminal.VTerminal;

public class ClearFromCursorToEndOfRowCommand implements TerminalCommand {
    public void call(VTerminal terminal) {
        terminal.clearFromCursorToEndOfRow();
    }
}
