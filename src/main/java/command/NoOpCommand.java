package command;

import terminal.VTerminal;

public class NoOpCommand implements TerminalCommand{
    public void call(VTerminal terminal) {
        //NOOP
    }
}
