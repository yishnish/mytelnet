package terminal;

import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;

public class UTCTimePieceTest {

    @Test
    public void testTheTimeIsBasedOnUTC(){
        UTCTimePiece timePiece = new UTCTimePiece();
        long currentUTCTime = new Date().getTime(); //GMT, not UTC but close enough and avoids testing the same method used by the OUT
        assertEquals(timePiece.getTimeMillis(), currentUTCTime, 10);
    }
}
