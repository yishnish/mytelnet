package parse;

import command.CursorMoveCommand;
import command.TerminalCommand;
import terminal.CursorPosition;

import java.util.ArrayList;
import java.util.function.Predicate;

public class CommandParser {

    public static TerminalCommand parseCommand(ArrayList<Character> characters) {
        return new CursorMoveCommand(extractCoordinates(characters));
    }

    private static CursorPosition extractCoordinates(ArrayList<Character> characters) {
        removeLeadingBracket(characters);
        StringBuilder builder = new StringBuilder();
        for (Character character : characters) {
            builder.append(character);
        }
        String[] params = builder.toString().split(";");
        if(params.length == 0 || params[0].isEmpty() || params[1].isEmpty()) {
            return CursorPosition.HOME;
        }
        return new CursorPosition(Integer.parseInt(params[0]), Integer.parseInt(params[1]));
    }

    private static void removeLeadingBracket(ArrayList<Character> characters) {
        characters.removeIf(new Predicate<Character>() {
            public boolean test(Character character) {
                return character == '[';
            }
        });
    }
}
