package command;

import org.junit.Test;
import terminal.BlankDisplay;
import locations.Coordinates;
import terminal.Vermont;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CharacterWriteCommandTest {

    @Test
    public void testCommandsTerminalToWriteACharacter() throws Exception {
        Vermont terminal = new Vermont(new BlankDisplay());
        terminal.home();
        CharacterWriteCommand command = new CharacterWriteCommand('L');

        command.call(terminal);
        assertThat(terminal.characterAt(Coordinates.HOME), equalTo('L'));
    }
}