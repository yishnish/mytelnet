package terminal;

public class UTCTimePiece implements TimePiece {
    public long getTimeMillis() {
        return System.currentTimeMillis();
    }
}
