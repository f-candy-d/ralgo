package d.candy.f.com.ralgo.domain.structure;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Comparator;

import d.candy.f.com.ralgo.utils.SortedArrayList;

/**
 * Created by daichi on 17/08/16.
 */

public class MergeableEvent extends Event {

    private static final long DEFAULT_EARLIEST_DATETIME = Long.MIN_VALUE;
    private static final long DEFAULT_LATEST_DATETIME = Long.MAX_VALUE;

    @NonNull private SortedArrayList<Event> mEvents;

    public MergeableEvent(@NonNull Event initialEvent, @NonNull Comparator<Event> comparator) {
        mEvents  = new SortedArrayList<>(comparator);
        mEvents.add(initialEvent);
        setEarliestDatetime(initialEvent.getStartDate());
        setLatestDatetime(initialEvent.getEndDate());
    }

    public long getEarliestDatetime() {
        return getStartDate();
    }

    public long getLatestDatetime() {
        return getEndDate();
    }

    public void setEarliestDatetime(long earliestDatetime) {
        setStartDate(earliestDatetime);
    }

    public void setLatestDatetime(long latestDatetime) {
        setEndDate(latestDatetime);
    }

    public void changeComparator(@NonNull Comparator<Event> comparator) {
        mEvents.changeComparator(comparator);
    }

    @NonNull
    public ArrayList<Event> getEvents() {
        return mEvents.asUnmodifiableArrayList();
    }

    public Event removeEvent(int index) {
        final Event removed = mEvents.remove(index);
        // Update dates
        if (mEvents.size() != 0) {
            setEarliestDatetime(findEarliestDatetime());
            setLatestDatetime(findLatestDatetime());

        } else {
            setEarliestDatetime(DEFAULT_EARLIEST_DATETIME);
            setLatestDatetime(DEFAULT_LATEST_DATETIME);
        }

        return removed;
    }

    public int getEventCount() {
        return mEvents.size();
    }

    public boolean mergeWith(@NonNull Event other) {
        if (isEventsMergeable(this, other)) {

            if (other instanceof MergeableEvent) {
                mEvents.addAll(((MergeableEvent) other).getEvents());
            } else {
                mEvents.add(other);
            }

            // Update dates
            final long earliest = getEarliestDatetime();
            final long latest = getLatestDatetime();
            final long start = other.getStartDate();
            final long end = other.getEndDate();
            setEarliestDatetime((earliest < start) ? earliest : start);
            setLatestDatetime((end < latest) ? latest  : earliest);

            return true;
        }

        // If merge failed, return false
        return false;
    }

    /**
     * Return true if possible to merge event2 into event1, false otherwise
     */
    private boolean isEventsMergeable(@NonNull Event event1, @NonNull Event event2) {
        final long start1 = event1.getStartDate();
        final long end1 = event1.getEndDate();
        final long start2 = event2.getStartDate();
        final long end2 = event2.getEndDate();

        return (start1 <= start2 && start2 < start1 ||
                end2 < end1 && end1 <= end2);
    }

    private long findEarliestDatetime() {
        if (mEvents.size() == 0) {
            return DEFAULT_EARLIEST_DATETIME;
        }

        long earliest = DEFAULT_LATEST_DATETIME;
        for (Event event : mEvents) {
            earliest = Math.min(earliest, event.getStartDate());
        }
        return earliest;
    }

    private long findLatestDatetime() {
        if (mEvents.size() == 0) {
            return DEFAULT_LATEST_DATETIME;
        }

        long latest = DEFAULT_EARLIEST_DATETIME;
        for (Event event : mEvents) {
            latest = Math.max(latest, event.getEndDate());
        }
        return latest;
    }
}
