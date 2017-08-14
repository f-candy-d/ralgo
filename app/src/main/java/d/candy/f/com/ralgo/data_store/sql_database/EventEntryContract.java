package d.candy.f.com.ralgo.data_store.sql_database;

import d.candy.f.com.ralgo.infra.sqlite.SqlTableCreator;
import d.candy.f.com.ralgo.infra.sqlite.SqliteDataType;

/**
 * Created by daichi on 8/15/17.
 */

public class EventEntryContract {

    public static final String TABLE_NAME = "event";
    private static final String PREFIX = "eve_";

    public static final String COL_ID = PREFIX + "id";
    public static final String COL_THING_ID = PREFIX + "thing_id";
    public static final String COL_START_DATE = PREFIX + "start_date";
    public static final String COL_END_DATE = PREFIX + "end_data";
    public static final String COL_REPETITION = PREFIX + "repetition";
    public static final String COL_NOTE = PREFIX + "note";
    
    public static SqlTableCreator.Source getTableCreatorSourse() {
        return new SqlTableCreator.Source(TABLE_NAME)
                .put(COL_ID, SqliteDataType.INTEGER_PK, false)
                .put(COL_THING_ID, SqliteDataType.INTEGER, false)
                .put(COL_START_DATE, SqliteDataType.INTEGER, false)
                .put(COL_END_DATE, SqliteDataType.INTEGER, false)
                .put(COL_REPETITION, SqliteDataType.INTEGER, false)
                .put(COL_NOTE, SqliteDataType.TEXT, true);
    }
}
