package terminal;

import command.CharacterWriteCommand;
import command.TerminalCommandCreator;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class VermontTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    TerminalCommandCreator commandCreator = new TerminalCommandCreator();
    Vermont vermont;

    @Before
    public void setUp() {
        vermont = new Vermont();
    }

    @Test
    public void testSize() {
        assertThat(vermont.getHeight(), equalTo(24));
        assertThat(vermont.getWidth(), equalTo(80));
    }

    @Test
    public void testCursorPosition() {
        CursorPosition position = new CursorPosition(4, 3);
        vermont.moveCursor(position);
        assertThat(vermont.getCursorPosition(), equalTo(position));
        CursorPosition newPosition = new CursorPosition(5, 6);
        vermont.moveCursor(newPosition);
        assertThat(vermont.getCursorPosition(), equalTo(new CursorPosition(5, 6)));
    }

    @Test
    public void testMovingCursorPositionOutsideSizeIsAnError_tooHigh() {
        thrown.expect(ScreenAccessOutOfBoundsException.class);
        vermont.moveCursor(new CursorPosition(vermont.getHeight(), 0));
    }

    @Test
    public void testMovingCursorPositionOutsideSizeIsAnError_tooLow() {
        thrown.expect(ScreenAccessOutOfBoundsException.class);
        vermont.moveCursor(new CursorPosition(-1, 0));
    }

    @Test
    public void testMovingCursorPositionOutsideSizeIsAnError_tooLeft() {
        thrown.expect(ScreenAccessOutOfBoundsException.class);
        vermont.moveCursor(new CursorPosition(0, -1));
    }

    @Test
    public void testMovingCursorPositionOutsideSizeIsAnError_tooRightMate() {
        thrown.expect(ScreenAccessOutOfBoundsException.class);
        vermont.moveCursor(new CursorPosition(-1, vermont.getWidth()));
    }

    @Test
    public void testWritingACharacterPutsItAtTheCursorLocation() {
        CursorPosition xPosition = new CursorPosition(3, 3);
        CursorPosition yPosition = new CursorPosition(6, 2);
        vermont.moveCursor(xPosition);
        commandCreator.write('X');
        vermont.accept(commandCreator.createCommand());
        vermont.moveCursor(yPosition);
        commandCreator.write('Y');
        vermont.accept(commandCreator.createCommand());
        assertThat(vermont.characterAt(xPosition), equalTo("X"));
        assertThat(vermont.characterAt(yPosition), equalTo("Y"));
    }

    @Test
    public void testExecutingATerminalCommand() throws Exception {
        CharacterWriteCommand command = new CharacterWriteCommand('z');
        CursorPosition home = new CursorPosition(0, 0);
        vermont.moveCursor(home);
        vermont.accept(command);
        assertThat(vermont.characterAt(home), equalTo(String.valueOf('z')));
    }

    @Test
    public void testGettingScreenContentsAsString() {
        vermont.moveCursor(new CursorPosition(10, 10));
        commandCreator.write('c');
        vermont.accept(commandCreator.createCommand());
        vermont.moveCursor(new CursorPosition(10, 11));
        commandCreator.write(' ');
        vermont.accept(commandCreator.createCommand());
        vermont.moveCursor(new CursorPosition(10, 12));
        commandCreator.write('t');
        vermont.accept(commandCreator.createCommand());
        assertThat(vermont.getScreenText(), containsString("c t"));
    }
}
