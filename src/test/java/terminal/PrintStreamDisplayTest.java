package terminal;

import org.junit.Test;
import org.mockito.InOrder;

import java.io.IOException;
import java.io.PrintStream;

import static org.mockito.Mockito.*;

public class PrintStreamDisplayTest {

    @Test
    public void testCreatingADisplayFromABufferDrawsFromTopToBottom() throws IOException {

        char[][] buffer = new char[][]{
                {'X', ' ', ' '},
                {' ', 'X', ' '},
                {' ', ' ', 'X'}
        };

        PrintStream output = mock(PrintStream.class);
        InOrder inOrder = inOrder(output);

        PrintStreamDisplay display = new PrintStreamDisplay(output);

        display.display(buffer);
        inOrder.verify(output).println(new String(buffer[0]));
        inOrder.verify(output).println(new String(buffer[1]));
        inOrder.verify(output).println(new String(buffer[2]));
    }

}
