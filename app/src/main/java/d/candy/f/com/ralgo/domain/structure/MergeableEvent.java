package d.candy.f.com.ralgo.domain.structure;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by daichi on 17/08/16.
 */

public class MergeableEvent extends Event {

    public enum EventSortOrder {
        BY_START_DATE,
        BY_END_DATE
    }

    private static final long DEFAULT_EARLIEST_DATE = Long.MIN_VALUE;
    private static final long DEFAULT_LATEST_DATE = Long.MAX_VALUE;

    @NonNull private ArrayList<Event> mEvents;
    @NonNull private EventSortOrder mSortOrder;

    public MergeableEvent(@NonNull EventSortOrder sortOrder) {
        this(null, sortOrder);
    }

    public MergeableEvent(Event initialEvent, @NonNull EventSortOrder sortOrder) {
        mSortOrder = sortOrder;
        mEvents  = new ArrayList<>();

        if (initialEvent != null) {
            mEvents.add(initialEvent);
            setEarliestDate(initialEvent.getStartDate());
            setLatestDate(initialEvent.getEndDate());
        }
    }

    public long getEarliestDate() {
        return getStartDate();
    }

    public long getLatestDate() {
        return getEndDate();
    }

    public void setEarliestDate(long earliestDate) {
        setStartDate(earliestDate);
    }

    public void setLatestDate(long latestDate) {
        setEndDate(latestDate);
    }

    @NonNull
    public EventSortOrder getSortOrder() {
        return mSortOrder;
    }

    public void setSortOrder(@NonNull EventSortOrder sortOrder) {
        mSortOrder = sortOrder;
    }

    @NonNull
    public ArrayList<Event> getEvents() {
        return (ArrayList<Event>) Collections.unmodifiableList(mEvents);
    }

    public Event removeEvent(int index) {
        final Event removed = mEvents.remove(index);
        // Update dates
        if (mEvents.size() != 0) {
            setEarliestDate(mEvents.get(0).getStartDate());
            setLatestDate(mEvents.get(mEvents.size() - 1).getEndDate());

        } else {
            setEarliestDate(DEFAULT_EARLIEST_DATE);
            setLatestDate(DEFAULT_LATEST_DATE);
        }

        return removed;
    }

    public int getEventCount() {
        return mEvents.size();
    }

    public Event mergeWith(@NonNull Event other) {
        final long earliest = getEarliestDate();
        final long latest = getLatestDate();
        final long start = other.getStartDate();
        final long end = other.getEndDate();

        if (earliest <= start && start < latest ||
                earliest < end && end <= latest) {

            if (other instanceof MergeableEvent) {
                mEvents.addAll(((MergeableEvent) other).getEvents());
            } else {
                mEvents.add(other);
            }

            sortInAsendingOrder();
            // Update dates
            setEarliestDate((earliest < start) ? earliest : start);
            setLatestDate((end < latest) ? latest  : earliest);

            return null;
        }

        // If merge failed, return again
        return other;
    }

    public void sortInAsendingOrder() {
        final Comparator<Event> eventComparator =
                new Comparator<Event>() {
                    @Override
                    public int compare(Event eve1, Event eve2) {
                        final long arg1, arg2;
                        if (mSortOrder == EventSortOrder.BY_START_DATE) {
                            arg1 = eve1.getStartDate();
                            arg2 = eve2.getStartDate();
                        } else {
                            arg1 = eve1.getEndDate();
                            arg2 = eve2.getEndDate();
                        }

                        if (arg1 < arg2) {
                            return -1;
                        } else if (arg2 > arg1) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                };

        Collections.sort(mEvents, eventComparator);
    }
}
