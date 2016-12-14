package parse;

import command.TerminalCommand;
import org.junit.Before;
import org.junit.Test;
import terminal.CursorPosition;
import terminal.VTerminal;
import terminal.Vermont;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CommandParserTest {
    VTerminal terminal;
    @Before
    public void setUp() throws Exception {
        terminal = new Vermont();
    }

    @Test
    public void testParseMoveHomeWithSemicolon() throws Exception {
        terminal.moveCursor(CursorPosition.HOME);
        TerminalCommand commandHSemi = CommandParser.parseCommand(listOf('[', ';', 'H'));
        terminal.accept(commandHSemi);
        assertThat(terminal.getCursorPosition(), equalTo(CursorPosition.HOME));
    }

    @Test
    public void testParseMoveCommandWithoutSemicolon() throws Exception {
        terminal.moveCursor(CursorPosition.HOME);
        TerminalCommand commandHSemi = CommandParser.parseCommand(listOf('[', 'H'));
        terminal.accept(commandHSemi);
        assertThat(terminal.getCursorPosition(), equalTo(CursorPosition.HOME));
    }

    @Test
    public void testParseMoveCommandWithCoords() throws Exception {
        terminal.moveCursor(CursorPosition.HOME);
        TerminalCommand commandH = CommandParser.parseCommand(listOf('[', '1', ';', '2'));
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
