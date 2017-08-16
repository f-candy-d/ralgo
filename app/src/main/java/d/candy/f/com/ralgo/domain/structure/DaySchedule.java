package d.candy.f.com.ralgo.domain.structure;

import android.support.annotation.NonNull;

import java.util.Calendar;
import java.util.Comparator;

import d.candy.f.com.ralgo.utils.SortedArrayList;

/**
 * Created by daichi on 8/16/17.
 */

public class DaySchedule {

    private final static Comparator<Event> EVENT_COMPARATOR =
            new Comparator<Event>() {
                @Override
                public int compare(Event event, Event t1) {
                    if (event.getStartDatetime() < t1.getStartDatetime()) {
                        return -1;
                    } else if (event.getStartDatetime() > t1.getStartDatetime()) {
                        return 1;
                    } else {
                        return 0;
                    }
                }
            };

    @NonNull private SortedArrayList<Event> mEvents;
    private long mDate;

    public DaySchedule(long date) {
        mEvents = new SortedArrayList<>(EVENT_COMPARATOR);
    }

    public long getDate() {
        return mDate;
    }

    public void changeDate(long date) {
        if (mDate != date) {
            mEvents.clear();
        }
        mDate = date;
    }

    public Calendar getDateAsCalendar() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(mDate);
        return calendar;
    }

    public boolean addEvent(@NonNull Event event) {
        if (isEventDatetimeValid(event)) {
            mEvents.add(event);
            return true;
        }
        return false;
    }

    public Event removeEvent(int index) {
        return mEvents.remove(index);
    }

    private boolean isEventDatetimeValid(Event event) {
        if (event == null) {
            return false;
        }

        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(mDate);
        setCalendarTimeZero(date);

        Calendar eventDate = Calendar.getInstance();
        eventDate.setTimeInMillis(event.getStartDatetime());
        setCalendarTimeZero(eventDate);

        if (date.compareTo(eventDate) != 0) {
            return false;
        }

        eventDate.setTimeInMillis(event.getEndDatetime());
        setCalendarTimeZero(eventDate);

        return (date.compareTo(eventDate) == 0);
    }

    public void mergeAllBlocksIfPossible() {
        Event iEvent;
        Event jEvent;
        MergeableEvent mergeableEvent;

        for (int i = mEvents.size() - 1; 0 <= i; --i) {
            iEvent = mEvents.get(i);
            for (int j = i - 1; 0 <= j; --j) {
                jEvent = mEvents.get(j);

                if (jEvent instanceof MergeableEvent) {
                    if (((MergeableEvent) jEvent).mergeWith(iEvent)) {
                        mEvents.remove(i);
                        break;
                    }

                } else {
                    mergeableEvent = new MergeableEvent(jEvent, EVENT_COMPARATOR);
                    if (mergeableEvent.mergeWith(iEvent)) {
                        mEvents.set(j, mergeableEvent);
                        mEvents.remove(i);
                        break;
                    }
                }
            }
        }
    }

    private void setCalendarTimeZero(@NonNull Calendar calendar) {
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.MILLISECOND, 0);
    }
}
