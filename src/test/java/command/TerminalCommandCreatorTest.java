package command;

import org.junit.Before;
import org.junit.Test;
import terminal.*;

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
        terminal = new Vermont(new BlankDisplay());
    }

    @Test
    public void testWritingANonEscapeCharacterCreatesAnAddCharacterCommand() throws Exception {
        TerminalCommand command = commandCreator.write('S');
        command.call(terminal);
        assertThat(terminal.characterAt(CursorPosition.HOME), equalTo('S'));
    }

    @Test
    public void testWritingAPartialCommandSequenceGivesANoOpCommand() throws Exception {
        terminal.home();
        terminal.write('X');

        TerminalCommand command = commandCreator.write(Ascii.ESC);
        command.call(terminal);

        assertThat(terminal.characterAt(CursorPosition.HOME), equalTo('X'));
    }

    @Test
    public void testAddingCharactersAfterStartingACommandSequenceReturnsNoOpCommands() throws Exception {
        terminal.home();
        terminal.write('X');

        commandCreator.write(Ascii.ESC).call(terminal);
        commandCreator.write('[').call(terminal);

        assertThat(terminal.characterAt(CursorPosition.HOME), equalTo('X'));
    }

    @Test
    public void testCompletingCommandSequenceReturnsACommand() throws Exception {
        terminal.home();
        terminal.write('X');

        commandCreator.write(Ascii.ESC).call(terminal);
        commandCreator.write('[').call(terminal);
        commandCreator.write('1').call(terminal);
        commandCreator.write(';').call(terminal);
        commandCreator.write('2').call(terminal);
        commandCreator.write('H').call(terminal);

        commandCreator.write('Y').call(terminal);

        assertThat(terminal.characterAt(CursorPosition.HOME), equalTo('X'));
        assertThat(terminal.characterAt(new CursorPosition(1, 2)), equalTo('Y'));
    }

    @Test
    public void testMovingTheCursorInZeroBasedMode() throws Exception {
        terminal.home();
        terminal.write('X');

        commandCreator.setMode(TerminalMode.ZERO_BASED);
        commandCreator.write(Ascii.ESC).call(terminal);
        commandCreator.write('[').call(terminal);
        commandCreator.write('1').call(terminal);
        commandCreator.write(';').call(terminal);
        commandCreator.write('2').call(terminal);
        commandCreator.write('H').call(terminal);

        commandCreator.write('Y').call(terminal);

        assertThat(terminal.characterAt(CursorPosition.HOME), equalTo('X'));
        assertThat(terminal.characterAt(new CursorPosition(1, 2)), equalTo('Y'));
    }

    @Test
    public void testMovingTheCursorInOnesBasedMode() throws Exception {
        terminal.home();
        terminal.write('X');

        commandCreator.setMode(TerminalMode.ONES_BASED);
        commandCreator.write(Ascii.ESC).call(terminal);
        commandCreator.write('[').call(terminal);
        commandCreator.write('1').call(terminal);
        commandCreator.write(';').call(terminal);
        commandCreator.write('2').call(terminal);
        commandCreator.write('H').call(terminal);

        commandCreator.write('Y').call(terminal);

        assertThat(terminal.characterAt(CursorPosition.HOME), equalTo('X'));
        assertThat(terminal.characterAt(new CursorPosition(0, 1)), equalTo('Y'));
        assertThat(terminal.characterAt(new CursorPosition(1, 2)), equalTo(Ascii.MIN));
    }

    @Test
    public void testCarriageReturn() throws Exception {
        terminal.moveCursor(new CursorPosition(3, 4));
        commandCreator.write(Ascii.CR).call(terminal);
        assertThat(terminal.getCursorPosition(), equalTo(new CursorPosition(3, 0)));
    }

    @Test
    public void testNewLine() throws Exception {
        terminal.moveCursor(new CursorPosition(3, 4));
        commandCreator.write(Ascii.LF).call(terminal);
        assertThat(terminal.getCursorPosition(), equalTo(new CursorPosition(4, 4)));
    }

    @Test
    public void testClearingFromCursorDown() throws Exception {
        VTerminal terminal = new Vermont(4, 4, new BlankDisplay());
        terminal.home();
        commandCreator.write('A').call(terminal);
        commandCreator.write('B').call(terminal);
        commandCreator.write('C').call(terminal);
        terminal.moveCursor(new CursorPosition(1, 2));
        commandCreator.write('D').call(terminal);
        terminal.moveCursor(new CursorPosition(0, 2));

        char[] clearFromCursorDown = {Ascii.ESC, '[', 'J'};
        for (char c : clearFromCursorDown) {
            commandCreator.write(c).call(terminal);
        }

        assertThat(terminal.getBufferAsString(), containsString("A"));
        assertThat(terminal.getBufferAsString(), containsString("B"));
        assertThat(terminal.getBufferAsString(), not(containsString("C")));
        assertThat(terminal.getBufferAsString(), not(containsString("D")));
    }

    @Test
    public void testClearingFromCursorToEndOfRow() throws Exception {
        VTerminal terminal = new Vermont(4, 4, new BlankDisplay());
        terminal.home();
        commandCreator.write('A').call(terminal);
        commandCreator.write('B').call(terminal);
        commandCreator.write('C').call(terminal);
        terminal.moveCursor(new CursorPosition(1, 0));
        commandCreator.write('D').call(terminal);
        terminal.moveCursor(new CursorPosition(0, 1));

        char[] clearRightSequence = {Ascii.ESC, '[', 'K'};
        for (char c : clearRightSequence) {
            commandCreator.write(c).call(terminal);
        }
        assertThat(terminal.getBufferAsString(), containsString("A"));
        assertThat(terminal.getBufferAsString(), not(containsString("B")));
        assertThat(terminal.getBufferAsString(), not(containsString("C")));
        assertThat(terminal.getBufferAsString(), containsString("D"));
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
        commandCreator.write('W').call(terminal);
        terminal.home();
        char[][] ignorables = {
                {Ascii.ESC, '(', 'B'},
                {Ascii.ESC, ')', '0'},
                {Ascii.ESC, '[', '7', 'm'},
                {Ascii.ESC, '[', 'C'},
                {Ascii.ESC, '[', 'A'},
        };
        for (char[] ignorable : ignorables) {
            for (char c : ignorable) {
                commandCreator.write(c).call(terminal);
            }
        }
        assertThat(terminal.getCursorPosition(), equalTo(CursorPosition.HOME));
        assertThat(terminal.characterAt(CursorPosition.HOME), equalTo('W'));
        commandCreator.write('M').call(terminal);
        assertThat(terminal.characterAt(CursorPosition.HOME), equalTo('M'));
    }
}