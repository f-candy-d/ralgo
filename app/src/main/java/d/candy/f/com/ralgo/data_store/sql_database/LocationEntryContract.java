package d.candy.f.com.ralgo.data_store.sql_database;

import android.support.annotation.NonNull;

import d.candy.f.com.ralgo.domain.structure.Location;
import d.candy.f.com.ralgo.infra.sqlite.SqliteTableUtils;
import d.candy.f.com.ralgo.infra.sqlite.SqliteDataType;

/**
 * Created by daichi on 8/15/17.
 */

public class LocationEntryContract {

    public static final String TABLE_NAME = "location";
    private static final String PREFIX = "loc_";

    public static final String COL_ID = PREFIX + "id";
    public static final String COL_NAME = PREFIX + "name";
    public static final String COL_NOTE = PREFIX + "note";

    public static SqliteTableUtils.TableSource getTableCreatorSourse() {
        return new SqliteTableUtils.TableSource(TABLE_NAME)
                .put(COL_ID, SqliteDataType.INTEGER_PK, false)
                .put(COL_NAME, SqliteDataType.TEXT, false)
                .put(COL_NOTE, SqliteDataType.TEXT, true);
    }

    /**
     * region; Dat validation checks
     */

    public static boolean isLocationValid(long id, String name) {
        return (id != DbContract.NULL_ID && isLocationValid(name));
    }

    public static boolean isLocationValid(String name) {
        return (name != null);
    }
}
