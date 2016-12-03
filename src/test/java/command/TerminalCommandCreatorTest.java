package command;

import org.junit.Test;
import org.mockito.Mockito;
import terminal.VTerminal;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class TerminalCommandCreatorTest {

    private VTerminal vTerminal = mock(VTerminal.class);

    @Test
    public void testCreatingAnAddCharacterCommand() throws Exception {
        TerminalCommandCreator commandCreator = new TerminalCommandCreator();
        commandCreator.write('a');
        TerminalCommand command = commandCreator.createCommand();
        command.call(vTerminal);
        verify(vTerminal, times(1)).write("a");
    }

    @Test
    public void testCreatingMultipleAddCharacterCommands() throws Exception {
        TerminalCommandCreator commandCreator = new TerminalCommandCreator();
        commandCreator.write('a');
        commandCreator.createCommand().call(vTerminal);
        verify(vTerminal, times(1)).write("a");
        verify(vTerminal, times(0)).write("b");
        Mockito.reset(vTerminal);
        commandCreator.write('b');
        commandCreator.createCommand().call(vTerminal);
        verify(vTerminal, times(0)).write("a");
        verify(vTerminal, times(1)).write("b");
    }
}
