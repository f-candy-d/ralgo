package d.candy.f.com.ralgo.domain.structure;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by daichi on 8/16/17.
 */

public class DaySchedule {

    private static final long ILLEGAL_DATE = -1;

    @NonNull private ArrayList<Event> mEvents;
    private long mDate = ILLEGAL_DATE;

    public DaySchedule() {
        mEvents = new ArrayList<>();
    }

    public long getDate() {
        return mDate;
    }

    public void setDate(long date) {
        mDate = date;
    }

    public Calendar getDateAsCalendar() {
        if (mDate != ILLEGAL_DATE) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(mDate);
            return calendar;
        }

        throw new IllegalStateException("Plese set date!");
    }
}
