package d.candy.f.com.ralgo.domain.structure;

import d.candy.f.com.ralgo.data_store.sql_database.DbContract;
import d.candy.f.com.ralgo.data_store.sql_database.LocationEntryContract;

/**
 * Created by daichi on 17/08/16.
 */

public class Location extends Thing {

    public static final String DEFAULT_NAME = null;
    public static final String DEFAULT_NOTE = null;

    private long mId;
    private String mName;
    private String mNote;

    public Location(long id, String name, String note) {
        super(DbContract.NULL_ID, id, LocationEntryContract.TABLE_NAME);
        mId = id;
        mName = name;
        mNote = note;
    }

    public Location() {
        this(DbContract.NULL_ID, DEFAULT_NAME, DEFAULT_NOTE);
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getNote() {
        return mNote;
    }

    public void setNote(String note) {
        mNote = note;
    }
}
