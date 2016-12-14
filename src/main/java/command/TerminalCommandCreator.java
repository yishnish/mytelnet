package command;

import parse.CommandParser;
import terminal.Ascii;
import terminal.CursorPosition;

import java.util.ArrayList;
import java.util.Optional;

public class TerminalCommandCreator {

    private boolean buildingCommand = false;
    private ArrayList<Character> command = new ArrayList<Character>();
    private boolean ignoreNextCharacter;

    public Optional<? extends TerminalCommand> write(char c) {
        if(c == Ascii.ESC) {
            buildingCommand = true;
        } else if(ignoreNextCharacter) {
            ignoreNextCharacter = false;
            return Optional.of(new NoOpCommand());
        } else if(buildingCommand)

        {
            if(c == '(') {
                buildingCommand = false;
                ignoreNextCharacter = true;
            }
            if(c == 'H' || c == 'f') {
                buildingCommand = false;
                Optional<TerminalCommand> terminalCommand = Optional.of(CommandParser.parseCommand(command));
                command.clear();
                return terminalCommand;
            } else {
                command.add(c);
            }
        } else

        {
            return Optional.of(new CharacterWriteCommand(c));
        }

        return Optional.of(new

                        NoOpCommand()

        );
    }
}
