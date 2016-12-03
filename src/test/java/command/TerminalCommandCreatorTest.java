package command;

import org.junit.Before;
import org.junit.Test;
import terminal.CursorPosition;
import terminal.Vermont;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TerminalCommandCreatorTest {

    private TerminalCommandCreator commandCreator;

    @Before
    public void setUp() throws Exception {
        commandCreator = new TerminalCommandCreator();
    }

    @Test
    public void testWritingANonEscapeCharacterCreatesAnAddCharacterCommand() throws Exception {
        TerminalCommand commandA = commandCreator.write('A');
        TerminalCommand commandB = commandCreator.write('B');
        Vermont terminal = new Vermont();
        terminal.accept(commandA);
        assertThat(terminal.characterAt(new CursorPosition(0, 0)), equalTo("A"));
        terminal.accept(commandB);
        assertThat(terminal.characterAt(new CursorPosition(0, 0)), equalTo("B"));
    }
}