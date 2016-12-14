package command;

import terminal.VTerminal;

public class CrNlCommand implements TerminalCommand{
    public void call(VTerminal terminal) {
        terminal.cRnL();
    }
}
