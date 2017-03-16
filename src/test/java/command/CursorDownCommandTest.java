package command;

import org.junit.Test;
import terminal.BlankDisplay;
import terminal.CursorPosition;
import terminal.Vermont;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CursorDownCommandTest {

    @Test
    public void testMovingTheCursorDown() throws Exception {
        Vermont terminal = new Vermont(new BlankDisplay());
        terminal.home();

        CursorDownCommand command = new CursorDownCommand();
        command.call(terminal);
        
        assertThat(terminal.getCursorPosition(), equalTo(new CursorPosition(1, 0)));
    }

}