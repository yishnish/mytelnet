package terminal;

import command.CarriageReturnCommand;
import command.CharacterWriteCommand;
import command.TerminalCommand;
import locations.Coordinates;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class VermontTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    Vermont vermont;

    @Before
    public void setUp() {
        vermont = new Vermont(new BlankDisplay());
    }

    @Test
    public void testSize() {
        assertThat(vermont.getHeight(), equalTo(24));
        assertThat(vermont.getWidth(), equalTo(80));
    }

    @Test
    public void testCoordinates() {
        Coordinates position = new Coordinates(4, 3);
        vermont.moveCursor(position);
        assertThat(vermont.getCoordinates(), equalTo(position));
        Coordinates newPosition = new Coordinates(5, 6);
        vermont.moveCursor(newPosition);
        assertThat(vermont.getCoordinates(), equalTo(new Coordinates(5, 6)));
    }

    @Test
    public void testMovingHome() {
        vermont.moveCursor(new Coordinates(4, 4));
        vermont.home();
        assertThat(vermont.getCoordinates(), equalTo(Coordinates.HOME));
    }

    @Test
    public void testMovingCoordinatesOutsideSizeIsAnError_tooLow() {
        thrown.expect(ScreenAccessOutOfBoundsException.class);
        vermont.moveCursor(new Coordinates(vermont.getHeight(), 0));
    }

    @Test
    public void testMovingCoordinatesOutsideSizeIsAnError_tooHigh() {
        thrown.expect(ScreenAccessOutOfBoundsException.class);
        vermont.moveCursor(new Coordinates(-1, 0));
    }

    @Test
    public void testMovingCoordinatesOutsideSizeIsAnError_tooLeft() {
        thrown.expect(ScreenAccessOutOfBoundsException.class);
        vermont.moveCursor(new Coordinates(0, -1));
    }

    @Test
    public void testMovingCoordinatesOutsideSizeIsAnError_tooRightMate() {
        thrown.expect(ScreenAccessOutOfBoundsException.class);
        vermont.moveCursor(new Coordinates(0, vermont.getWidth()));
    }

    @Test
    public void testAdvancingCursorWithinBounds() throws Exception {
        vermont.home();
        vermont.advanceCursor();
        assertThat(vermont.getCoordinates(), equalTo(new Coordinates(0, 1)));
    }

    @Test
    public void testTryingToAdvanceCoordinatesOutOfBoundsDoesNotMoveCursor() throws Exception {
        Coordinates edgeOfScreen = new Coordinates(0, vermont.getWidth() - 1);
        vermont.moveCursor(edgeOfScreen);
        vermont.advanceCursor();
        assertThat(vermont.getCoordinates(), equalTo(edgeOfScreen));
    }

    @Test
    public void testWritingACharacterPutsItAtTheCursorCoordinates() {
        Coordinates xPosition = new Coordinates(3, 3);
        Coordinates yPosition = new Coordinates(6, 2);
        vermont.moveCursor(xPosition);
        vermont.accept(new CharacterWriteCommand('X'));
        vermont.moveCursor(yPosition);
        vermont.accept(new CharacterWriteCommand('Y'));
        assertThat(vermont.characterAt(xPosition), equalTo('X'));
        assertThat(vermont.characterAt(yPosition), equalTo('Y'));
    }

    @Test
    public void testExecutingATerminalCommand() throws Exception {
        CharacterWriteCommand command = new CharacterWriteCommand('z');
        vermont.home();
        vermont.accept(command);
        assertThat(vermont.characterAt(Coordinates.HOME), equalTo('z'));
    }

    @Test
    public void testGettingScreenContentsAsString() {
        vermont.moveCursor(new Coordinates(10, 10));
        vermont.accept(new CharacterWriteCommand('c'));
        vermont.moveCursor(new Coordinates(10, 11));
        vermont.accept(new CharacterWriteCommand(' '));
        vermont.moveCursor(new Coordinates(10, 12));
        vermont.accept(new CharacterWriteCommand('t'));
        assertThat(vermont.getBufferAsString(), containsString("c t"));
    }

    @Test
    public void testCarriageReturn() throws Exception {
        vermont.moveCursor(new Coordinates(1, 2));
        vermont.carriageReturn();
        assertThat(vermont.getCoordinates(), equalTo(new Coordinates(1, 0)));
    }

    @Test
    public void testNewLine() throws Exception {
        vermont.moveCursor(new Coordinates(1, 2));
        vermont.newLine();
        assertThat(vermont.getCoordinates(), equalTo(new Coordinates(2, 2)));
    }

    @Test
    public void testCarriageReturnDoesntMoveCursorBelowLastLine_ForConvenienceReallyThisShouldMaybeScroll() {
        vermont.moveCursor(new Coordinates(vermont.getHeight() - 1, 1));
        vermont.carriageReturn();
        assertThat(vermont.getCoordinates(), equalTo(new Coordinates(vermont.getHeight() - 1, 0)));
    }

    @Test
    public void testClearingLineFromCursorRight() throws Exception {
        VTerminal vermont = new Vermont(4, 4, new BlankDisplay());
        vermont.home();
        vermont.write('A');
        vermont.write('B');
        vermont.write('C');
        vermont.moveCursor(new Coordinates(0, 1));
        vermont.clearFromCursorToEndOfRow();
        assertThat(vermont.getBufferAsString(), containsString("A"));
        assertThat(vermont.getBufferAsString(), not(containsString("B")));
        assertThat(vermont.getBufferAsString(), not(containsString("C")));
    }

    @Test
    public void testAccessToTheScreenBuffer() throws Exception {
        vermont.home();
        vermont.write('X');
        assertThat(vermont.getScreenBuffer().charAt(Coordinates.HOME), equalTo('X'));
    }

    @Test
    public void testFetchingTheScreenBufferWaitsForWritesToTheBufferToFinish() throws InterruptedException {
        vermont.home();
        vermont.write('G');

        final char FINAL_VALUE = 'U';

        final AtomicBoolean passed = new AtomicBoolean(false);

        Thread write = new Thread(new Runnable() {
            public void run() {
                vermont.accept(new TerminalCommand() {
                    public void call(VTerminal terminal) {
                        try {
                            Thread.sleep(100L);
                            terminal.home();
                            terminal.accept(new CharacterWriteCommand(FINAL_VALUE));
                        } catch (InterruptedException e) {
                            throw new AssertionError("Interrupted while waiting to or writing to the VTerminal");
                        }

                    }
                });
            }
        });

        Thread fetch = new Thread(new Runnable() {
            public void run() {
                char actual = vermont.getScreenBuffer().charAt(Coordinates.HOME);
                if (actual == FINAL_VALUE) {
                    passed.set(true);
                }
            }
        });

        write.start();
        fetch.start();
        fetch.join();
        assertTrue(passed.get());
    }

    @Test
    public void testDisplayIsNotifiedWhenAcceptingTerminalCommand() throws IOException {
        Display display = mock(Display.class);
        Vermont vermont = new Vermont(display);
        vermont.accept(new CarriageReturnCommand());
        verify(display).display(vermont.getScreenBuffer());
    }

    @Test
    public void testTimestampRecordsWhenTheTerminalWasLastUpdated() {
        TimePiece timePiece = mock(TimePiece.class);
        long currentTime = 100L;

        Mockito.when(timePiece.getTimeMillis()).thenReturn(currentTime).thenReturn(currentTime + 1);
        Vermont vermont = new Vermont(1, 1, new BlankDisplay(), timePiece);
        vermont.accept(new CarriageReturnCommand());

        assertThat(vermont.getLastUpdateTime(), equalTo(currentTime));
    }

    @Test
    public void testGettingTimeElapsedSinceLastChange() {
        TimePiece timePiece = mock(TimePiece.class);
        long updateTime = 100L;
        long now = updateTime + 1L;

        Mockito.when(timePiece.getTimeMillis()).thenReturn(updateTime).thenReturn(now);
        Vermont vermont = new Vermont(1, 1, new BlankDisplay(), timePiece);
        vermont.accept(new CarriageReturnCommand());

        assertThat(vermont.unchangedFor(), equalTo(now - updateTime));
    }
}
