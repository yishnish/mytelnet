package command;

import terminal.Ascii;
import terminal.TerminalMode;

import java.util.ArrayList;

public class TerminalCommandCreator {

    private static TerminalCommand NOOP = new NoOpCommand();
    private static TerminalCommand CR = new CarriageReturnCommand();
    private static TerminalCommand NEW_LINE = new NewLineCommand();
    private static TerminalCommand CLEAR_CURSOR_DOWN = new ClearFromCursorDownCommand();
    private static TerminalCommand CLEAR_TO_END_OF_ROW = new ClearFromCursorToEndOfRowCommand();
    private static TerminalCommand BS = new BackSpaceCommand();

    private boolean buildingCommand = false;
    private boolean dealingWithTwoCharacterIgnorable;
    private ArrayList<Character> command = new ArrayList<Character>();
    private CursorMoveCommandFactory cursorMoveCommandFactory;

    public TerminalCommandCreator(){
        cursorMoveCommandFactory = new CursorMoveCommandFactory();
        cursorMoveCommandFactory.setMode(TerminalMode.ZERO_BASED);
    }

    public TerminalCommand create(char c) {
        if(c == Ascii.ESC) {
            buildingCommand = true;
        } else if(c == Ascii.BS) {
            return BS;
        } else if(c == Ascii.CR) {
            return CR;
        } else if(c == Ascii.LF) {
            return NEW_LINE;
        } else if(dealingWithTwoCharacterIgnorable) {
            command.clear();
            dealingWithTwoCharacterIgnorable = false;
        } else if(buildingCommand) {
            if(c == 'm') {
                command.clear();
                buildingCommand = false;
                return NOOP;
            } else if(c == 'A') {
                command.clear();
                buildingCommand = false;
                return NOOP;
            }  else if(c == 'C') {
                command.clear();
                buildingCommand = false;
                return NOOP;
            } else if(c == '(' || c == ')') {
                buildingCommand = false;
                dealingWithTwoCharacterIgnorable = true;
            } else if(c == 'J') {
                command.clear();
                buildingCommand = false;
                return CLEAR_CURSOR_DOWN;
            } else if(c == 'K') {
                command.clear();
                buildingCommand = false;
                return CLEAR_TO_END_OF_ROW;
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
        return NOOP;
    }

    public void setMode(TerminalMode mode) {
        cursorMoveCommandFactory.setMode(mode);
    }
}
