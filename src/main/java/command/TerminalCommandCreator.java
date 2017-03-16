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
    private static TerminalCommand CURSOR_RIGHT = new CursorRightCommand();
    private static TerminalCommand CURSOR_UP = new CursorUpCommand();
    private static TerminalCommand CURSOR_DOWN = new CursorDownCommand();

    private boolean buildingCoordinatesMoveCommand = false;
    private boolean dealingWithTwoCharacterIgnorable;
    private ArrayList<Character> command = new ArrayList<Character>();
    private CursorMoveCommandFactory cursorMoveCommandFactory;

    public TerminalCommandCreator(){
        cursorMoveCommandFactory = new CursorMoveCommandFactory();
        cursorMoveCommandFactory.setMode(TerminalMode.ZERO_BASED);
    }

    public TerminalCommand create(char c) {
        if(c == Ascii.ESC) {
            buildingCoordinatesMoveCommand = true;
        }  else if(dealingWithTwoCharacterIgnorable) {
            command.clear();
            dealingWithTwoCharacterIgnorable = false;
        } else if(buildingCoordinatesMoveCommand) {
            if(c == 'm') {
                command.clear();
                buildingCoordinatesMoveCommand = false;
                return NOOP;
            } else if(c == 'A') {
                command.clear();
                buildingCoordinatesMoveCommand = false;
                return CURSOR_UP;
            } else if(c == 'B') {
                command.clear();
                buildingCoordinatesMoveCommand = false;
                return CURSOR_DOWN;
            }  else if(c == 'C') {
                command.clear();
                buildingCoordinatesMoveCommand = false;
                return CURSOR_RIGHT;
            } else if(c == '(' || c == ')') {
                buildingCoordinatesMoveCommand = false;
                dealingWithTwoCharacterIgnorable = true;
            } else if(c == 'J') {
                command.clear();
                buildingCoordinatesMoveCommand = false;
                return CLEAR_CURSOR_DOWN;
            } else if(c == 'K') {
                command.clear();
                buildingCoordinatesMoveCommand = false;
                return CLEAR_TO_END_OF_ROW;
            } else if(c == 'H' || c == 'f') {
                buildingCoordinatesMoveCommand = false;
                TerminalCommand terminalCommand = cursorMoveCommandFactory.createCommand(command);
                command.clear();
                return terminalCommand;
            } else {
                command.add(c);
            }//replace below with cursorMoveCommandFactory.createCommand(c)
        } else if(c == Ascii.BS) {
            return BS;
        } else if(c == Ascii.CR) {
            return CR;
        } else if(c == Ascii.LF) {
            return NEW_LINE;
        }else {
            return new CharacterWriteCommand(c);
        }
        return NOOP;
    }

    public void setMode(TerminalMode mode) {
        cursorMoveCommandFactory.setMode(mode);
    }
}
