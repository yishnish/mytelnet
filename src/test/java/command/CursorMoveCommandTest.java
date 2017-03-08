package command;

import org.junit.Before;
import org.junit.Test;
import terminal.BlankDisplay;
import terminal.CursorPosition;
import terminal.VTerminal;
import terminal.Vermont;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public abstract class CursorMoveCommandTest {
    VTerminal terminal;

    protected abstract CursorMoveCommand moveCommandFor(ArrayList<Character> commandSequence);

    @Before
    public void setUp() throws Exception {
        terminal = new Vermont(new BlankDisplay());
    }

    @Test
    public void testMoveHomeWithSemicolon() throws Exception {
        terminal.moveCursor(new CursorPosition(1, 1));
        CursorMoveCommand commandHSemi = moveCommandFor(listOf('[', ';', 'H'));
        terminal.accept(commandHSemi);
        assertThat(terminal.getCursorPosition(), equalTo(CursorPosition.HOME));
    }

    @Test
    public void testMoveHomeCommandWithoutSemicolon() throws Exception {
        terminal.moveCursor(new CursorPosition(1, 1));
        CursorMoveCommand commandHSemi = moveCommandFor(listOf('[', 'H'));
        terminal.accept(commandHSemi);
        assertThat(terminal.getCursorPosition(), equalTo(CursorPosition.HOME));
    }

    protected ArrayList<Character> listOf(char... chars) {
        ArrayList<Character> characters = new ArrayList<Character>();
        for (char c : chars) {
            characters.add(c);
        }
        return characters;
    }

}
