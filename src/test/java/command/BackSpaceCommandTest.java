package command;

import org.junit.Test;
import terminal.BlankDisplay;
import locations.Coordinates;
import terminal.Vermont;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class BackSpaceCommandTest {

    @Test
    public void testMovingTheCursorLeftOneSpot() throws Exception {
        Vermont terminal = new Vermont(new BlankDisplay());
        terminal.moveCursor(new Coordinates(0, 1));

        BackSpaceCommand command = new BackSpaceCommand();
        command.call(terminal);
        
        assertThat(terminal.getCoordinates(), equalTo(Coordinates.HOME));
    }

}