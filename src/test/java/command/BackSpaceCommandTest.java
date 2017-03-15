package command;

import org.junit.Test;
import terminal.Ascii;
import terminal.BlankDisplay;
import terminal.CursorPosition;
import terminal.Vermont;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BackSpaceCommandTest {

    @Test
    public void testMovingTheCursorLeftOneSpot() throws Exception {
        Vermont terminal = new Vermont(new BlankDisplay());
        terminal.moveCursor(new CursorPosition(0, 1));

        BackSpaceCommand command = new BackSpaceCommand();
        command.call(terminal);
        
        assertThat(terminal.getCursorPosition(), equalTo(CursorPosition.HOME));
    }

    @Test
    public void testMovingTheCursorLeftOneSpotDoesNothingIfAlreadyAllTheWayLeft() throws Exception {
        Vermont terminal = new Vermont(new BlankDisplay());
        terminal.home();

        BackSpaceCommand command = new BackSpaceCommand();
        command.call(terminal);

        assertThat(terminal.getCursorPosition(), equalTo(CursorPosition.HOME));
    }
}