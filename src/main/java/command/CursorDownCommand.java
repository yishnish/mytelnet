package command;

import locations.Coordinates;
import terminal.VTerminal;

class CursorDownCommand implements TerminalCommand{

    public void call(VTerminal terminal) {
        Coordinates currentPosition = terminal.getCoordinates();
        terminal.moveCursor(new Coordinates(currentPosition.getRow() + 1, currentPosition.getColumn()));
    }
}
