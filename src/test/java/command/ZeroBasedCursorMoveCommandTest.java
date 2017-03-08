package command;

import org.junit.Test;
import terminal.CursorPosition;

import java.util.ArrayList;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ZeroBasedCursorMoveCommandTest extends CursorMoveCommandTest{

    @Test
    public void testCreatingCommandFromCharacters() throws Exception {
        terminal.home();
        ZeroBasedCursorMoveCommand command = new ZeroBasedCursorMoveCommand(listOf('[', '1', ';', '3'));
        terminal.accept(command);

        assertThat(terminal.getCursorPosition(), equalTo(new CursorPosition(1, 3)));
    }

    @Override
    protected CursorMoveCommand moveCommandFor(ArrayList<Character> commandSequence) {
        return new ZeroBasedCursorMoveCommand(commandSequence);
    }
}
