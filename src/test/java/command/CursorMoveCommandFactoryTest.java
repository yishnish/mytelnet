package command;

import locations.Coordinates;
import org.junit.Test;
import terminal.*;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CursorMoveCommandFactoryTest {

    @Test
    public void testCreatingCursorMoveCommandsInZeroBasedModeIsZeroBased() throws Exception {
        VTerminal terminal = new Vermont(new BlankDisplay());
        CursorMoveCommandFactory commandFactory = new CursorMoveCommandFactory();
        commandFactory.setMode(TerminalMode.ZERO_BASED);
        CursorMoveCommand command = commandFactory.createCommand(listOf('[', '1', ';', '1'));
        terminal.accept(command);

        Coordinates Coordinates = terminal.getCoordinates();
        assertThat(Coordinates.getRow(), equalTo(1));
        assertThat(Coordinates.getColumn(), equalTo(1));
    }

    @Test
    public void testCreatingCursorMoveCommandsInOnesBasedModeIsOnesBased() throws Exception {
        VTerminal terminal = new Vermont(new BlankDisplay());
        CursorMoveCommandFactory commandFactory = new CursorMoveCommandFactory();
        commandFactory.setMode(TerminalMode.ONES_BASED);
        CursorMoveCommand command = commandFactory.createCommand(listOf('[', '1', ';', '1'));
        terminal.accept(command);

        Coordinates Coordinates = terminal.getCoordinates();
        assertThat(Coordinates.getRow(), equalTo(0));
        assertThat(Coordinates.getColumn(), equalTo(0));
    }

    private ArrayList<Character> listOf(char... chars) {
        ArrayList<Character> characters = new ArrayList<Character>();
        for (char c : chars) {
            characters.add(c);
        }
        return characters;
    }
}
