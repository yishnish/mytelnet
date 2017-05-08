package terminal;

import org.junit.Test;

import java.util.Date;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class UTCTimePieceTest {

    @Test
    public void testTheTimeIsBasedOnUTC(){
        UTCTimePiece timePiece = new UTCTimePiece();
        long currentUTCTime = new Date().getTime(); //GMT, not UTC but close enough and avoids testing the same method used by the OUT
        assertThat(timePiece.getTimeMillis(), equalTo(currentUTCTime));
    }
}
