package d.candy.f.com.ralgo.data_store.sql_database;

import android.support.annotation.NonNull;

import d.candy.f.com.ralgo.infra.sqlite.SqliteTableUtils;

/**
 * Created by daichi on 8/15/17.
 */

abstract public class DbContract {

    public static final String DATABASE_NAME = "ralgo_contents_database";
    public static final int DATABASE_VERSION = 1;
    public static final long NULL_ENTRY_ID = -1;

    @NonNull
    static SqliteTableUtils.TableSource[] getTableSourses() {
        return new SqliteTableUtils.TableSource[] {
                EventEntryContract.getTableCreatorSourse(),
                LocationEntryContract.getTableCreatorSourse(),
                PeopleEntryContract.getTableCreatorSourse(),
                PlanEntryContract.getTableCreatorSourse(),
                ThingEntryContract.getTableCreatorSourse(),
                ThingsJunctionEntryContract.getTableCreatorSourse()
        };

    }
}