package command;

import org.junit.Before;
import org.junit.Test;
import terminal.Ascii;
import terminal.CursorPosition;
import terminal.Vermont;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class TerminalCommandCreatorTest {

    private Vermont terminal;

    private TerminalCommandCreator commandCreator;

    @Before
    public void setUp() throws Exception {
        commandCreator = new TerminalCommandCreator();
        terminal = new Vermont();
    }

    @Test
    public void testWritingANonEscapeCharacterCreatesAnAddCharacterCommand() throws Exception {
        Optional<? extends TerminalCommand> command = commandCreator.write('S');
        command.ifPresent(terminal);
        assertThat(terminal.characterAt(CursorPosition.HOME), equalTo("S"));
    }

    @Test
    public void testWritingAPartialCommandSequenceGivesANoOpCommand() throws Exception {
        terminal.moveCursor(CursorPosition.HOME);
        terminal.write("X");

        Optional<? extends TerminalCommand> command = commandCreator.write(Ascii.ESC);
        command.ifPresent(terminal);

        assertThat(terminal.characterAt(CursorPosition.HOME), equalTo("X"));
    }

    @Test
    public void testAddingCharactersAfterStartingACommandSequenceReturnsNoOpCommands() throws Exception {
        terminal.moveCursor(CursorPosition.HOME);
        terminal.write("X");

        commandCreator.write(Ascii.ESC).ifPresent(terminal);
        commandCreator.write('[').ifPresent(terminal);

        assertThat(terminal.characterAt(CursorPosition.HOME), equalTo("X"));
    }

    @Test
    public void testCompletingCommandSequenceReturnsACommand() throws Exception {
        terminal.moveCursor(CursorPosition.HOME);
        terminal.write("X");

        commandCreator.write(Ascii.ESC).ifPresent(terminal);
        commandCreator.write('[').ifPresent(terminal);
        commandCreator.write('1').ifPresent(terminal);
        commandCreator.write(';').ifPresent(terminal);
        commandCreator.write('2').ifPresent(terminal);
        commandCreator.write('H').ifPresent(terminal);

        commandCreator.write('Y').ifPresent(terminal);

        assertThat(terminal.characterAt(CursorPosition.HOME), equalTo("X"));
        assertThat(terminal.characterAt(new CursorPosition(1, 2)), equalTo("Y"));
    }

    @Test
    public void testCarriageReturnNewLine() throws Exception {


    }

    @Test
    public void testIgnoringBoringCommands() {
        commandCreator.write(Ascii.ESC).ifPresent(terminal);
        commandCreator.write('[').ifPresent(terminal);
        commandCreator.write('(').ifPresent(terminal);
        commandCreator.write('B').ifPresent(terminal);
        assertThat(true, equalTo(true)); //just testing that nothing is thrown
    }
}