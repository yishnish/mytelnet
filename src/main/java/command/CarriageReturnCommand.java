package command;

import terminal.VTerminal;

public class CarriageReturnCommand implements TerminalCommand{

    public void call(VTerminal terminal) {
        terminal.carriageReturn();
    }
}
