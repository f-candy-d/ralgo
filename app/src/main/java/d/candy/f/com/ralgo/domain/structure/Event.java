package d.candy.f.com.ralgo.domain.structure;

import d.candy.f.com.ralgo.data_store.sql_database.DbContract;
import d.candy.f.com.ralgo.data_store.sql_database.EventEntryContract;
import d.candy.f.com.ralgo.utils.Quantizable;

/**
 * Created by daichi on 17/08/16.
 */

public class Event extends Thing {

    public static final long DEFAULT_START_DATE = -1;
    public static final long DEFAULT_END_DATE = DEFAULT_START_DATE - 1;
    public static final Event.Repetition DEFAULT_REPETITION = null;
    public static final String DEFAULT_NOTE = null;

    public enum Repetition implements Quantizable {
        EVERYDAY {@Override public int quantize() { return 0; }},
        WEEKLY {@Override public int quantize() { return 1; }},
        ONE_DAY {@Override public int quantize() { return 2; }},
        CUSTOM {@Override public int quantize() { return 3;}}
    }

    private long mId;
    private long mContentThingId;
    private long mStartDatetime;
    private long mEndDatetime;
    private String mNote;
    private Repetition mRepetition;

    public Event() {
        super(DbContract.NULL_ID, DbContract.NULL_ID, EventEntryContract.TABLE_NAME);
        mId = DbContract.NULL_ID;
        mContentThingId = DbContract.NULL_ID;
        mStartDatetime = DEFAULT_START_DATE;
        mEndDatetime = DEFAULT_END_DATE;
        mNote = DEFAULT_NOTE;
        mRepetition = DEFAULT_REPETITION;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public long getContentThingId() {
        return mContentThingId;
    }

    public void setContentThingId(long contentThingId) {
        mContentThingId = contentThingId;
    }

    public long getStartDatetime() {
        return mStartDatetime;
    }

    public void setStartDatetime(long startDatetime) {
        mStartDatetime = startDatetime;
    }

    public long getEndDatetime() {
        return mEndDatetime;
    }

    public void setEndDatetime(long endDatetime) {
        mEndDatetime = endDatetime;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String note) {
        mNote = note;
    }

    public Repetition getRepetition() {
        return mRepetition;
    }

    public void setRepetition(Repetition repetition) {
        mRepetition = repetition;
    }
}
