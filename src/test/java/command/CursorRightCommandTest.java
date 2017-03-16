package command;

import org.junit.Test;
import terminal.BlankDisplay;
import terminal.CursorPosition;
import terminal.Vermont;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CursorRightCommandTest {

    @Test
    public void testMovingTheCursorRight() throws Exception {
        Vermont terminal = new Vermont(new BlankDisplay());
        terminal.home();

        CursorRightCommand command = new CursorRightCommand();
        command.call(terminal);
        
        assertThat(terminal.getCursorPosition(), equalTo(new CursorPosition(0, 1)));
    }

}