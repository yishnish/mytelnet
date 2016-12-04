package command;

import org.junit.Test;
import terminal.CursorPosition;
import terminal.Vermont;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CursorMoveCommandTest {
    @Test
    public void testMovingCursor() throws Exception {
        Vermont terminal = new Vermont();
        terminal.moveCursor(CursorPosition.HOME);
        assertThat(terminal.getCursorPosition(), equalTo(CursorPosition.HOME));

        CursorPosition newPosition = new CursorPosition(1, 3);
        CursorMoveCommand command = new CursorMoveCommand(newPosition);

        terminal.accept(command);
        assertThat(terminal.getCursorPosition(), equalTo(newPosition));
    }
}
