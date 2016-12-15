package command;

import terminal.CursorPosition;

import java.util.ArrayList;

public class ZeroBasedCursorMoveCommand extends CursorMoveCommand {
    public ZeroBasedCursorMoveCommand(ArrayList<Character> commandSequence) {
        super(commandSequence);
    }

    @Override
    protected CursorPosition makeCursorPosition(int row, int column) {
        return new CursorPosition(row, column);
    }
}
