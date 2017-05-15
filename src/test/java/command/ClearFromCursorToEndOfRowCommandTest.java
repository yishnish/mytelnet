package command;

import org.junit.Before;
import org.junit.Test;
import terminal.BlankDisplay;
import locations.Coordinates;
import terminal.VTerminal;
import terminal.Vermont;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;

public class ClearFromCursorToEndOfRowCommandTest {
    VTerminal terminal;

    @Before
    public void setUp() throws Exception {
        terminal = new Vermont(4, 4, new BlankDisplay());
    }

    @Test
    public void testClearFromCursorToEndOfLine() throws Exception {
        terminal.home();
        terminal.write('A');
        terminal.write('B');
        terminal.write('C');
        terminal.moveCursor(new Coordinates(1, 0));
        terminal.write('D');
        terminal.moveCursor(new Coordinates(0, 1));
        terminal.accept(new ClearFromCursorToEndOfRowCommand());

        assertThat(terminal.getBufferAsString(), containsString("A"));
        assertThat(terminal.getBufferAsString(), not(containsString("B")));
        assertThat(terminal.getBufferAsString(), not(containsString("C")));
        assertThat(terminal.getBufferAsString(), containsString("D"));
    }
}
