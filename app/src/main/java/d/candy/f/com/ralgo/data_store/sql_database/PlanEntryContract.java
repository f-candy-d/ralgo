package d.candy.f.com.ralgo.data_store.sql_database;

import d.candy.f.com.ralgo.infra.sqlite.SqlTableCreator;
import d.candy.f.com.ralgo.infra.sqlite.SqliteDataType;

/**
 * Created by daichi on 8/15/17.
 */

public class PlanEntryContract {

    public static final String TABLE_NAME = "plan";
    private static final String PREFIX = "pl_";

    public static final String COL_ID = PREFIX + "id";
    public static final String COL_NAME = PREFIX + "name";
    public static final String COL_NOTE = PREFIX + "note";

    public static SqlTableCreator.Source getTableCreatorSourse() {
        return new SqlTableCreator.Source(TABLE_NAME)
                .put(COL_ID, SqliteDataType.INTEGER_PK, false)
                .put(COL_NAME, SqliteDataType.TEXT, false)
                .put(COL_NOTE, SqliteDataType.TEXT, true);
    }
}
