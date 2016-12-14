package command;

import org.junit.Before;
import org.junit.Test;
import terminal.Ascii;
import terminal.CursorPosition;
import terminal.VTerminal;
import terminal.Vermont;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.not;
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
        terminal.home();
        terminal.write("X");

        Optional<? extends TerminalCommand> command = commandCreator.write(Ascii.ESC);
        command.ifPresent(terminal);

        assertThat(terminal.characterAt(CursorPosition.HOME), equalTo("X"));
    }

    @Test
    public void testAddingCharactersAfterStartingACommandSequenceReturnsNoOpCommands() throws Exception {
        terminal.home();
        terminal.write("X");

        commandCreator.write(Ascii.ESC).ifPresent(terminal);
        commandCreator.write('[').ifPresent(terminal);

        assertThat(terminal.characterAt(CursorPosition.HOME), equalTo("X"));
    }

    @Test
    public void testCompletingCommandSequenceReturnsACommand() throws Exception {
        terminal.home();
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
    public void testCarriageReturn() throws Exception {
        terminal.moveCursor(new CursorPosition(3, 4));
        commandCreator.write(Ascii.CR).ifPresent(terminal);
        assertThat(terminal.getCursorPosition(), equalTo(new CursorPosition(3, 0)));
    }

    @Test
    public void testNewLine() throws Exception {
        terminal.moveCursor(new CursorPosition(3, 4));
        commandCreator.write(Ascii.LF).ifPresent(terminal);
        assertThat(terminal.getCursorPosition(), equalTo(new CursorPosition(4, 4)));
    }

    @Test
    public void testClearingFromCursorDown() throws Exception {
        VTerminal terminal = new Vermont(4, 4);
        terminal.home();
        commandCreator.write('A').ifPresent(terminal);
        commandCreator.write('B').ifPresent(terminal);
        commandCreator.write('C').ifPresent(terminal);
        terminal.moveCursor(new CursorPosition(1, 2));
        commandCreator.write('D').ifPresent(terminal);
        terminal.moveCursor(new CursorPosition(0, 2));

        char[] clearFromCursorDown = {Ascii.ESC, '[', 'J'};
        for (char c : clearFromCursorDown) {
            commandCreator.write(c).ifPresent(terminal);
        }

        assertThat(terminal.getScreenText(), containsString("A"));
        assertThat(terminal.getScreenText(), containsString("B"));
        assertThat(terminal.getScreenText(), not(containsString("C")));
        assertThat(terminal.getScreenText(), not(containsString("D")));
    }

    @Test
    public void testClearingFromCursorToEndOfRow() throws Exception {
        VTerminal terminal = new Vermont(4, 4);
        terminal.home();
        commandCreator.write('A').ifPresent(terminal);
        commandCreator.write('B').ifPresent(terminal);
        commandCreator.write('C').ifPresent(terminal);
        terminal.moveCursor(new CursorPosition(1, 0));
        commandCreator.write('D').ifPresent(terminal);
        terminal.moveCursor(new CursorPosition(0, 1));

        char[] clearRightSequence = {Ascii.ESC, '[', 'K'};
        for (char c : clearRightSequence) {
            commandCreator.write(c).ifPresent(terminal);
        }
        assertThat(terminal.getScreenText(), containsString("A"));
        assertThat(terminal.getScreenText(), not(containsString("B")));
        assertThat(terminal.getScreenText(), not(containsString("C")));
        assertThat(terminal.getScreenText(), containsString("D"));
    }

    @Test
    public void testIgnoringBoringCommands() {
        /*
        An explanation: move the cursor to a known position. Write a character. Move back to known position (because we
        auto-increment after writing). Execute a bunch of commands that should be ignored. Verify the cursor is where
        we left it (although it could have moved away then back...). Verify the character hasn't changed. Verify that
        we aren't in the process of building a command by writing a character and seeing that it gets written to the
        screen.
         */
        terminal.home();
        commandCreator.write('W').ifPresent(terminal);
        terminal.home();
        char[][] ignorables = {
                {Ascii.ESC, '(', 'B'},
                {Ascii.ESC, ')', '0'},
                {Ascii.ESC, '[', '7', 'm'},
        };
        for (char[] ignorable : ignorables) {
            for (char c : ignorable) {
                commandCreator.write(c).ifPresent(terminal);
            }
        }
        assertThat(terminal.getCursorPosition(), equalTo(CursorPosition.HOME));
        assertThat(terminal.characterAt(CursorPosition.HOME), equalTo("W"));
        commandCreator.write('M').ifPresent(terminal);
        assertThat(terminal.characterAt(CursorPosition.HOME), equalTo("M"));
    }
}