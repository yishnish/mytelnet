package command;

import terminal.VTerminal;

public class NewLineCommand implements TerminalCommand {
    public void call(VTerminal terminal) {
        terminal.newLine();
    }
}
