package command;

import terminal.TerminalMode;

import java.util.ArrayList;

public class CursorMoveCommandFactory {
    private TerminalMode mode;

    public void setMode(TerminalMode mode) {
        this.mode = mode;
    }

    public CursorMoveCommand createCommand(ArrayList<Character> command) {
        if(mode == TerminalMode.ZERO_BASED) {
            return new ZeroBasedCursorMoveCommand(command);
        } else {
            return new OnesBasedCursorMoveCommand(command);
        }
    }
}
