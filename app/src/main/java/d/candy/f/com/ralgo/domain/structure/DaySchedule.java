package d.candy.f.com.ralgo.domain.structure;

import android.support.annotation.NonNull;

import java.util.ArrayList;

/**
 * Created by daichi on 8/16/17.
 */

public class DaySchedule {

    @NonNull private ArrayList<Event> mEvents;
    private long mDate;

    public DaySchedule() {
        mEvents = new ArrayList<>();
    }
}
