package command;

import org.junit.Test;
import terminal.BlankDisplay;
import locations.Coordinates;
import terminal.VTerminal;
import terminal.Vermont;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class NewLineCommandTest {

    @Test
    public void testMovesCursorToCurrentColumnOnNextLine() {
        VTerminal terminal = new Vermont(new BlankDisplay());
        terminal.moveCursor(new Coordinates(1, 2));
        NewLineCommand command = new NewLineCommand();
        terminal.accept(command);

        assertThat(terminal.getCoordinates(), equalTo(new Coordinates(2, 2)));
    }
}
