package command;

import org.junit.Test;
import terminal.BlankDisplay;
import locations.Coordinates;
import terminal.VTerminal;
import terminal.Vermont;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CarriageReturnCommandTest {

    @Test
    public void testMovesCursorToStartOfCurrentLine() {
        VTerminal terminal = new Vermont(new BlankDisplay());
        terminal.moveCursor(new Coordinates(1, 2));
        CarriageReturnCommand command = new CarriageReturnCommand();
        terminal.accept(command);

        assertThat(terminal.getCoordinates(), equalTo(new Coordinates(1, 0)));
    }
}
