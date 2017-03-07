package command;

import terminal.Ascii;
import terminal.CursorMoveCommandFactory;
import terminal.TerminalMode;

import java.util.ArrayList;

public class TerminalCommandCreator {
    private boolean buildingCommand = false;
    private boolean dealingWithTwoCharacterIgnorable;
    private ArrayList<Character> command = new ArrayList<Character>();
    private CursorMoveCommandFactory cursorMoveCommandFactory;

    public TerminalCommandCreator(){
        cursorMoveCommandFactory = new CursorMoveCommandFactory();
        cursorMoveCommandFactory.setMode(TerminalMode.ZERO_BASED);
    }

    public TerminalCommand write(char c) {
        if(c == Ascii.ESC) {
            buildingCommand = true;
        } else if(c == Ascii.CR) {
            return new CarriageReturnCommand();
        } else if(c == Ascii.LF) {
            return new NewLineCommand();
        } else if(dealingWithTwoCharacterIgnorable) {
            command.clear();
            dealingWithTwoCharacterIgnorable = false;
        } else if(buildingCommand) {
            if(c == 'm') {
                command.clear();
                buildingCommand = false;
                return new NoOpCommand();
            } else if(c == 'A') {
                command.clear();
                buildingCommand = false;
                return new NoOpCommand();
            }  else if(c == 'C') {
                command.clear();
                buildingCommand = false;
                return new NoOpCommand();
            } else if(c == '(' || c == ')') {
                buildingCommand = false;
                dealingWithTwoCharacterIgnorable = true;
            } else if(c == 'J') {
                command.clear();
                buildingCommand = false;
                return new ClearFromCursorDownCommand();
            } else if(c == 'K') {
                command.clear();
                buildingCommand = false;
                return new ClearFromCursorToEndOfRowCommand();
            } else if(c == 'H' || c == 'f') {
                buildingCommand = false;
                TerminalCommand terminalCommand = cursorMoveCommandFactory.createCommand(command);
                command.clear();
                return terminalCommand;
            } else {
                command.add(c);
            }
        } else {
            return new CharacterWriteCommand(c);
        }
        return new NoOpCommand();
    }

    public void setMode(TerminalMode mode) {
        cursorMoveCommandFactory.setMode(mode);
    }
}
