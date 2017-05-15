package terminal;

import org.junit.Before;
import org.junit.Test;

import static locations.Coordinates.coordinates;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ScreenBufferTest {

    BufferMetadata metadata;
    long timestamp = 10000L;

    @Before
    public void setUp() {
        metadata = new BufferMetadata(timestamp);
    }

    @Test
    public void testContainsLastUpdatedTimestampMetadata() {
        ScreenBuffer screenBuffer = new ScreenBuffer(null, metadata);

        assertThat(screenBuffer.getTimestamp(), equalTo(timestamp));
    }
    @Test
    public void testAccessingBufferElementsViaCursorPosition() {
        char character = '@';
        ScreenBuffer screenBuffer = new ScreenBuffer(new char[][]{{character}});

        assertThat(screenBuffer.charAt(coordinates(0, 0)), equalTo(character));
    }

}
