package command;

import terminal.CursorPosition;

import java.util.ArrayList;

public class OnesBasedCursorMoveCommand extends CursorMoveCommand {
    public OnesBasedCursorMoveCommand(ArrayList<Character> commandSequence) {
        super(commandSequence);
    }

    @Override
    protected CursorPosition makeCursorPosition(int row, int column) {
        return new CursorPosition(row - 1, column - 1);
    }
}
