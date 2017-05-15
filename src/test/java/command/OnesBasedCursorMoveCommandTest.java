package command;

import org.junit.Test;
import locations.Coordinates;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class OnesBasedCursorMoveCommandTest extends CursorMoveCommandTest {

    @Test
    public void testCreatingCommandFromCharactersConvertsRowAndColumnValuesToZeroBasedValues() throws Exception {
        terminal.home();
        OnesBasedCursorMoveCommand command = new OnesBasedCursorMoveCommand(listOf('[', '1', ';', '3'));
        terminal.accept(command);

        assertThat(terminal.getCoordinates(), equalTo(new Coordinates(0, 2)));
    }

    @Override
    protected CursorMoveCommand moveCommandFor(ArrayList<Character> commandSequence) {
        return new OnesBasedCursorMoveCommand(commandSequence);
    }
}
