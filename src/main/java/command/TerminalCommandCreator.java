package command;

import terminal.Ascii;

import java.util.ArrayList;
import java.util.Optional;

public class TerminalCommandCreator {
    private boolean buildingCommand = false;
    private boolean dealingWithTwoCharacterIgnorable;
    private ArrayList<Character> command = new ArrayList<Character>();

    public Optional<? extends TerminalCommand> write(char c) {
        if(c == Ascii.ESC) {
            buildingCommand = true;
        } else if(c == Ascii.CR) {
            return Optional.of(new CarriageReturnCommand());
        } else if(c == Ascii.LF) {
            return Optional.of(new NewLineCommand());
        } else if(dealingWithTwoCharacterIgnorable) {
            command.clear();
            dealingWithTwoCharacterIgnorable = false;
        } else if(buildingCommand) {
            if(c == 'm') {
                command.clear();
                buildingCommand = false;
                return Optional.of(new NoOpCommand());
            }
            if(c == '(' || c == ')') {
                buildingCommand = false;
                dealingWithTwoCharacterIgnorable = true;
            } else if(c == 'J') {
                command.clear();
                return Optional.of(new ClearFromCursorDownCommand());
            } else if(c == 'K') {
                command.clear();
                return Optional.of(new ClearFromCursorToEndOfRowCommand());
            } else if(c == 'H' || c == 'f') {
                buildingCommand = false;
                Optional<CursorMoveCommand> terminalCommand = Optional.of(new CursorMoveCommand(command));
                command.clear();
                return terminalCommand;
            } else {
                command.add(c);
            }
        } else {
            return Optional.of(new CharacterWriteCommand(c));
        }
        return Optional.of(new NoOpCommand());
    }
}
