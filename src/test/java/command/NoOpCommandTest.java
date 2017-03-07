package command;

import org.junit.Test;
import terminal.BlankDisplay;
import terminal.CursorPosition;
import terminal.VTerminal;
import terminal.Vermont;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class NoOpCommandTest {

    @Test
    public void testDoesNothing() throws Exception {
        VTerminal vermont = new Vermont(new BlankDisplay());
        CursorPosition cp = vermont.getCursorPosition();
        String screenText = vermont.getBufferAsString();
        (new NoOpCommand()).call(vermont);
        assertThat(vermont.getCursorPosition(), equalTo(cp));
        assertThat(vermont.getBufferAsString(), equalTo(screenText));
    }
}
