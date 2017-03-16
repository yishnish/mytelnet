package terminal;


import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class CursorPositionTest {

    @Test
    public void testGettingCursorPositionToTheLeft() {
        CursorPosition originalPosition = new CursorPosition(0, 1);
        CursorPosition toTheLeft = originalPosition.toTheLeft();

        assertThat(toTheLeft.getRow(), equalTo(originalPosition.getRow()));
        assertThat(toTheLeft.getCol(), equalTo(originalPosition.getCol() - 1));
    }

    @Test
    public void testGettingCursorPositionDirectlyAbove() {
        CursorPosition cursorPosition = new CursorPosition(1, 1);
        CursorPosition upOne = cursorPosition.upOne();

        assertThat(upOne.getRow(), equalTo(cursorPosition.getRow() -1));
        assertThat(upOne.getCol(), equalTo(cursorPosition.getCol()));
    }

}