package command;

import terminal.CursorPosition;
import terminal.VTerminal;

import java.util.ArrayList;
import java.util.function.Predicate;

public abstract class CursorMoveCommand implements TerminalCommand{
    protected CursorPosition position;

    protected CursorMoveCommand(ArrayList<Character> commandSequence) {
        this.position = extractCoordinates(commandSequence);
    }

    public void call(VTerminal terminal) {
        terminal.moveCursor(position);
    }

    private CursorPosition extractCoordinates(ArrayList<Character> characters) {
        removeLeadingBracket(characters);
        StringBuilder builder = new StringBuilder();
        for (Character character : characters) {
            builder.append(character);
        }
        String[] params = builder.toString().split(";");

        if(params.length == 0 || params.length == 1 || isNullOrEmpty(params[0]) || isNullOrEmpty(params[1])) {
            return CursorPosition.HOME;
        }
        return makeCursorPosition(Integer.parseInt(params[0]), Integer.parseInt(params[1]));
    }

    protected abstract CursorPosition makeCursorPosition(int row, int column);

    private static void removeLeadingBracket(ArrayList<Character> characters) {
        characters.removeIf(new Predicate<Character>() {
            public boolean test(Character character) {
                return character == '[';
            }
        });
    }

    private static boolean isNullOrEmpty(String string) {
        return string == null || string.isEmpty();
    }
}
