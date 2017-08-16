package d.candy.f.com.ralgo.domain.structure;

import d.candy.f.com.ralgo.utils.Quantizable;

/**
 * Created by daichi on 17/08/16.
 */

public class Event extends Thing {

    public enum Repetition implements Quantizable {
        EVERYDAY {@Override public int quantize() { return 0; }},
        WEEKLY {@Override public int quantize() { return 1; }},
        ONE_DAY {@Override public int quantize() { return 2; }},
        CUSTOM {@Override public int quantize() { return 3;}}
    }


    private long mId;
    private long mContentThingId;
    private long mStartDate;
    private long mEndDate;
    private String mNote;

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

    public long getStartDate() {
        return mStartDate;
    }

    public void setStartDate(long startDate) {
        mStartDate = startDate;
    }

    public long getEndDate() {
        return mEndDate;
    }

    public void setEndDate(long endDate) {
        mEndDate = endDate;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String note) {
        mNote = note;
    }
}
