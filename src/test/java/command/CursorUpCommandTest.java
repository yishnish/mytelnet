package command;

import org.junit.Test;
import terminal.BlankDisplay;
import terminal.CursorPosition;
import terminal.Vermont;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CursorUpCommandTest {

    @Test
    public void testMovingTheCursorUp() throws Exception {
        Vermont terminal = new Vermont(new BlankDisplay());
        terminal.moveCursor(new CursorPosition(1, 0));

        CursorUpCommand command = new CursorUpCommand();
        command.call(terminal);
        
        assertThat(terminal.getCursorPosition(), equalTo(CursorPosition.HOME));
    }

    @Test
    public void testMovingTheCursorUpDoesNothingIfAlreadyAtTheTopOfTheScreen() throws Exception {
        Vermont terminal = new Vermont(new BlankDisplay());
        terminal.home();

        CursorUpCommand command = new CursorUpCommand();
        command.call(terminal);

        assertThat(terminal.getCursorPosition(), equalTo(CursorPosition.HOME));
    }
}