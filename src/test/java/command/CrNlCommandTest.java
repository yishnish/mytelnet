package command;

import org.junit.Test;
import terminal.CursorPosition;
import terminal.VTerminal;
import terminal.Vermont;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CrNlCommandTest {

    @Test
    public void testMovesCursorToStartOfNextLine() {
        VTerminal terminal = new Vermont();
        terminal.moveCursor(new CursorPosition(1, 2));
        CrNlCommand command = new CrNlCommand();
        terminal.accept(command);

        assertThat(terminal.getCursorPosition(), equalTo(new CursorPosition(2, 0)));
    }
}
