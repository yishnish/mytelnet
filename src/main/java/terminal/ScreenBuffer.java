package terminal;

import locations.Coordinates;

import java.util.Arrays;
import java.util.Objects;

public class ScreenBuffer {
    private final char[][] screenData;
    private final BufferMetadata metaData;

    public ScreenBuffer(char[][] screenData) {
        this(screenData, new BufferMetadata(0));
    }

    public ScreenBuffer(char[][] screenData, BufferMetadata metaData) {
        this.screenData =screenData;
        this.metaData = metaData;
    }

    public char charAt(Coordinates coordinates) {
        return screenData[coordinates.getRow()][coordinates.getColumn()];
    }

    public long getTimestamp() {
        return metaData.getTimestamp();
    }
    public char[][] getScreenData() {
        return screenData;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScreenBuffer that = (ScreenBuffer) o;
        return Arrays.deepEquals(screenData, that.screenData) &&
                Objects.equals(metaData, that.metaData);
    }

    @Override
    public int hashCode() {
        return Objects.hash(screenData, metaData);
    }
}
