package command;

import locations.Coordinates;
import terminal.VTerminal;

public class BackSpaceCommand implements TerminalCommand{

    public void call(VTerminal terminal) {
        Coordinates currentPosition = terminal.getCoordinates();
        terminal.moveCursor(new Coordinates(currentPosition.getRow(), currentPosition.getColumn() - 1));
    }
}
