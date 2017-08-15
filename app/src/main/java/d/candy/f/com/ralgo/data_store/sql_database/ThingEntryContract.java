package d.candy.f.com.ralgo.data_store.sql_database;

import d.candy.f.com.ralgo.infra.sqlite.SqliteTableUtils;
import d.candy.f.com.ralgo.infra.sqlite.SqliteDataType;

/**
 * Created by daichi on 8/15/17.
 */

public class ThingEntryContract {

    public static final String TABLE_NAME = "thing";
    private static final String PREFIX = "tng_";

    public static final String COL_ID = PREFIX + "id";
    public static final String COL_EMBODIER_ID = PREFIX + "embodier_id";
    public static final String COL_TABLE_OF_EMBODIER = PREFIX + "table_of_embodier";

    public static SqliteTableUtils.TableSource getTableCreatorSourse() {
        return new SqliteTableUtils.TableSource(TABLE_NAME)
                .put(COL_ID, SqliteDataType.INTEGER_PK, false)
                .put(COL_EMBODIER_ID, SqliteDataType.INTEGER, false)
                .put(COL_TABLE_OF_EMBODIER, SqliteDataType.TEXT, false);
    }
}
