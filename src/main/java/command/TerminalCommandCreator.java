package command;

import terminal.Ascii;

import java.util.ArrayList;
import java.util.Optional;

public class TerminalCommandCreator {

    boolean buildingCommand = false;
    ArrayList<Character> command = new ArrayList<Character>();

    public Optional<? extends TerminalCommand> write(char c) {
        if(c == Ascii.ESC) {
            buildingCommand = true;
        } else if(buildingCommand) {
            command.add(c);
            //add cursor move command if finished (H or f)
        } else {
            return Optional.of(new CharacterWriteCommand(c));
        }
        return Optional.empty();
    }
}
