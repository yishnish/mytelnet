package command;

import locations.Coordinates;

import java.util.ArrayList;

public class OnesBasedCursorMoveCommand extends CursorMoveCommand {
    public OnesBasedCursorMoveCommand(ArrayList<Character> commandSequence) {
        super(commandSequence);
    }

    @Override
    protected Coordinates makeCoordinates(int row, int column) {
        return new Coordinates(row - 1, column - 1);
    }
}
