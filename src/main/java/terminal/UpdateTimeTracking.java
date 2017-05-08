package terminal;

public interface UpdateTimeTracking {

    void setLastUpdated();

    long getLastUpdateTime();

    long unchangedFor();
}
