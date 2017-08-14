package d.candy.f.com.ralgo.data_store.sql_database;

import d.candy.f.com.ralgo.infra.sqlite.SqlTableCreator;
import d.candy.f.com.ralgo.infra.sqlite.SqliteDataType;

/**
 * Created by daichi on 8/15/17.
 */

public class ThingsJunctionEntryContract {

    public static final String TABLE_NAME = "thing_thing";
    private static final String PREFIX = "tngtng_";

    public static final String COL_ID = PREFIX + "id";
    public static final String COL_ONE_THING_ID = PREFIX + "one_thing_id";
    public static final String COL_OTHER_THING_ID = PREFIX + "other_thing_id";

    public static SqlTableCreator.Source getTableCreatorSourse() {
        return new SqlTableCreator.Source(TABLE_NAME)
                .put(COL_ID, SqliteDataType.INTEGER_PK, false)
                .put(COL_ONE_THING_ID, SqliteDataType.INTEGER, false)
                .put(COL_OTHER_THING_ID, SqliteDataType.INTEGER, false);
    }
}
