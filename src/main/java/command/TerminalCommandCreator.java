package command;

import terminal.Ascii;
import terminal.CursorMoveCommandFactory;
import terminal.TerminalMode;

import java.util.ArrayList;
import java.util.Optional;

public class TerminalCommandCreator {
    private boolean buildingCommand = false;
    private boolean dealingWithTwoCharacterIgnorable;
    private ArrayList<Character> command = new ArrayList<Character>();
    private CursorMoveCommandFactory cursorMoveCommandFactory;

    public TerminalCommandCreator(){
        cursorMoveCommandFactory = new CursorMoveCommandFactory();
        cursorMoveCommandFactory.setMode(TerminalMode.ZERO_BASED);
    }

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
            } else if(c == 'A') {
                command.clear();
                buildingCommand = false;
                return Optional.of(new NoOpCommand());
            }  else if(c == 'C') {
                command.clear();
                buildingCommand = false;
                return Optional.of(new NoOpCommand());
            } else if(c == '(' || c == ')') {
                buildingCommand = false;
                dealingWithTwoCharacterIgnorable = true;
            } else if(c == 'J') {
                command.clear();
                buildingCommand = false;
                return Optional.of(new ClearFromCursorDownCommand());
            } else if(c == 'K') {
                command.clear();
                buildingCommand = false;
                return Optional.of(new ClearFromCursorToEndOfRowCommand());
            } else if(c == 'H' || c == 'f') {
                buildingCommand = false;
                Optional<? extends TerminalCommand> terminalCommand = Optional.of(cursorMoveCommandFactory.createCommand(command));
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

    public void setMode(TerminalMode mode) {
        cursorMoveCommandFactory.setMode(mode);
    }
}
