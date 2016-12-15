package command;

import org.junit.Before;
import org.junit.Test;
import terminal.CursorPosition;
import terminal.VTerminal;
import terminal.Vermont;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ZeroBasedCursorMoveCommandTest {
    VTerminal terminal;

    @Before
    public void setUp() throws Exception {
        terminal = new Vermont();
    }

    @Test
    public void testCreatingCommandFromCharacters() throws Exception {
        assertThat(terminal.getCursorPosition(), equalTo(CursorPosition.HOME));
        ZeroBasedCursorMoveCommand command = new ZeroBasedCursorMoveCommand(listOf('[', '1', ';', '3'));
        terminal.accept(command);

        assertThat(terminal.getCursorPosition(), equalTo(new CursorPosition(1, 3)));
    }

    @Test
    public void testMoveHomeWithSemicolon() throws Exception {
        terminal.home();
        ZeroBasedCursorMoveCommand commandHSemi = new ZeroBasedCursorMoveCommand(listOf('[', ';', 'H'));
        terminal.accept(commandHSemi);
        assertThat(terminal.getCursorPosition(), equalTo(CursorPosition.HOME));
    }

    @Test
    public void testMoveCommandWithoutSemicolon() throws Exception {
        terminal.home();
        ZeroBasedCursorMoveCommand commandHSemi = new ZeroBasedCursorMoveCommand(listOf('[', 'H'));
        terminal.accept(commandHSemi);
        assertThat(terminal.getCursorPosition(), equalTo(CursorPosition.HOME));
    }

    @Test
    public void testMoveCommandWithCoords() throws Exception {
        terminal.home();
        ZeroBasedCursorMoveCommand commandH = new ZeroBasedCursorMoveCommand(listOf('[', '1', ';', '2'));
        terminal.accept(commandH);
        assertThat(terminal.getCursorPosition(), equalTo(new CursorPosition(1, 2)));
    }

    private ArrayList<Character> listOf(char... chars) {
        ArrayList<Character> characters = new ArrayList<Character>();
        for (char c : chars) {
            characters.add(c);
        }
        return characters;
    }
}
