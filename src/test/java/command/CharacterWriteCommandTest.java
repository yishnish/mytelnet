package command;

import org.junit.Test;
import terminal.CursorPosition;
import terminal.Vermont;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CharacterWriteCommandTest {

    @Test
    public void testCommandsTerminalToWriteACharacter() throws Exception {
        Vermont terminal = new Vermont();
        terminal.moveCursor(CursorPosition.HOME);
        CharacterWriteCommand command = new CharacterWriteCommand('L');

        command.call(terminal);
        assertThat(terminal.characterAt(CursorPosition.HOME), equalTo("L"));
    }
}