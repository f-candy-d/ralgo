package d.candy.f.com.ralgo.data_store.sql_database;

import android.support.annotation.NonNull;

import d.candy.f.com.ralgo.domain.structure.Event;
import d.candy.f.com.ralgo.infra.sqlite.SqliteTableUtils;
import d.candy.f.com.ralgo.infra.sqlite.SqliteDataType;

/**
 * Created by daichi on 8/15/17.
 *
 * NOTICE : When add/remove any columns, edit {@link EntryContractHelper#getEventEntryColumns()}.
 */

public class EventEntryContract {

    public static final String TABLE_NAME = "event";
    private static final String PREFIX = "eve_";

    public static final String COL_ID = PREFIX + "id";
    public static final String COL_CONTENT_THING_ID = PREFIX + "content_thing_id";
    public static final String COL_START_DATE = PREFIX + "start_date";
    public static final String COL_END_DATE = PREFIX + "end_data";
    public static final String COL_REPETITION = PREFIX + "repetition";
    public static final String COL_NOTE = PREFIX + "note";
    
    public static SqliteTableUtils.TableSource getTableCreatorSourse() {
        return new SqliteTableUtils.TableSource(TABLE_NAME)
                .put(COL_ID, SqliteDataType.INTEGER_PK, false)
                .put(COL_CONTENT_THING_ID, SqliteDataType.INTEGER, false)
                .put(COL_START_DATE, SqliteDataType.INTEGER, false)
                .put(COL_END_DATE, SqliteDataType.INTEGER, false)
                .put(COL_REPETITION, SqliteDataType.INTEGER, false)
                .put(COL_NOTE, SqliteDataType.TEXT, true);
    }

    /**
     * When add/remove any columns, consider editing this method
     */
    public static boolean isEventValid
    (long id, long contentThingId, long startDate, long endDate, Event.Repetition repetition) {

        return (id != DbContract.NULL_ID &&
                isEventValid(contentThingId, startDate, endDate, repetition));
    }

    public static boolean isEventValid
            (long contentThingId, long startDate, long endDate, Event.Repetition repetition) {

        return (contentThingId != DbContract.NULL_ID &&
                startDate <= endDate &&
                repetition != null);
    }

}