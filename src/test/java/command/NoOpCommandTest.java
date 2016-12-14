package command;

import org.junit.Test;
import terminal.CursorPosition;
import terminal.VTerminal;
import terminal.Vermont;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class NoOpCommandTest {

    @Test
    public void testDoesNothing() throws Exception {
        VTerminal vermont = new Vermont();
        CursorPosition cp = vermont.getCursorPosition();
        String screenText = vermont.getScreenText();
        (new NoOpCommand()).call(vermont);
        assertThat(vermont.getCursorPosition(), equalTo(cp));
        assertThat(vermont.getScreenText(), equalTo(screenText));
    }
}
