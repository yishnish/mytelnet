package command;

import locations.Coordinates;

import java.util.ArrayList;

public class ZeroBasedCursorMoveCommand extends CursorMoveCommand {
    public ZeroBasedCursorMoveCommand(ArrayList<Character> commandSequence) {
        super(commandSequence);
    }

    @Override
    protected Coordinates makeCoordinates(int row, int column) {
        return new Coordinates(row, column);
    }
}
