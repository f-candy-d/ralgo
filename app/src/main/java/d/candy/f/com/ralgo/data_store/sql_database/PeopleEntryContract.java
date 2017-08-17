package d.candy.f.com.ralgo.data_store.sql_database;

import android.support.annotation.NonNull;

import d.candy.f.com.ralgo.domain.structure.People;
import d.candy.f.com.ralgo.infra.sqlite.SqliteTableUtils;
import d.candy.f.com.ralgo.infra.sqlite.SqliteDataType;

/**
 * Created by daichi on 8/15/17.
 */

public class PeopleEntryContract {

    public static final String TABLE_NAME = "people";
    private static final String PREFIX = "pple_";

    public static final String COL_ID = PREFIX + "id";
    public static final String COL_NAME = PREFIX + "name";
    public static final String COL_NOTE = PREFIX + "note";
    public static final String COL_MAIL = PREFIX + "mail";
    public static final String COL_TEL = PREFIX + "tel";

    public static SqliteTableUtils.TableSource getTableCreatorSourse() {
        return new SqliteTableUtils.TableSource(TABLE_NAME)
                .put(COL_ID, SqliteDataType.INTEGER_PK, false)
                .put(COL_NAME, SqliteDataType.TEXT, false)
                .put(COL_NOTE, SqliteDataType.TEXT, true)
                .put(COL_MAIL, SqliteDataType.TEXT, true)
                .put(COL_TEL, SqliteDataType.TEXT, true);
    }

    /**
     * Data validation checks
     */

    public static boolean isPeopleValid(long id, String name) {
        return (id != DbContract.NULL_ID && isPeopleValid(name));
    }

    public static boolean isPeopleValid(String name) {
        return (name != null);
    }
}
