package terminal;

import org.junit.Test;
import org.mockito.InOrder;

import java.io.IOException;
import java.io.PrintStream;

import static org.mockito.Mockito.*;

public class DisplayTest {

    @Test
    public void testCreatingADisplayFromABufferDrawsFromTopToBottom() throws IOException {

        char[][] buffer = new char[][]{
                {'X', ' ', ' '},
                {' ', 'X', ' '},
                {' ', ' ', 'X'}
        };

        PrintStream output = mock(PrintStream.class);
        InOrder inOrder = inOrder(output);

        Display display = new Display(buffer, output);

        display.display();
        inOrder.verify(output).println(new String(buffer[0]));
        inOrder.verify(output).println(new String(buffer[1]));
        inOrder.verify(output).println(new String(buffer[2]));
    }

    @Test
    public void testDisplayingTheBufferFirstWritesANumberOfBlankLinesEqualToTheNumberOfRowsInTheBufferToClearTheScreen() throws IOException {
        char[][] buffer = new char[][]{
                {'X', ' ', ' '},
                {' ', 'X', ' '},
                {' ', ' ', 'X'}
        };

        PrintStream output = mock(PrintStream.class);

        Display display = new Display(buffer, output);
        InOrder inOrder = inOrder(output);

        display.display();
        inOrder.verify(output, times(3)).println();
        inOrder.verify(output).println(new String(buffer[0]));
        inOrder.verify(output).println(new String(buffer[1]));
        inOrder.verify(output).println(new String(buffer[2]));
    }

}
